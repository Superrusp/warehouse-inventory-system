package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.Goods;

import java.util.List;

public interface GoodsService {

    List<Goods> getAllGoods();

    Goods getGoodsById(long id);

    Goods createGoods(Goods goods);

    void updateGoods(Goods goods);

    void deleteGoods(long id);

    boolean existsGoods(long id);
}
