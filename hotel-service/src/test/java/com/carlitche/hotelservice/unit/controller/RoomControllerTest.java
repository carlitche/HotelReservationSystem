package com.carlitche.hotelservice.unit.controller;

import com.carlitche.hotelservice.config.AppConfig;
import com.carlitche.hotelservice.controller.RoomController;
import com.carlitche.hotelservice.entity.Room;
import com.carlitche.hotelservice.entity.RoomType;
import com.carlitche.hotelservice.exception.NoSuchElementFoundException;
import com.carlitche.hotelservice.service.RoomService;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(RoomController.class)
@Import(AppConfig.class)
@ActiveProfiles("test")
class RoomControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ModelMapper modelMapper;

    @MockBean
    private RoomService roomService;

    @Test
    void getHotelRoomById_whenGetHotelRoomId_thenReturnRoom() throws Exception {

        RoomType singleType = new RoomType("Single", "Single Room");
        Room singleRoom = new Room(101, 1, "single", true);
        singleRoom.setRoomType(singleType);

        long roomId = 1;
        long hotelId = 1;
        when(roomService.getHotelRoomById(hotelId, roomId)).thenReturn(singleRoom);

        mvc.perform(get("/hotels/{idHotel}/rooms/{id}", hotelId, roomId))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.number").value(singleRoom.getNumber()));
    }

    @Test
    void notFound4xx_whenGetHotelRoomId_thenReturnNotFoundError() throws Exception {

        Long roomId = 1L;
        Long hotelId = 1L;

        when(roomService.getHotelRoomById(hotelId, roomId)).thenThrow(new NoSuchElementFoundException(Room.class,
                                                                                                      "roomId", roomId.toString(),
                                                                                                      "hotelId", hotelId.toString()));

        mvc.perform(get("/hotels/{idHotel}/rooms/{id}", hotelId, roomId))
           .andDo(print())
           .andExpect(status().isNotFound())
           .andExpect(content().contentType(MediaType.APPLICATION_JSON))
           .andExpect(jsonPath("$.message").value(
               "Room was not found for parameters {hotelId=" + hotelId + ", roomId=" + roomId + "}"));

    }

    @Test
    void getAllHotelRooms_whenGetHotelRooms_thenReturnListRooms() throws Exception {

        RoomType singleType = new RoomType("Single", "Single Room");
        Room singleRoom = new Room(101, 1, "single", true);
        singleRoom.setRoomType(singleType);

        RoomType doubleType = new RoomType("Double", "Double Room");
        Room doubleRoom = new Room(201, 2, "double", true);
        doubleRoom.setRoomType(doubleType);

        long hotelId = 1;
        when(roomService.getAllHotelRooms(hotelId)).thenReturn(List.of(singleRoom, doubleRoom));

        mvc.perform(get("/hotels/{idHotel}/rooms", hotelId))
           .andDo(print())
           .andExpect(status().isOk())
           .andExpect(jsonPath("$.*").isArray())
           .andExpect(jsonPath("$", hasSize(2)));
    }
}
