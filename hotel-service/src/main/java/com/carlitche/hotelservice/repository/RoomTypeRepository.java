package com.carlitche.hotelservice.repository;

import com.carlitche.hotelservice.entity.RoomType;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface RoomTypeRepository extends CrudRepository<RoomType, Long> {

  Optional<RoomType> findByType(String type);

}
