package com.poly.repo;

import com.poly.entity.Account;
import com.poly.entity.Orders;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.persistence.criteria.Order;
import java.util.List;

@EnableJpaRepositories
public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    @Query("select o from Orders o where o.account.id =?1")
    List<Orders> findByIdAccount(Integer idAccount);
}