package kr.co.hr;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import io.github.cdimascio.dotenv.Dotenv;

@EnableAsync
@EnableConfigurationProperties
@SpringBootApplication
@EnableScheduling 
public class HrSpringApplication {

    private static final Logger logger = LogManager.getLogger(HrSpringApplication.class);

    public static void main(String[] args) {
        Dotenv dotenv = Dotenv.configure().ignoreIfMissing().load();
        
        dotenv.entries().forEach(entry -> {
            System.setProperty(entry.getKey(), entry.getValue());
        });

        logger.info("스프링 프로젝트가 시작중입니다.");
        SpringApplication.run(HrSpringApplication.class, args);
        logger.info("스프링 프로젝트가 성공적으로 시작되었습니다.");
    }
}