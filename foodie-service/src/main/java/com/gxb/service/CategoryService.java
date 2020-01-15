package com.gxb.service;

import com.gxb.pojo.Category;
import com.gxb.pojo.vo.CategoryVO;
import com.gxb.pojo.vo.NewItemsVO;

import java.util.List;

public interface CategoryService {

    /**
     * 查询根基目录列表
     * @return
     */
    List<Category> queryRootCategoryList();

    List<CategoryVO> getSubCatList(Integer rootCatId);

    List<NewItemsVO> getSixNewItemsLazy(Integer rootCatId);
}
