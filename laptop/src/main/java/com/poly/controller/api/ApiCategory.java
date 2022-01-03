package com.poly.controller.api;

import com.poly.entity.ResponObject;
import com.poly.filter.ProductSearchCriteria;
import com.poly.service.CategoryService;
import com.poly.service.ProductService;
import com.poly.vo.CategoryVO;
import com.poly.vo.ProductsVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ApiCategory {
    @Autowired
    private ProductService productService;
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/findId/{numberPage}")
    public Page<ProductsVO> products(@PathVariable(name = "numberPage") Integer numberPage,
                                     @RequestParam(name = "search", defaultValue = "") String search,
                                     @RequestParam(name = "type", defaultValue = "") String type,
                                     @RequestParam(name = "sort", defaultValue = "asc") String sort

    ) {
        return productService.findAllByNameLike(numberPage, 24, search,type,sort);
    }

    @GetMapping("/findTrademark/{id}")
    public List<CategoryVO> trademark(@PathVariable(name = "id") String id) {
        return categoryService.findAllByParentId(id);
    }

    @RequestMapping(value = "/category/{nameBrand}", method = RequestMethod.GET)
    public ResponseEntity<String> getPageByIdCateGory(@PathVariable("nameBrand") String nameBrand
    ) {
        CategoryVO categoryVO = categoryService.findByName(nameBrand);
        List<CategoryVO> lstCategory = categoryService.findAllByParentId(categoryVO.getId());
        String id = (lstCategory.isEmpty()) ? categoryVO.getParentId() : categoryVO.getId();
        return new ResponseEntity<>(id, HttpStatus.OK);
    }

    @RequestMapping(value = "/page/{pageNumber}/category/{nameBrand}", method = RequestMethod.GET)
    public ResponseEntity<ResponObject> getPageByCateGoryAndBrandAPI(@PathVariable(value = "pageNumber") Integer pageNumber,
                                                                     @PathVariable("nameBrand") String nameBrand,
                                                                     @RequestParam(value = "brand", defaultValue = "") String brand,
                                                                     @RequestParam(name = "category") Set<String> category,
                                                                     @RequestParam(name = "minPrice") Optional<Float> minPriceIn,
                                                                     @RequestParam(name = "maxPrice") Optional<Float> maxPriceIn,
                                                                     @RequestParam(name = "cpu") String cpu,
                                                                     @RequestParam(name = "ram") String ram,
                                                                     @RequestParam(name = "sale") String sale,
                                                                     @RequestParam(name = "displaySize") String displaySize,
                                                                     @RequestParam(name = "screenRatio") String screenRatio,
                                                                     @RequestParam(name = "scanFrequency") String scanFrequency,
                                                                     @RequestParam(name = "resolution") String resolution,
                                                                     @RequestParam(name = "minMass") Optional<Float> minMass,
                                                                     @RequestParam(name = "maxMass") Optional<Float> maxMass,
                                                                     @RequestParam(name = "vga") String vga,
                                                                     @RequestParam(value = "sort", defaultValue = "asc") String sort) {

        ResponObject responObject = new ResponObject();

        List<ProductsVO> productsList;
        List<ProductsVO> productsListTotal;

        CategoryVO categoryVO = categoryService.findByName(nameBrand);

        String id = (brand != null && !brand.equals("")) ? categoryService.getOne(brand).getId() : categoryVO.getId();
        String idRoots = (categoryVO.getParentId() == null) ? categoryVO.getId() : categoryVO.getParentId();

        List<ProductsVO> lstProducts = productService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId());
        List<CategoryVO> lstCate = (categoryVO.getParentId() != null) ? categoryService.getListByParent(idRoots) : categoryService.findAllById(idRoots);

        productsList = (lstCate.size() != 0) ? (categoryVO.getParentId() != null) ? ((lstProducts.size() != 0) ? productService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId()) : productService.findAllByCategory_Id(categoryVO.getId())) : productService.findAllByTypeOfItem(idRoots) : productService.findAllByTypeOfItemAndCategory_Id(idRoots, categoryVO.getId());
        productsListTotal = (brand != null && !brand.equals("")) ? productService.findAllByCategory_IdOrCategory_Id(categoryVO.getId(), id) : productsList;

        ProductSearchCriteria searchCriteria = ProductSearchCriteria.builder()
                .category(category)
                .minPrice(minPriceIn)
                .maxPrice(maxPriceIn)
                .cpu(cpu)
                .ram(ram)
                .displaySize(displaySize)
                .screenRatio(screenRatio)
                .scanFrequency(scanFrequency)
                .resolution(resolution)
                .minMass(minMass)
                .maxMass(maxMass)
                .vga(vga)
                .build();

        List<ProductsVO> search = this.productService.retrieveProducts(searchCriteria);
        List<ProductsVO> intersectElements = productsListTotal.stream()
                .filter(search::contains)
                .collect(Collectors.toList());
        List<ProductsVO> result = (sale.equals("true")) ? productService.getListProductByCodeSale(categoryVO.getId()) : intersectElements;

        Page<ProductsVO> page = productService.getListByPageNumber(pageNumber, 24, result, sort);

        responObject.setTotalPages(page.getTotalPages());
        responObject.setTotalElements(page.getTotalElements());
        responObject.setId(categoryVO.getId());
        responObject.setData(page.getContent());

        return new ResponseEntity<>(responObject, HttpStatus.OK);
    }
}
