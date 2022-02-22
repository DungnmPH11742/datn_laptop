package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Blogs;
import com.poly.repo.AccountRepository;
import com.poly.repo.BlogsRepository;
import com.poly.service.BlogService;
import com.poly.service.DescriptionService;
import com.poly.vo.AccountVO;
import com.poly.vo.BlogsVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogsRepository blogsRepository;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private DescriptionService descriptionService;

    @Override
    public List<BlogsVO> findAll() {
        List<BlogsVO> vos = new ArrayList<>();
        blogsRepository.findAll().forEach(blogs -> {
            vos.add(modelMapper.map(blogs, BlogsVO.class));
        });
        return vos;
    }

    @Override
    public BlogsVO getOneBlog(String id) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(id);
        if (optionalBlogs.isPresent()){
            BlogsVO blogsVO = new BlogsVO();
            blogsVO.setAccount(modelMapper.map(optionalBlogs.get().getAccount(), AccountVO.class));
            BeanUtils.copyProperties(optionalBlogs.get(),blogsVO);
            return blogsVO;
        }else {
            return null;
        }
    }

    @Override
    public BlogsVO create(BlogsVO vo) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getId());
        if (!optionalBlogs.isPresent()){
            Blogs blogs = new Blogs();
            BeanUtils.copyProperties(vo,blogs);
            blogs.setAccount(accountRepository.getById(1));
            this.blogsRepository.save(blogs);
            return vo;
        }else {
            return null;
        }

    }

    @Override
    public BlogsVO update(BlogsVO vo) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getId());
        if (optionalBlogs.isPresent()){
            Blogs blogs = optionalBlogs.get();
            BeanUtils.copyProperties(vo,blogs);
            this.blogsRepository.save(blogs);
            return vo;
        }else {
            return null;
        }
    }

    @Override
    public BlogsVO delete(String id) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(id);
        if (optionalBlogs.isPresent()){
            this.blogsRepository.delete(optionalBlogs.get());
            BlogsVO blogsVO = new BlogsVO();
            blogsVO.setAccount(modelMapper.map(optionalBlogs.get().getAccount(), AccountVO.class));
            BeanUtils.copyProperties(optionalBlogs.get(),blogsVO);
            return blogsVO;
        }
        return null;
    }
    @Override
    public List<BlogsVO> getListBlog(Integer start, Integer end) {
        List<Blogs> listEntity = blogsRepository.getFlowByNumber(start,end);
        List<BlogsVO> listVo = new ArrayList<>();
        listEntity.forEach(b ->{
            listVo.add(modelMapper.map(b,BlogsVO.class));
        });

        return listVo;
    }

    @Override
    public List<BlogsVO> getBlogIsHot() {
        List<Blogs> listEntity = blogsRepository.getIsHot();
        List<BlogsVO> listVo = new ArrayList<>();
        listEntity.forEach(b ->{
            listVo.add(modelMapper.map(b,BlogsVO.class));
        });
        return listVo;
    }

    @Override
    public BlogsVO findBlogById(String id) {
        Optional<Blogs> entity = blogsRepository.findById(id);
        if (entity.isPresent()){
            BlogsVO vo = modelMapper.map(entity.get(),BlogsVO.class);
            vo.setDescriptions(descriptionService.getDescriptionByBlog(id));
            return vo;
        }
        return null;
    }

    @Override
    public List<BlogsVO> getFourBlog(String idBlog) {
        List<Blogs> listEntity = blogsRepository.getBlogsDesc();
        List<BlogsVO> listVo = new ArrayList<>();
        int size = 4;
        for (int i = 0; i < size; i++) {
            if (!listEntity.get(i).getId().equals(idBlog)){
                listVo.add(modelMapper.map(listEntity.get(i),BlogsVO.class));
            }else {
                size = 5;
            }

        }
        return listVo;
    }
}
