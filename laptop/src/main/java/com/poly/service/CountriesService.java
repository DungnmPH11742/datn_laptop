package com.poly.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.poly.DTO.Countries;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CountriesService {
    public List<HashMap<String,?>> getListCountries(String url) throws JsonProcessingException {
        RestTemplate restTemplate = new RestTemplate();
        Object[] countries = restTemplate.getForObject(url,Object[].class);
        List<Object> list = Arrays.asList(countries);
        List<HashMap<String,?>> mapList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            String json = new Gson().toJson(list.get(i));
            ObjectMapper mapper = new ObjectMapper();
            HashMap<String,?> map = mapper.readValue(json,HashMap.class);
            mapList.add(map);
        }
        return mapList;
    }

    public List<HashMap<String,?>> getDistric(Integer code,String url) throws JsonProcessingException {
        List<HashMap<String,?>> mapList = getListCountries(url);
        Object getCity =null;
        for (int i = 0; i < mapList.size(); i++) {
            if (mapList.get(i).get("code").equals(code)){
                getCity = mapList.get(i);
            }
        }
        String huyen = new Gson().toJson(getCity);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,?> map = mapper.readValue(huyen,HashMap.class);
        List<Object> objectList = (List<Object>) map.get("districts");

        List<HashMap<String,?>> mapListDistric = new ArrayList<>();
        for (int i = 0; i < objectList.size(); i++) {
            String json = new Gson().toJson(objectList.get(i));
            ObjectMapper mapper1 = new ObjectMapper();
            HashMap<String,?> getDistric = mapper1.readValue(json,HashMap.class);
            mapListDistric.add(getDistric);
        }
        return mapListDistric;
    }
    public List<HashMap<String,?>> getWards(Integer code,String url, Integer keyCity) throws JsonProcessingException {

        List<HashMap<String,?>> listDistric = getDistric(keyCity,url);
        Object getWard =null;
        for (int i = 0; i < listDistric.size(); i++) {
            if (listDistric.get(i).get("code").equals(code)){
                getWard = listDistric.get(i);
            }
        }
        String huyen = new Gson().toJson(getWard);
        ObjectMapper mapper = new ObjectMapper();
        HashMap<String,?> map = mapper.readValue(huyen,HashMap.class);
        List<Object> objectList = objectList = (List<Object>) map.get("wards");


        List<HashMap<String,?>> mapListWards = new ArrayList<>();
        for (int i = 0; i < objectList.size(); i++) {
            String json = new Gson().toJson(objectList.get(i));
            ObjectMapper mapper1 = new ObjectMapper();
            HashMap<String,?> getWards = mapper1.readValue(json,HashMap.class);
            mapListWards.add(getWards);
        }
        return mapListWards;
    }

    public Countries getCountry(String address) throws JsonProcessingException {
        Countries countries = new Countries();
        String[] splitStr= address.split(", ");
        List<String> listAddress = Arrays.stream(splitStr).collect(Collectors.toList());
        countries.setSoNha(listAddress.get(0));
        countries.setNameCity(listAddress.get(3));
        countries.setNameDistrict(listAddress.get(2));
        countries.setNameWard(listAddress.get(1));
        String url = "https://provinces.open-api.vn/api/?depth=1";
        List<HashMap<String,?>> countriesList = getListCountries(url);
        Integer codeCity = null;
        Integer codeDistrict = null;
        Integer codeWard = null;
        for (HashMap<String, ?> m :countriesList) {
            if (m.get("name").equals(listAddress.get(3))){
                codeCity = Integer.parseInt(m.get("code").toString());
            }
        }
        String urlDistrict = "https://provinces.open-api.vn/api/?depth=2";
        List<HashMap<String,?>> mapListDistric = getDistric(codeCity,urlDistrict);
        for (HashMap<String, ?> m :mapListDistric) {
            if (m.get("name").equals(listAddress.get(2))){
                codeDistrict = Integer.parseInt(m.get("code").toString());
            }
        }

        String urlWard = "https://provinces.open-api.vn/api/?depth=3";
        List<HashMap<String,?>> mapListWards = getWards(codeDistrict,urlWard, codeCity);
        for (HashMap<String, ?> m :mapListWards) {
            if (m.get("name").equals(listAddress.get(1))){
                codeWard = Integer.parseInt(m.get("code").toString());
            }
        }
        countries.setCity(codeCity);
        countries.setDistrict(codeDistrict);
        countries.setWard(codeWard);
        return countries;
    }
}
