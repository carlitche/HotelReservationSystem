package com.carlitche.hotelservice.model;

public class RoomDto {
    private Long roomId;

    private Integer number;

    private Integer floor;

    private String name;

    private Boolean available;

    private RoomTypeDto roomType;

    public RoomDto() {
    }

    public RoomDto(Integer number, Integer floor, String name, Boolean available) {
        this.number = number;
        this.floor = floor;
        this.name = name;
        this.available = available;
    }

    public Long getRoomId() {
        return roomId;
    }

    public void setRoomId(Long roomId) {
        this.roomId = roomId;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public Integer getFloor() {
        return floor;
    }

    public void setFloor(Integer floor) {
        this.floor = floor;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getAvailable() {
        return available;
    }

    public void setAvailable(Boolean available) {
        this.available = available;
    }

    public RoomTypeDto getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomTypeDto roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "RoomDto{" +
                "roomId=" + roomId +
                ", number=" + number +
                ", floor=" + floor +
                ", name='" + name + '\'' +
                ", available=" + available +
                ", roomType=" + roomType +
                '}';
    }
}
