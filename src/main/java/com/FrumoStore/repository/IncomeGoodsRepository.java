package com.FrumoStore.repository;

import com.FrumoStore.entity.IncomeGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IncomeGoodsRepository extends JpaRepository<IncomeGoodsEntity, Long> {

    @Modifying
    @Query(
            nativeQuery = true,
            value = "UPDATE consumables_entity SET stock_balance = " +
                    "(SELECT :stockBalance FROM (SELECT 1) AS dummy) " +
                    "WHERE id = (SELECT id FROM consumables_entity ORDER BY id DESC LIMIT 1);"
    )
    void updateStockBalance(@Param("stockBalance") Double stockBalance);
}
