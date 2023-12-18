package com.memory.Mediation;

import com.memory.Mediation.Services.CDRService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@EnableAsync(proxyTargetClass=true)
public class MediationApplication {

	public static void main(String[] args) {




		SpringApplication.run(MediationApplication.class, args);




	}

}
