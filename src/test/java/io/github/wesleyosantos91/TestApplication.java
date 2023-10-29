package io.github.wesleyosantos91;

import io.github.wesleyosantos91.config.MySQLContainerBaseTest;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.TestConfiguration;

@TestConfiguration(proxyBeanMethods = false)
public class TestApplication {

    public static void main(String[] args) {
        SpringApplication.from(Application::main)
                .with(MySQLContainerBaseTest.class)
                .run(args);
    }

}
