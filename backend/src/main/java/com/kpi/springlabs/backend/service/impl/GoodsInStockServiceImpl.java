package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.repository.jdbc.GoodsInStockRepository;
import com.kpi.springlabs.backend.service.GoodsInStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoodsInStockServiceImpl implements GoodsInStockService {

    private final GoodsInStockRepository goodsInStockRepository;

    @Autowired
    public GoodsInStockServiceImpl(GoodsInStockRepository goodsInStockRepository) {
        this.goodsInStockRepository = goodsInStockRepository;
    }

    @Override
    public List<GoodsInStock> getAllGoodsInStocks() {
        LOG.debug("Getting all goods in stocks");
        return goodsInStockRepository.getAll();
    }

    @Override
    public List<GoodsInStock> getAllGoodsInStock(long stockId) {
        LOG.debug("Getting all goods in Warehouse(id = {})", stockId);
        return goodsInStockRepository.getAllGoodsByStockId(stockId);
    }

    @Override
    public GoodsInStock getCertainGoodsInStock(long stockId, long goodsId) {
        LOG.debug("Getting Goods(id = {}) in Warehouse(id = {})", goodsId, stockId);
        return goodsInStockRepository.getGoodsByIdAndStock(stockId, goodsId)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) in Warehouse(id = {}) not found", goodsId, stockId);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) in Warehouse(id = %s) not found", goodsId, stockId));
                });
    }

    @Override
    public GoodsInStock createGoodsInStock(GoodsInStock goodsInStock) {
        LOG.debug("Creating GoodsInStock {}", goodsInStock);
        return goodsInStockRepository.save(goodsInStock)
                .orElseThrow(() -> {
                    LOG.error("GoodsInStock {} cannot be created", goodsInStock);
                    return new ConflictException(String.format("GoodsInStock %s cannot be created", goodsInStock));
                });
    }

    @Override
    public void updateGoodsInStock(GoodsInStock goodsInStock) {
        LOG.debug("Updating GoodsInStock {}", goodsInStock);
        goodsInStockRepository.update(goodsInStock);
    }

    @Override
    public void deleteGoodsInStock(long id) {
        LOG.debug("Deleting GoodsInStock(id = {})", id);
        goodsInStockRepository.delete(id);
    }
}
