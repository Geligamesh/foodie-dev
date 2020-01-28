package com.gxb.service.Impl;

import com.github.pagehelper.PageInfo;
import com.gxb.utils.PagedGridResult;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BaseService {

    public PagedGridResult setterPagedGrid(List<?> list, Integer page){
        PageInfo pageList = new PageInfo<>(list);
        PagedGridResult grid = new PagedGridResult();
        grid.setPage(page);
        grid.setRows(list);
        grid.setTotal(pageList.getPages());
        grid.setRecords(pageList.getTotal());
        return grid;
    }
}
