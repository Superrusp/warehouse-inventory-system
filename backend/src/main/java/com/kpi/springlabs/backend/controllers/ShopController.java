package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.Shop;
import com.kpi.springlabs.backend.service.ShopService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/shops")
@Api(value = "Shop Controller", tags = "shops")
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @ApiOperation(value = "Get all shops", response = Shop.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return shops")
    @GetMapping
    public List<Shop> loadAllShops() {
        LOG.debug("Request all shops");
        return shopService.getShops();
    }

    @ApiOperation(value = "Get shop by id", response = Shop.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return shop"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @GetMapping("/{id}")
    public Shop getShop(@ApiParam(value = "Shop Id") @PathVariable long id) {
        LOG.debug("Request special shop");
        return shopService.getShopById(id);
    }

    @ApiOperation(value = "Create shop")
    @ApiResponse(code = 201, message = "Shop created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public Shop createShop(@ApiParam(value = "Shop") @RequestBody Shop shop) {
        LOG.debug("Request shop creation");
        return shopService.createShop(shop);
    }

    @ApiOperation(value = "Update shop")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop updated successfully"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @PutMapping
    public ResponseEntity<?> updateShop(@ApiParam(value = "Shop") @RequestBody Shop shop) {
        LOG.debug("Request shop update");
        shopService.updateShop(shop);
        return ResponseEntity.ok().build();
    }

    @ApiOperation(value = "Delete shop by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop deleted successfully"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@ApiParam(value = "Shop Id") @PathVariable long id) {
        LOG.debug("Request shop deletion");
        shopService.deleteShop(id);
        return ResponseEntity.ok().build();
    }
}
