package com.poly.service;

import com.poly.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryVO> getList();

    CategoryVO getOne(String id);

    CategoryVO create(CategoryVO vo);

    CategoryVO update(CategoryVO vo);

    void delete(String id);

    List<CategoryVO> getListPage(Optional<Integer> page, Optional<Integer> row);
}
