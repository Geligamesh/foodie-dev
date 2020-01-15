package com.gxb.service.Impl;

import com.gxb.enums.Sex;
import com.gxb.mapper.UsersMapper;
import com.gxb.pojo.Users;
import com.gxb.pojo.bo.UserBO;
import com.gxb.service.UserService;
import com.gxb.utils.DateUtil;
import com.gxb.utils.MD5Utils;
import org.n3r.idworker.Sid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import java.util.Date;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersMapper usersMapper;
    @Autowired
    private Sid sid;
    public static final String USER_FACE = "http://122.152.205.72:88/group1/M00/00/05/CpoxxFw_8_qAIlFXAAAcIhVPdSg994.png";

    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public boolean queryUsernameIsExist(String username) {
        Example example = new Example(Users.class);
        example.createCriteria().andEqualTo("username", username);
        Users users = usersMapper.selectOneByExample(example);
        return users != null;
    }

    /**
     * 注册用户
     * @param userBO
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.REQUIRED)
    public Users createUser(UserBO userBO) {
        Users users = new Users();
        users.setUsername(userBO.getUsername());
        try {
            users.setPassword(MD5Utils.getMD5Str(userBO.getPassword()));
        } catch (Exception e) {
            e.printStackTrace();
        }
        users.setNickname(userBO.getUsername());
        //设置头像
        users.setFace(USER_FACE);
        users.setBirthday(DateUtil.stringToDate("1970-01-01"));
        users.setSex(Sex.secret.getType());

        users.setCreatedTime(new Date());
        users.setUpdatedTime(new Date());
        users.setId(sid.next());
        usersMapper.insertSelective(users);
        return users;
    }

    /**
     * 检索用户名和密码是否匹配，用于登录
     * @param username
     * @param password
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public Users queryUserForLogin(String username, String password) {

//        try {
//            Thread.sleep(2500);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }

        Example userExample = new Example(Users.class);
        Example.Criteria userCriteria = userExample.createCriteria();

        userCriteria.andEqualTo("username", username);
        userCriteria.andEqualTo("password", password);

        return usersMapper.selectOneByExample(userExample);
    }

}
