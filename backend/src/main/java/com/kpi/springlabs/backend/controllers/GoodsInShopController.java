package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.dto.GoodsInShopDto;
import com.kpi.springlabs.backend.security.access.AdminPermission;
import com.kpi.springlabs.backend.service.GoodsInShopService;
import com.kpi.springlabs.backend.validation.constraints.groups.ExcludeIdValidation;
import com.kpi.springlabs.backend.validation.constraints.groups.IncludeIdValidation;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/goods-in-shops")
@Api(value = "Goods In Shop Controller", tags = "goods in shops")
@Slf4j
public class GoodsInShopController {

    private final GoodsInShopService goodsInShopService;

    @Autowired
    public GoodsInShopController(GoodsInShopService goodsInShopService) {
        this.goodsInShopService = goodsInShopService;
    }

    @ApiOperation(value = "Get all goods in shops", response = GoodsInShopDto.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return goods in shops")
    @GetMapping
    public List<GoodsInShopDto> loadAllGoodsInShops() {
        LOG.debug("Request all goods in shops");
        return goodsInShopService.getAllGoodsInShops();
    }

    @ApiOperation(value = "Get goods by shop id", response = GoodsInShopDto.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return goods in shop")
    @GetMapping("/{shopId}")
    public List<GoodsInShopDto> loadAllGoodsInShop(@ApiParam(value = "Shop Id", required = true) @PathVariable long shopId) {
        LOG.debug("Request all goods in shop");
        return goodsInShopService.getAllGoodsInShop(shopId);
    }

    @ApiOperation(value = "Get special goods by shop id", response = GoodsInShopDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return goods in shop"),
            @ApiResponse(code = 404, message = "Goods in shop not found")
    })
    @GetMapping("/{shopId}/{goodsId}")
    public GoodsInShopDto getCertainGoodsInShop(@ApiParam(value = "Shop Id", required = true) @PathVariable long shopId,
                                                @ApiParam(value = "Goods Id", required = true) @PathVariable long goodsId) {
        LOG.debug("Request special goods in shop");
        return goodsInShopService.getCertainGoodsInShop(shopId, goodsId);
    }

    @ApiOperation(value = "Create goods in shop", response = GoodsInShopDto.class)
    @ApiResponse(code = 201, message = "Goods in shop created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @AdminPermission
    @PostMapping
    public GoodsInShopDto createGoodsInShop(@ApiParam(value = "Goods In Shop", required = true)
                                            @Validated(ExcludeIdValidation.class)
                                            @RequestBody GoodsInShopDto goodsInShop) {
        LOG.debug("Request goods in shop creation");
        return goodsInShopService.createGoodsInShop(goodsInShop);
    }

    @ApiOperation(value = "Update goods in shop")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods in shop updated successfully"),
            @ApiResponse(code = 404, message = "Goods in shop not found")
    })
    @AdminPermission
    @PutMapping
    public ResponseEntity<?> updateGoodsInShop(@ApiParam(value = "Goods In Shop", required = true)
                                               @Validated(IncludeIdValidation.class)
                                               @RequestBody GoodsInShopDto goodsInShopDto) {
        LOG.debug("Request goods in shop update");
        goodsInShopService.updateGoodsInShop(goodsInShopDto);
        return ResponseEntity.ok("Goods in shop was successfully updated.");
    }

    @ApiOperation(value = "Delete goods in shop by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods in shop deleted successfully"),
            @ApiResponse(code = 404, message = "Goods in shop not found")
    })
    @AdminPermission
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInShop(@ApiParam(value = "Goods In Shop Id", required = true)
                                               @PathVariable long id) {
        LOG.debug("Request goods in shop deletion");
        goodsInShopService.deleteGoodsInShop(id);
        return ResponseEntity.ok("Goods in shop was successfully deleted.");
    }
}
