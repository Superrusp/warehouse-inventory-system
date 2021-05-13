package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.GoodsInStock;

import java.util.List;
import java.util.Optional;

public interface GoodsInStockJpaRepository extends JpaBaseRepository<GoodsInStock> {

    List<GoodsInStock> getAllGoodsByStockId(long stockId);

    Optional<GoodsInStock> getGoodsByIdAndStock(long stockId, long goodsId);
}
