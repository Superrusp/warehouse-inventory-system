package com.kpi.springlabs.backend.controller;

import com.kpi.springlabs.backend.model.GoodsInShop;
import com.kpi.springlabs.backend.repository.GoodsInShopRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/goods-in-shop")
@Slf4j
public class GoodsInShopController {

    private final GoodsInShopRepository goodsInShopRepository;

    @Autowired
    public GoodsInShopController(GoodsInShopRepository goodsInShopRepository) {
        this.goodsInShopRepository = goodsInShopRepository;
    }

    @GetMapping("/{shopId}")
    public ResponseEntity<?> loadAllGoodsInShop(@PathVariable long shopId) {
        LOG.debug("Request all goods in shop");
        return ResponseEntity.ok(goodsInShopRepository.getAllGoodsByShopId(shopId));
    }

    @GetMapping("/{shopId}/{goodsId}")
    public ResponseEntity<?> getGoodsInShop(@PathVariable long shopId, @PathVariable long goodsId) {
        LOG.debug("Request special goods in shop");
        return ResponseEntity.ok(goodsInShopRepository.getGoodsByIdAndShop(shopId, goodsId));
    }

    @PostMapping
    public ResponseEntity<?> createGoodsInShop(@RequestBody GoodsInShop goodsInShop) {
        LOG.debug("Request goods in shop creation");
        return ResponseEntity.ok(goodsInShopRepository.insert(goodsInShop));
    }

    @PutMapping
    public ResponseEntity<?> updateGoodsInShop(@RequestBody GoodsInShop goodsInShop) {
        LOG.debug("Request goods in shop update");
        goodsInShopRepository.update(goodsInShop);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInShop(@PathVariable long id) {
        LOG.debug("Request goods in shop deletion");
        goodsInShopRepository.delete(id);
        return ResponseEntity.ok().build();
    }
}
