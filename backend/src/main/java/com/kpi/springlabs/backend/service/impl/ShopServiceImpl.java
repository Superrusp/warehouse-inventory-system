package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.repository.jpa.impl.ShopJpaRepositoryImpl;
import com.kpi.springlabs.backend.service.ShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Slf4j
public class ShopServiceImpl implements ShopService {

    private final ShopJpaRepositoryImpl shopRepository;

    @Autowired
    public ShopServiceImpl(ShopJpaRepositoryImpl shopRepository) {
        this.shopRepository = shopRepository;
    }

    @Override
    public List<Shop> getShops() {
        LOG.debug("Getting all shops");
        return shopRepository.getAll();
    }

    @Override
    public Shop getShopById(long id) {
        LOG.debug("Getting Shop(id = {})", id);
        return shopRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Shop(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Shop(id = %s) not found", id));
                });
    }

    @Override
    @Transactional
    public Shop createShop(Shop shop) {
        LOG.debug("Creating Shop {}", shop);
        return shopRepository.save(shop)
                .orElseThrow(() -> {
                    LOG.error("Shop {} cannot be created", shop);
                    return new ConflictException(String.format("Shop %s cannot be created", shop));
                });
    }

    @Override
    @Transactional
    public void updateShop(Shop shop) {
        LOG.debug("Updating Shop {}", shop);
        shopRepository.update(shop);
    }

    @Override
    @Transactional
    public void deleteShop(long id) {
        LOG.debug("Deleting Shop(id = {})", id);
        shopRepository.delete(id);
    }
}
