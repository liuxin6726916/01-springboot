package com.zty.spring.service;


import com.zty.spring.model.Goods;
import com.zty.spring.model.ResultObject;

import java.util.List;

public interface GoodsService {

    public List<Goods> getAllGoods();

    public Goods getGoodsById(Integer goodsId);

    public ResultObject doOrder(Integer uid, Integer goodsId, Integer buyNum);
}