package com.hyva.hotel.mapper;

import com.hyva.hotel.entities.Rooms;
import com.hyva.hotel.pojo.RoomsPojo;

import java.util.ArrayList;
import java.util.List;

public class HotelRoomsMapper {
    public static Rooms mapPojoToEntity(RoomsPojo roomsPojo){
        Rooms rooms=new Rooms();
        rooms.setId(roomsPojo.getId());
//        rooms.setFloorId(roomsPojo.getFloorId());
        rooms.setRoomno(roomsPojo.getRoomno());
        rooms.setRoomTypeId(roomsPojo.getRoomTypeId());
        return rooms;
    }

    public static List<RoomsPojo> mapEntityToPojo(List<Rooms> roomList){
        List<RoomsPojo> list=new ArrayList<>();
        for(Rooms roomTypes:roomList) {
            RoomsPojo pojo = new RoomsPojo();
            pojo.setId(roomTypes.getId());
//            pojo.setFloorId(roomTypes.getFloorId());
            pojo.setRoomno(roomTypes.getRoomno());
            pojo.setRoomTypeId(roomTypes.getRoomTypeId());
            list.add(pojo);
        }
        return list;
    }
}
