package com.carlitche.hotelservice.repository;

import com.carlitche.hotelservice.entity.Hotel;
import org.springframework.data.repository.CrudRepository;

public interface HotelRepository extends CrudRepository<Hotel, Long> {
}
