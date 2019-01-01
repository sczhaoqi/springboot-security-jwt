package com.sc.zhaoqi.ssj.service;

import com.sc.zhaoqi.ssj.entity.Goods;

import java.util.List;

public interface GoodsService
{
    List<Goods> listPagedGoods(Integer pageNum, Integer pageSize);

    List<Goods> listNumGoods(int num);

    long count();

    Goods findById(Long id);

    Goods update(Goods goods);

    boolean deleteById(Long id);
}
