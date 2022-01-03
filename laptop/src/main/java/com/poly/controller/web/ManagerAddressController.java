package com.poly.controller.web;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.poly.DTO.Countries;
import com.poly.helper.HeaderHelper;
import com.poly.service.AccountService;
import com.poly.service.CountriesService;
import com.poly.service.DeliveryAddressService;
import com.poly.vo.AccountVO;
import com.poly.vo.DeliveryAddressVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

@Controller
public class ManagerAddressController {

    @Autowired private CountriesService countriesService;
    @Autowired private HeaderHelper headerHelper;
    @Autowired private DeliveryAddressService addressService;
    @Autowired private AccountService accountService;

    @RequestMapping(value = "/list-address")
    public String viewList(Model model, HttpServletRequest request){
        headerHelper.setHeaderSession(model);
        if (request.getParameter("errorSize") != null){
            model.addAttribute("errorSize","Mỗi tài khoản tối đa 4 địa chỉ vui lòng xóa trước khi thêm !");
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            List<DeliveryAddressVO> voList = this.addressService.getListAddressByUser(accountVO.getId());
            model.addAttribute("listAddress",voList);
            return "user/list-address-manager";
        }else {
            return "redirect:/login";
        }

    }

    @RequestMapping(value = "/address-form")
    public String viewFrom(Model model) throws JsonProcessingException {
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            List<DeliveryAddressVO> voList = this.addressService.getListAddressByUser(accountVO.getId());
            if (voList.size() >= 4){
                return "redirect:/list-address?errorSize="+true;
            }else {
                String url = "https://provinces.open-api.vn/api/?depth=1";
                List<HashMap<String,?>> countries = this.countriesService.getListCountries(url);
                model.addAttribute("countries",countries);
                return "user/form-address-manager";
            }
        }else {
            return "redirect:/login";
        }

    }
    @RequestMapping(value = "/address-form/{id}")
    public String viewFromEdit(Model model, @PathVariable("id") Integer id) throws JsonProcessingException {
        headerHelper.setHeaderSession(model);
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            DeliveryAddressVO vo = this.addressService.getAddressByIdAndAccount(id,accountVO.getId());
            if (vo==null){
                return "redirect:/error-page";
            }
            Countries countries = this.countriesService.getCountry(vo.getAddress());

            String url = "https://provinces.open-api.vn/api/?depth=1";
            List<HashMap<String,?>> cityList = this.countriesService.getListCountries(url);
            String urlDistrict = "https://provinces.open-api.vn/api/?depth=2";
            List<HashMap<String,?>> mapListDistric = this.countriesService.getDistric(countries.getCity(),urlDistrict);
            String urlWard = "https://provinces.open-api.vn/api/?depth=3";
            List<HashMap<String,?>> mapListWards = this.countriesService.getWards(countries.getDistrict(),urlWard, countries.getCity());
            model.addAttribute("countries",cityList);
            model.addAttribute("country",countries);
            model.addAttribute("listDistrict",mapListDistric);
            model.addAttribute("listWard",mapListWards);
            model.addAttribute("address",vo);
            return "user/form-address-manager";
        }else {
            return "redirect:/login";
        }

    }



    @PostMapping(value = "/save-update-address")
    public String addAddress(Model model, HttpServletRequest request){
        String city = request.getParameter("cityname");
        String distic = request.getParameter("districtname");
        String ward = request.getParameter("wardsname");
        String name = request.getParameter("username");
        String phone = request.getParameter("userphone");
        String idAddress = request.getParameter("idaddress");
        //Null or on
        String defaultAddress = request.getParameter("setdefault");
        String addressDetail = request.getParameter("numberhome").trim();
        String address = addressDetail+", "+ward+", "+distic+", "+city;
        DeliveryAddressVO addressVO = this.addressService.createAddressVO(name,phone,address,defaultAddress);
        if (idAddress.equals("") || idAddress == null){
            addressVO.setId(null);
        }else {
            addressVO.setId(Integer.parseInt(idAddress));
        }
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth != null && !auth.getName().equals("anonymousUser")) {
            String nameLogin = auth.getName();
            AccountVO accountVO = this.accountService.findByEmailUser(nameLogin);
            if (accountVO != null){
                addressVO.setIdAccount(accountVO.getId());
                if (addressVO.getId() == null){
                    this.addressService.save(addressVO);
                }else {
                    this.addressService.update(addressVO);
                }
            }
            return "redirect:/list-address";
        }else {
            return "redirect:/login";
        }

    }
}
