package com.gxb.mapper;

import com.gxb.pojo.vo.MyOrdersVO;
import org.apache.ibatis.annotations.Param;
import java.util.List;
import java.util.Map;

public interface OrdersMapperCustom {

    List<MyOrdersVO> queryMyOrders(@Param("paramsMap") Map<String,Object> map);
}
