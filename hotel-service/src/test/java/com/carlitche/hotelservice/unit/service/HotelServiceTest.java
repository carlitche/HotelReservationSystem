package com.carlitche.hotelservice.unit.service;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.exception.NoSuchElementFoundException;
import com.carlitche.hotelservice.repository.HotelRepository;
import com.carlitche.hotelservice.repository.RoomTypeRepository;
import com.carlitche.hotelservice.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.StreamSupport;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HotelService.class)
@ActiveProfiles("test")
class HotelServiceTest {

  @MockBean
  private HotelRepository hotelRepository;

  @MockBean
  private RoomTypeRepository roomTypeRepository;

  @Autowired
  private HotelService service;

  @Test
  void saveHotel_whenCreateHotelAndRooms() {

    RoomType singleType = new RoomType("Single", "Single Room");
    RoomType doubleType = new RoomType("Double", "Double Room");

    Room singleRoom = new Room(101, 1, "Single", true);
    singleRoom.setRoomType(singleType);

    Room doubleRoom = new Room(102, 1, "Double", true);
    doubleRoom.setRoomType(doubleType);

    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");

    hotel.setRooms(List.of(singleRoom, doubleRoom));
    Optional<RoomType> singleOpType = Optional.of(singleType);
    Optional<RoomType> doubleOpType = Optional.of(doubleType);

    when(roomTypeRepository.findByType(any(String.class))).thenReturn(singleOpType)
                                                          .thenReturn(doubleOpType);

    when(hotelRepository.save(hotel)).thenReturn(hotel);

    Hotel result = service.saveHotel(hotel);

    assertEquals(result, hotel);

  }

  @Test
  void getHotel_whenGetHotelById() {

    Long id = 1L;
    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
    hotel.setHotelId(id);

    when(hotelRepository.findByHotelId(id)).thenReturn(Optional.of(hotel));

    Hotel result = service.getHotelById(id);

    assertEquals(result, hotel);

  }

  @Test
  void getNoSuchElementFoundException_whenNoRoomTypeFound(){

    RoomType standardType = new RoomType("Standard", "Single Room");

    Room StandardRoom = new Room(101, 1, "Single", true);
    StandardRoom.setRoomType(standardType);

    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");

    hotel.setRooms(List.of(StandardRoom));

    when(roomTypeRepository.findByType(any(String.class))).thenReturn(Optional.empty());

    when(hotelRepository.save(hotel)).thenReturn(hotel);

    Exception exec = assertThrows(NoSuchElementFoundException.class, () -> service.saveHotel(hotel));
    assertEquals("RoomType was not found for parameters {type=" + standardType.getType() + "}", exec.getMessage());
  }

  @Test
  void getAllHotels_thenReturnListHotels() {
    int n = 3;
    List<Hotel> hotels = IntStream.range(0, n)
            .mapToObj(i -> new Hotel()) // create n Hotel objects
            .collect(Collectors.toList());

    when(hotelRepository.findAll()).thenReturn(hotels);

    Iterable<Hotel> result = service.getAllHotels();

    assertEquals(n, StreamSupport.stream(hotels.spliterator(), false).count());

  }

}
