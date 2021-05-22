package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.GoodsDto;
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

    @Autowired
    public GoodsServiceImpl(GoodsJpaRepository goodsJpaRepositoryImpl) {
        this.goodsRepository = goodsJpaRepositoryImpl;
    }

    @Override
    public List<Goods> getAllGoods() {
        LOG.debug("Getting all goods");
        return goodsRepository.getAll();
    }

    @Override
    public Goods getGoodsById(long id) {
        LOG.debug("Getting certain Goods(id = {})", id);
        return goodsRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) not found", id));
                });
    }

    @Override
    @TrackExecutionTime
    public GoodsDto getGoodsByName(String name) {
        LOG.debug("Getting certain Goods(name = {})", name);
        return goodsRepository.findByName(name)
                .orElseThrow(() -> {
                    LOG.error("Goods(name = {}) not found", name);
                    return new ObjectNotFoundException(String.format("Goods(name = %s) not found", name));
                });
    }

    @Override
    @Transactional
    public Goods createGoods(Goods goods) {
        LOG.debug("Creating Goods: {}", goods);
        return goodsRepository.save(goods)
                .orElseThrow(() -> {
                    LOG.error("Goods {} cannot be created", goods);
                    return new ConflictException(String.format("Goods %s cannot be created", goods));
                });
    }

    @Override
    @Transactional
    public void updateGoods(Goods goods) {
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
