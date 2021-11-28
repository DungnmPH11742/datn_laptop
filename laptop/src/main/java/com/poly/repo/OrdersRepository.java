package com.poly.repo;

import com.poly.entity.Account;
import com.poly.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import javax.persistence.criteria.Order;
import java.util.List;

@EnableJpaRepositories
public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    @Query("select o from Orders o where o.account.email =?1 and o.received = -2")
    Orders findByEmailAccount(String email);

    @Query("select o  from Orders  o where o.orderCode =:code")
    Orders getOrderByCode(@Param("code") String code);
}