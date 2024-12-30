package com.FrumoStore.consumablesRepository;

import com.FrumoStore.entity.WasteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WasteRepository extends JpaRepository<WasteEntity, Long> {
}
