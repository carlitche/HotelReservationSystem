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
import java.util.stream.StreamSupport;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.FactoryBasedNavigableListAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/insert.sql")
@Sql(scripts = "/insert_hotel.sql")
class HotelRepositoryTest {

  @Autowired
  private HotelRepository hotelRepository;

  @Autowired
  private RoomTypeRepository roomTypeRepository;


  @Test
  void saveHotel_whenNewHotel_thenReturnHotel() {

    Optional<RoomType> singleType = roomTypeRepository.findByType("Single");
    Optional<RoomType> doubleType = roomTypeRepository.findByType("Double");

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
  void getHotel_whenFindByHotelId_thenReturnHotel(){
    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
    Hotel newHotel = hotelRepository.save(hotel);

    long id = newHotel.getHotelId();
    Optional<Hotel> result = hotelRepository.findById(id);
    assertFalse(result.isEmpty());
    assertEquals(result.get().getName(), hotel.getName());
    assertEquals(result.get().getHotelId(), id);

  }

  @Test
  void getAllHotel_whenfindAll_thenReturnListHotels(){
    Optional<RoomType> singleType = roomTypeRepository.findByType("Single");
    Optional<RoomType> doubleType = roomTypeRepository.findByType("Double");

    assertAll(() -> assertFalse(singleType.isEmpty()), () -> assertFalse(doubleType.isEmpty()));

    Hotel hotel1 = new Hotel("Grand Hotel", "123 Main St", "City Center");
    Room singleRoom = new Room(201, 2, "single", true);
    singleRoom.setRoomType(singleType.get());
    hotel1.setRooms(List.of(singleRoom));
    Hotel newHotel1 = hotelRepository.save(hotel1);

    Hotel hotel2 = new Hotel("Hotel Two", "Coimbra", "Portugal");
    Room doubleRoom = new Room(301, 3, "double", true);
    doubleRoom.setRoomType(doubleType.get());
    hotel1.setRooms(List.of(doubleRoom));
    Hotel newHotel2 = hotelRepository.save(hotel2);

    Iterable<Hotel> hotels = hotelRepository.findAll();

    assertEquals(3, StreamSupport.stream(hotels.spliterator(), false).count());

  }

}
