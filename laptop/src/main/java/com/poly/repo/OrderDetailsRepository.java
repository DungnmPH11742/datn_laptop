package com.poly.repo;

import com.poly.entity.OrderDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
<<<<<<< HEAD
=======

import javax.transaction.Transactional;
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2

@EnableJpaRepositories
public interface OrderDetailsRepository extends JpaRepository<OrderDetails, Integer>, JpaSpecificationExecutor<OrderDetails> {
    @Transactional
    @Modifying
    @Query("update OrderDetails o set o.quantity=?1 where o.id =?2")
    void updateQuantityOrderDetail(Integer quan,Integer id);

    @Query("select od from OrderDetails  od where od.order.id =:idOrder")
    List<OrderDetails> getOrderDetailsByOrder(@Param("idOrder") Integer idOder);

<<<<<<< HEAD
    @Query("select od from OrderDetails  od where od.order.id =:idOrder")
    List<OrderDetails> getOrderDetailsByOrder(@Param("idOrder") Integer idOder);

=======
>>>>>>> c2400cd1e734dc5f4f8d76a75ee825f00156bab2
    @Transactional
    @Modifying
    @Query("update OrderDetails o set o.quantity=?1 where o.id =?2")
    void updateQuantityOrderDetail(Integer quan,Integer id);
}