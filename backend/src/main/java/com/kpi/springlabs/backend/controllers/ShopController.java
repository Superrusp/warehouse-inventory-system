package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.dto.ShopDto;
import com.kpi.springlabs.backend.security.access.AdminPermission;
import com.kpi.springlabs.backend.service.ShopService;
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
@RequestMapping("api/shops")
@Api(value = "Shop Controller", tags = "shops")
@Slf4j
public class ShopController {

    private final ShopService shopService;

    @Autowired
    public ShopController(ShopService shopService) {
        this.shopService = shopService;
    }

    @ApiOperation(value = "Get all shops", response = ShopDto.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return shops")
    @GetMapping
    public List<ShopDto> loadAllShops() {
        LOG.debug("Request all shops");
        return shopService.getShops();
    }

    @ApiOperation(value = "Get shop by id", response = ShopDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return shop"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @GetMapping("/{id}")
    public ShopDto getShop(@ApiParam(value = "Shop Id", required = true) @PathVariable long id) {
        LOG.debug("Request special shop");
        return shopService.getShopById(id);
    }

    @ApiOperation(value = "Create shop", response = ShopDto.class)
    @ApiResponse(code = 201, message = "Shop created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @AdminPermission
    @PostMapping
    public ShopDto createShop(@ApiParam(value = "Shop", required = true)
                              @Validated(ExcludeIdValidation.class) @RequestBody ShopDto shopDto) {
        LOG.debug("Request shop creation");
        return shopService.createShop(shopDto);
    }

    @ApiOperation(value = "Update shop")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop updated successfully"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @AdminPermission
    @PutMapping
    public ResponseEntity<?> updateShop(@ApiParam(value = "Shop", required = true)
                                        @Validated(IncludeIdValidation.class) @RequestBody ShopDto shopDto) {
        LOG.debug("Request shop update");
        shopService.updateShop(shopDto);
        return ResponseEntity.ok("Shop was successfully updated.");
    }

    @ApiOperation(value = "Delete shop by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Shop deleted successfully"),
            @ApiResponse(code = 404, message = "Shop not found")
    })
    @AdminPermission
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteShop(@ApiParam(value = "Shop Id", required = true) @PathVariable long id) {
        LOG.debug("Request shop deletion");
        shopService.deleteShop(id);
        return ResponseEntity.ok("Shop was successfully deleted.");
    }
}
