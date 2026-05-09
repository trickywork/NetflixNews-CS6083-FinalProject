package netflix.news.system.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("netflix.news.system.mapper")
public class MyBatisConfig {
}
