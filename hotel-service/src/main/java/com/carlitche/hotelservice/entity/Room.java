package com.carlitche.hotelservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Room",
       indexes = {@Index(name = "idx_room_room_id_hotel_id",
                         columnList = "room_id, hotel_id")})
public class Room {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_id")
    private Long roomId;

    private Integer number;

    private Integer floor;

    private String name;

    private Boolean available;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hotel_id", nullable = false)
    private Hotel hotel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "room_type_id", nullable = false)
    private RoomType roomType;

    public Room() {
    }

    public Room(Integer number, Integer floor, String name, Boolean available) {
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

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    @Override
    public String toString() {
        return "Room{" +
                "roomId=" + roomId +
                ", number=" + number +
                ", floor=" + floor +
                ", name='" + name + '\'' +
                ", available=" + available +
                ", roomType=" + roomType +
                '}';
    }
}
