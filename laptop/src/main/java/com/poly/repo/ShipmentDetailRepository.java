package com.poly.repo;

import com.poly.entity.ShipmentDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ShipmentDetailRepository extends JpaRepository<ShipmentDetail, Integer>, JpaSpecificationExecutor<ShipmentDetail> {

}