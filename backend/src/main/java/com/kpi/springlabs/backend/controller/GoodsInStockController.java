package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.service.GoodsInStockService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/goods-in-stocks")
@Slf4j
public class GoodsInStockController {


    private final GoodsInStockService goodsInStockService;

    @Autowired
    public GoodsInStockController(GoodsInStockService goodsInStockService) {
        this.goodsInStockService = goodsInStockService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllGoodsInStocks() {
        LOG.debug("Request all goods in stocks");
        return ResponseEntity.ok(goodsInStockService.getAllGoodsInStocks());
    }

    @GetMapping("/{stockId}")
    public ResponseEntity<?> loadAllGoodsInStock(@PathVariable long stockId) {
        LOG.debug("Request all goods in stock");
        return ResponseEntity.ok(goodsInStockService.getAllGoodsInStock(stockId));
    }

    @GetMapping("/{stockId}/{goodsId}")
    public ResponseEntity<?> getGoodsInStock(@PathVariable long stockId, @PathVariable long goodsId) {
        LOG.debug("Request special goods in stock");
        return ResponseEntity.ok(goodsInStockService.getCertainGoodsInStock(stockId, goodsId));
    }

    @PostMapping
    public ResponseEntity<?> createGoodsInStock(@RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock creation");
        return ResponseEntity.ok(goodsInStockService.createGoodsInStock(goodsInStock));
    }

    @PutMapping
    public ResponseEntity<?> updateGoodsInStock(@RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock update");
        goodsInStockService.updateGoodsInStock(goodsInStock);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInStock(@PathVariable long id) {
        LOG.debug("Request goods in stock deletion");
        goodsInStockService.deleteGoodsInStock(id);
        return ResponseEntity.ok().build();
    }
}
