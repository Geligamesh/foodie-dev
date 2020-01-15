package com.gxb.service.Impl;

import com.gxb.mapper.CarouselMapper;
import com.gxb.pojo.Carousel;
import com.gxb.service.CarouselService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

@Service
public class CarouselServiceImpl implements CarouselService {

    @Autowired
    private CarouselMapper carouselMapper;

    /**
     * 查询轮播图列表
     * @param isShow
     * @return
     */
    @Override
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<Carousel> queryAll(Integer isShow) {
        Example example = new Example(Carousel.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("isShow", isShow);
        example.orderBy("sort").desc();
        return carouselMapper.selectByExample(example);
    }
}
