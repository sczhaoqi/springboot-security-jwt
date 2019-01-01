package com.sc.zhaoqi.ssj.controller;

import com.sc.zhaoqi.ssj.bean.Msg;
import com.sc.zhaoqi.ssj.dto.PageDto;
import com.sc.zhaoqi.ssj.entity.Goods;
import com.sc.zhaoqi.ssj.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Random;

@RestController
public class ApiController
{
    @Autowired
    private GoodsService goodsService;

    @GetMapping("/goods")
    public Msg<PageDto<Goods>> index(@RequestParam(required = false) Integer n,
            @RequestParam(required = false) Integer pageNum,
            @RequestParam(required = false) Integer pageSize)
    {
        PageDto<Goods> goodsPageDto = new PageDto<>();
        if(n != null && n > 0) {
            goodsPageDto.setList(goodsService.listNumGoods(n));
            goodsPageDto.setTotal(n);
            return Msg.ok("", goodsPageDto);
        }else if (pageNum != null && pageNum >= 0 && pageSize != null && pageSize > 0) {
            goodsPageDto.setList(goodsService.listPagedGoods(pageNum, pageSize));
            goodsPageDto.setTotal(goodsService.count());
            return Msg.ok("", goodsPageDto);
        } else{
            return Msg.error("分页参数有误,请检查后重试!");
        }
    }
    @GetMapping("/goods/find")
    public Msg<Goods> find(Long id){
        return Msg.ok("",goodsService.findById(id));
    }

    @PostMapping("/goods/update")
    public Msg<Goods> update(@RequestBody Goods goods){
        return Msg.ok("更新成功 ",goodsService.update(goods));
    }
    @GetMapping("/goods/delete")
    public Msg<String> del(Long id){
        boolean res = goodsService.deleteById(id);
        if(res){
            return Msg.ok("删除成功");
        }else{
            return Msg.ok("删除失败,请重试");
        }
    }
}
