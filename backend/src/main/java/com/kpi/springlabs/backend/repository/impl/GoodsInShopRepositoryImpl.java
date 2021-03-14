package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.repository.GoodsInShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class GoodsInShopRepositoryImpl extends BaseRepositoryImpl<GoodsInShop> implements GoodsInShopRepository {

    private static long GOODS_IN_SHOP_COUNT;
    private static List<GoodsInShop> goodsInShopList;

    private final GoodsRepository goodsRepository;
    private final ShopRepository shopRepository;

    @Autowired
    public GoodsInShopRepositoryImpl(GoodsRepository goodsRepository, ShopRepository warehouseRepository) {
        this.goodsRepository = goodsRepository;
        this.shopRepository = warehouseRepository;

        LOG.debug("GoodsInShop list initialization");
        goodsInShopList = new ArrayList<>();

        GoodsInShop goodsInShop1 = new GoodsInShop(++GOODS_IN_SHOP_COUNT,
                shopRepository.getList().stream().findFirst().get(),
                goodsRepository.getList().stream().findFirst().get(),
                3
        );

        GoodsInShop goodsInShop2 = new GoodsInShop(++GOODS_IN_SHOP_COUNT,
                shopRepository.getList().stream().findAny().get(),
                goodsRepository.getList().stream().findAny().get(),
                2
        );

        goodsInShopList.add(goodsInShop1);
        goodsInShopList.add(goodsInShop2);
        LOG.debug("GoodsInShop list: {}", goodsInShopList);
    }

    @Override
    protected List<GoodsInShop> getList() {
        return goodsInShopList;
    }

    @Override
    public List<GoodsInShop> getAllGoodsByShopId(long shopId) {
        LOG.debug("Getting all goods by shopID: {}", shopId);
        return goodsInShopList.stream()
                .filter(goodsInStock -> goodsInStock.getShop().getId() == shopId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GoodsInShop> getGoodsByIdAndShop(long shopId, long goodsId) {
        LOG.debug("Getting goods by id {} and shopID {}", goodsId, shopId);
        return goodsInShopList.stream()
                .filter(goodsInStock -> goodsInStock.getShop().getId() == shopId
                        && goodsInStock.getGoods().getId() == goodsId)
                .findFirst();
    }
}
