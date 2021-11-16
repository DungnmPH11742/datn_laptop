package com.poly.controller.api;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product_api")
public class Product_api {
    @Autowired private ProductsRepository repository;
    @Autowired private CategoryRepository categoryRepository;

    @GetMapping("/{nameBrand}")
    public List<Products> getBrandCate(@PathVariable("nameBrand") String nameBrand, @RequestParam(value = "brand", defaultValue = "") String brand){
        List<Products> productsList;
       if(brand!= null){
           System.out.println("null");
       }
        if(brand.equals("")){
            System.out.println("equa");
        }
        System.out.println("nameBrand: "+nameBrand);
        System.out.println("brand: "+brand);
        Category category = categoryRepository.findByName(nameBrand);
        Integer id =  (brand!=null && !brand.equals(""))?categoryRepository.findById(Integer.parseInt(brand)).get().getId():category.getId();
        System.out.println("id sắp xếp thứ 1: "+category.getId()+"___"+category.getParentId());
        System.out.println("id sắp xếp thứ 2: "+id+"_____"+category.getId());
        Integer idGocRe = (category.getParentId()==null) ? category.getId() :category.getParentId();
        List<Category> lstCate = (category.getParentId()!=null) ? categoryRepository.findAllByParentId(category.getParentId()): categoryRepository.findAllById(idGocRe);
        productsList = (lstCate.size() !=0 )? (category.getParentId()!=null)?repository.findAllByTypeOfItemAndCategory_ParentId(idGocRe,category.getId()):repository.findAllByTypeOfItem(idGocRe) : repository.findAllByTypeOfItemAndCategory_Id(idGocRe,category.getId());

     return  (brand!=null && !brand.equals(""))? repository.findAllByCategory_IdOrCategory_Id(category.getId(),id): productsList;
    }

}
