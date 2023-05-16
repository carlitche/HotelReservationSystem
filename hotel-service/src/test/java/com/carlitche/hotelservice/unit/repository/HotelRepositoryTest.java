package com.carlitche.hotelservice.unit.repository;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.repository.HotelRepository;
import com.carlitche.hotelservice.repository.RoomRepository;
import com.carlitche.hotelservice.repository.RoomTypeRepository;
import jakarta.persistence.PersistenceException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/insert.sql")
@Sql(scripts = "/insert_hotel.sql")
class HotelRepositoryTest {

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private RoomRepository roomRepository;

  @Autowired
  private RoomTypeRepository roomTypeRepository;


  @Test
  void saveHotel_whenNewHotel_thenReturnHotel() {

    Optional<RoomType> singleType = roomTypeRepository.findByType("Single");
    Optional<RoomType> doubleType = roomTypeRepository.findByType("Single");

    assertAll(() -> assertFalse(singleType.isEmpty()), () -> assertFalse(doubleType.isEmpty()));

    Room singleRoom = new Room(101, 1, "single", true);
    Room doubleRoom = new Room(102, 1, "double", true);

    singleRoom.setRoomType(singleType.get());
    doubleRoom.setRoomType(doubleType.get());

    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
    hotel.setRooms(List.of(singleRoom, doubleRoom));

    Hotel newHotel = hotelRepository.save(hotel);

    assertFalse(hotelRepository.findById(newHotel.getHotelId())
                               .isEmpty());


    Hotel result = hotelRepository.findById(newHotel.getHotelId())
                                  .get();
    assertEquals(result.getName(), hotel.getName());
    assertEquals(result.getRooms()
                       .size(), hotel.getRooms()
                                     .size());

  }

  @Test
  void getHotel_whenGetHotelById_thenReturnHotel(){
    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
    Hotel newHotel = hotelRepository.save(hotel);

    long id = 2L;
    Hotel result = hotelRepository.findById(id)
                                  .get();
    assertEquals(result.getName(), hotel.getName());
    assertEquals(result.getHotelId(), id);

  }

}
