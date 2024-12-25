package com.FrumoStore.incomingConsumablesRepository;

import com.FrumoStore.entity.DeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRep extends JpaRepository<DeliveryEntity, Long> {
}
