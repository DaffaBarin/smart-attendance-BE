package com.dipl.smartattendance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class SmartAttendanceApplication {

	public static void main(String[] args) {
		System.out.println("Asiap");
		SpringApplication.run(SmartAttendanceApplication.class, args);
	}

}
