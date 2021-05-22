package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.GoodsInShop;

import java.util.List;
import java.util.Optional;

public interface GoodsInShopJpaRepository extends JpaBaseRepository<GoodsInShop> {

    List<GoodsInShop> getAllGoodsByShopId(long shopId);

    Optional<GoodsInShop> getGoodsByIdAndShop(long shopId, long goodsId);
}
