package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.GoodsInStockMapper;
import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.model.dto.GoodsInStockDto;
import com.kpi.springlabs.backend.repository.jpa.GoodsInStockJpaRepository;
import com.kpi.springlabs.backend.service.GoodsInStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class GoodsInStockServiceImpl implements GoodsInStockService {

    private final GoodsInStockJpaRepository goodsInStockRepository;
    private final GoodsInStockMapper goodsInStockMapper;

    @Autowired
    public GoodsInStockServiceImpl(GoodsInStockJpaRepository goodsInStockRepository,
                                   GoodsInStockMapper goodsInStockMapper) {
        this.goodsInStockRepository = goodsInStockRepository;
        this.goodsInStockMapper = goodsInStockMapper;
    }

    @Override
    public List<GoodsInStockDto> getAllGoodsInStocks() {
        LOG.debug("Getting all goods in stocks");
        List<GoodsInStock> allGoodsInStocks = goodsInStockRepository.getAll();
        return goodsInStockMapper.toDtoList(allGoodsInStocks);
    }

    @Override
    public List<GoodsInStockDto> getAllGoodsInStock(long stockId) {
        LOG.debug("Getting all goods in Warehouse(id = {})", stockId);
        List<GoodsInStock> allGoodsByStock = goodsInStockRepository.getAllGoodsByStockId(stockId);
        return goodsInStockMapper.toDtoList(allGoodsByStock);
    }

    @Override
    public GoodsInStockDto getCertainGoodsInStock(long stockId, long goodsId) {
        LOG.debug("Getting Goods(id = {}) in Warehouse(id = {})", goodsId, stockId);
        GoodsInStock goodsInStock = goodsInStockRepository.getGoodsByIdAndStock(stockId, goodsId)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) in Warehouse(id = {}) not found", goodsId, stockId);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) in Warehouse(id = %s) not found", goodsId, stockId));
                });
        return goodsInStockMapper.toDto(goodsInStock);
    }

    @Override
    @Transactional
    public GoodsInStockDto createGoodsInStock(GoodsInStockDto goodsInStockDto) {
        GoodsInStock goodsInStock = goodsInStockMapper.toEntityIgnoringId(goodsInStockDto);
        LOG.debug("Creating GoodsInStock {}", goodsInStock);
        GoodsInStock createdGoodsInStock = goodsInStockRepository.save(goodsInStock)
                .orElseThrow(() -> {
                    LOG.error("GoodsInStock {} cannot be created", goodsInStock);
                    return new ConflictException(String.format("GoodsInStock %s cannot be created", goodsInStock));
                });
        return goodsInStockMapper.toDto(createdGoodsInStock);
    }

    @Override
    @Transactional
    public void updateGoodsInStock(GoodsInStockDto goodsInStockDto) {
        GoodsInStock goodsInStock = goodsInStockMapper.toEntity(goodsInStockDto);
        LOG.debug("Updating GoodsInStock {}", goodsInStock);
        goodsInStockRepository.update(goodsInStock);
    }

    @Override
    @Transactional
    public void deleteGoodsInStock(long id) {
        LOG.debug("Deleting GoodsInStock(id = {})", id);
        goodsInStockRepository.delete(id);
    }
}
