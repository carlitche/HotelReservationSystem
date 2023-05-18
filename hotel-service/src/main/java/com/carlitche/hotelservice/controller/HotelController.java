package com.carlitche.hotelservice.controller;

import com.carlitche.hotelservice.exception.ContentNotFoundException;
import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.model.HotelDto;
import com.carlitche.hotelservice.service.HotelService;
import org.modelmapper.ModelMapper;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

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
  public void createHotelAndRooms(@RequestBody HotelDto dto) {

    Hotel hotel = modelMapper.map(dto, Hotel.class);
    service.saveHotel(hotel);
  }

  @GetMapping("/hotels/{id}")
  public HotelDto getHotelById(@PathVariable Long id) {

    Optional<Hotel> hotelById = service.getHotelById(id);
    HotelDto result = null;
    if(hotelById.isPresent())
      result = modelMapper.map( hotelById, HotelDto.class);
    else
      throw new ContentNotFoundException("No Hotel found with the id: " + id);

    return result;
  }

  @GetMapping("/hotels")
  public List<HotelDto> getAllHotels(){
    Iterable<Hotel> hotels = service.getAllHotels();
    return StreamSupport.stream(hotels.spliterator(), false)
            .map(data -> modelMapper.map(data, HotelDto.class))
            .toList();
  }

}
