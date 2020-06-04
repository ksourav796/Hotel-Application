package com.hyva.hotel.pojo;

import com.hyva.hotel.entities.RoomTypes;

public class RoomsPojo {
    private Long id;

    private String roomno;
    private RoomTypes roomTypeId;
    private Long floor_Id;
    private Long room_typeId;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getRoomno() {
        return roomno;
    }

    public void setRoomno(String roomno) {
        this.roomno = roomno;
    }

    public RoomTypes getRoomTypeId() {
        return roomTypeId;
    }

    public void setRoomTypeId(RoomTypes roomTypeId) {
        this.roomTypeId = roomTypeId;
    }

    public Long getFloor_Id() {
        return floor_Id;
    }

    public void setFloor_Id(Long floor_Id) {
        this.floor_Id = floor_Id;
    }

    public Long getRoom_typeId() {
        return room_typeId;
    }

    public void setRoom_typeId(Long room_typeId) {
        this.room_typeId = room_typeId;
    }
}
