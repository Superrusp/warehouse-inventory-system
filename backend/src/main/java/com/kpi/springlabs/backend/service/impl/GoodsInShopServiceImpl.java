package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.GoodsInShopMapper;
import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.model.dto.GoodsInShopDto;
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
    private final GoodsInShopMapper goodsInShopMapper;

    @Autowired
    public GoodsInShopServiceImpl(GoodsInShopJpaRepository goodsInShopRepository, GoodsInShopMapper goodsInShopMapper) {
        this.goodsInShopRepository = goodsInShopRepository;
        this.goodsInShopMapper = goodsInShopMapper;
    }

    @Override
    public List<GoodsInShopDto> getAllGoodsInShops() {
        LOG.debug("Getting all goods in shops");
        List<GoodsInShop> allGoodsInShops = goodsInShopRepository.getAll();
        return goodsInShopMapper.toDtoList(allGoodsInShops);
    }

    @Override
    public List<GoodsInShopDto> getAllGoodsInShop(long id) {
        LOG.debug("Getting all goods in Shop(id = {})", id);
        List<GoodsInShop> allGoodsInShop = goodsInShopRepository.getAllGoodsByShopId(id);
        return goodsInShopMapper.toDtoList(allGoodsInShop);
    }

    @Override
    public GoodsInShopDto getCertainGoodsInShop(long shopId, long goodsId) {
        LOG.debug("Getting Goods(id = {}) in Shop(id = {})", goodsId, shopId);
        GoodsInShop goodsInShop = goodsInShopRepository.getGoodsByIdAndShop(shopId, goodsId)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) in Shop(id = {}) not found", goodsId, shopId);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) in Shop(id = %s) not found", goodsId, shopId));
                });
        return goodsInShopMapper.toDto(goodsInShop);
    }

    @Override
    @Transactional
    public GoodsInShopDto createGoodsInShop(GoodsInShopDto goodsInShopDto) {
        GoodsInShop goodsInShop = goodsInShopMapper.toEntityIgnoringId(goodsInShopDto);
        LOG.debug("Creating GoodsInShop {}", goodsInShop);
        GoodsInShop createdGoodsInShop = goodsInShopRepository.save(goodsInShop)
                .orElseThrow(() -> {
                    LOG.error("GoodsInShop {} cannot be created", goodsInShop);
                    return new ConflictException(String.format("GoodsInShop %s cannot be created", goodsInShop));
                });
        return goodsInShopMapper.toDto(createdGoodsInShop);
    }

    @Override
    @Transactional
    public void updateGoodsInShop(GoodsInShopDto goodsInShopDto) {
        GoodsInShop goodsInShop = goodsInShopMapper.toEntity(goodsInShopDto);
        LOG.debug("Updating GoodsInShop {}", goodsInShop);
        goodsInShopRepository.update(goodsInShop);
    }

    @Override
    @Transactional
    public void deleteGoodsInShop(long id) {
        LOG.debug("Deleting GoodsInShop(id = {})", id);
        goodsInShopRepository.delete(id);
    }
}
