package com.poly.repo;

import com.poly.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.transaction.Transactional;

@EnableJpaRepositories
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>, JpaSpecificationExecutor<OrderDetails> {
    @Transactional
    @Modifying
    @Query("update OrderDetails o set o.quantity=?1 where o.id =?2")
    void updateQuantityOrderDetail(Integer quan,Integer id);

}