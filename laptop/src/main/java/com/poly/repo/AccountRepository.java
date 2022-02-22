package com.poly.repo;

import com.poly.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface AccountRepository extends JpaRepository<Account, Integer>, JpaSpecificationExecutor<Account> {

    @Query("select a from Account a where a.email =?1")
    Account findUserAccount(String email);
    void deleteById(Integer id);

    Account findByVerificationCode(String code);

    @Query("select a from Account a where a.verificationCode =?1")
    Account findUserAccountByToken(String token);

    Account findByResetPasswordToken(String token);

    @Query("select a from Account a where a.actived = true ")
    List<Account> findAllByActived();
}