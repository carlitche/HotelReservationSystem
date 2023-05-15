package com.carlitche.hotelservice.repository;

import com.carlitche.hotelservice.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface HotelRepository extends CrudRepository<Hotel, Long> {

  Optional<Hotel> findByHotelId(Long hotelId);
}
