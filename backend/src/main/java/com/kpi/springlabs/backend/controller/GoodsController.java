package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.repository.impl.GoodsRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods")
@Slf4j
public class GoodsController {

    private final GoodsRepository goodsRepository;

    @Autowired
    public GoodsController(GoodsRepository goodsRepository) {
        this.goodsRepository = goodsRepository;
    }

    @GetMapping
    public ResponseEntity<?> loadAllGoods() {
        LOG.debug("Request all goods");
        return ResponseEntity.ok(goodsRepository.getAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoods(@PathVariable long id) {
        LOG.debug("Request special goods");
        return ResponseEntity.ok(goodsRepository.getById(id));
    }

    @PostMapping
    public ResponseEntity<?> createGoods(@RequestBody Goods goods) {
        LOG.debug("Request goods creation");
        return ResponseEntity.ok(goodsRepository.insert(goods));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Goods goods) {
        LOG.debug("Request goods update");
        goodsRepository.update(goods);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request goods deletion");
        goodsRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
