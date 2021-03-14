package com.kpi.springlabs.backend.repository.impl;

import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.repository.GoodsInStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Slf4j
public class GoodsInStockRepositoryImpl extends BaseRepositoryImpl<GoodsInStock> implements GoodsInStockRepository {

    private static long GOODS_IN_STOCK_COUNT;
    private static List<GoodsInStock> goodsInStockList;

    private final GoodsRepository goodsRepository;
    private final WarehouseRepository warehouseRepository;

    @Autowired
    public GoodsInStockRepositoryImpl(GoodsRepository goodsRepository, WarehouseRepository warehouseRepository) {
        this.goodsRepository = goodsRepository;
        this.warehouseRepository = warehouseRepository;

        LOG.debug("GoodsInStock list initialization");
        goodsInStockList = new ArrayList<>();

        GoodsInStock goodsInStock1 = new GoodsInStock(++GOODS_IN_STOCK_COUNT,
                goodsRepository.getList().stream().findFirst().get(),
                warehouseRepository.getList().stream().findFirst().get(),
                10
        );

        GoodsInStock goodsInStock2 = new GoodsInStock(++GOODS_IN_STOCK_COUNT,
                goodsRepository.getList().stream().findAny().get(),
                warehouseRepository.getList().stream().findAny().get(),
                5
        );

        goodsInStockList.add(goodsInStock1);
        goodsInStockList.add(goodsInStock2);
        LOG.debug("GoodsInStock list: {}", goodsInStockList);
    }

    @Override
    protected List<GoodsInStock> getList() {
        return goodsInStockList;
    }

    @Override
    public List<GoodsInStock> getAllGoodsByStockId(long stockId) {
        LOG.debug("Getting all goods by stockID: {}", stockId);
        return goodsInStockList.stream()
                .filter(goodsInStock -> goodsInStock.getWarehouse().getId() == stockId)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<GoodsInStock> getGoodsByIdAndStock(long stockId, long goodsId) {
        LOG.debug("Getting goods by id {} and stockID {}", goodsId, stockId);
        return goodsInStockList.stream()
                .filter(goodsInStock -> goodsInStock.getWarehouse().getId() == stockId
                        && goodsInStock.getGoods().getId() == goodsId)
                .findFirst();
    }
}
