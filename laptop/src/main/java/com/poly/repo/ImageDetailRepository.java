package com.poly.repo;

import com.poly.entity.ImageDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface ImageDetailRepository extends JpaRepository<ImageDetail, Integer>, JpaSpecificationExecutor<ImageDetail> {

    List<ImageDetail> findByProductsDetailSku(String id);
}