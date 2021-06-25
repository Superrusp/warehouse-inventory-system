package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.model.Goods;
import com.kpi.springlabs.backend.model.dto.GoodsDto;
import com.kpi.springlabs.backend.security.access.AdminPermission;
import com.kpi.springlabs.backend.service.GoodsService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/goods")
@Api(value = "Goods Controller", tags = "goods")
@Slf4j
public class GoodsController {

    private final GoodsService goodsService;

    @Autowired
    public GoodsController(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @ApiOperation(value = "Get all goods", response = Goods.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return goods")
    @GetMapping
    public List<Goods> loadAllGoods() {
        LOG.debug("Request all goods");
        return goodsService.getAllGoods();
    }

    @ApiOperation(value = "Get goods by id", response = Goods.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return goods"),
            @ApiResponse(code = 404, message = "Goods not found")
    })
    @GetMapping("/{id}")
    public Goods getGoods(@ApiParam(value = "Goods Id", required = true) @PathVariable long id) {
        LOG.debug("Request special goods");
        return goodsService.getGoodsById(id);
    }

    @ApiOperation(value = "Get goods by name", response = GoodsDto.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return goods"),
            @ApiResponse(code = 404, message = "Goods not found")
    })
    @GetMapping("/search")
    @TrackExecutionTime
    public GoodsDto getGoodsByName(@ApiParam(value = "Goods Name", required = true) @RequestParam String name) {
        LOG.debug("Request special goods by its name");
        return goodsService.getGoodsByName(name);
    }

    @ApiOperation(value = "Create goods")
    @ApiResponse(code = 201, message = "Goods created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @AdminPermission
    @PostMapping
    public Goods createGoods(@ApiParam(value = "Goods", required = true) @RequestBody Goods goods) {
        LOG.debug("Request goods creation");
        return goodsService.createGoods(goods);
    }

    @ApiOperation(value = "Update goods")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods updated successfully"),
            @ApiResponse(code = 404, message = "Goods not found")
    })
    @AdminPermission
    @PutMapping
    public ResponseEntity<?> updateGoods(@ApiParam(value = "Goods", required = true) @RequestBody Goods goods) {
        LOG.debug("Request goods update");
        goodsService.updateGoods(goods);
        return ResponseEntity.ok("Goods was successfully updated.");
    }

    @ApiOperation(value = "Delete goods by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Goods deleted successfully"),
            @ApiResponse(code = 404, message = "Goods not found")
    })
    @AdminPermission
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteGoods(@ApiParam(value = "Goods Id", required = true) @PathVariable long id) {
        LOG.debug("Request goods deletion");
        goodsService.deleteGoods(id);
        return ResponseEntity.ok("Goods was successfully deleted.");
    }
}
