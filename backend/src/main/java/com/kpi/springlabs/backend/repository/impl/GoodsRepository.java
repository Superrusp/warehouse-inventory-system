package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.Goods;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
@Slf4j
public class GoodsRepository extends BaseRepositoryImpl<Goods> {

    private static long GOODS_COUNT;
    private static List<Goods> goodsList;

    static {
        LOG.debug("Goods list initialization");
        goodsList = new ArrayList<>();

        goodsList.add(new Goods(++GOODS_COUNT, "Iphone XS", "Silver 64Gb", 650.00));
        goodsList.add(new Goods(++GOODS_COUNT, "Iphone XR", "Red 128Gb", 550.00));
        goodsList.add(new Goods(++GOODS_COUNT, "Iphone XS Max", "Gold 256Gb", 750.00));
        goodsList.add(new Goods(++GOODS_COUNT, "Iphone XS Max", "Gold 512Gb", 850.00));
        goodsList.add(new Goods(++GOODS_COUNT, "Iphone 11 Pro", "Silver 256Gb", 950.00));
        goodsList.add(new Goods(++GOODS_COUNT, "Iphone 11 Pro Max", "Black 512Gb", 1100.00));
        LOG.debug("Goods list: {}", goodsList);
    }

    @Override
    protected List<Goods> getList() {
        return goodsList;
    }
}
