package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.dto.GoodsDto;
import com.kpi.springlabs.backend.model.dto.response.GoodsNameResponse;

import java.util.List;

public interface GoodsService {

    List<GoodsDto> getAllGoods();

    GoodsDto getGoodsById(long id);

    GoodsNameResponse getGoodsByName(String name);

    GoodsDto createGoods(GoodsDto goodsDto);

    void updateGoods(GoodsDto goodsDto);

    void deleteGoods(long id);

    boolean existsGoods(long id);
}
