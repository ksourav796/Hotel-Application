package com.hyva.hotel.mapper;

import com.hyva.hotel.pojo.RoomTypesPojo;
import com.hyva.hotel.entities.RoomTypes;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomTypeMapper {
    public static RoomTypes mapPojoToEntity(RoomTypesPojo roomTypesPojo){
        RoomTypes roomTypes=new RoomTypes();
        roomTypes.setId(roomTypesPojo.getId());
        roomTypes.setTitle(roomTypesPojo.getTitle());
        roomTypes.setDescription(roomTypesPojo.getDescription());
        roomTypes.setBase_price(roomTypesPojo.getBase_price());
        return roomTypes;
    }

    public static List<RoomTypesPojo> mapEntityToPojo(List<RoomTypes> typeList){
        List<RoomTypesPojo> list=new ArrayList<>();
        for(RoomTypes roomTypes:typeList) {
            RoomTypesPojo pojo = new RoomTypesPojo();
            pojo.setId(roomTypes.getId());
            pojo.setTitle(roomTypes.getTitle());
            pojo.setDescription(roomTypes.getDescription());
            pojo.setBase_price(roomTypes.getBase_price());
            list.add(pojo);
        }
        return list;
    }
}