package com.gxb.controller;

import com.gxb.enums.YesOrNo;
import com.gxb.pojo.*;
import com.gxb.pojo.vo.CategoryVO;
import com.gxb.pojo.vo.ItemInfoVO;
import com.gxb.pojo.vo.NewItemsVO;
import com.gxb.service.CarouselService;
import com.gxb.service.CategoryService;
import com.gxb.service.ItemService;
import com.gxb.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("items")
@Api(value = "商品接口",tags = {"商品信息展示的相关接口"})
public class ItemsController {

    @Autowired
    private ItemService itemService;

    @ApiOperation(value = "查询商品详情",notes = "查询商品详情",httpMethod = "GET")
    @GetMapping("info/{itemId}")
    public JSONResult usernameIsExist(
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


}
