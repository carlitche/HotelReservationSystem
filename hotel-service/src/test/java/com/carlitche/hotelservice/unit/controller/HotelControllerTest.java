package com.carlitche.hotelservice.unit.controller;

import com.carlitche.hotelservice.config.AppConfig;
import com.carlitche.hotelservice.controller.HotelController;
import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.model.HotelDto;
import com.carlitche.hotelservice.service.HotelService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(HotelController.class)
@Import(AppConfig.class)
@ActiveProfiles("test")
class HotelControllerTest {

  @Autowired
  private MockMvc mvc;

  @Autowired
  private ModelMapper modelMapper;

  @MockBean
  private HotelService hotelService;

  @Test
  void createHotelAndRooms_whenPostHotel_thenReturnCreateHttpCode() throws Exception {

    String text = "\n{\n" + "  \"name\": \"Grand Hotel\",\n" + "  \"address\": \"123 Main St\",\n" +
                  "  \"location\": \"City Center\",\n" + "  \"rooms\": [\n" + "    {\n" + "      \"number\": 101,\n" +
                  "      \"floor\": 1,\n" + "      \"name\": \"Standard Room\",\n" + "      \"available\": true,\n" +
                  "      \"roomType\": {\n" + "        \"type\": \"Standard\"\n" + "      }\n" + "    },\n" +
                  "    {\n" + "      \"number\": 102,\n" + "      \"floor\": 1,\n" +
                  "      \"name\": \"Deluxe Room\",\n" + "      \"available\": true,\n" + "      \"roomType\": {\n" +
                  "        \"type\": \"Deluxe\"\n" + "      }\n" + "    },\n" + "    {\n" + "      \"number\": 201,\n" +
                  "      \"floor\": 2,\n" + "      \"name\": \"Suite\",\n" + "      \"available\": true,\n" +
                  "      \"roomType\": {\n" + "        \"type\": \"Suite\"\n" + "      }\n" + "    }\n" + "  ]\n" + "}";

    mvc.perform(post("/hotels")
                    .accept(MediaType.APPLICATION_JSON)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(text))
            .andDo(print())
            .andExpect(status().isCreated());

    ObjectMapper objectMapper = new ObjectMapper();
    HotelDto hotelDto = objectMapper.readValue(text, HotelDto.class);

    // Capture the argument passed to the saveHotel method
    ArgumentCaptor<Hotel> hotelCaptor = ArgumentCaptor.forClass(Hotel.class);
    verify(hotelService, times(1)).saveHotel(hotelCaptor.capture());
    Hotel capturedHotel = hotelCaptor.getValue();

    assertEquals(hotelDto.getName(), capturedHotel.getName());
    assertEquals(hotelDto.getRooms().size(), capturedHotel.getRooms().size());

  }

  @Test
  void getHotel_whenGetHotelID_thenReturnHotel() throws Exception {
    Long id = 1L;
    Hotel hotel = new Hotel("Grand Hotel", "123 Main St", "City Center");;
    hotel.setHotelId(id);

    when(hotelService.getHotelById(id)).thenReturn(Optional.of(hotel));

    mvc.perform(get("/hotels/{id}", id))
       .andDo(print())
       .andExpect(status().isOk())
       .andExpect(content().contentType(MediaType.APPLICATION_JSON))
       .andExpect(jsonPath("$.name").value(hotel.getName()));
  }

}
