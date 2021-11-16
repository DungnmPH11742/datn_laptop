package com.poly.controller.web;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.CategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CategoryWebController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductsRepository repository;
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/{nameBrand}")
    public String getBrandCate(Model model, @PathVariable("nameBrand") String nameBrand, @RequestParam(value = "brand", defaultValue = "") String brand,
                               @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        return getPageByCateGoryAndBrand(1, nameBrand, brand, model, sort);
    }

    @RequestMapping("/category/page/{pageNumber}/{nameBrand}")
    public String getPageByCateGoryAndBrand(@PathVariable(value = "pageNumber") Integer pageNumber,
                                            @PathVariable("nameBrand") String nameBrand,
                                            @RequestParam(value = "brand", defaultValue = "") String brand,
                                            Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        List<Products> productsList;
        List<Products> productsListTotal;
        Category category = categoryRepository.findByName(nameBrand);
        Integer id = (brand != null && !brand.equals("")) ? categoryRepository.findById(Integer.parseInt(brand)).get().getId() : category.getId();
        Integer idRoots = (category.getParentId() == null) ? category.getId() : category.getParentId();
        List<Products> lstProducts = repository.findAllByTypeOfItemAndCategory_ParentId(idRoots, category.getId());
        List<Category> lstCate = (category.getParentId() != null) ? categoryRepository.findAllByParentId(idRoots) : categoryRepository.findAllById(idRoots);
        productsList = (lstCate.size() != 0) ? (category.getParentId() != null) ? ((lstProducts.size() != 0) ? repository.findAllByTypeOfItemAndCategory_ParentId(idRoots, category.getId()) : repository.findAllByCategory_Id(category.getId())) : repository.findAllByTypeOfItem(idRoots) : repository.findAllByTypeOfItemAndCategory_Id(idRoots, category.getId());
        productsListTotal = (brand != null && !brand.equals("")) ? repository.findAllByCategory_IdOrCategory_Id(category.getId(), id) : productsList;

        Page<Products> page = productService.getListByPageNumber(pageNumber, 3, productsListTotal, sort);
        List<Products> listProducts = page.getContent();

        if (idRoots == 1) {
            model.addAttribute("cate_filter", categoryService.getListByParent(1));
            model.addAttribute("cate_id",idRoots);
        }
        if (idRoots == 57) {
            model.addAttribute("cate_filter", categoryService.getListByParent(57));
            model.addAttribute("cate_id",idRoots);
        }
        if (idRoots == 76) {
            model.addAttribute("cate_filter", categoryService.getListByParent(76));
            model.addAttribute("cate_id",idRoots);
        }
        model.addAttribute("cate_all", categoryService.getList());
        model.addAttribute("cate_lt", categoryService.getListByParent(1));
        model.addAttribute("cate_pc", categoryService.getListByParent(57));
        model.addAttribute("cate_mo", categoryService.getListByParent(76));
        model.addAttribute("list_sale", productService.getListByCodeSale("SL001"));

//
//        model.addAttribute("nameBrand", nameBrand);
//        model.addAttribute("brand", brand);
//        model.addAttribute("currentPage", pageNumber);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("list_product", listProducts);
//        model.addAttribute("sort", sort);

        return "user/duong-test";
    }
}
