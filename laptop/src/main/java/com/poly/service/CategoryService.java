package com.poly.service;

import com.poly.vo.CategoryVO;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<CategoryVO> getList();

    List<CategoryVO> getNodeCate();

    CategoryVO getOne(String id);

    CategoryVO create(CategoryVO vo);

    CategoryVO update(CategoryVO vo);

    void delete(String id);

    List<CategoryVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<CategoryVO> getListByParent(Integer id);
}
