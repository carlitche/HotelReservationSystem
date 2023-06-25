package com.carlitche.hotelservice.controller;

import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.model.RoomDto;
import com.carlitche.hotelservice.service.RoomService;
import org.modelmapper.ModelMapper;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.StreamSupport;

@RestController
public class RoomController {

    private final ModelMapper modelMapper;

    private final RoomService service;

    public RoomController(ModelMapper modelMapper, RoomService service) {
        this.modelMapper = modelMapper;
        this.service = service;
    }

    @GetMapping("/hotels/{hotelId}/rooms/{roomId}")
    public RoomDto getHotelRoomById(@PathVariable Long hotelId, @PathVariable Long roomId){
        Room room = service.getHotelRoomById(hotelId, roomId);

        return modelMapper.map(room, RoomDto.class);
    }

    @GetMapping("/hotels/{hotelId}/rooms")
    public List<RoomDto> getAllHotelRooms(@PathVariable Long hotelId){
        List<Room> roomList = service.getAllHotelRooms(hotelId);

        return StreamSupport.stream(roomList.spliterator(), false)
                     .map(data -> modelMapper.map(data, RoomDto.class))
                     .toList();
    }

}
