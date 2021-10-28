package com.poly.repo;

import com.poly.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer>, JpaSpecificationExecutor<DeliveryAddress> {

}