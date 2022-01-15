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

    boolean delete(String id);

    List<CategoryVO> getListPage(Optional<Integer> page, Optional<Integer> row);

    List<CategoryVO> getListByParent(String id);

    CategoryVO findByName(String name);

    List<CategoryVO> findAllByParentId(String id);

    List<CategoryVO> findAllById(String id);

    List<CategoryVO> getAllByParentIdIsNull();
}
