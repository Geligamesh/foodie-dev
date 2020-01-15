package com.gxb.service;

import com.gxb.pojo.Users;
import com.gxb.pojo.bo.UserBO;

public interface UserService {

    boolean queryUsernameIsExist(String username);

    Users createUser(UserBO userBO);

    Users queryUserForLogin(String username,String password);
}

