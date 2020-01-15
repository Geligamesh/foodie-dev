package com.gxb.service;

import com.gxb.pojo.Carousel;

import java.util.List;

public interface CarouselService{

    List<Carousel> queryAll(Integer isShow);
}
