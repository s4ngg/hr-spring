package kr.co.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HrSpringApplication {

    public static void main(String[] args) {
        SpringApplication.run(HrSpringApplication.class, args);
    }
}
