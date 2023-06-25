package com.carlitche.hotelservice.unit.service;

import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.exception.NoSuchElementFoundException;
import com.carlitche.hotelservice.repository.RoomRepository;
import com.carlitche.hotelservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = RoomService.class)
@ActiveProfiles("test")
class RoomServiceTest {

    @MockBean
    private RoomRepository roomRepository;

    @Autowired
    private RoomService service;

    @Test
    void getRoomById_thenReturnRoom(){

        Room room = new Room(101, 1, "single", true);
        Long hotelId = 1L;
        Long roomId = 1L;
        when(roomRepository.findByHotel_HotelIdAndRoomId(hotelId, roomId)).thenReturn(Optional.of(room));

        Room result = service.getHotelRoomById(hotelId, roomId);

        assertEquals(result, room);

    }

    @Test
    void getNoSuchElementFoundException_whenNoRoomFound(){

        Long hotelId = 999L;
        Long roomId = 1L;
        when(roomRepository.findByHotel_HotelIdAndRoomId(hotelId, roomId)).thenReturn(Optional.empty());

        Exception exec = assertThrows(NoSuchElementFoundException.class, () -> service.getHotelRoomById(hotelId, roomId));
        assertEquals("Room was not found for parameters {hotelId=" + hotelId + ", roomId=" + roomId + "}", exec.getMessage());

    }

    @Test
    void getHotelRooms_thenReturnListRooms(){

        Room singleRoom = new Room(101, 1, "single", true);
        Room doubleRoom = new Room(201, 2, "double", true);

        Long hotelId = 1L;

        when(roomRepository.findByHotel_HotelId(hotelId)).thenReturn(List.of(singleRoom, doubleRoom));

        List<Room> roomList = service.getAllHotelRooms(hotelId);

        assertFalse(roomList.isEmpty());
        assertTrue(roomList.contains(singleRoom));
        assertTrue(roomList.contains(doubleRoom));  
    }

}
