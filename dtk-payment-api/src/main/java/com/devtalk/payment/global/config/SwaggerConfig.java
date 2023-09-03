package com.devtalk.payment.global.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(){
        Info info = new Info()
                .version("v1.0.0")
                .title("데브톡 - 결제 서비스 API DOCS")
                .description("데브톡 결제 서비스의 API를 명세합니다");

        return new OpenAPI()
                .info(info);
    }
}
