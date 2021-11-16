package com.poly.repo;

import com.poly.entity.Description;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

@EnableJpaRepositories
public interface DescriptionRepository extends JpaRepository<Description, Integer>, JpaSpecificationExecutor<Description> {

    List<Description> getDescriptionByProductId(String id);

    @Query("select d from Description d where d.blog.title =?1")
    List<Description> getDescriptionByBlogId(String id);

    @Query("select d from Description d where d.id =:idDes")
    Description getOneDescription(@Param("idDes") Integer id);
}