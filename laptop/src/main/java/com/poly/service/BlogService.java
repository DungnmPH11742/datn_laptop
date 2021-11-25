package com.poly.service;

import com.poly.vo.BlogsVO;

public interface BlogService {

    BlogsVO getOneBlog(String title);

    BlogsVO create(BlogsVO vo);

    BlogsVO update(BlogsVO vo);

    BlogsVO delete(String id);
}
