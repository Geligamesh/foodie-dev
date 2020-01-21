package com.gxb.service;

import com.gxb.pojo.UserAddress;
import com.gxb.pojo.bo.AddressBO;

import java.util.List;

public interface AddressService {

    List<UserAddress> queryAll(String userId);

    void addNewUserAddress(AddressBO addressBO);

    void updateUserAddress(AddressBO addressBO);

    void deleteUserAddress(String userId,String addressId);

    void deleteUserAddressToBeDefault(String userId,String addressId);

    UserAddress queryUserAddress(String userId,String addressId);
}
