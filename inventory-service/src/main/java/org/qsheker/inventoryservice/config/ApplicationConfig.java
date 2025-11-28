package org.qsheker.inventoryservice.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class ApplicationConfig
{
    @Bean
    public WebClient webClient()
    {
        return WebClient.builder()
                .baseUrl("http://127.0.0.1:9090")
                .build();
    }
}
