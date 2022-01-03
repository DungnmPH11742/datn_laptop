package com.poly.service;

import com.poly.vo.ImageDetailVO;

import java.util.List;

public interface ImageDetailService {

    List<ImageDetailVO> findByProductId(String id);

    ImageDetailVO save(ImageDetailVO vo);

    ImageDetailVO update(ImageDetailVO vo);

    boolean remove(ImageDetailVO vo);
}
