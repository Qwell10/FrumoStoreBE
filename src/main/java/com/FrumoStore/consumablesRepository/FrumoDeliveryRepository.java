package com.FrumoStore.consumablesRepository;

import com.FrumoStore.entity.FrumoDeliveryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FrumoDeliveryRepository extends JpaRepository<FrumoDeliveryEntity, Long> {
}
