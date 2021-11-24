package com.poly.controller.web;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.helper.HeaderHelper;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.CategoryVO;
import com.poly.vo.ProductsVO;
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
    private ProductsRepository productsRepository;

    @Autowired
    private HeaderHelper headerHelper;

    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping("/category/{nameBrand}")
    public String getBrandCate(Model model,  @PathVariable("nameBrand") String nameBrand) {
        headerHelper.setHeaderSession(model);
        return "user/duong-test";
    }

//    @RequestMapping("/category/page/{pageNumber}/{nameBrand}")
//    public String getPageByCateGoryAndBrand(@PathVariable(value = "pageNumber") Integer pageNumber,
//                                            @PathVariable("nameBrand") String nameBrand,
//                                            @RequestParam(value = "brand", defaultValue = "") String brand,
//                                            Model model, @RequestParam(value = "sort", defaultValue = "asc") String sort) {
//
//
//        List<ProductsVO> productsList;
//        List<ProductsVO> productsListTotal;
//        CategoryVO categoryVO = categoryService.findByName(nameBrand);
//        Integer id = (brand != null && !brand.equals("")) ? categoryService.getOne(Integer.parseInt(brand)).getId() : categoryVO.getId();
//        Integer idRoots = (categoryVO.getParentId() == null) ? categoryVO.getId() : categoryVO.getParentId();
//        List<ProductsVO> lstProducts = productService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId());
//        List<Category> lstCate = (categoryVO.getParentId() != null) ? categoryRepository.findAllByParentId(idRoots) : categoryRepository.findAllById(idRoots);
//        productsList = (lstCate.size() != 0) ? (categoryVO.getParentId() != null) ? ((lstProducts.size() != 0) ? productService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId()) : productService.findAllByCategory_Id(categoryVO.getId())) : productService.findAllByTypeOfItem(idRoots) : productService.findAllByTypeOfItemAndCategory_Id(idRoots, categoryVO.getId());
//        productsListTotal = (brand != null && !brand.equals("")) ? productService.findAllByCategory_IdOrCategory_Id(categoryVO.getId(), id) : productsList;
//        Page<ProductsVO> page = productService.getListByPageNumber(pageNumber, 12, productsListTotal, sort);
//        List<ProductsVO> listProducts = page.getContent();
//        if (idRoots == 1) {
//            model.addAttribute("cate_filter", categoryService.getListByParent(1));
//            model.addAttribute("cate_id", idRoots);
//        }
//        if (idRoots == 57) {
//            model.addAttribute("cate_filter", categoryService.getListByParent(57));
//            model.addAttribute("cate_id", idRoots);
//        }
//        if (idRoots == 76) {
//            model.addAttribute("cate_filter", categoryService.getListByParent(76));
//            model.addAttribute("cate_id", idRoots);
//        }
//        model.addAttribute("list_sale", productService.getListByCodeSale("SL001"));
//        model.addAttribute("list_product", listProducts);
//        headerHelper.setHeaderSession(model);
//        model.addAttribute("nameBrand", nameBrand);
//        model.addAttribute("brand", brand);
//        model.addAttribute("currentPage", pageNumber);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
//        model.addAttribute("sort", sort);
//        return "user/duong-test";
//    }
}
