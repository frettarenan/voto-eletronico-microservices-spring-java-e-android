package br.com.renanfretta.ve.votoeletronico;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableEurekaClient
@EnableScheduling
@SpringBootApplication
public class VeVotoEletronicoApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeVotoEletronicoApplication.class, args);
	}

}
