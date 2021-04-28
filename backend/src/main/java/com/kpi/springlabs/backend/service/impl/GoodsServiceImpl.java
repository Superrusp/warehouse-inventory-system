package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.repository.jdbc.impl.GoodsRepositoryImpl;
import com.kpi.springlabs.backend.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class GoodsServiceImpl implements GoodsService {

    private final GoodsRepositoryImpl goodsRepositoryImpl;

    @Autowired
    public GoodsServiceImpl(GoodsRepositoryImpl goodsRepositoryImpl) {
        this.goodsRepositoryImpl = goodsRepositoryImpl;
    }

    @Override
    public List<Goods> getAllGoods() {
        LOG.debug("Getting all goods");
        return goodsRepositoryImpl.getAll();
    }

    @Override
    public Goods getGoodsById(long id) {
        LOG.debug("Getting certain Goods(id = {})", id);
        return goodsRepositoryImpl.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Goods(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Goods(id = %s) not found", id));
                });
    }

    @Override
    public Goods createGoods(Goods goods) {
        LOG.debug("Creating Goods: {}", goods);
        return goodsRepositoryImpl.save(goods)
                .orElseThrow(() -> {
                    LOG.error("Goods {} cannot be created", goods);
                    return new ConflictException(String.format("Goods %s cannot be created", goods));
                });
    }

    @Override
    public void updateGoods(Goods goods) {
        LOG.debug("Updating Goods: {}", goods);
        goodsRepositoryImpl.update(goods);
    }

    @Override
    public void deleteGoods(long id) {
        LOG.debug("Deleting Goods(id = {})", id);
        goodsRepositoryImpl.delete(id);
    }

    @Override
    public boolean existsGoods(long id) {
        return goodsRepositoryImpl.exists(id);
    }
}
