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

<<<<<<< HEAD
    @Query("select o from Orders o where o.account.id =:idAccount")
//    @Query(value = "select * from dbo.[orders] o where o.id_account =?1", nativeQuery = true)
    List<Orders> findByIdAccount(@Param("idAccount") Integer idAccount);
=======
    @Query("select o from Orders o where o.account.email =?1 and o.received = -2")
    Orders findByEmailAccount(String email);
>>>>>>> 27f2e6e45a9b5994cd973e713bcb841756d5df06


    @Query("select o from Orders o where o.account.email =?1 and o.received = -2")
    Orders findByEmailAccount(String email);

//    @Query("select o from Orders o where o.orderCode =:code")
    @Query(value = "select * from dbo.[orders] o where o.order_code =?1", nativeQuery = true)
    Orders findOrderByCodeOrder( String code);
    /*@Query(value = "select * from dbo.[orders] o where o.order_code =?1", nativeQuery = true)
    List<Orders> getOrderByCode(String code);*/
}