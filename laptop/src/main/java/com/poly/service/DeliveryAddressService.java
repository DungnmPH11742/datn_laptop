package com.poly.service;

import com.poly.vo.DeliveryAddressVO;

import java.util.List;

public interface DeliveryAddressService {
    DeliveryAddressVO save(DeliveryAddressVO addressVO);

    List<DeliveryAddressVO> getListAddressByUser(Integer user);

    List<DeliveryAddressVO> getListAddressDefaultTrue();
    DeliveryAddressVO update(DeliveryAddressVO addressVO);

    DeliveryAddressVO delete(Integer id);
    DeliveryAddressVO getAddressById(Integer id);
}
