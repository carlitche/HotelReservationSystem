package com.carlitche.hotelservice.controller;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.model.HotelDto;
import com.carlitche.hotelservice.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
public class HotelController {

  private final HotelService service;

  private final ModelMapper modelMapper;

  public HotelController(HotelService service, ModelMapper modelMapper) {

    this.service = service;
    this.modelMapper = modelMapper;
  }

  @PostMapping("/hotels")
  @ResponseStatus(HttpStatus.CREATED)
  public void createHotelAndRooms(
      @RequestBody HotelDto dto) {

    Hotel hotel = modelMapper.map(dto, Hotel.class);
    service.saveHotel(hotel);
  }

  @GetMapping("/hotels/{id}")
  public Hotel getHotelById(@PathVariable Long id) {
    return null;
  }

}
