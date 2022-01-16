package com.poly.repo;

import com.poly.entity.Orders;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;

@EnableJpaRepositories
public interface OrdersRepository extends JpaRepository<Orders, Integer>, JpaSpecificationExecutor<Orders> {

    List<Orders> findByReceivedIsNot(int revered);

    @Query("select o from Orders o where o.account.id =:idAccount order by o.orderDate desc, o.received asc")
//    @Query(value = "select * from dbo.[orders] o where o.id_account =?1", nativeQuery = true)
    List<Orders> findByIdAccount(@Param("idAccount") Integer idAccount);

    @Query("select o from Orders o where o.account.id =:idAccount")
    Page<Orders> findAllByAccountPage(@Param("idAccount") Integer idAccount, Pageable pageable);

    @Query("select o from Orders o where o.account.email =?1 and o.received = -2")
    Orders findByEmailAccount(String email);

    //    @Query("select o from Orders o where o.orderCode =:code")
    @Query(value = "select * from orders o where o.order_code =?1", nativeQuery = true)
    List<Orders> findOrderByCodeOrder(String code);


    @Query(value = "select o from Orders o where o.received =?1 and o.account.id =?2")
    Page<Orders> findOrderByReceivedOrderPage(Integer received, Integer account, Pageable pageable);

    List<Orders> findAllByAccount_EmailAndCompletionDateNotNull(String email);

    List<Orders> findAllByAccountEmailAndCompletionDateNotNull(String email);

    @Query("select o from Orders o where o.received <> -2 and o.received =?1 order by o.id")
    List<Orders> findAllOrdersByReceived(Integer received);

    @Query("select o from Orders o where o.received <> -2 and o.received =?1 order by o.id")
    List<Orders> findAllOrdersByReceivedAndNameAccount(Integer received);
}