package com.pig4cloud.pig.goods.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.base.Splitter;
import com.google.common.collect.Maps;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.goods.dto.SearchItem;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.entity.TbPanel;
import com.pig4cloud.pig.goods.entity.TbPanelContent;
import com.pig4cloud.pig.goods.service.GoodsService;
import com.pig4cloud.pig.goods.service.TbItemService;
import com.pig4cloud.pig.goods.service.TbPanelContentService;
import com.pig4cloud.pig.goods.service.TbPanelService;
import com.pig4cloud.pig.goods.vo.Product;
import com.pig4cloud.pig.goods.vo.ProductDet;
import com.pig4cloud.pig.search.dto.SearchDto;
import com.pig4cloud.pig.search.dto.SearchResult;
import com.pig4cloud.pig.search.feign.RemoteSearchService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 物资业务类
 *
 * @author hanguilin
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GoodsServiceImpl implements GoodsService {

	private static final String RECOMMEND_PANEL = "RECOMMEND_PANEL";

	private static final Integer RECOMMEND_PANEL_ID = 0;

	private final TbPanelService tbPanelService;

	private final TbPanelContentService tbPanelContentService;

	private final TbItemService tbItemService;

	private final RemoteSearchService remoteSearchService;

	private final RedisTemplate<String, String> redisTemplate;

	@Override
	public List<TbPanel> getProductHome() {
		LambdaQueryWrapper<TbPanel> panelQueryWrapper = Wrappers.lambdaQuery(TbPanel.class);
		panelQueryWrapper.eq(TbPanel::getPosition, 0);
		panelQueryWrapper.eq(TbPanel::getStatus, 1);
		panelQueryWrapper.orderByAsc(TbPanel::getSortOrder);
		List<TbPanel> panelList = tbPanelService.list(panelQueryWrapper);
		panelList.forEach(panel -> {
			LambdaQueryWrapper<TbPanelContent> panelContentQueryWrapper = Wrappers.lambdaQuery(TbPanelContent.class);
			panelContentQueryWrapper.orderByAsc(TbPanelContent::getSortOrder);
			panelContentQueryWrapper.eq(TbPanelContent::getPanelId, panel.getId());
			List<TbPanelContent> panelContentList = tbPanelContentService.list(panelContentQueryWrapper);
			panelContentList.stream().filter(content -> content.getProductId() != null).forEach(content -> {
				TbItem item = tbItemService.getById(content.getProductId());
				content.setProductName(item.getTitle());
				content.setSalePrice(item.getPrice());
				content.setSubTitle(item.getSellPoint());
			});
			panel.setPanelContents(panelContentList);
		});
		return panelList;
	}

	/**
	 * 获取导航栏
	 *
	 * @return List<TbPanelContent>
	 */
	@Override
	public List<TbPanelContent> getNavList() {
		LambdaQueryWrapper<TbPanelContent> tbPanelContentLambdaQueryWrapper = Wrappers.lambdaQuery(TbPanelContent.class);
		tbPanelContentLambdaQueryWrapper.eq(TbPanelContent::getPanelId, 0);
		return tbPanelContentService.list(tbPanelContentLambdaQueryWrapper);
	}

	@Override
	public ProductDet getDetail(Long productId) {
		TbItem tbItem = tbItemService.getById(productId);
		ProductDet productDet = new ProductDet();
		productDet.setProductId(productId);
		productDet.setProductName(tbItem.getTitle());
		productDet.setSubTitle(tbItem.getSellPoint());
		Integer limitNum = tbItem.getLimitNum();
		if (limitNum != null) {
			productDet.setLimitNum(Long.valueOf(limitNum));
		} else {
			productDet.setLimitNum(Long.valueOf(tbItem.getNum()));
		}
		productDet.setSalePrice(tbItem.getPrice());
		productDet.setDetail(tbItem.getDescription());
		String images = tbItem.getImage();
		if (StringUtils.isNotBlank(images)) {
			List<String> imageList = Splitter.on(",").splitToList(images);
			productDet.setProductImageBig(imageList.get(0));
			productDet.setProductImageSmall(imageList);
		}
		return productDet;
	}

	@Override
	public Page<Product> getAllGoods(Page<TbItem> page, TbItem tbItem) {
		LambdaQueryWrapper<TbItem> tbItemLambdaQueryWrapper = Wrappers.lambdaQuery(tbItem);
		Integer sortFilter = tbItem.getSortFilter();
		if (sortFilter != null) {
			// 价格降序
			if (sortFilter.equals(-1)) {
				tbItemLambdaQueryWrapper.orderByDesc(TbItem::getPrice);
			} else {
				// 价格升序
				tbItemLambdaQueryWrapper.orderByAsc(TbItem::getPrice);
			}
		} else {
			// 创建时间降序
			tbItemLambdaQueryWrapper.orderByDesc(TbItem::getCreated);
		}

		tbItemLambdaQueryWrapper.between(tbItem.getPriceEnd() != null && tbItem.getPriceStart() != null, TbItem::getPrice, tbItem.getPriceStart(), tbItem.getPriceEnd());
		Page<TbItem> pageResult = tbItemService.page(page, tbItemLambdaQueryWrapper);
		List<TbItem> records = pageResult.getRecords();
		List<Product> result = records.stream().map(o -> convertProduct(o)).collect(Collectors.toList());
		Page<Product> productPage = new Page<>();
		productPage.setRecords(result);
		productPage.setTotal(pageResult.getTotal());
		return productPage;
	}

	/**
	 * 根据关键词快速搜索
	 *
	 * @param key 关键词
	 * @return R<List < Map < String, Object>>> 商品信息
	 * @throws IOException
	 */
	@Override
	public R<List<Map<String, Object>>> quickSearch(String key) throws IOException {
		SearchDto searchDto = new SearchDto();
		HashMap<String, Object> filter = Maps.newHashMap();
		filter.put("title", key);
		searchDto.setFilter(filter);
		searchDto.setIndex("item");
		searchDto.setFields("title");
		searchDto.setPage(1);
		searchDto.setSize(5);
		R<SearchResult<Map<String, Object>>> list = remoteSearchService.getList(searchDto);
		return R.ok(list.getData().getRecords());
	}

	/**
	 * 分页搜索
	 *
	 * @param key        关键词
	 * @param page       当前页
	 * @param size       分页大小
	 * @param sort       排序
	 * @param priceStart 价格-开始
	 * @param priceEnd   价格-结束
	 * @return R<SearchResult < SearchItem>>
	 */
	@Override
	public R<SearchResult<SearchItem>> search(String key, int page, int size, String sort, BigDecimal priceStart, BigDecimal priceEnd) throws IOException {
		SearchDto searchDto = new SearchDto();
		HashMap<String, Object> filter = Maps.newHashMap();
		filter.put("title", key);
		searchDto.setFilter(filter);
		searchDto.setIndex("item");
		searchDto.setPage(page);
		searchDto.setSize(size);
		searchDto.setHighlightField("title");
		R<SearchResult<Map<String, Object>>> rResult = remoteSearchService.getList(searchDto);
		SearchResult<Map<String, Object>> rSearchResult = rResult.getData();
		// 结果转换
		List<Map<String, Object>> records = rSearchResult.getRecords();
		SearchResult<SearchItem> convertResult = new SearchResult<>();
		if (records != null && !records.isEmpty()) {
			List<SearchItem> searchItemList = records.stream().map(o -> {
				SearchItem searchItem = new SearchItem();
				searchItem.setProductId(Long.valueOf(String.valueOf(o.get("id"))));
				searchItem.setProductName(String.valueOf(o.get("title")));
				searchItem.setProductImageBig(getImagesBig(String.valueOf(o.get("image"))));
				searchItem.setSalePrice(new BigDecimal(String.valueOf(o.get("price"))));
				return searchItem;
			}).collect(Collectors.toList());
			convertResult.setTotal(rSearchResult.getTotal());
			convertResult.setTotalPages(rSearchResult.getTotalPages());
			convertResult.setRecords(searchItemList);
		}
		return R.ok(convertResult);
	}

	/**
	 * 商品推荐板块
	 * @return List<TbPanel>
	 */
	@Override
	public List<TbPanel> getRecommendGoods() {
		//查询缓存
		try {
			//有缓存则读取
			String json = redisTemplate.opsForValue().get(RECOMMEND_PANEL);
			if (StringUtils.isNotBlank(json)) {
				return JSON.parseArray(json, TbPanel.class);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		List<TbPanel> list = getTbPanelAndContentsById(RECOMMEND_PANEL_ID);
		//把结果添加至缓存
		try {
			redisTemplate.opsForValue().set(RECOMMEND_PANEL, JSON.toJSONString(list));
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return list;
	}

	private List<TbPanel> getTbPanelAndContentsById(Integer panelId) {
		LambdaQueryWrapper<TbPanel> tbPanelLambdaQueryWrapper = Wrappers.lambdaQuery(TbPanel.class);
		tbPanelLambdaQueryWrapper.eq(TbPanel::getId, panelId);
		tbPanelLambdaQueryWrapper.eq(TbPanel::getStatus, 1);
		List<TbPanel> list = tbPanelService.list(tbPanelLambdaQueryWrapper);
		for (TbPanel tbPanel : list) {
			LambdaQueryWrapper<TbPanelContent> tbPanelContentLambdaQueryWrapper = Wrappers.lambdaQuery(TbPanelContent.class);
			tbPanelContentLambdaQueryWrapper.eq(TbPanelContent::getPanelId, tbPanel.getId());
			tbPanelContentLambdaQueryWrapper.orderByAsc(TbPanelContent::getSortOrder);
			List<TbPanelContent> contentList = tbPanelContentService.list(tbPanelContentLambdaQueryWrapper);
			for (TbPanelContent content : contentList) {
				if (content.getProductId() != null) {
					TbItem tbItem = tbItemService.getById(content.getProductId());
					content.setProductName(tbItem.getTitle());
					content.setSalePrice(tbItem.getPrice());
					content.setSubTitle(tbItem.getSellPoint());
				}
			}
			tbPanel.setPanelContents(contentList);
		}
		return list;
	}

	private Product convertProduct(TbItem tbItem) {
		Product product = new Product();
		product.setProductId(tbItem.getId());
		product.setProductName(tbItem.getTitle());
		product.setSalePrice(tbItem.getPrice());
		product.setSubTitle(tbItem.getSellPoint());
		product.setProductImageBig(getImagesBig(tbItem.getImage()));
		return product;
	}

	private String getImagesBig(String image) {
		if (StringUtils.isNotBlank(image)) {
			List<String> imageList = Splitter.on(",").splitToList(image);
			return imageList.get(0);
		}
		return null;
	}
}
