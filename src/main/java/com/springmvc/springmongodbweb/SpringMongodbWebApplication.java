package com.springmvc.springmongodbweb;

import org.modelmapper.AbstractConverter;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.config.EnableMongoAuditing;
import org.springframework.util.StringUtils;

@SpringBootApplication
@EnableMongoAuditing
public class SpringMongodbWebApplication {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.addConverter(new AbstractConverter<String, String>() {
            protected String convert(String source) {
                return StringUtils.isEmpty(source) ? null : source;
            }
        });

        return modelMapper;
    }

    public static void main(String[] args) {
        SpringApplication.run(SpringMongodbWebApplication.class, args);
    }
}
