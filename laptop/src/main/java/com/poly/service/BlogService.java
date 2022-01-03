package com.poly.service;

import com.poly.vo.BlogsVO;

import java.util.List;

public interface BlogService {

    List<BlogsVO> findAll();

    BlogsVO getOneBlog(String id);

    BlogsVO create(BlogsVO vo);

    BlogsVO update(BlogsVO vo);

    BlogsVO delete(String id);

    List<BlogsVO> getListBlog(Integer start, Integer end);

    List<BlogsVO> getBlogIsHot();

    BlogsVO findBlogById(String id);

    List<BlogsVO> getFourBlog(String idBlog);
}
