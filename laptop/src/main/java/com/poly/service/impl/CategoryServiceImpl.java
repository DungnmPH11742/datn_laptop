package com.poly.service.impl;

import com.poly.entity.Category;
import com.poly.repo.CategoryRepository;
import com.poly.service.CategoryService;
import com.poly.vo.CategoryVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private CategoryRepository repository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryVO> getList() {
        List<CategoryVO> vos = new ArrayList<>();
        repository.findAll().forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    @Override
    public CategoryVO getOne(String id) {

        return null;
    }

    @Override
    public CategoryVO create(CategoryVO vo) {
        return null;
    }

    @Override
    public CategoryVO update(CategoryVO vo) {
        return null;
    }

    @Override
    public void delete(String id) {

    }

    @Override
    public List<CategoryVO> getListPage(Optional<Integer> page, Optional<Integer> row) {
        return null;
    }

    @Override
    public List<CategoryVO> getListByParent(Integer id) {
        List<CategoryVO> vos = new ArrayList<>();
        repository.getListByParent(id).forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }
}
