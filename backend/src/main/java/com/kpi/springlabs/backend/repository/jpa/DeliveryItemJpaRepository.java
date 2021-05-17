package com.kpi.springlabs.backend.repository.jpa;

import com.kpi.springlabs.backend.model.DeliveryItem;
import com.kpi.springlabs.backend.model.dto.DeliveryItemDto;

import java.util.List;

public interface DeliveryItemJpaRepository extends JpaBaseRepository<DeliveryItem> {

    List<DeliveryItemDto> findDeliveryItemsByDeliveryStatus(String deliveryStatus);
}
