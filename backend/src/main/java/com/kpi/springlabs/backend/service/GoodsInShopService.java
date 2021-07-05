package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.GoodsInShopDto;

import java.util.List;

public interface GoodsInShopService {

    List<GoodsInShopDto> getAllGoodsInShops();

    List<GoodsInShopDto> getAllGoodsInShop(long id);

    GoodsInShopDto getCertainGoodsInShop(long shopId, long goodsId);

    GoodsInShopDto createGoodsInShop(GoodsInShopDto goodsInShopDto);

    void updateGoodsInShop(GoodsInShopDto goodsInShopDto);

    void deleteGoodsInShop(long id);
}
