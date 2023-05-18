package com.carlitche.hotelservice.model;

import java.util.ArrayList;
import java.util.List;

public class HotelDto {

    private Long hotelId;

    private String name;

    private String address;

    private String location;

    private List<RoomDto> rooms = new ArrayList<>();

    public HotelDto() {
    }

    public HotelDto(String name, String address, String location) {
        this.name = name;
        this.address = address;
        this.location = location;
//        this.rooms = rooms;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<RoomDto> getRooms() {
        return rooms;
    }

    public void setRooms(List<RoomDto> rooms) {
        this.rooms = rooms;
    }

    @Override
    public String toString() {
        return "HotelDto{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", address='" + address + '\'' +
                ", location='" + location + '\'' +
                ", rooms=" + rooms +
                '}';
    }
}
