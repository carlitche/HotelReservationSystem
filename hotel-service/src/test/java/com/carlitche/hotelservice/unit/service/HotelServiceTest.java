package com.carlitche.hotelservice.unit.service;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.repository.HotelRepository;
import com.carlitche.hotelservice.service.HotelService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@SpringBootTest(classes = HotelService.class)
@ActiveProfiles("test")
public class HotelServiceTest {

    @MockBean
    private HotelRepository hotelRepository;

    @Autowired
    private HotelService service;

    @Test
    void saveHotel_whenCreateHotelAndRooms() {

        Room singleRoom = new Room(101, 1, "single", true);
        Room doubleRoom = new Room(102, 1, "double", true);

        Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");
        hotel.setRooms(List.of(singleRoom, doubleRoom));

        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel result = service.saveHotel(hotel);

        assertEquals(result, hotel);

    }

}
