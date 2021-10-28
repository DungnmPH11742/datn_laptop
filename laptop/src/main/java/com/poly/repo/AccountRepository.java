package com.poly.repo;

import com.poly.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    @Query("select a from Account a where a.email =?1 and a.actived= true")
    Account findUserAccount(String email);
    @Query("select a from Account a where a.email =?1 and a.actived= false")
    Account findUserAccountByEmailFalse(String email);
    void deleteById(Integer id);
    Account findByEmail(String email);
    Account findByVerificationCode(String code);
    @Query("select a from Account a where a.verificationCode =?1")
    Account findUserAccountByToken(String token);
}