package com.kpi.springlabs.backend.repository;

import com.kpi.springlabs.backend.model.GoodsInStock;

import java.util.List;
import java.util.Optional;

public interface GoodsInStockRepository extends BaseRepository<GoodsInStock> {

    List<GoodsInStock> getAllGoodsByStockId(long stockId);

    Optional<GoodsInStock> getGoodsByIdAndStock(long stockId, long goodsId);
}
