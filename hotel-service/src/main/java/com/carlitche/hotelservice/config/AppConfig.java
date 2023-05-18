package com.carlitche.hotelservice.config;

import com.carlitche.hotelservice.entity.Hotel;
import com.carlitche.hotelservice.model.HotelDto;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

@Configuration
public class AppConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Hotel.class, HotelDto.class)
                .addMappings(mapper -> mapper.skip(HotelDto::setRooms));
        return modelMapper;
    }
}
