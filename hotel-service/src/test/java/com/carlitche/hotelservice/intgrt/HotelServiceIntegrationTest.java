package com.carlitche.hotelservice.intgrt;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("intgrtest")
@Sql(scripts = "/insert.sql")
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
    void checkForAllowedOperation(){
        Set<HttpMethod> optionsForAllow = this.testRestTemplate.optionsForAllow("/hotels");
        HttpMethod[] supportedMethods
            = {/*HttpMethod.GET,*/ HttpMethod.POST/*, HttpMethod.PUT, HttpMethod.DELETE*/};
        assertTrue(optionsForAllow.containsAll(Arrays.asList(supportedMethods)));

    }

    @Test
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

        assertEquals(response.getStatusCodeValue(), HttpStatus.CREATED);
    }

    @Test
    void shouldGetHotelById(){

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.add(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE);

        Long id = 1L;
        ResponseEntity<String> response = this.testRestTemplate.getForEntity("/hotels/" + id, String.class);
        assertEquals(response.getStatusCode(), HttpStatus.OK);
    }
}
