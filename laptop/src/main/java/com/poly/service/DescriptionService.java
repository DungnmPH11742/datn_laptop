package com.poly.service;

import com.poly.vo.DescriptionVO;

public interface DescriptionService {

    DescriptionVO create(DescriptionVO vo);

    DescriptionVO update(DescriptionVO vo);

    boolean delete(String id);
}
