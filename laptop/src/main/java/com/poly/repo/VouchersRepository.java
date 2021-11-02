package com.poly.repo;

import com.poly.entity.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface VouchersRepository extends JpaRepository<Vouchers, String>, JpaSpecificationExecutor<Vouchers> {

}