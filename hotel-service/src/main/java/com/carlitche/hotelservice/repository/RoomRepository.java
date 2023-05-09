package com.carlitche.hotelservice.repository;

import com.carlitche.hotelservice.entity.Room;
import org.springframework.data.repository.CrudRepository;

public interface RoomRepository extends CrudRepository<Room, Long> {
}
