package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Services;
import com.hyva.hotel.pojo.ServicesPojo;

import java.util.ArrayList;
import java.util.List;

public class ServiceMapper {
    public static Services mapPojoToEntity(ServicesPojo servicesPojo){
        Services service = new Services();
        service.setId(servicesPojo.getId());
        service.setTitle(servicesPojo.getTitle());
        service.setDescription(servicesPojo.getDescription());
        service.setPrice(servicesPojo.getPrice());
        service.setShort_description(servicesPojo.getShort_description());
        service.setStatus(servicesPojo.getStatus());
        service.setRoomTypes(servicesPojo.getRoomTypes());
        service.setPrice_type(servicesPojo.getPrice_type());
        return service;
    }

    public static List<ServicesPojo> mapEntityToPojo(List<Services> servicesListList){
        List<ServicesPojo> list=new ArrayList<>();
        for(Services roomTypes:servicesListList) {
            ServicesPojo pojo = new ServicesPojo();
            pojo.setId(roomTypes.getId());
            pojo.setTitle(roomTypes.getTitle());
            pojo.setDescription(roomTypes.getDescription());
            pojo.setPrice(roomTypes.getPrice());
            pojo.setDescription(roomTypes.getDescription());
            pojo.setShort_description(roomTypes.getShort_description());
            pojo.setStatus(roomTypes.getStatus());
            pojo.setRoomTypes(roomTypes.getRoomTypes());
            pojo.setPrice_type(roomTypes.getPrice_type());
            list.add(pojo);
        }
        return list;
    }
}
