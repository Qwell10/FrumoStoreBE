package com.FrumoStore.incomingConsumablesRepository;

import com.FrumoStore.entity.IncomingConsumablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomingConsumablesRep extends JpaRepository<IncomingConsumablesEntity,Long> {
}
