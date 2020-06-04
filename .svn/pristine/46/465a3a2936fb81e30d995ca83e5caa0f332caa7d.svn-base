package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Cities;
import com.hyva.hotel.pojo.CityPojo;
import java.util.ArrayList;
import java.util.List;

public class CityMapper {
    public static Cities mapCityPojoToEntity(CityPojo cityPojo){
        Cities cities = new Cities();
        cities.setId(cityPojo.getId());
        cities.setCountry(cityPojo.getCountry());
        cities.setName(cityPojo.getName());
        cities.setState(cityPojo.getState());
        return cities;
    }
    public static List<CityPojo> mapCitiesEntityToPojo(List<Cities> cityList){
        List<CityPojo> list=new ArrayList<>();
        for(Cities cities:cityList) {
            CityPojo cityPojo = new CityPojo();
            cityPojo.setId(cities.getId());
            cityPojo.setCountry(cities.getCountry());
            cityPojo.setName(cities.getName());
            cityPojo.setState(cities.getState());
            list.add(cityPojo);
        }
        return list;
    }
}
