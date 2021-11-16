package com.poly.repo;

import com.poly.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.Calendar;
import java.util.List;
import java.util.Set;

@EnableJpaRepositories
public interface CategoryRepository extends JpaRepository<Category, Integer>, JpaSpecificationExecutor<Category> {

    @Query("select c from Category c where c.parentId =?1")
    List<Category> getListByParent(Integer id);
    Category findByName(String name);
   List<Category> findAllByParentId(Integer id);
    Category findByParentId(Integer id);
   List<Category> findAllById(Integer id);
}