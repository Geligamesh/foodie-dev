package com.gxb.controller;

import com.gxb.enums.YesOrNo;
import com.gxb.pojo.Carousel;
import com.gxb.pojo.Category;
import com.gxb.pojo.vo.CategoryVO;
import com.gxb.service.CarouselService;
import com.gxb.service.CategoryService;
import com.gxb.utils.JSONResult;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("index")
@Api(value = "首页",tags = {"首页展示的相关接口"})
public class IndexController {

    @Autowired
    private CarouselService carouselService;
    @Autowired
    private CategoryService categoryService;

    @ApiOperation(value = "获取首页轮播图",notes = "获取首页轮播图",httpMethod = "GET")
    @GetMapping("carousel")
    public JSONResult usernameIsExist( ) {
        List<Carousel> carousels = carouselService.queryAll(YesOrNo.YES.getType());
        return JSONResult.ok(carousels);
    }

    /**
     * 首页分类展示需求：
     * 1. 第一次刷新主页查询大分类，渲染展示到首页
     * 2. 如果鼠标上移到大分类，则加载其子分类的内容，如果已经存在子分类，则不需要加载（懒加载）
     */
    @ApiOperation(value = "获取商品分类（一级分类）",notes = "获取商品分类（一级分类）",httpMethod = "GET")
    @GetMapping("cats")
    public JSONResult cats() {
        List<Category> categories = categoryService.queryRootCategoryList();
        return JSONResult.ok(categories);
    }

    @ApiOperation(value = "获取商品子分类",notes = "获取商品子分类",httpMethod = "GET")
    @GetMapping("subCat/{rootCatId}")
    public JSONResult subCat(
            @ApiParam(name = "rootCatId",value = "一级分类id",required = true)
            @PathVariable("rootCatId") Integer rootCatId) {
        if (rootCatId == null || "".equals(rootCatId)) {
            return JSONResult.errorMsg("分类不存在");
        }
        List<CategoryVO> subCatList = categoryService.getSubCatList(rootCatId);
        return JSONResult.ok(subCatList);
    }
}
