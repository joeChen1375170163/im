package com.cxh;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.cxh.base.dao.BaseDaoImpl;

@SpringBootApplication
@EnableRabbit // 开启RabbitMQ支持
@EnableScheduling
@EnableJpaRepositories(repositoryBaseClass = BaseDaoImpl.class)
public class Application
{
    public static void main(String[] args)
    {
        SpringApplication.run(Application.class, args);
    }
}
