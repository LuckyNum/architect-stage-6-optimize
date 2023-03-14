package org.example.objectpool;

import org.example.objectpool.datasource.MyDataSource;
import org.example.objectpool.datasource.MyDataSourceEndpoint;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;

/**
 * @author lch
 * @date 2023年03月14日 23:18
 */
@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }

    @Bean
    @Primary
    public MyDataSource dataSource() {
        return new MyDataSource();
    }

    @Bean
    public MyDataSourceEndpoint dataSourceEndpoint() {
        return new MyDataSourceEndpoint(this.dataSource());
    }
}
