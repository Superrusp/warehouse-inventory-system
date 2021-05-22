package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.GoodsDto;

import java.util.Optional;

public interface GoodsJpaRepository extends JpaBaseRepository<Goods> {

    Optional<GoodsDto> findByName(String name);
}
