package com.gxb.mapper;

import com.gxb.pojo.vo.CategoryVO;
import java.util.List;

public interface CategoryMapperCustom{

    List<CategoryVO> getSubCategoryList(Integer rootCategoryId);
}
