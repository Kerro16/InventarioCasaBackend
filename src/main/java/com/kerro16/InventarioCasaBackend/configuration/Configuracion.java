package com.kerro16.InventarioCasaBackend.configuration;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Configuracion {

    @Bean
    public ModelMapper modelmapper(){
        return new ModelMapper();
    }

} 