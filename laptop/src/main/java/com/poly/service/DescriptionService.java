package com.poly.service;

import com.poly.vo.DescriptionVO;

import java.util.List;

public interface DescriptionService {

    List<DescriptionVO> getDescriptionByProduct(String idProduct);

    List<DescriptionVO> getDescriptionByBlog(String idBlog);

    DescriptionVO getOneDescriptionById(Integer id);

    DescriptionVO create(DescriptionVO vo);

    DescriptionVO update(DescriptionVO vo, Integer id);

    DescriptionVO delete(Integer id);
}
