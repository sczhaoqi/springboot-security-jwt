package com.sc.zhaoqi.ssj.dao;

import com.sc.zhaoqi.ssj.entity.Goods;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface GoodsRepository
        extends JpaRepository<Goods, Long>
{
    @Query(value = "select * from goods limit ?1,?2", nativeQuery = true)
    List<Goods> listPagedGoods(Integer offset, Integer pageNum);
}
