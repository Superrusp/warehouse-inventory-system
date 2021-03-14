package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.repository.GoodsInStockRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods-in-stock")
@Slf4j
public class GoodsInStockController {


    private final GoodsInStockRepository goodsInStockRepository;

    @Autowired
    public GoodsInStockController(GoodsInStockRepository goodsInStockRepository) {
        this.goodsInStockRepository = goodsInStockRepository;
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<?> loadAllGoodsInStock(@PathVariable long stockId) {
        LOG.debug("Request all goods in stock");
        return ResponseEntity.ok(goodsInStockRepository.getAllGoodsByStockId(stockId));
    }

    @GetMapping("/{stockId}/{goodsId}")
    public ResponseEntity<?> getGoodsInStock(@PathVariable long stockId, @PathVariable long goodsId) {
        LOG.debug("Request special goods in stock");
        return ResponseEntity.ok(goodsInStockRepository.getGoodsByIdAndStock(stockId, goodsId));
    }

    @PostMapping
    public ResponseEntity<?> createGoodsInStock(@RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock creation");
        return ResponseEntity.ok(goodsInStockRepository.insert(goodsInStock));
    }

    @PutMapping
    public ResponseEntity<?> updateGoodsInStock(@RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock update");
        goodsInStockRepository.update(goodsInStock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInStock(@PathVariable long id) {
        LOG.debug("Request goods in stock deletion");
        goodsInStockRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
