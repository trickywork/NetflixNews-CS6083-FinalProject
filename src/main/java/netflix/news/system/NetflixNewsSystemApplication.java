package netflix.news.system;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Netflix News System - Main Application
 * A web-based management system for Netflix content database
 */
@SpringBootApplication
@MapperScan("netflix.news.system.mapper")
@EnableTransactionManagement
public class NetflixNewsSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(NetflixNewsSystemApplication.class, args);
    }

}