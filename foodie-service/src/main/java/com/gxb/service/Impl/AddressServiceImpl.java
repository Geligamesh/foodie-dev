package com.gxb.service.Impl;

import com.gxb.mapper.UserAddressMapper;
import com.gxb.pojo.UserAddress;
import com.gxb.pojo.bo.AddressBO;
import com.gxb.service.AddressService;
import org.n3r.idworker.Sid;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private Sid sid;

    /**
     * 根据userId查询地址列表
     * @param userId
     * @return
     */
    @Override
    public List<UserAddress> queryAll(String userId) {
        UserAddress userAddress = new UserAddress();
        userAddress.setUserId(userId);
        return userAddressMapper.select(userAddress);
    }


    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public void addNewUserAddress(AddressBO addressBO) {
        Integer isDefault = 0;
        //判断当前用户是否存在地址，如果没有则新增为默认地址
        List<UserAddress> userAddresses = this.queryAll(addressBO.getUserId());
        if (CollectionUtils.isEmpty(userAddresses)) {
            isDefault = 1;
        }
        //保存地址到数据库
        UserAddress userAddress = new UserAddress();
        BeanUtils.copyProperties(addressBO, userAddress);
        userAddress.setId(sid.next());
        userAddress.setIsDefault(isDefault);
        userAddress.setCreatedTime(new Date());
        userAddress.setUpdatedTime(new Date());
        userAddressMapper.insertSelective(userAddress);
    }
}
