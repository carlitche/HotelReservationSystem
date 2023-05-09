package com.carlitche.hotelservice.intgrt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("intgrtest")
class HotelServiceIntegrationTest {

    @Autowired
    private TestRestTemplate testRestTemplate;

    @LocalServerPort
    private Integer port;

    @Container
    public static PostgreSQLContainer postgreSQLContainer = new PostgreSQLContainer("postgres:latest");

    @DynamicPropertySource
    static void postgresqlProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgreSQLContainer::getJdbcUrl);
        registry.add("spring.datasource.password", postgreSQLContainer::getPassword);
        registry.add("spring.datasource.username", postgreSQLContainer::getUsername);
    }

    @Test
    void accessApplication() {
        System.out.println(port);
        System.out.println(postgreSQLContainer.getJdbcUrl());
        System.out.println(postgreSQLContainer.getTestQueryString());
    }

    @Test
    @Sql(scripts = "/insert.sql")
    void shouldCreateHotel(){

        String text = "{\n" + "  \"name\": \"Grand Hotel\",\n" + "  \"address\": \"123 Main St\",\n" +
                      "  \"location\": \"City Center\",\n" + "  \"rooms\": [\n" + "    {\n" +
                      "      \"number\": 101,\n" + "      \"floor\": 1,\n" + "      \"name\": \"Single Room\",\n" +
                      "      \"available\": true,\n" + "      \"roomType\": {\n" + "        \"type\": \"Single\"\n" +
                      "      }\n" + "    },\n" + "    {\n" + "      \"number\": 102,\n" + "      \"floor\": 1,\n" +
                      "      \"name\": \"Single Room\",\n" + "      \"available\": true,\n" +
                      "      \"roomType\": {\n" + "        \"type\": \"Single\"\n" + "      }\n" + "    },\n" +
                      "    {\n" + "      \"number\": 201,\n" + "      \"floor\": 2,\n" +
                      "      \"name\": \"Double Room\",\n" + "      \"available\": true,\n" +
                      "      \"roomType\": {\n" + "        \"type\": \"Double\"\n" + "      }\n" + "    }\n" + "  ]\n" +
                      "}";

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        HttpEntity<String> requestEntity = new HttpEntity<>(text, requestHeaders);

        ResponseEntity<Void> response = this.testRestTemplate.exchange("/hotels", HttpMethod.POST, requestEntity,
                                                                       Void.class);

        assertEquals(response.getStatusCodeValue(), 201);
    }
}
