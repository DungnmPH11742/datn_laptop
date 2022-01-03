package com.poly.repo;

import com.poly.entity.Blogs;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.util.List;

@EnableJpaRepositories
public interface BlogsRepository extends JpaRepository<Blogs, String> {

    @Query(value = "SELECT * FROM (SELECT *, ROW_NUMBER() OVER (ORDER BY id) as RowNum from dbo.[blogs] where is_hot=0) as MyTable WHERE MyTable.RowNum between ?1 and ?2",
            nativeQuery = true)
    List<Blogs> getFlowByNumber(Integer start, Integer end);

    @Query(value = "select b from Blogs b where b.isHot=true")
    List<Blogs> getIsHot();

    @Query(value = "select * from dbo.[blogs] b ORDER BY b.date_created DESC ", nativeQuery = true)
    List<Blogs> getBlogsDesc();
}