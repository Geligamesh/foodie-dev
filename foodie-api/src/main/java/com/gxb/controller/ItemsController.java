package com.gxb.controller;

import com.gxb.pojo.*;
import com.gxb.pojo.vo.CommentLevelCountsVO;
import com.gxb.pojo.vo.ItemInfoVO;
import com.gxb.service.ItemService;
import com.gxb.utils.JSONResult;
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
public class ItemsController {

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
}
