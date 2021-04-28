package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.GoodsInStock;

import java.util.List;

public interface GoodsInStockService {

    List<GoodsInStock> getAllGoodsInStocks();

    List<GoodsInStock> getAllGoodsInStock(long stockId);

    GoodsInStock getCertainGoodsInStock(long stockId, long goodsId);

    GoodsInStock createGoodsInStock(GoodsInStock goodsInStock);

    void updateGoodsInStock(GoodsInStock goodsInStock);

    void deleteGoodsInStock(long id);
}
