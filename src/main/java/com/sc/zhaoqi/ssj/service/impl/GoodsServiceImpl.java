package com.sc.zhaoqi.ssj.service.impl;

import com.sc.zhaoqi.ssj.dao.GoodsRepository;
import com.sc.zhaoqi.ssj.entity.Goods;
import com.sc.zhaoqi.ssj.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service("goodsService")
public class GoodsServiceImpl
        implements GoodsService
{
    @Autowired
    private GoodsRepository goodsRepository;

    @Override
    public List<Goods> listPagedGoods(Integer pageNum, Integer pageSize)
    {
        int offset = (pageNum - 1) * pageSize;
        return goodsRepository.listPagedGoods(offset, pageSize);
    }

    @Override
    public List<Goods> listNumGoods(int num)
    {
        return goodsRepository.findAll();
    }

    @Override
    public long count()
    {
        return goodsRepository.count();
    }

    @Override
    public Goods findById(Long id)
    {
        return goodsRepository.findById(id).get();
    }

    @Override
    public Goods update(Goods goods)
    {
        Goods g = goodsRepository.findById(goods.getId()).get();
        g.setPrice(goods.getPrice());
        g.setName(goods.getName());
        g.setDetails(goods.getDetails());
        g.setCatalog(goods.getCatalog());
        g.setUpdate_time(new Timestamp(System.currentTimeMillis()));
        goodsRepository.saveAndFlush(g);
        return g;
    }

    @Override
    public boolean deleteById(Long id)
    {
        if(!goodsRepository.existsById(id)){
            return false;
        }
        goodsRepository.deleteById(id);
        return true;
    }
}
