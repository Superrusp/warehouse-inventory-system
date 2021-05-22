package com.kpi.springlabs.backend.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeliveryItemDto {

    private long id;
    private String deliveryStatus;
}
