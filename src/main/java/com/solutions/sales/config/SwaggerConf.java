package com.solutions.sales.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;


@Configuration
public class SwaggerConf {
    
    @Bean
    public OpenAPI customOpenAPI() {
          return new OpenAPI()
              .info(new Info()
                  .title("Sales API")
                  .version("1.0")
                  .description("Sales Tax calculator"));
    }

}
