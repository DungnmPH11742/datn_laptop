package com.poly.repo;

import com.poly.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, String>, JpaSpecificationExecutor<Category> {

    @Query("select c from Category c where c.parentId =?1")
    List<Category> getListByParent(String id);

    List<Category> findByParentIdIsNull();

    Category findByName(String name);

    List<Category> findAllByParentId(String id);

    List<Category> findAllById(String id);

    List<Category> getAllByParentIdIsNull();
}