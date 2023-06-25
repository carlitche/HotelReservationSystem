package com.carlitche.hotelservice.service;

import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.exception.NoSuchElementFoundException;
import com.carlitche.hotelservice.repository.RoomRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class RoomService {

    private final RoomRepository roomRepository;

    public RoomService(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }


    public Room getHotelRoomById(Long hotelId, Long roomId) {
        return roomRepository.findByHotel_HotelIdAndRoomId(hotelId, roomId)
                             .orElseThrow(() -> new NoSuchElementFoundException(Room.class,
                                                                                "roomId", roomId.toString(),
                                                                                "hotelId", hotelId.toString()));
    }

    public List<Room> getAllHotelRooms(Long hotelId){
        return roomRepository.findByHotel_HotelId(hotelId);
    }
}
