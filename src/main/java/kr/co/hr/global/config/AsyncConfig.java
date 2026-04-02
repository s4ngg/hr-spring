package kr.co.hr.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

@Configuration
public class AsyncConfig {

    @Bean(name = "mailExecutor")
    public Executor mailExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);     
        executor.setMaxPoolSize(10);     
        executor.setQueueCapacity(50);  
        executor.setThreadNamePrefix("mail-");
        executor.initialize();
        return executor;
    }
    
    @Bean(name = "dashboardExecutor")
    public Executor dashboardExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(20);
        executor.setMaxPoolSize(50);
        executor.setQueueCapacity(1000);  // 100 → 1000으로 늘리기
        executor.setThreadNamePrefix("dashboard-");
        executor.initialize();
        return executor;
    }
}