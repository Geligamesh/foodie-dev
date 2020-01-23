package com.gxb.service.center;

import com.gxb.pojo.Users;
import com.gxb.pojo.bo.center.CenterUserBO;

public interface CenterUserService {

    /**
     * 根据用户id查找用户信息
     * @param userId
     * @return
     */
    Users queryUserInfo(String userId);

    Users updateUserInfo(String userId, CenterUserBO centerUserBO);
}
