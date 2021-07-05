package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.ShopDto;

import java.util.List;

public interface ShopService {

    List<ShopDto> getShops();

    ShopDto getShopById(long id);

    ShopDto createShop(ShopDto shopDto);

    void updateShop(ShopDto shopDto);

    void deleteShop(long id);
}
