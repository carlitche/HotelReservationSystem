package com.carlitche.hotelservice.service;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.repository.HotelRepository;
import com.carlitche.hotelservice.repository.RoomTypeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class HotelService {

    private final HotelRepository hotelRepository;
    private final RoomTypeRepository roomTypeRepository;

    public HotelService(HotelRepository hotelRepository, RoomTypeRepository roomTypeRepository) {

        this.hotelRepository = hotelRepository;
        this.roomTypeRepository = roomTypeRepository;
    }


    public Hotel saveHotel(Hotel hotel) {

        for (Room room : hotel.getRooms()) {
            // Check if the room type already exists
            Optional<RoomType> existingRoomType = roomTypeRepository.findByType(room.getRoomType().getType());

            // If the room type exists, use the existing one
            if (existingRoomType.isPresent()) {
                room.setRoomType(existingRoomType.get());
            } else {
                throw new RuntimeException("Unknown RoomType");
            }
        }

        return hotelRepository.save(hotel);
    }
}
