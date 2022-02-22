package com.poly.controller.api;

import com.poly.entity.ResponObject;
import com.poly.filter.ProductSearchCriteria;
import com.poly.service.CategoryService;
import com.poly.service.ProductDetailService;
import com.poly.vo.CategoryVO;
import com.poly.vo.ProductsDetailVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class ApiCategory {
    @Autowired
    private CategoryService categoryService;
    @Autowired
    private ProductDetailService productDetailService;

    @GetMapping("/findId/{numberPage}")
    public Page<ProductsDetailVO> products(@PathVariable(name = "numberPage") Integer numberPage,
                                           @RequestParam(name = "search", defaultValue = "") String search,
                                           @RequestParam(name = "type", defaultValue = "") String type,
                                           @RequestParam(name = "sort", defaultValue = "asc") String sort

    ) {
        return productDetailService.findAllByNameLike(numberPage, 24, search, type, sort);
    }

    @GetMapping("/findTrademark/{id}")
    public List<CategoryVO> trademark(@PathVariable(name = "id") String id) {
        return categoryService.findAllByParentId(id);
    }
    @GetMapping("/get-category")
    public List<CategoryVO> getCategory() {
        return categoryService.getAllByParentIdIsNull();
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
                                                                     @RequestParam(name = "minPrice") Set<Float> minPriceIn,
                                                                     @RequestParam(name = "maxPrice") Set<Float> maxPriceIn,
                                                                     @RequestParam(name = "cpu") Set<String> cpu,
                                                                     @RequestParam(name = "ram") Set<String> ram,
                                                                     @RequestParam(name = "sale") String sale,
                                                                     @RequestParam(name = "displaySize") Set<String> displaySize,
                                                                     @RequestParam(name = "screenRatio") Set<String> screenRatio,
                                                                     @RequestParam(name = "scanFrequency") Set<String> scanFrequency,
                                                                     @RequestParam(name = "resolution") Set<String> resolution,
                                                                     @RequestParam(name = "vga") Set<String> vga,
                                                                     @RequestParam(name = "name") String name,
                                                                     @RequestParam(value = "sort", defaultValue = "asc") String sort) {
        System.out.println(name);
        category.stream().forEach(e -> System.out.println("E: " + e));

        ResponObject responObject = new ResponObject();
        Iterator<Float> minPrice = minPriceIn.iterator();
        Iterator<Float> maxPrice = maxPriceIn.iterator();
        Map<Float, Float> mapPrice = new HashMap<>();

        while (minPrice.hasNext() && maxPrice.hasNext()) {
            mapPrice.put(minPrice.next(), maxPrice.next());
        }

        List<ProductsDetailVO> productsList;
        List<ProductsDetailVO> productsListTotal;

        CategoryVO categoryVO = categoryService.findByName(nameBrand);

        String id = (brand != null && !brand.equals("")) ? categoryService.getOne(brand).getId() : categoryVO.getId();
        String idRoots = (categoryVO.getParentId() == null) ? categoryVO.getId() : categoryVO.getParentId();

        List<ProductsDetailVO> lstProducts = productDetailService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId());
        List<CategoryVO> lstCate = (categoryVO.getParentId() != null) ? categoryService.getListByParent(idRoots) : categoryService.findAllById(idRoots);

        productsList = (lstCate.size() != 0) ? (categoryVO.getParentId() != null) ? ((lstProducts.size() != 0) ? productDetailService.findAllByTypeOfItemAndCategory_ParentId(idRoots, categoryVO.getId()) : productDetailService.findAllByCategory_Id(categoryVO.getId())) : productDetailService.findAllByTypeOfItem(idRoots) : productDetailService.findAllByTypeOfItemAndCategory_Id(idRoots, categoryVO.getId());
        productsListTotal = (brand != null && !brand.equals("")) ? productDetailService.findAllByCategory_IdOrCategory_Id(categoryVO.getId(), id) : productsList;

        ProductSearchCriteria searchCriteria = ProductSearchCriteria.builder()
                .category(category)
                .status(sale)
                .name(name)
                .mapPrice(mapPrice)
                .cpu(cpu)
                .ram(ram)
                .displaySize(displaySize)
                .screenRatio(screenRatio)
                .scanFrequency(scanFrequency)
                .resolution(resolution)
                .vga(vga)
                .build();

        List<ProductsDetailVO> search = this.productDetailService.retrieveProducts(searchCriteria);
//
        ProductsDetailVO intersectElements = new ProductsDetailVO();
//
        List<ProductsDetailVO> contains = new ArrayList<>();

        for (ProductsDetailVO p : productsListTotal) {
            for (ProductsDetailVO s : search) {
                if (p.getSku().equals(s.getSku())) {
                    intersectElements = s;
                    intersectElements.setPrice(s.getPrice());
                }
            }
            contains.add(intersectElements);
            continue;
        }
        contains = contains.stream().distinct().filter(e -> e.getPrice() != null).collect(Collectors.toList());
        Page<ProductsDetailVO> page = productDetailService.getListByPageNumber(pageNumber, 24, contains, sort);
        responObject.setTotalPages(page.getTotalPages());
        responObject.setTotalElements(page.getTotalElements());
        responObject.setId(categoryVO.getId());
        responObject.setData(page.getContent());
        return new ResponseEntity<>(responObject, HttpStatus.OK);
    }


    @RequestMapping(value = "/page/{pageNumber}/search", method = RequestMethod.GET)
    public ResponseEntity<ResponObject> getPageByCateGoryAndBrandAPISearch(@PathVariable(value = "pageNumber") Integer pageNumber,
                                                                           @RequestParam(name = "category") Set<String> category,
                                                                           @RequestParam(name = "minPrice") Set<Float> minPriceIn,
                                                                           @RequestParam(name = "maxPrice") Set<Float> maxPriceIn,
                                                                           @RequestParam(name = "sale") String sale,
                                                                           @RequestParam(name = "name") String name,
                                                                           @RequestParam(value = "sort", defaultValue = "asc") String sort) {


        ResponObject responObject = new ResponObject();
        Iterator<Float> minPrice = minPriceIn.iterator();
        Iterator<Float> maxPrice = maxPriceIn.iterator();
        Map<Float, Float> mapPrice = new HashMap<>();

        while (minPrice.hasNext() && maxPrice.hasNext()) {
            mapPrice.put(minPrice.next(), maxPrice.next());
        }

        ProductSearchCriteria searchCriteria = ProductSearchCriteria.builder()
                .category(category)
                .status(sale)
                .name(name)
                .mapPrice(mapPrice)
                .build();

        List<ProductsDetailVO> search = this.productDetailService.retrieveProductsSearch(searchCriteria);

        Page<ProductsDetailVO> page = productDetailService.getListByPageNumber(pageNumber, 24, search, sort);
        responObject.setTotalPages(page.getTotalPages());
        responObject.setTotalElements(page.getTotalElements());
        responObject.setData(page.getContent());
        return new ResponseEntity<>(responObject, HttpStatus.OK);
    }

}
