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
    public List<CategoryVO> getAllCategory() {
        List<CategoryVO> vos = new ArrayList<>();
        repository.findAll().forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }


    @Override
    public Category saveCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public Category updateCategory(Category category) {
        return repository.save(category);
    }

    @Override
    public void deleteById(Integer id) {
      repository.deleteById(id);
    }

    @Override
    public Optional<Category> findCategoryById(Integer id) {
        return repository.findById(id);
    }
}
