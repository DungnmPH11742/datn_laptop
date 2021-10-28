package com.poly.repo;

import com.poly.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface DescriptionRepository extends JpaRepository<Description, Integer>, JpaSpecificationExecutor<Description> {

}