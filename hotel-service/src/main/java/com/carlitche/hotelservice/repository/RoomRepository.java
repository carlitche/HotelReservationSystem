package com.carlitche.hotelservice.repository;

import com.carlitche.hotelservice.entity.Room;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface RoomRepository extends CrudRepository<Room, Long> {

    Optional<Room> findByHotel_HotelIdAndRoomId(Long hotelId, Long roomId);

    List<Room> findByHotel_HotelId(Long hotelId);
}
