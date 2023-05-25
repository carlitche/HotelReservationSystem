package com.carlitche.hotelservice.service;

import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public Optional<Room> getHotelRoomById(Long hotelId, Long roomId) {
        return roomRepository.findByHotel_HotelIdAndRoomId(hotelId, roomId);
    }

    public List<Room> getAllHotelRooms(Long hotelId){
        return roomRepository.findByHotel_HotelId(hotelId);
    }
}
