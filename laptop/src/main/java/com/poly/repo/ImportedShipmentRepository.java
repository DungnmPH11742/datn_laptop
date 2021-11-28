package com.poly.repo;

import com.poly.entity.ImportedShipment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface ImportedShipmentRepository extends JpaRepository<ImportedShipment, Integer>, JpaSpecificationExecutor<ImportedShipment> {

}