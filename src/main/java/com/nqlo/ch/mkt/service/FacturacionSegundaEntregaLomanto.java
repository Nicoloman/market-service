package com.nqlo.ch.mkt.service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;


@SpringBootApplication
public class FacturacionSegundaEntregaLomanto  {



    public static void main(String[] args) {
        SpringApplication.run(FacturacionSegundaEntregaLomanto.class, args);
    }

    @Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}


}
