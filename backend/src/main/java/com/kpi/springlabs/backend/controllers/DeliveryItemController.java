package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.aop.TrackExecutionTime;
import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;
import com.kpi.springlabs.backend.service.DeliveryItemService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/delivery-items")
@Api(value = "Delivery Item Controller", tags = "delivery items")
@Slf4j
public class DeliveryItemController {

    private final DeliveryItemService deliveryItemService;

    @Autowired
    public DeliveryItemController(DeliveryItemService deliveryItemService) {
        this.deliveryItemService = deliveryItemService;
    }

    @ApiOperation(value = "Get all delivery items", response = DeliveryItem.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return delivery items")
    @GetMapping
    public List<DeliveryItem> loadAllDeliveryItems() {
        LOG.debug("Request all delivery items");
        return deliveryItemService.getDeliveryItems();
    }

    @ApiOperation(value = "Get delivery item by id", response = DeliveryItem.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return delivery item"),
            @ApiResponse(code = 404, message = "Delivery item not found")
    })
    @GetMapping("/{id}")
    public DeliveryItem getDeliveryItem(@ApiParam(value = "Delivery Item Id", required = true) @PathVariable long id) {
        LOG.debug("Request special delivery item");
        return deliveryItemService.getDeliveryItemById(id);
    }

    @ApiOperation(value = "Get delivery items by delivery status", response = DeliveryItemDto.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return delivery items")
    @GetMapping("/search")
    @TrackExecutionTime
    public List<DeliveryItemDto> getDeliveryItemByDeliveryStatus(@ApiParam(value = "Delivery Status", required = true)
                                                                 @RequestParam String deliveryStatus) {
        LOG.debug("Request delivery items by delivery status");
        return deliveryItemService.getDeliveryItemByDeliveryStatus(deliveryStatus);
    }

    @ApiOperation(value = "Create delivery item")
    @ApiResponse(code = 201, message = "Delivery item created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public DeliveryItem createDeliveryItem(@ApiParam(value = "Delivery Item", required = true)
                                           @RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item creation");
        return deliveryItemService.createDeliveryItem(deliveryItem);
    }

    @ApiOperation(value = "Update delivery item")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delivery item updated successfully"),
            @ApiResponse(code = 404, message = "Delivery item not found")
    })
    @PutMapping
    public ResponseEntity<?> updateDeliveryItem(@ApiParam(value = "Delivery Item", required = true)
                                                @RequestBody DeliveryItem deliveryItem) {
        LOG.debug("Request delivery item update");
        deliveryItemService.updateDeliveryItem(deliveryItem);
        return ResponseEntity.ok("Delivery item was successfully updated.");
    }

    @ApiOperation(value = "Delete delivery item by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delivery item deleted successfully"),
            @ApiResponse(code = 404, message = "Delivery item not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryItem(@ApiParam(value = "Delivery Item Id", required = true)
                                                @PathVariable long id) {
        LOG.debug("Request delivery item deletion");
        deliveryItemService.deleteDeliveryItem(id);
        return ResponseEntity.ok("Delivery item was successfully deleted.");
    }
}
