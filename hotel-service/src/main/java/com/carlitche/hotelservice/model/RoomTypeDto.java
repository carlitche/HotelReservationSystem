package com.carlitche.hotelservice.model;

public class RoomTypeDto {

    private Long roomTypeId;

    private String type;

    private String description;

    public RoomTypeDto() {
    }

    public RoomTypeDto(String type) {
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "RoomTypeDto{" +
                "roomTypeId=" + roomTypeId +
                ", type='" + type + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
