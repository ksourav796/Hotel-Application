package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Country;
import com.hyva.hotel.pojo.CountryPojo;

import java.util.ArrayList;
import java.util.List;

public class CountryMapper {
    public static Country mapCountryPojoToEntity(CountryPojo countryPojo){
        Country country = new Country();
        country.setCountryId(countryPojo.getCountryId());
        country.setCountryName(countryPojo.getCountryName());
        country.setStatus(countryPojo.getStatus());
        return country;
    }
    public static List<CountryPojo> mapCountryEntityToPojo(List<Country> countryList){
        List<CountryPojo> list=new ArrayList<>();
        for(Country country:countryList) {
            CountryPojo countryPojo = new CountryPojo();
            countryPojo.setCountryId(country.getCountryId());
            countryPojo.setCountryName(country.getCountryName());
            countryPojo.setStatus(country.getStatus());
            list.add(countryPojo);
        }
        return list;
    }
}
