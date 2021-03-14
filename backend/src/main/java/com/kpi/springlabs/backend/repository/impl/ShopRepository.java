package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.Shop;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class ShopRepository extends BaseRepositoryImpl<Shop> {

    private static long SHOP_COUNT;
    private static List<Shop> shops;

    static {
        LOG.debug("Shop list initialization");
        shops = new ArrayList<>();

        shops.add(new Shop(++SHOP_COUNT, "Shop#1"));
        shops.add(new Shop(++SHOP_COUNT, "Shop#2"));
        shops.add(new Shop(++SHOP_COUNT, "Shop#3"));
        LOG.debug("Shop list: {}", shops);
    }

    @Override
    protected List<Shop> getList() {
        return shops;
    }
}
