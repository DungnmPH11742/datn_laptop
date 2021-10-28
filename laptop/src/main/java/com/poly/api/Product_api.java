package com.poly.api;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/product_api")
public class Product_api {
    @Autowired private ProductsRepository repository;
    @Autowired private CategoryRepository categoryRepository;

    @GetMapping("/{nameBrand}")
    public List<Products> getBrandCate(@PathVariable("nameBrand") String nameBrand, @RequestParam(value = "brand", defaultValue = "") String brand){
        List<Products> productsList;
        Category category = categoryRepository.findByName(nameBrand);
        Integer id =  (brand!=null && !brand.equals(""))?categoryRepository.findById(Integer.parseInt(brand)).get().getId():category.getId();
        Integer idRoots = (category.getParentId()==null) ? category.getId() :category.getParentId();
        List<Category> lstCate = (category.getParentId()!=null) ? categoryRepository.findAllByParentId(category.getParentId()): categoryRepository.findAllById(idRoots);
        productsList = (lstCate.size() !=0 )? (category.getParentId()!=null)?repository.findAllByTypeOfItemAndCategory_ParentId(idRoots,category.getId()):repository.findAllByTypeOfItem(idRoots) : repository.findAllByTypeOfItemAndCategory_Id(idRoots,category.getId());
        return  (brand!=null && !brand.equals(""))? repository.findAllByCategory_IdOrCategory_Id(category.getId(),id): productsList;
    }

}
