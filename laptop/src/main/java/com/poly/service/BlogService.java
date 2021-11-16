package com.poly.service;

import com.poly.vo.BlogsVO;
import com.poly.vo.DescriptionVO;

public interface BlogService {

    BlogsVO getOneBlog(String title);

    BlogsVO create(BlogsVO vo);

    BlogsVO update(BlogsVO vo);

    BlogsVO delete(String id);
}
