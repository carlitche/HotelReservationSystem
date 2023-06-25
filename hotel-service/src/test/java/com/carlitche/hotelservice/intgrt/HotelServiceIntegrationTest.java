package com.carlitche.hotelservice.intgrt;

import com.carlitche.hotelservice.config.AppConfig;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.annotation.Import;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("intgrtest")
@Import(AppConfig.class)
class HotelServiceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Autowired
    private ModelMapper modelMapper;

    @Container
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:alpine").withInitScript("data_insert.sql");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

//    @Test
    void accessApplication() {
        System.out.println(port);
        System.out.println(postgreSQLContainer.getJdbcUrl());
        System.out.println(postgreSQLContainer.getContainerName());
        System.out.println(postgreSQLContainer.getTestQueryString());
    }

    @BeforeEach
    void setup(){
        postgreSQLContainer.setWaitStrategy(Wait.forListeningPort());
    }

    @Test
    void shouldCreateHotel(){
        System.out.println(postgreSQLContainer.getContainerName());
        String text = """
                {
                  "name": "Grand Hotel",
                  "address": "123 Main St",
                  "location": "City Center",
                  "rooms": [
                    {
                      "number": 101,
                      "floor": 1,
                      "name": "Standard Room",
                      "available": true,
                      "roomType": {
                        "type": "Standard"
                      }
                    },
                    {
                      "number": 102,
                      "floor": 1,
                      "name": "Suite Room",
                      "available": true,
                      "roomType": {
                        "type": "Suite"
                      }
                    },
                    {
                      "number": 201,
                      "floor": 2,
                      "name": "Deluxe Room",
                      "available": true,
                      "roomType": {
                        "type": "Deluxe"
                      }
                    }
                  ]
                }""";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<String> requestEntity = new HttpEntity<>(text, requestHeaders);

        ResponseEntity<Void> response = this.testRestTemplate.exchange("/hotels", HttpMethod.POST, requestEntity,
                                                                       Void.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
    }

    @Test
    void shouldGetHotelById() throws JsonProcessingException {

        Long id = 1L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + id, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode hotelId = root.path("hotelId");
        assertNotNull(hotelId.asText());
        assertEquals(id, Long.valueOf(hotelId.asText()));
    }


    @Test
    void errorMsg4XXWhenGetHotelIdNotExist() throws JsonProcessingException {

        long id = 999L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + id, String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNotNull(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode errorMsg = root.path("message");
        assertNotNull(errorMsg.asText());
        assertEquals("Hotel was not found for parameters {id=" + 999L + "}", errorMsg.asText());
    }

    @Test
    void errorMsg4XXWhenRoomTypeNotExist() throws JsonProcessingException {

        String text = "{\n" +
                      "  \"name\": \"Grand Hotel\",\n" +
                      "  \"address\": \"123 Main St\",\n" +
                      "  \"location\": \"City Center\",\n" +
                      "  \"rooms\": [\n" +
                      "    {\n" +
                      "      \"number\": 101,\n" +
                      "      \"floor\": 1,\n" +
                      "      \"name\": \"Single Room\",\n" +
                      "      \"available\": true,\n" +
                      "      \"roomType\": {\n" +
                      "        \"type\": \"Single\"\n" +
                      "      }\n" +
                      "    }\n" +
                      "  ]\n" +
                      "}";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<String> requestEntity = new HttpEntity<>(text, requestHeaders);

        ResponseEntity<String> response = this.testRestTemplate.exchange("/hotels", HttpMethod.POST, requestEntity,
                String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode errorMsg = root.path("message");
        assertNotNull(errorMsg.asText());
        assertEquals("RoomType was not found for parameters {type=Single}", errorMsg.asText());
    }

    @Test
    void shouldGetAllHotel() throws JsonProcessingException {

        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels", String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        assertTrue(root.isArray());
    }

    @Test
    void shouldGetHotelRoomById() throws JsonProcessingException {
        Long hotelId = 1L;
        Long roomId = 1L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + hotelId + "/rooms/" + roomId,
                                                                             String.class);

        System.out.println(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode roomId_result = root.path("roomId");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(roomId, roomId_result.longValue());
    }

    @Test
    void errorMsg4xxWhenGetRoomIdNotExist() throws JsonProcessingException {
        Long hotelId = 1L;
        Long roomId = 99L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + hotelId + "/rooms/" + roomId,
                String.class);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        System.out.println(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        JsonNode errorMsg = root.path("message");
        assertNotNull(errorMsg.asText());
        assertEquals("Room was not found for parameters {hotelId=" + hotelId + ", roomId=" + roomId + "}", errorMsg.asText());
    }

    @Test
    void shouldGetAllHotelRooms() throws JsonProcessingException {
        Long hotelId = 1L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + hotelId + "/rooms",
                String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        System.out.println(response.getBody());

        ObjectMapper mapper = new ObjectMapper();
        JsonNode root = mapper.readTree(response.getBody());
        assertTrue(root.isArray());
    }

}
