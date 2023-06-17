package cn.bdqn.urltest;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("cn.bdqn.urltest.mapper")
@ComponentScan("cn.bdqn")
public class UrlTestApplication {

    public static void main(String[] args) {
        SpringApplication.run(UrlTestApplication.class, args);
    }

}
