package com.gxb.controller;

import org.springframework.stereotype.Controller;

@Controller
public class BaseController {

    public static final Integer COMMENT_PAGE_SIZE = 10;
    public static final Integer PAGE_SIZE = 20;
    public static final String FOODIE_SHOPCART = "shopcart";


    //用户上传头像的位置
    public static final String IMAGE_USER_FACE_LOCATION = "";
    //支付中心的调用地址
    String paymentUrl = "http://localhost:8089/payment/createMerchantOrder";

    //微信支付成功 --> 支付中心 --> 天天吃货中心
    public static final String payReturnUrl = "http://localhost:8088/orders/notifyMerchantOrderPaid";

    public static void main(String[] args) {
        String path = BaseController.class.getResource("/").getPath();
        System.out.println(path);
    }

}
