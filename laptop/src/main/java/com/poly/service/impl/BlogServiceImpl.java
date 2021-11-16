package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.Blogs;
import com.poly.repo.AccountRepository;
import com.poly.repo.BlogsRepository;
import com.poly.service.BlogService;
import com.poly.vo.AccountVO;
import com.poly.vo.BlogsVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogServiceImpl implements BlogService {

    @Autowired
    private BlogsRepository blogsRepository;
    @Autowired
    private AccountRepository accountRepository;

    @Override
    public BlogsVO getOneBlog(String title) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(title);
        if (optionalBlogs.isPresent()){
            BlogsVO blogsVO = new BlogsVO();
            blogsVO.setIdAccount(optionalBlogs.get().getAccount().getId());
            BeanUtils.copyProperties(optionalBlogs.get(),blogsVO);
            return blogsVO;
        }else {
            return null;
        }
    }

    @Override
    public BlogsVO create(BlogsVO vo) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getTitle());
        Optional<Account> optionalAccount = this.accountRepository.findById(vo.getIdAccount());
        if (!optionalBlogs.isPresent() && optionalAccount.isPresent()){
            Blogs blogs = new Blogs();
            blogs.setAccount(optionalAccount.get());
            BeanUtils.copyProperties(vo,blogs);
            this.blogsRepository.save(blogs);
            return vo;
        }else {
            return null;
        }

    }

    @Override
    public BlogsVO update(BlogsVO vo) {
        Optional<Blogs> optionalBlogs = this.blogsRepository.findById(vo.getTitle());
        Optional<Account> optionalAccount = this.accountRepository.findById(vo.getIdAccount());
        if (optionalBlogs.isPresent() && optionalAccount.isPresent()){
            Blogs blogs = optionalBlogs.get();
            blogs.setAccount(optionalAccount.get());
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
            blogsVO.setIdAccount(optionalBlogs.get().getAccount().getId());
            BeanUtils.copyProperties(optionalBlogs.get(),blogsVO);
            return blogsVO;
        }
        return null;
    }
}
