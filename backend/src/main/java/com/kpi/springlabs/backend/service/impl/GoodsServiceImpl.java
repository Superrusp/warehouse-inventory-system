package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.GoodsMapper;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.GoodsDto;
import com.kpi.springlabs.backend.model.dto.response.GoodsNameResponse;
import com.kpi.springlabs.backend.repository.jpa.GoodsJpaRepository;
import com.kpi.springlabs.backend.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    private final GoodsJpaRepository goodsRepository;
    private final GoodsMapper goodsMapper;

    @Autowired
    public GoodsServiceImpl(GoodsJpaRepository goodsJpaRepositoryImpl, GoodsMapper goodsMapper) {
        this.goodsRepository = goodsJpaRepositoryImpl;
        this.goodsMapper = goodsMapper;
    }

    @Override
    public List<GoodsDto> getAllGoods() {
        LOG.debug("Getting all goods");
        List<Goods> allGoods = goodsRepository.getAll();
        return goodsMapper.toDtoList(allGoods);
    }

    @Override
    public GoodsDto getGoodsById(long id) {
        LOG.debug("Getting certain Goods(id = {})", id);
        Goods goods = goodsRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) not found", id));
                });
        return goodsMapper.toDto(goods);
    }

    @Override
    @TrackExecutionTime
    public GoodsNameResponse getGoodsByName(String name) {
        LOG.debug("Getting certain Goods(name = {})", name);
        return goodsRepository.findByName(name)
                .orElseThrow(() -> {
                    LOG.error("Goods(name = {}) not found", name);
                    return new ObjectNotFoundException(String.format("Goods(name = %s) not found", name));
                });
    }

    @Override
    @Transactional
    public GoodsDto createGoods(GoodsDto goodsDto) {
        Goods goods = goodsMapper.toEntityIgnoringId(goodsDto);
        LOG.debug("Creating Goods: {}", goods);
        Goods createdGoods = goodsRepository.save(goods)
                .orElseThrow(() -> {
                    LOG.error("Goods {} cannot be created", goods);
                    return new ConflictException(String.format("Goods %s cannot be created", goods));
                });
        return goodsMapper.toDto(createdGoods);
    }

    @Override
    @Transactional
    public void updateGoods(GoodsDto goodsDto) {
        Goods goods = goodsMapper.toEntity(goodsDto);
        LOG.debug("Updating Goods: {}", goods);
        goodsRepository.update(goods);
    }

    @Override
    @Transactional
    public void deleteGoods(long id) {
        LOG.debug("Deleting Goods(id = {})", id);
        goodsRepository.delete(id);
    }

    @Override
    public boolean existsGoods(long id) {
        return goodsRepository.exists(id);
    }
}
