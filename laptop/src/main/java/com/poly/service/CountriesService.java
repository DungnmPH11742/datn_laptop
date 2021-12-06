package com.poly.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
}
