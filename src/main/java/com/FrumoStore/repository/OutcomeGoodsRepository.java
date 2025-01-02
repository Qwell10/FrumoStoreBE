package com.FrumoStore.repository;

import com.FrumoStore.entity.OutcomeGoodsEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OutcomeGoodsRepository extends JpaRepository<OutcomeGoodsEntity, Long> {
}
