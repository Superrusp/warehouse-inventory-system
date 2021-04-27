package com.kpi.springlabs.backend.repository.jdbc;

import com.kpi.springlabs.backend.model.GoodsInShop;

import java.util.List;
import java.util.Optional;

public interface GoodsInShopRepository extends BaseRepository<GoodsInShop> {

    List<GoodsInShop> getAllGoodsByShopId(long shopId);

    Optional<GoodsInShop> getGoodsByIdAndShop(long shopId, long goodsId);
}
