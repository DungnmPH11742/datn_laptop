package com.poly.repo;

import com.poly.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaRepositories
public interface BlogsRepository extends JpaRepository<Blogs, String>, JpaSpecificationExecutor<Blogs> {

}