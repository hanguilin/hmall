package com.pig4cloud.pig.goods.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.pig4cloud.pig.common.core.util.R;
import com.pig4cloud.pig.common.security.annotation.Inner;
import com.pig4cloud.pig.goods.dto.SearchItem;
import com.pig4cloud.pig.goods.entity.TbItem;
import com.pig4cloud.pig.goods.entity.TbPanel;
import com.pig4cloud.pig.goods.entity.TbPanelContent;
import com.pig4cloud.pig.goods.service.GoodsService;
import com.pig4cloud.pig.goods.vo.Product;
import com.pig4cloud.pig.goods.vo.ProductDet;
import com.pig4cloud.pig.search.dto.SearchResult;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * 物资接口
 *
 * @author yl
 */
@Inner(value = false)
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class GoodsController {

	/**
	 * 物资业务类
	 */
	private final GoodsService goodsService;

	/**
	 * 获取首页物资
	 *
	 * @return R<List < TbPanel>>
	 */
	@GetMapping("/home")
	public R<List<TbPanel>> getProductHome() {
		return R.ok(goodsService.getProductHome());
	}

	/**
	 * 获取导航栏
	 *
	 * @return R<List < TbPanelContent>>
	 */
	@GetMapping("/navList")
	public R<List<TbPanelContent>> getNavList() {
		return R.ok(goodsService.getNavList());
	}

	/**
	 * 获取商品详情
	 *
	 * @param productId 商品id
	 * @return R<ProductDet>
	 */
	@GetMapping("/detail")
	public R<ProductDet> getDetail(@RequestParam("productId") Long productId) {
		return R.ok(goodsService.getDetail(productId));
	}

	/**
	 * 分页获取商品
	 *
	 * @param page 分页对象
	 * @param tbItem 过滤参数
	 * @return R<Page<Product>>
	 */
	@GetMapping("/allGoods")
	public R<Page<Product>> getAllGoods(Page<TbItem> page, TbItem tbItem) {
		return R.ok(goodsService.getAllGoods(page, tbItem));
	}

	/**
	 * 根据关键词快速搜索
	 *
	 * @param key 关键词
	 * @return R<List<Map<String, Object>>> 商品信息
	IOException
	 */
	@GetMapping("/quickSearch")
	public R<List<Map<String, Object>>> quickSearch(@RequestParam(defaultValue = "") String key) throws IOException {
		return goodsService.quickSearch(key);
	}

	/**
	 * 根据关键词快速搜索
	 *
	 * @param key 关键词
	 * @return R<List<Map<String, Object>>> 商品信息
	IOException
	 */
	@GetMapping("/search")
	public R<SearchResult<SearchItem>> search(@RequestParam(defaultValue = "") String key,
											  @RequestParam(defaultValue = "1") int page,
											  @RequestParam(defaultValue = "20") int size,
											  @RequestParam(defaultValue = "") String sort,
											  @RequestParam(defaultValue = "-1") BigDecimal priceStart,
											  @RequestParam(defaultValue = "-1") BigDecimal priceEnd) throws IOException {
		return goodsService.search(key,page,size,sort,priceStart,priceEnd);
	}

	/**
	 * 商品推荐板块
	 * @return R<List<TbPanel>>
	 */
	@GetMapping("/recommend")
	@ApiOperation(value = "商品推荐板块", notes = "商品推荐板块")
	public R<List<TbPanel>> getRecommendGoods(){
		return R.ok(goodsService.getRecommendGoods());
	}
}
