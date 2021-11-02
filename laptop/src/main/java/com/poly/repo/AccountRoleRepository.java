package com.poly.repo;

import com.poly.entity.AccountRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AccountRoleRepository extends JpaRepository<AccountRole, Integer>, JpaSpecificationExecutor<AccountRole> {

}