package com.FrumoStore.incomingConsumablesRepository;

import com.FrumoStore.entity.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteRep extends JpaRepository<WasteEntity, Long> {
}
