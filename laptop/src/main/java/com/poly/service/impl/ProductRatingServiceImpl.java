package com.poly.service.impl;

import com.poly.entity.ProductRating;
import com.poly.repo.ProductRatingRepository;
import com.poly.service.ProductRatingService;
import com.poly.vo.ProductRatingVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductRatingServiceImpl implements ProductRatingService {

    @Autowired
    private ProductRatingRepository productRatingRepository;
    @Autowired
    private ModelMapper modelMapper;

    private ProductRatingVO convertProductRatingToDto(ProductRating productRating) {
        ProductRatingVO productRatingVO = modelMapper.map(productRating, ProductRatingVO.class);
        return productRatingVO;
    }

    private ProductRating convertProductRatingVO(ProductRatingVO productRatingVO) {
        ProductRating productRating = modelMapper.map(productRatingVO, ProductRating.class);
        return productRating;
    }

    private List<ProductRatingVO> convertListProductRatingDto(List<ProductRating> lstProductRating) {
        List<ProductRatingVO> vos = new ArrayList<>();
        lstProductRating.forEach(productRating -> {
            vos.add(modelMapper.map(productRating, ProductRatingVO.class));
        });
        return vos;
    }

    public ProductRatingVO convertProductRatingToDtoById(Integer id) {
        ProductRatingVO productRatingVO = new ProductRatingVO();
        Optional<ProductRating> optional = productRatingRepository.findById(id);
        if (optional.isPresent()) {
            ProductRating productRating = optional.get();
            productRatingVO = modelMapper.map(productRating, ProductRatingVO.class);
        }
        return productRatingVO;
    }

    public List<ProductRatingVO> convertListProductRatingToDtoById(String id) {
        List<ProductRatingVO> vos = new ArrayList<>();
        productRatingRepository.findAllByProductsDetail_Sku(id).forEach(productRating -> {
            vos.add(modelMapper.map(productRating, ProductRatingVO.class));
        });
        return vos;
    }

    @Override
    public ProductRatingVO getOne(Integer id) {
        return convertProductRatingToDtoById(id);
    }

    @Override
    public List<ProductRatingVO> findAllByProductsDetail_Sku(String id) {
        return convertListProductRatingToDtoById(id);
    }

    @Override
    public ProductRating findProductRatingByAccountAndProductDetail(String email, String sku) {
        return productRatingRepository.findByAccount_EmailAndProductsDetail_Sku(email, sku);
    }

    @Override
    public ProductRatingVO create(ProductRatingVO vo) {
        return convertProductRatingToDto(productRatingRepository.save(convertProductRatingVO(vo)));
    }

    @Override
    public ProductRatingVO update(ProductRatingVO vo) {
        return convertProductRatingToDto(productRatingRepository.save(convertProductRatingVO(vo)));
    }

    @Override
    public Page<ProductRatingVO> getAllProductRatingVOByPageNumber(int page, int limit, String id) {
        List<ProductRatingVO> lstProductRatingVO = convertListProductRatingDto(productRatingRepository.findAllByProductsDetail_Sku(id));

        lstProductRatingVO = lstProductRatingVO.stream().filter(x -> x.getComment() != null).collect(Collectors.toList());
        Collections.sort(lstProductRatingVO, new Comparator<ProductRatingVO>() {
            DateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            @Override
            public int compare(ProductRatingVO p1, ProductRatingVO p2) {
                return f.format(p2.getStartComment()).compareTo(f.format(p1.getStartComment()));
            }

        });

        Pageable pageable = PageRequest.of(page - 1, limit);
        final int start = (int) pageable.getOffset();
        final int end = Math.min((start + pageable.getPageSize()), lstProductRatingVO.size());
        Page page1 = new PageImpl<>(lstProductRatingVO.subList(start, end), pageable, lstProductRatingVO.size());
        return page1;
    }
}
