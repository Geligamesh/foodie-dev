package com.gxb.mapper;

import com.gxb.pojo.vo.ItemCommentVO;
import com.gxb.pojo.vo.SearchItemsVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface ItemsMapperCustom{

    List<ItemCommentVO> queryItemComments(@Param("paramsMap") Map<String,Object> map);

    List<SearchItemsVO> searchItems(@Param("paramsMap") Map<String,Object> map);
}
