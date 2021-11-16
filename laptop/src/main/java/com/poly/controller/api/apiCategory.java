package com.poly.controller.api;

import com.poly.entity.Category;
import com.poly.entity.Products;
import com.poly.filter.ProductSearchCriteria;
import com.poly.repo.CategoryRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RestController
public class apiCategory {
    @Autowired private ProductsRepository repository;
    @Autowired private ProductService productService;
    @Autowired private CategoryRepository categoryRepository;
    @GetMapping("/findId")
    public Page<Products> products(@RequestParam(name = "numberPage",defaultValue = "1") Integer numberPage,
                                   @RequestParam(name = "cateParent" ) Integer cateParent,
                                   @RequestParam(name = "name",defaultValue = "" ) String name){

    return  productService.findAllByNameLike(numberPage,12,name,cateParent);
}
    @GetMapping("/filter")
    public ResponseEntity<Page<Products>> filterProducts(
            @RequestParam(name = "currentPage",defaultValue = "1") Integer numberPage,
            @RequestParam(name = "category") Set<Integer> category,
            @RequestParam(name = "minPrice") Optional<Float> minPriceIn,
            @RequestParam(name = "maxPrice") Optional<Float> maxPriceIn,
            @RequestParam(name = "cateParent") Integer cateParent,
            @RequestParam(name = "cpu") String cpu,
            @RequestParam(name = "ram") String ram,
            @RequestParam(name = "sale") String sale,
            @RequestParam(name = "displaySize") String   displaySize,
            @RequestParam(name = "screenRatio") String   screenRatio,
            @RequestParam(name = "scanFrequency") String scanFrequency,
            @RequestParam(name = "resolution") String resolution,
            @RequestParam(name = "minMass") Optional<Float> minMass,
            @RequestParam(name = "maxMass") Optional<Float> maxMass,
            @RequestParam(name = "vga") String vga,
            @RequestParam(value = "sort", defaultValue = "asc") String sort
    ){
        ProductSearchCriteria searchCriteria =   ProductSearchCriteria.builder()
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
                .build() ;
        List<Products> result = (sale.equals("true")) ? repository.getListProductByCodeSale() :this.productService.retrieveProducts(searchCriteria);
        List<Products> search = this.productService.retrieveProducts(searchCriteria);
        Optional<Category> cate =  categoryRepository.findById(cateParent);
        result = result.stream().filter(x-> x.getTypeOfItem()==cate.get().getParentId())
                .collect(Collectors.toList());
        List<Products> intersectElements = result.stream()
                .filter(search :: contains)
                .collect(Collectors.toList());

       Page page = productService.getListByPageNumber(numberPage,12, (sale.equals("true")) ? intersectElements : result  ,sort);
        return new ResponseEntity<>(page, HttpStatus.OK);
    }

}
