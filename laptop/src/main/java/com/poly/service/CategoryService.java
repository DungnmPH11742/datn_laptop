package com.poly.service;

import com.poly.entity.Category;
import com.poly.vo.CategoryVO;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public interface CategoryService {
    List<CategoryVO> getAllCategory();

    Category saveCategory(Category Category);

    Category updateCategory(Category Category);
     void deleteById(Integer id);
     Optional<Category> findCategoryById(Integer id);;
}
