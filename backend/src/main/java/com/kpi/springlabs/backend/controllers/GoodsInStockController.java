package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.GoodsInStock;
import com.kpi.springlabs.backend.security.access.AdminPermission;
import com.kpi.springlabs.backend.service.GoodsInStockService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/goods-in-stocks")
@Api(value = "Goods In Stock Controller", tags = "goods in stocks")
@AdminPermission
@Slf4j
public class GoodsInStockController {


    private final GoodsInStockService goodsInStockService;

    @Autowired
    public GoodsInStockController(GoodsInStockService goodsInStockService) {
        this.goodsInStockService = goodsInStockService;
    }

    @ApiOperation(value = "Get all goods in stocks", response = GoodsInStock.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return goods in stocks")
    @GetMapping
    public List<GoodsInStock> loadAllGoodsInStocks() {
        LOG.debug("Request all goods in stocks");
        return goodsInStockService.getAllGoodsInStocks();
    }

    @ApiOperation(value = "Get goods by stock id", response = GoodsInStock.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return goods in stock")
    @GetMapping("/{stockId}")
    public List<GoodsInStock> loadAllGoodsInStock(@ApiParam(value = "Stock Id", required = true)
                                                  @PathVariable long stockId) {
        LOG.debug("Request all goods in stock");
        return goodsInStockService.getAllGoodsInStock(stockId);
    }

    @ApiOperation(value = "Get special goods by stock id", response = GoodsInStock.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return goods in stock"),
            @ApiResponse(code = 404, message = "Goods in stock not found")
    })
    @GetMapping("/{stockId}/{goodsId}")
    public GoodsInStock getGoodsInStock(@ApiParam(value = "Stock Id", required = true) @PathVariable long stockId,
                                        @ApiParam(value = "Goods Id", required = true) @PathVariable long goodsId) {
        LOG.debug("Request special goods in stock");
        return goodsInStockService.getCertainGoodsInStock(stockId, goodsId);
    }

    @ApiOperation(value = "Create goods in stock")
    @ApiResponse(code = 201, message = "Goods in stock created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public GoodsInStock createGoodsInStock(@ApiParam(value = "Goods In Stock", required = true)
                                           @RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock creation");
        return goodsInStockService.createGoodsInStock(goodsInStock);
    }

    @ApiOperation(value = "Update goods in stock")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods in stock updated successfully"),
            @ApiResponse(code = 404, message = "Goods in stock not found")
    })
    @PutMapping
    public ResponseEntity<?> updateGoodsInStock(@ApiParam(value = "Goods In Stock", required = true)
                                                @RequestBody GoodsInStock goodsInStock) {
        LOG.debug("Request goods in stock update");
        goodsInStockService.updateGoodsInStock(goodsInStock);
        return ResponseEntity.ok("Goods in shop was successfully updated.");
    }

    @ApiOperation(value = "Delete goods in stock by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods in stock deleted successfully"),
            @ApiResponse(code = 404, message = "Goods in stock not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoodsInStock(@ApiParam(value = "Goods In Stock Id", required = true)
                                                @PathVariable long id) {
        LOG.debug("Request goods in stock deletion");
        goodsInStockService.deleteGoodsInStock(id);
        return ResponseEntity.ok("Goods in stock was successfully deleted.");
    }
}
