package com.poly.service;

import com.poly.vo.ContactVO;
import org.springframework.stereotype.Service;

@Service
public interface ContactService {
    ContactVO create(ContactVO vo);
}
