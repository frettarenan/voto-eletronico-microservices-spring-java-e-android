package br.com.renanfretta.ve.eurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer
@SpringBootApplication
public class VeEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(VeEurekaServerApplication.class, args);
	}

}
