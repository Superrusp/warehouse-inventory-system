package com.kpi.springlabs.backend.service.impl;

import com.kpi.springlabs.backend.exception.ConflictException;
import com.kpi.springlabs.backend.exception.ObjectNotFoundException;
import com.kpi.springlabs.backend.mappers.ShopMapper;
import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.model.dto.ShopDto;
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
    private final ShopMapper shopMapper;

    @Autowired
    public ShopServiceImpl(ShopJpaRepositoryImpl shopRepository, ShopMapper shopMapper) {
        this.shopRepository = shopRepository;
        this.shopMapper = shopMapper;
    }

    @Override
    public List<ShopDto> getShops() {
        LOG.debug("Getting all shops");
        List<Shop> shops = shopRepository.getAll();
        return shopMapper.toDtoList(shops);
    }

    @Override
    public ShopDto getShopById(long id) {
        LOG.debug("Getting Shop(id = {})", id);
        Shop shop = shopRepository.getById(id)
                .orElseThrow(() -> {
                    LOG.error("Shop(id = {}) not found", id);
                    return new ObjectNotFoundException(String.format("Shop(id = %s) not found", id));
                });
        return shopMapper.toDto(shop);
    }

    @Override
    @Transactional
    public ShopDto createShop(ShopDto shopDto) {
        Shop shop = shopMapper.toEntityIgnoringId(shopDto);
        LOG.debug("Creating Shop {}", shop);
        Shop createdShop = shopRepository.save(shop)
                .orElseThrow(() -> {
                    LOG.error("Shop {} cannot be created", shop);
                    return new ConflictException(String.format("Shop %s cannot be created", shop));
                });
        return shopMapper.toDto(createdShop);
    }

    @Override
    @Transactional
    public void updateShop(ShopDto shopDto) {
        Shop shop = shopMapper.toEntity(shopDto);
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
