package com.carlitche.hotelservice.unit.repository;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.repository.HotelRepository;
import com.carlitche.hotelservice.repository.RoomRepository;
import com.carlitche.hotelservice.repository.RoomTypeRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
@Sql(scripts = "/insert.sql")
class RoomRepositoryTest {

    @Autowired
    private RoomTypeRepository roomTypeRepository;

    @Autowired
    private HotelRepository hotelRepository;
    @Autowired
    private RoomRepository roomRepository;

    @Test
    void getRoom_whenFindByRoomId_thenReturnRoom(){
        Optional<RoomType> singleType = roomTypeRepository.findByType("Single");

        assertFalse(singleType.isEmpty());

        Room singleRoom = new Room(101, 1, "single", true);
        singleRoom.setRoomType(singleType.get());

        Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
        hotel.setRooms(List.of(singleRoom));


        Hotel hotelResult = hotelRepository.save(hotel);

        Optional<Room> roomResult = roomRepository.findByHotel_HotelIdAndRoomId(hotelResult.getHotelId(),
                                                                                hotelResult.getRooms()
                                                                                           .get(0)
                                                                                           .getRoomId());

        assertFalse(roomResult.isEmpty());

        assertAll(() -> assertEquals(singleRoom.getName(), roomResult.get().getName()),
                () -> assertEquals(singleRoom.getFloor(), roomResult.get().getFloor()),
                () -> assertEquals(singleRoom.getNumber(), roomResult.get().getNumber()),
                () -> assertEquals(singleRoom.getAvailable(), roomResult.get().getAvailable()));

    }

    @Test
    void getAllRooms_whenFindRoomByHotel_thenReturnListRooms(){
        Optional<RoomType> singleType = roomTypeRepository.findByType("Single");
        Optional<RoomType> doubleType = roomTypeRepository.findByType("Double");

        assertAll(() -> assertFalse(singleType.isEmpty()),
                  () -> assertFalse(doubleType.isEmpty()));

        Room singleRoom = new Room(101, 1, "single", true);
        singleRoom.setRoomType(singleType.get());
        Room doubleRoom = new Room(101, 1, "double", true);
        doubleRoom.setRoomType(doubleType.get());

        Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
        hotel.setRooms(List.of(singleRoom, doubleRoom));


        Hotel hotelResult = hotelRepository.save(hotel);

        List<Room> roomList = roomRepository.findByHotel_HotelId(hotelResult.getHotelId());

        assertFalse(roomList.isEmpty());
        assertTrue(roomList.contains(singleRoom));
        assertTrue(roomList.contains(doubleRoom));
    }

}
