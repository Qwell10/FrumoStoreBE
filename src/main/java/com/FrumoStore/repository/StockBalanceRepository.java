package com.FrumoStore.repository;

import com.FrumoStore.entity.StockBalanceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface StockBalanceRepository extends JpaRepository<StockBalanceEntity, Long> {

    @Query(
            nativeQuery = true,
            value = "SELECT c.stock_balance FROM stock_balance_entity c ORDER BY c.id DESC LIMIT 1")
    Double findLastStockBalance();
}
