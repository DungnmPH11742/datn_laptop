package com.poly.service.impl;

import com.poly.entity.Contact;
import com.poly.repo.ContactRepository;
import com.poly.service.ContactService;
import com.poly.vo.ContactVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ContactServiceImpl implements ContactService {

    @Autowired
    private ContactRepository contactRepository;
    @Autowired
    private ModelMapper modelMapper;

    private ContactVO convertContactToDto(Contact contact) {
        ContactVO contactVO = modelMapper.map(contact, ContactVO.class);
        return contactVO;
    }
    private Contact convertContactVO(ContactVO contactVO) {
        Contact contact = modelMapper.map(contactVO, Contact.class);
        return contact;
    }
    private List<ContactVO> convertListContactDto(List<Contact> lstContact) {
        List<ContactVO> vos = new ArrayList<>();
        lstContact.forEach(contact -> {
            vos.add(modelMapper.map(contact, ContactVO.class));
        });
        return vos;
    }

    public ContactVO convertContactToDtoById(Integer id) {

        ContactVO contactVO = new ContactVO();
        Optional<Contact> optional = contactRepository.findById(id);
        if (optional.isPresent()) {
            Contact contact = optional.get();
            contactVO = modelMapper.map(contact, ContactVO.class);
        }
        return contactVO;
    }

    @Override
    public ContactVO create(ContactVO vo) {
        vo.setContactDate(String.valueOf(new Date()));
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        vo.setContactPersonEmail(auth.getName());
        return convertContactToDto(contactRepository.save(convertContactVO(vo)));
    }
}
