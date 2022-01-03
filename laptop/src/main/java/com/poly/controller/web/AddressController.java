package com.poly.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poly.service.CountriesService;
import com.poly.service.DeliveryAddressService;
import com.poly.vo.AccountVO;
import com.poly.vo.DeliveryAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class AddressController {

    @Autowired
    private DeliveryAddressService addressService;

    @Autowired
    private CountriesService countriesService;

    @PostMapping(value = "/ajax/delete-address")
    public ResponseEntity<Map<String,Object>> deleteAddress(HttpServletRequest request){
        String idAddress = request.getParameter("idAddress");
        DeliveryAddressVO vo = this.addressService.delete(Integer.parseInt(idAddress));
        Map<String,Object> map = new HashMap<>();
        if (vo != null){
            map.put("code",200);
            map.put("data",vo);
        }
        return ResponseEntity.ok(map);
    }

    @PostMapping(value = "/ajax/districts-address")
    public ResponseEntity<List<HashMap<String,?>>> getDistricts(HttpServletRequest request) throws JsonProcessingException{
        String code = request.getParameter("code");
        String url = "https://provinces.open-api.vn/api/?depth=2";
        List<HashMap<String,?>> mapListDistric = this.countriesService.getDistric(Integer.parseInt(code),url);
        return ResponseEntity.ok(mapListDistric);
    }
    @PostMapping(value = "/ajax/wards-address")
    public ResponseEntity<List<HashMap<String,?>>> getWards(HttpServletRequest request) throws JsonProcessingException{
        String code = request.getParameter("code");
        String keyCity = request.getParameter("city");
        String url = "https://provinces.open-api.vn/api/?depth=3";
        List<HashMap<String,?>> mapListWards = this.countriesService.getWards(Integer.parseInt(code),url, Integer.parseInt(keyCity));
        return ResponseEntity.ok(mapListWards);
    }
}
