package com.gxb.mapper;

import com.gxb.pojo.vo.CategoryVO;
import com.gxb.pojo.vo.NewItemsVO;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface CategoryMapperCustom{

    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);

    List<NewItemsVO> getSixNewItemsLazy(@Param("paramsMap") Map<String,Object> map);
}
