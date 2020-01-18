package com.gxb.controller;

import com.gxb.pojo.*;
import com.gxb.pojo.vo.CommentLevelCountsVO;
import com.gxb.pojo.vo.ItemInfoVO;
import com.gxb.pojo.vo.ShopcartVO;
import com.gxb.service.ItemService;
import com.gxb.utils.JSONResult;
import com.gxb.utils.PagedGridResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("items")
@Api(value = "商品接口",tags = {"商品信息展示的相关接口"})
public class ItemsController extends BaseController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("info/{itemId}")
    public JSONResult info(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @PathVariable("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg( null);
        }
        Items items = itemService.queryItemById(itemId);
        List<ItemsImg> itemsImgs = itemService.queryItemImgList(itemId);
        ItemsParam itemsParam = itemService.queryItemParam(itemId);
        List<ItemsSpec> itemsSpecs = itemService.queryItemSpecList(itemId);

        ItemInfoVO itemInfoVO = new ItemInfoVO();
        itemInfoVO.setItem(items);
        itemInfoVO.setItemImgList(itemsImgs);
        itemInfoVO.setItemParams(itemsParam);
        itemInfoVO.setItemSpecList(itemsSpecs);
        return JSONResult.ok(itemInfoVO);
    }

    @ApiOperation(value = "查询商品评价等级",notes = "查询商品评价等级",httpMethod = "GET")
    @GetMapping("commentLevel")
    public JSONResult usernameIsExist(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam("itemId") String itemId) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg( null);
        }
        CommentLevelCountsVO commentLevelCountsVO = itemService.queryCommentCounts(itemId);
        return JSONResult.ok(commentLevelCountsVO);
     }

    @ApiOperation(value = "查询商品评价",notes = "查询商品评价",httpMethod = "GET")
    @GetMapping("comments")
    public JSONResult comments(
            @ApiParam(name = "itemId",value = "商品id",required = true)
            @RequestParam("itemId") String itemId,
            @ApiParam(name = "level",value = "评价等级")
            @RequestParam("level")Integer level,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
            @ApiParam(name = "pageSize",value = "分页的每一页显示的条数")
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize) {
        if (StringUtils.isBlank(itemId)) {
            return JSONResult.errorMsg( null);
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = COMMENT_PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.queryPagedComments(itemId, level, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "搜索商品列表",notes = "搜索商品列表",httpMethod = "GET")
    @GetMapping("search")
    public JSONResult search(
            @ApiParam(name = "keywords",value = "关键字",required = true)
            @RequestParam("keywords") String keywords,
            @ApiParam(name = "sort",value = "排序")
            @RequestParam("sort")String sort,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
            @ApiParam(name = "pageSize",value = "分页的每一页显示的条数")
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize) {
        if (StringUtils.isBlank(keywords)) {
            return JSONResult.errorMsg( null);
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItems(keywords, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }

    @ApiOperation(value = "通过分类id搜索商品列表",notes = "通过分类id搜索商品列表",httpMethod = "GET")
    @GetMapping("catItems")
    public JSONResult searchByCatId(
            @ApiParam(name = "catId",value = "三级分类id",required = true)
            @RequestParam("catId") Integer catId,
            @ApiParam(name = "sort",value = "排序")
            @RequestParam("sort")String sort,
            @ApiParam(name = "page",value = "查询下一页的第几页")
            @RequestParam(value = "page",required = false,defaultValue = "1")Integer page,
            @ApiParam(name = "pageSize",value = "分页的每一页显示的条数")
            @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize) {
        if (catId == null) {
            return JSONResult.errorMsg("分类id不能为空");
        }
        if (page == null || page <= 0) {
            page = 1;
        }
        if (pageSize == null || pageSize <= 0) {
            pageSize = PAGE_SIZE;
        }
        PagedGridResult pagedGridResult = itemService.searchItemsByThirdCatId(catId, sort, page, pageSize);
        return JSONResult.ok(pagedGridResult);
    }


    //用于用户长时间未登录网站，刷新购物车中的数据（主要是商品价格），类似淘宝京东
    @ApiOperation(value = "根据商品规格ids查找最新的商品数据",notes = "根据商品规格ids查找最新的商品数据",tags = {"根据商品规格ids查找最新的商品数据"},httpMethod = "POST")
    @GetMapping("refresh")
    public JSONResult add(@RequestParam("itemSpecIds")
                          @ApiParam(name = "itemSpecIds",value = "拼接的规格ids",required = true,example = "1001,1002,1005") String itemSpecIds) {
        if (StringUtils.isBlank(itemSpecIds)) {
            return JSONResult.ok();
        }
        List<ShopcartVO> shopcartVOS = itemService.queryItemsBySpecIds(itemSpecIds);
        return JSONResult.ok(shopcartVOS);
    }

}
