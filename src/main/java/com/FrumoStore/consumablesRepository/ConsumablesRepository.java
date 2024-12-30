package com.FrumoStore.consumablesRepository;

import com.FrumoStore.entity.ConsumablesEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ConsumablesRepository extends JpaRepository<ConsumablesEntity, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT c.stock_balance FROM consumables_entity c ORDER BY c.id DESC LIMIT 1")
    Double findLastStockBalance();
}
