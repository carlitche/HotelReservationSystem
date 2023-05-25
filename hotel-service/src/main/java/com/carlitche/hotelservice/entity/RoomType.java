package com.carlitche.hotelservice.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "room_type",
       indexes = {@Index(name = "idx_roomtype_type_unq",
                         columnList = "type",
                         unique = true)},
       uniqueConstraints = {@UniqueConstraint(name = "uc_roomtype_type",
                                              columnNames = {"type"})})
public class RoomType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "room_type_id")
    private Long roomTypeId;

    private String type;

    private String description;

    @OneToMany(mappedBy = "roomType", fetch = FetchType.LAZY)
    private List<Room> rooms = new ArrayList<>();

    public RoomType() {

    }

    public RoomType(String type, String description) {
        this.type = type;
        this.description = description;
    }

    public RoomType(String type) {

        this.type = type;
    }

    public Long getRoomTypeId() {

        return roomTypeId;
    }

    public void setRoomTypeId(Long roomTypeId) {

        this.roomTypeId = roomTypeId;
    }

    public String getType() {

        return type;
    }

    public void setType(String type) {

        this.type = type;
    }

    public List<Room> getRooms() {

        return rooms;
    }

    public void setRooms(List<Room> rooms) {
        if (this.rooms == null)
            this.rooms = new ArrayList<>();
        for (Room room : rooms) {
            this.rooms.add(room);
            room.setRoomType(this);
        }
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoomType{" +
                "roomTypeId=" + roomTypeId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                ", rooms=" + rooms +
                '}';
    }

}
