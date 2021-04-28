package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.service.GoodsInShopService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/goods-in-shops")
@Slf4j
public class GoodsInShopController {

    private final GoodsInShopService goodsInShopService;

    @Autowired
    public GoodsInShopController(GoodsInShopService goodsInShopService) {
        this.goodsInShopService = goodsInShopService;
    }

    @GetMapping
    public ResponseEntity<?> loadAllGoodsInShops() {
        LOG.debug("Request all goods in shops");
        return ResponseEntity.ok(goodsInShopService.getAllGoodsInShops());
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> loadAllGoodsInShop(@PathVariable long shopId) {
        LOG.debug("Request all goods in shop");
        return ResponseEntity.ok(goodsInShopService.getAllGoodsInShop(shopId));
    }

    @GetMapping("/{shopId}/{goodsId}")
    public ResponseEntity<?> getCertainGoodsInShop(@PathVariable long shopId, @PathVariable long goodsId) {
        LOG.debug("Request special goods in shop");
        return ResponseEntity.ok(goodsInShopService.getCertainGoodsInShop(shopId, goodsId));
    }

    @PostMapping
    public ResponseEntity<?> createGoodsInShop(@RequestBody GoodsInShop goodsInShop) {
        LOG.debug("Request goods in shop creation");
        return ResponseEntity.ok(goodsInShopService.createGoodsInShop(goodsInShop));
    }

    @PutMapping
    public ResponseEntity<?> updateGoodsInShop(@RequestBody GoodsInShop goodsInShop) {
        LOG.debug("Request goods in shop update");
        goodsInShopService.updateGoodsInShop(goodsInShop);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInShop(@PathVariable long id) {
        LOG.debug("Request goods in shop deletion");
        goodsInShopService.deleteGoodsInShop(id);
        return ResponseEntity.ok().build();
    }
}
