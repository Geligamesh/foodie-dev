package com.gxb.service;

import com.gxb.pojo.Items;
import com.gxb.pojo.ItemsImg;
import com.gxb.pojo.ItemsParam;
import com.gxb.pojo.ItemsSpec;
import com.gxb.pojo.vo.CommentLevelCountsVO;
import com.gxb.pojo.vo.ItemCommentVO;

import java.util.List;

public interface ItemService {

    /**
     * 根据商品id查找详情
     * @param itemId
     * @return
     */
    Items queryItemById(String itemId);

    /**
     * 根据商品id查找商品图片列表
     * @param itemId
     * @return
     */
    List<ItemsImg> queryItemImgList(String itemId);

    /**
     * 根据商品id查找商品规格
     * @param itemId
     * @return
     */
    List<ItemsSpec> queryItemSpecList(String itemId);

    /**
     * 根据商品id查找商品参数
     * @param itemId
     * @return
     */
    ItemsParam queryItemParam(String itemId);

    /**
     * 根据商品id查询商品的评价等级数量
     * @param itemId
     */
    CommentLevelCountsVO queryCommentCounts(String itemId);

    /**
     * 根据商品id查询商品的评价（分页）
     * @param itemId
     * @param level
     * @return
     */
    List<ItemCommentVO> queryPagedComments(String itemId,String level);
}