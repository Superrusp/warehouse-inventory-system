package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.GoodsInStockDto;

import java.util.List;

public interface GoodsInStockService {

    List<GoodsInStockDto> getAllGoodsInStocks();

    List<GoodsInStockDto> getAllGoodsInStock(long stockId);

    GoodsInStockDto getCertainGoodsInStock(long stockId, long goodsId);

    GoodsInStockDto createGoodsInStock(GoodsInStockDto goodsInStockDto);

    void updateGoodsInStock(GoodsInStockDto goodsInStockDto);

    void deleteGoodsInStock(long id);
}
