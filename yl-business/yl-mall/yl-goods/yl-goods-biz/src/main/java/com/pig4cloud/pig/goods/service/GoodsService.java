package com.pig4cloud.pig.goods.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.goods.dto.SearchItem;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.entity.TbPanel;
import com.pig4cloud.pig.goods.entity.TbPanelContent;
import com.pig4cloud.pig.goods.vo.Product;
import com.pig4cloud.pig.goods.vo.ProductDet;
import com.pig4cloud.pig.search.dto.SearchResult;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface GoodsService {

	/**
	 * 获取首页物资
	 *
	 * @return List<TbPanel>
	 */
	List<TbPanel> getProductHome();

	/**
	 * 获取导航栏
	 *
	 * @return List<TbPanelContent>
	 */
	List<TbPanelContent> getNavList();

	/**
	 * 获取商品详情
	 *
	 * @param productId 商品id
	 * @return R<ProductDet>
	 */
	ProductDet getDetail(Long productId);

	/**
	 * 分页获取商品
	 *
	 * @param page   分页对象
	 * @param tbItem 过滤参数
	 * @return R<Page < Product>>
	 */
	Page<Product> getAllGoods(Page<TbItem> page, TbItem tbItem);

	/**
	 * 根据关键词快速搜索
	 *
	 * @param key 关键词
	 * @return R<List < Map < String, Object>>> 商品信息
	 * @throws IOException
	 */
	R<List<Map<String, Object>>> quickSearch(String key) throws IOException;

	/**
	 * 分页搜索
	 *
	 * @param key 关键词
	 * @param page 当前页
	 * @param size 分页大小
	 * @param sort 排序
	 * @param priceStart 价格-开始
	 * @param priceEnd 价格-结束
	 * @return R<SearchResult<SearchItem>>
	 */
	R<SearchResult<SearchItem>> search(String key, int page, int size, String sort, BigDecimal priceStart, BigDecimal priceEnd) throws IOException;

	/**
	 * 商品推荐板块
	 * @return List<TbPanel>
	 */
	List<TbPanel> getRecommendGoods();
}
