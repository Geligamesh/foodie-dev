package com.gxb.service;

import com.gxb.pojo.UserAddress;
import com.gxb.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);
}
