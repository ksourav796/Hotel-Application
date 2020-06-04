package com.hyva.hotel.service;

import com.hyva.hotel.entities.*;
import com.hyva.hotel.respositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BasicDataService {

    @Autowired
    BsUserRepository bsUserRepository;
    @Autowired
    CountryRepository countryRepository;
    @Autowired
    FormSetupRepository formSetupRepository;
    @Autowired
    StateRepository stateRepository;
    @Autowired
    CityRepository cityRepository;

    public void insertBasicData() throws Exception {
        //============================================= User ======================================================================
        List<User> userList = (List<User>) bsUserRepository.findAll();
        if (userList.isEmpty()) {
            User userObj = new User();
            userObj.setEmail("");
            userObj.setFull_name("admin");
            userObj.setPasswordUser("admin");
            userObj.setPhone("");
            userObj.setSecurityAnswer("");
            userObj.setSecurityQuestion("");
            userObj.setStatus("Active");
            userObj.setUserName("admin");
            userObj.setUserToken("");
            bsUserRepository.save(userObj);


        }
        List<Country> countryList = countryRepository.findAll();
        if (countryList.isEmpty()) {
            insertCountryData("India");
            insertCountryData("Europe");
            insertCountryData("Malaysia");
            insertCountryData("Indonesia");
            insertCountryData("Singapore");
            insertCountryData("Thailand");
            insertCountryData("USA");
            insertCountryData("England");
            insertCountryData("Australia");
            insertCountryData("Srilanka");
            insertCountryData("Pakistan");
            insertCountryData("Brunei");
        }
        List<State> stateList = stateRepository.findAll();
        if (stateList.isEmpty()) {
            insertStateData("Andhra Pradesh","India");
            insertStateData("Telangana","India");
            insertStateData("Arunachal Pradesh","India");
            insertStateData("Assam","India");
            insertStateData("Bihar","India");
            insertStateData("Chhattisgarh","India");
            insertStateData("Goa","India");
            insertStateData("Gujarat","India");
            insertStateData("Haryana","India");
            insertStateData("Himachal Pradesh","India");
            insertStateData("Jammu and Kashmir","India");
            insertStateData("Jharkhand","India");
            insertStateData("Karnataka","India");
            insertStateData("Kerala","India");
            insertStateData("Madya Pradesh","India");
            insertStateData("Maharashtra","India");
            insertStateData("Manipur","India");
            insertStateData("Meghalaya","India");
            insertStateData("Mizoram","India");
            insertStateData("Nagaland","India");
            insertStateData("Orissa","India");
            insertStateData("Punjab","India");
            insertStateData("Rajasthan","India");
            insertStateData("Sikkim","India");
            insertStateData("Tamil Nadu","India");
            insertStateData("Uttaranchal","India");
            insertStateData("Uttar Pradesh","India");
            insertStateData("West Bengal","India");
            insertStateData("Andaman and Nicobar Islands","India");
            insertStateData("Chandigarh","India");
            insertStateData("Dadar and Nagar Haveli","India");
            insertStateData("Daman and Diu","India");


        }

        List<Cities> citiesList=cityRepository.findAll();
        if(citiesList.isEmpty()){
            insertCityData("Bangalore","India","Karnataka");
            insertCityData("Hyderabad","India","Andhra Pradesh");
            insertCityData("Kurnool","India","Andhra Pradesh");

        }

    }

    public  void pushBasicData(){
        List<FormSetUp> formSetUpList = formSetupRepository.findAll();
        if (formSetUpList.isEmpty()) {
            insertFormSetUp("OrderInvoiceNo", "OIN", "00000000", true, "AR","");

        }
    }

    public void insertFormSetUp(String typename, String typeprefix, String nextref, boolean include_date, String transactionType,String smsMessage) {
        FormSetUp c = new FormSetUp();
        c.setTypename(typename);
        c.setTypeprefix(typeprefix);
        c.setNextref(nextref);
        c.setTransactionType(transactionType);
        c.setInclude_date(include_date);
//        c.setSmsId(addSmsConfigurator(smsMessage,"true"));
        formSetupRepository.save(c);
    }

    private void insertCountryData(String countryName){
        Country country=new Country();
        country.setCountryName(countryName);
        country.setStatus("Active");
        countryRepository.save(country);
    }
    private void insertStateData(String stateName,String countryName){
        State state=new State();
        state.setCountryName(countryName);
        state.setStateName(stateName);
        state.setStatus("Active");
        stateRepository.save(state);
    }

    private void insertCityData(String name,String country, String state){
        Cities cities=new Cities();
        cities.setName(name);
        cities.setCountry(country);
        cities.setState(state);
        cityRepository.save(cities);

    }
//    private void insertFloorsData(String name,String floorno,String description){
//        Floors floors=new Floors();
//        floors.setName(name);
//        floors.setDescription(description);
//        floors.setFloor_no(floorno);
//        floors.setActive("Active");
//        hotelFloorRepository.save(floors);
//    }
}


















