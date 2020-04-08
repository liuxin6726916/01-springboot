package com.zty.spring.service.impl;


import com.zty.spring.constants.Constant;
import com.zty.spring.mapper.goods.GoodsMapper;
import com.zty.spring.mapper.orders.OrdersMapper;
import com.zty.spring.model.Goods;
import com.zty.spring.model.Orders;
import com.zty.spring.model.ResultObject;
import com.zty.spring.service.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
public class GoodsServiceImpl implements GoodsService {

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private OrdersMapper ordersMapper;

    public List<Goods> getAllGoods() {
        return goodsMapper.selectAllGoods();
    }

    public Goods getGoodsById(Integer goodsId) {
        return goodsMapper.selectByPrimaryKey(goodsId);
    }

    /**
     * 下单
     *
     * @param uid
     * @param goodsId
     * @param buyNum
     * @return
     */
    @Transactional
    public ResultObject doOrder(Integer uid, Integer goodsId, Integer buyNum) {
        //减库存 (操作商品库)
        goodsMapper.updateByStore(goodsId, buyNum);
        //下订单
        Orders orders = new Orders();
        orders.setBuynum(buyNum);
        orders.setUid(uid);
        orders.setCreatetime(new Date());
        orders.setGoodsid(goodsId);
        //操作订单库
        ordersMapper.insertSelective(orders);
        //发生异常，测试是否回滚
        int i = 10 / 0;
        return new ResultObject(Constant.ZERO, "下单成功", orders);
    }
}