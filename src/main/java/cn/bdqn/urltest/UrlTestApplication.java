package cn.bdqn.urltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
@MapperScan("cn.bdqn.urltest.mapper")
@ComponentScan("cn.bdqn")
public class UrlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlTestApplication.class, args);
    }

}
