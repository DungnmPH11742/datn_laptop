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
    private CategoryRepository categoryRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<CategoryVO> getList() {
        List<CategoryVO> vos = new ArrayList<>();
        categoryRepository.findAll().forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    @Override
    public List<CategoryVO> getNodeCate() {
        List<CategoryVO> vos = new ArrayList<>();
        categoryRepository.findByParentIdIsNull().forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    @Override
    public CategoryVO getOne(String id) {
        return convertCategoryToDtoById(id);
    }

    @Override
    public CategoryVO create(CategoryVO vo) {
        Category category = modelMapper.map(vo, Category.class);
        category.setActived(true);
        return modelMapper.map(categoryRepository.save(category), CategoryVO.class);
    }

    @Override
    public CategoryVO update(CategoryVO vo) {
        if (!categoryRepository.existsById(vo.getId())) {
            return null;
        } else
            return modelMapper.map(categoryRepository.save(modelMapper.map(vo, Category.class)), CategoryVO.class);
    }

    @Override
    public boolean delete(String id) {
        return false;
    }

    @Override
    public List<CategoryVO> getListPage(Optional<Integer> page, Optional<Integer> row) {
        return null;
    }

    @Override
    public List<CategoryVO> getListByParent(String id) {
        List<CategoryVO> vos = new ArrayList<>();
        categoryRepository.getListByParent(id).forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    @Override
    public CategoryVO findByName(String name) {
        return convertCategoryToDto(categoryRepository.findByName(name));
    }

    @Override
    public List<CategoryVO> findAllByParentId(String id) {
        List<CategoryVO> vos = new ArrayList<>();
        categoryRepository.findAllByParentId(id).forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    @Override
    public List<CategoryVO> findAllById(String id) {
        return convertListCategoryDto(categoryRepository.findAllById(id));
    }

    private CategoryVO convertCategoryToDto(Category category) {
        CategoryVO categoryVO = modelMapper.map(category, CategoryVO.class);
        return categoryVO;
    }

    private List<CategoryVO> convertListCategoryDto(List<Category> lstCategory) {
        List<CategoryVO> vos = new ArrayList<>();
        lstCategory.forEach(category -> {
            vos.add(modelMapper.map(category, CategoryVO.class));
        });
        return vos;
    }

    public CategoryVO convertCategoryToDtoById(String id) {

        CategoryVO categoryVO = new CategoryVO();
        Optional<Category> optional = categoryRepository.findById(id);
        if (optional.isPresent()) {
            Category category = optional.get();
            categoryVO = modelMapper.map(category, CategoryVO.class);
        }
        return categoryVO;
    }

    @Override
    public List<CategoryVO> getAllByParentIdIsNull() {
        return convertListCategoryDto(categoryRepository.getAllByParentIdIsNull());
    }
}
