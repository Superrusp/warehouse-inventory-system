package com.kpi.springlabs.backend.mappers;

import com.kpi.springlabs.backend.mappers.config.BaseMapperConfig;
import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.model.dto.GoodsInShopDto;
import org.mapstruct.Mapper;

@Mapper(config = BaseMapperConfig.class)
public interface GoodsInShopMapper extends BaseMapper<GoodsInShop, GoodsInShopDto> {
}
