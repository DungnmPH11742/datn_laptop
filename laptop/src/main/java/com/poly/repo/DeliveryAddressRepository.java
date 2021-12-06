package com.poly.repo;

import com.poly.entity.DeliveryAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface DeliveryAddressRepository extends JpaRepository<DeliveryAddress, Integer>, JpaSpecificationExecutor<DeliveryAddress> {

    @Query("select d from DeliveryAddress d where d.account.id =?1")
    List<DeliveryAddress> getListDeliveryAddressByAccount(Integer idUser);

    @Query("select d from DeliveryAddress d where d.setAsDefault=true")
    List<DeliveryAddress> getDeliveryAddressBySetAsDefaultTrue();

}