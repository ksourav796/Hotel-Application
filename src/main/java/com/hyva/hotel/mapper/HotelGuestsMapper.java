package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Guests;
import com.hyva.hotel.pojo.GuestsPojo;

import java.util.ArrayList;
import java.util.List;

public class HotelGuestsMapper {
    public static Guests mapPojoToEntity(GuestsPojo pojo){
        Guests guests=new Guests();
        guests.setId(pojo.getId());
        guests.setFirstname(pojo.getFirstname());
        guests.setLastname(pojo.getLastname());
        guests.setAddress(pojo.getAddress());
        guests.setDob(pojo.getDob());
        guests.setEmail(pojo.getEmail());
        guests.setGender(pojo.getGender());
        guests.setMobile(pojo.getMobile());
        guests.setPassword(pojo.getPassword());
        guests.setCompanyname(pojo.getCompanyname());
        guests.setCity(pojo.getCity());
        guests.setCountry(pojo.getCountry());
        guests.setStatus(pojo.getStatus());
        guests.setState(pojo.getState());
        return guests;
    }
    public static List<GuestsPojo> mapEntityToPojo(List<Guests> guestsList){
        List<GuestsPojo> list=new ArrayList<>();
        for(Guests guests:guestsList) {
            GuestsPojo guestsPojo = new GuestsPojo();
            guestsPojo.setId(guests.getId());
            guestsPojo.setFirstname(guests.getFirstname());
            guestsPojo.setLastname(guests.getLastname());
            guestsPojo.setAddress(guests.getAddress());
            guestsPojo.setDob(guests.getDob());
            guestsPojo.setEmail(guests.getEmail());
            guestsPojo.setGender(guests.getGender());
            guestsPojo.setMobile(guests.getMobile());
            guestsPojo.setPassword(guests.getPassword());
            guestsPojo.setCompanyname(guests.getCompanyname());
            guestsPojo.setCity(guests.getCity());
            guestsPojo.setCountry(guests.getCountry());
            guestsPojo.setStatus(guests.getStatus());
            guestsPojo.setState(guests.getState());
            list.add(guestsPojo);
        }
        return list;
    }
}

