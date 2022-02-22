package com.poly.repo;

import com.poly.DTO.CategorySelectOption;
import com.poly.DTO.ProductSelectOption;
import com.poly.entity.Vouchers;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableJpaRepositories
public interface VouchersRepository extends JpaRepository<Vouchers, String>, JpaSpecificationExecutor<Vouchers> {
    /*@Query("select v from Vouchers v where v.actived =true ")
    List<Vouchers> findAllVoucher();

    @Query("select new com.poly.DTO.CategorySelectOption(c.id, c.name) from Category c where c.actived = true ")
    List<CategorySelectOption> cateSelectOption();

    @Query("select new com.poly.DTO.ProductSelectOption( p.id, p.name) from Products p where p.active=true ")
    List<ProductSelectOption> productsSelectOption();

    @Transactional
    @Modifying
    @Query("update Vouchers v set v.actived=false where v.id=?1")
    void updateStatusVoucher(String id);*/
}