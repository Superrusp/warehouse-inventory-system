package com.kpi.springlabs.backend.controllers;

import com.kpi.springlabs.backend.model.DeliveryRequest;
import com.kpi.springlabs.backend.service.DeliveryRequestService;
import io.swagger.annotations.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/delivery-requests")
@Api(value = "Delivery Request Controller", tags = "delivery requests")
@Slf4j
public class DeliveryRequestController {

    private final DeliveryRequestService deliveryRequestService;

    @Autowired
    public DeliveryRequestController(DeliveryRequestService deliveryRequestService) {
        this.deliveryRequestService = deliveryRequestService;
    }

    @ApiOperation(value = "Get all delivery requests", response = DeliveryRequest.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return delivery requests")
    @GetMapping
    public List<DeliveryRequest> loadAllDeliveryRequests() {
        LOG.debug("Request all delivery requests");
        return deliveryRequestService.getDeliveryRequests();
    }

    @ApiOperation(value = "Get delivery request by id", response = DeliveryRequest.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Return delivery request"),
            @ApiResponse(code = 404, message = "Delivery request not found")
    })
    @GetMapping("/{id}")
    public DeliveryRequest getDeliveryRequest(@ApiParam(value = "Delivery Request Id", required = true)
                                              @PathVariable long id) {
        LOG.debug("Request special delivery request");
        return deliveryRequestService.getDeliveryRequestById(id);
    }

    @ApiOperation(value = "Get delivery requests by goods id", response = DeliveryRequest.class, responseContainer = "List")
    @ApiResponse(code = 200, message = "Return delivery requests")
    @GetMapping("/goods")
    public List<DeliveryRequest> getDeliveryRequestsOfGoods(@ApiParam(value = "Goods Id", required = true)
                                                            @RequestParam long goodsId) {
        LOG.debug("Request delivery requests of goods");
        return deliveryRequestService.getDeliveryRequestsByGoodsId(goodsId);
    }

    @ApiOperation(value = "Create delivery request")
    @ApiResponse(code = 201, message = "Delivery request created successfully")
    @ResponseStatus(code = HttpStatus.CREATED)
    @PostMapping
    public DeliveryRequest createDeliveryRequest(@ApiParam(value = "Delivery Request", required = true)
                                                 @RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request creation");
        return deliveryRequestService.createDeliveryRequest(deliveryRequest);
    }

    @ApiOperation(value = "Update delivery request")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delivery request updated successfully"),
            @ApiResponse(code = 404, message = "Delivery request not found")
    })
    @PutMapping
    public ResponseEntity<?> updateDeliveryRequest(@ApiParam(value = "Delivery Request", required = true)
                                                   @RequestBody DeliveryRequest deliveryRequest) {
        LOG.debug("Request delivery request update");
        deliveryRequestService.updateDeliveryRequest(deliveryRequest);
        return ResponseEntity.ok("Delivery request was successfully updated.");
    }

    @ApiOperation(value = "Delete delivery request by id")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delivery request deleted successfully"),
            @ApiResponse(code = 404, message = "Delivery request not found")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteDeliveryRequest(@ApiParam(value = "Delivery Request Id", required = true)
                                                   @PathVariable long id) {
        LOG.debug("Request delivery request deletion");
        deliveryRequestService.deleteDeliveryRequest(id);
        return ResponseEntity.ok("Delivery request was successfully deleted.");
    }
}
