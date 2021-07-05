package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.response.GoodsNameResponse;

import java.util.Optional;

public interface GoodsJpaRepository extends JpaBaseRepository<Goods> {

    Optional<GoodsNameResponse> findByName(String name);
}
