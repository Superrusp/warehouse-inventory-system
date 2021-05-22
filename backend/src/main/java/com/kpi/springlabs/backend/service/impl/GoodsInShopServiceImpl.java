package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.repository.jpa.GoodsInShopJpaRepository;
import com.kpi.springlabs.backend.service.GoodsInShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class GoodsInShopServiceImpl implements GoodsInShopService {

    private final GoodsInShopJpaRepository goodsInShopRepository;

    @Autowired
    public GoodsInShopServiceImpl(GoodsInShopJpaRepository goodsInShopRepository) {
        this.goodsInShopRepository = goodsInShopRepository;
    }

    @Override
    public List<GoodsInShop> getAllGoodsInShops() {
        LOG.debug("Getting all goods in shops");
        return goodsInShopRepository.getAll();
    }

    @Override
    public List<GoodsInShop> getAllGoodsInShop(long id) {
        LOG.debug("Getting all goods in Shop(id = {})", id);
        return goodsInShopRepository.getAllGoodsByShopId(id);
    }

    @Override
    public GoodsInShop getCertainGoodsInShop(long shopId, long goodsId) {
        LOG.debug("Getting Goods(id = {}) in Shop(id = {})", goodsId, shopId);
        return goodsInShopRepository.getGoodsByIdAndShop(shopId, goodsId)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) in Shop(id = {}) not found", goodsId, shopId);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) in Shop(id = %s) not found", goodsId, shopId));
                });
    }

    @Override
    @Transactional
    public GoodsInShop createGoodsInShop(GoodsInShop goodsInStock) {
        LOG.debug("Creating GoodsInShop {}", goodsInStock);
        return goodsInShopRepository.save(goodsInStock)
                .orElseThrow(() -> {
                    LOG.error("GoodsInShop {} cannot be created", goodsInStock);
                    return new ConflictException(String.format("GoodsInShop %s cannot be created", goodsInStock));
                });
    }

    @Override
    @Transactional
    public void updateGoodsInShop(GoodsInShop goodsInStock) {
        LOG.debug("Updating GoodsInShop {}", goodsInStock);
        goodsInShopRepository.update(goodsInStock);
    }

    @Override
    @Transactional
    public void deleteGoodsInShop(long id) {
        LOG.debug("Deleting GoodsInShop(id = {})", id);
        goodsInShopRepository.delete(id);
    }
}
