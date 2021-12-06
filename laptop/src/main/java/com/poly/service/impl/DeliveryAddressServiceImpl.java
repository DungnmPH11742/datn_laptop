package com.poly.service.impl;

import com.poly.entity.Account;
import com.poly.entity.DeliveryAddress;
import com.poly.repo.AccountRepository;
import com.poly.repo.DeliveryAddressRepository;
import com.poly.service.DeliveryAddressService;
import com.poly.vo.DeliveryAddressVO;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryAddressServiceImpl implements DeliveryAddressService {
    @Autowired
    private DeliveryAddressRepository repository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Override
    public DeliveryAddressVO save(DeliveryAddressVO addressVO) {
        DeliveryAddress deliveryAddress = modelMapper.map(addressVO,DeliveryAddress.class);
        Optional<Account> account = this.accountRepository.findById(addressVO.getIdAccount());
        if (account.isPresent()){
            deliveryAddress.setAccount(account.get());
            if (addressVO.getSetAsDefault()){
                List<DeliveryAddress> listEntity = this.repository.getDeliveryAddressBySetAsDefaultTrue();
                listEntity.forEach(d -> {
                    d.setSetAsDefault(false);
                    this.repository.save(d);
                });
            }
            this.repository.save(deliveryAddress);
            addressVO.setId(deliveryAddress.getId());
            return addressVO;
        }
        return null;
    }

    @Override
    public DeliveryAddressVO update(DeliveryAddressVO addressVO) {
        Optional<DeliveryAddress> entity = this.repository.findById(addressVO.getId());
        if (entity.isPresent()){
            modelMapper.map(addressVO,entity.get());
            if (addressVO.getSetAsDefault()){
                List<DeliveryAddress> listEntity = this.repository.getDeliveryAddressBySetAsDefaultTrue();
                listEntity.forEach(d -> {
                    d.setSetAsDefault(false);
                    this.repository.save(d);
                });
            }
            this.repository.save(entity.get());
            return addressVO;
        }
        return null;
    }

    @Override
    public DeliveryAddressVO delete(Integer id) {
        Optional<DeliveryAddress> entity = this.repository.findById(id);
        if (entity.isPresent()){
            DeliveryAddressVO vo = this.modelMapper.map(entity.get(),DeliveryAddressVO.class);
            vo.setIdAccount(entity.get().getAccount().getId());
            this.repository.delete(entity.get());
            return vo;
        }
        return null;
    }

    @Override
    public List<DeliveryAddressVO> getListAddressByUser(Integer user) {
        List<DeliveryAddress> listEntity = this.repository.getListDeliveryAddressByAccount(user);
        List<DeliveryAddressVO> listVo = new ArrayList<>();
        listEntity.forEach(d ->{
            listVo.add(modelMapper.map(d,DeliveryAddressVO.class));
        });
        return listVo;
    }

    @Override
    public List<DeliveryAddressVO> getListAddressDefaultTrue() {
        return null;
    }

    @Override
    public DeliveryAddressVO getAddressById(Integer id) {
        Optional<DeliveryAddress> entity = this.repository.findById(id);
        if (entity.isPresent()){
            DeliveryAddressVO vo = this.modelMapper.map(entity.get(),DeliveryAddressVO.class);
            vo.setIdAccount(entity.get().getAccount().getId());
            return vo;
        }
        return null;
    }
}
