package com.poly.repo;

import com.poly.entity.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface VouchersRepository extends JpaRepository<Vouchers, String>, JpaSpecificationExecutor<Vouchers> {

    @Query("select v from Vouchers v where v.actived =true ")
    List<Vouchers> findAllVoucher();
}