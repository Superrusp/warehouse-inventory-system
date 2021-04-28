package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.GoodsInShop;

import java.util.List;

public interface GoodsInShopService {

    List<GoodsInShop> getAllGoodsInShops();

    List<GoodsInShop> getAllGoodsInShop(long id);

    GoodsInShop getCertainGoodsInShop(long shopId, long goodsId);

    GoodsInShop createGoodsInShop(GoodsInShop goodsInStock);

    void updateGoodsInShop(GoodsInShop goodsInStock);

    void deleteGoodsInShop(long id);
}
