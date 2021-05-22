package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.service.GoodsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/goods")
@Slf4j
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllGoods() {
        LOG.debug("Request all goods");
        return ResponseEntity.ok(goodsService.getAllGoods());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getGoods(@PathVariable long id) {
        LOG.debug("Request special goods");
        return ResponseEntity.ok(goodsService.getGoodsById(id));
    }

    @GetMapping("/search")
    @TrackExecutionTime
    public ResponseEntity<?> getGoodsByName(@RequestParam String name) {
        LOG.debug("Request special goods by its name");
        return ResponseEntity.ok(goodsService.getGoodsByName(name));
    }

    @PostMapping
    public ResponseEntity<?> createGoods(@RequestBody Goods goods) {
        LOG.debug("Request goods creation");
        return ResponseEntity.ok(goodsService.createGoods(goods));
    }

    @PutMapping
    public ResponseEntity<?> updateGoods(@RequestBody Goods goods) {
        LOG.debug("Request goods update");
        goodsService.updateGoods(goods);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@PathVariable long id) {
        LOG.debug("Request goods deletion");
        goodsService.deleteGoods(id);
        return ResponseEntity.ok().build();
    }
}
