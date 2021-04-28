package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.Shop;

import java.util.List;

public interface ShopService {

    List<Shop> getShops();

    Shop getShopById(long id);

    Shop createShop(Shop shop);

    void updateShop(Shop shop);

    void deleteShop(long id);
}
