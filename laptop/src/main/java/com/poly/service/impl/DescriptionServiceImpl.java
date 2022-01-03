package com.poly.service.impl;

import com.poly.entity.Blogs;
import com.poly.entity.Description;
import com.poly.entity.Products;
import com.poly.repo.BlogsRepository;
import com.poly.repo.DescriptionRepository;
import com.poly.repo.ProductsRepository;
import com.poly.service.DescriptionService;
import com.poly.vo.DescriptionVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class DescriptionServiceImpl implements DescriptionService {

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DescriptionRepository descriptionRepository;

    @Autowired
    private ProductsRepository productsRepository;

    @Autowired
    private BlogsRepository blogsRepository;


    @Override
    public List<DescriptionVO> getDescriptionByProduct(String idProduct) {
        List<Description> descriptionList = this.descriptionRepository.getDescriptionByProductId(idProduct);
        if (descriptionList != null || descriptionList.size() > 0) {
            List<DescriptionVO> descriptionVOList = new ArrayList<>();
            descriptionList.forEach(d -> {
                DescriptionVO vo = modelMapper.map(d, DescriptionVO.class);
                if (d.getProduct() != null) {
                    vo.setIdProduct(d.getProduct().getId());
                }
                if (d.getBlog() != null) {
                    vo.setIdBlog(d.getBlog().getTitle());
                }
                descriptionVOList.add(vo);
            });
            return descriptionVOList;
        }
        return null;
    }

    @Override
    public List<DescriptionVO> getDescriptionByBlog(String idBlog) {
        List<Description> descriptionList = this.descriptionRepository.getDescriptionByBlogId(idBlog);
        List<DescriptionVO> listVo = new ArrayList<>();
        if (descriptionList != null || descriptionList.size() > 0) {
            descriptionList.forEach(d -> {
                DescriptionVO vo = modelMapper.map(d, DescriptionVO.class);
                if (d.getProduct() != null) {
                    vo.setIdProduct(d.getProduct().getId());
                }
                if (d.getBlog() != null) {
                    vo.setIdBlog(d.getBlog().getTitle());
                }
                listVo.add(vo);
            });
            return listVo;
        }
        return null;
    }

    @Override
    public DescriptionVO getOneDescriptionById(Integer id) {
        Optional<Description> optionalDescription = this.descriptionRepository.findById(id);

        DescriptionVO descriptionVO = new DescriptionVO();
        if (optionalDescription.isPresent()) {
            Description description = optionalDescription.get();
            descriptionVO.setId(description.getId());
            if (description.getBlog() != null) {
                descriptionVO.setIdBlog(description.getBlog().getTitle());
            } else {
                descriptionVO.setIdBlog(null);
            }
            if (description.getProduct() != null) {
                descriptionVO.setIdProduct(description.getProduct().getId());
            }
            BeanUtils.copyProperties(description, descriptionVO);
            return descriptionVO;
        } else {
            return null;
        }

    }

    @Override
    public DescriptionVO create(DescriptionVO vo) {
        Optional<Products> products = this.productsRepository.findById(vo.getIdProduct());
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getIdBlog());
        Description description = new Description();
        if (products.isPresent() || optionalBlogs.isPresent()) {
            if (products.isPresent()) {
                description.setProduct(products.get());
            } else if (optionalBlogs.isPresent()) {
                description.setBlog(optionalBlogs.get());

            }
            BeanUtils.copyProperties(vo, description);
            this.descriptionRepository.save(description);
            vo.setId(description.getId());
            return vo;
        } else {
            return null;
        }

    }

    @Override
    public DescriptionVO update(DescriptionVO vo, Integer id) {
        vo.setId(id);
        Optional<Description> optionalDescription = this.descriptionRepository.findById(vo.getId());
        Optional<Products> optionalProducts = this.productsRepository.findById(vo.getIdProduct());
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getIdBlog());
        if (optionalDescription.isPresent() && optionalProducts.isPresent() && optionalBlogs.isPresent()) {
            Description description = optionalDescription.get();
            description.setBlog(optionalBlogs.get());
            description.setProduct(optionalProducts.get());
            BeanUtils.copyProperties(vo, description);
            this.descriptionRepository.save(description);
            return vo;
        }
        return null;
    }

    @Override
    public DescriptionVO delete(Integer id) {
        Optional<Description> optionalDescription = this.descriptionRepository.findById(id);
        if (optionalDescription.isPresent()) {
            Description description = optionalDescription.get();
            DescriptionVO vo = modelMapper.map(description, DescriptionVO.class);
            if (description.getProduct() != null) {
                vo.setIdProduct(description.getProduct().getId());
            }
            if (description.getBlog() != null) {
                vo.setIdBlog(description.getBlog().getTitle());
            }
            this.descriptionRepository.delete(description);
            return vo;
        }
        return null;
    }


}
