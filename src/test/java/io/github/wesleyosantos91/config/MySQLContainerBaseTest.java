package io.github.wesleyosantos91.config;

import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

@Testcontainers
public class MySQLContainerBaseTest {

    @Bean
    @ServiceConnection
    public MySQLContainer<?> mysqlContainer() {
        return new MySQLContainer<>(DockerImageName.parse("mysql:latest"));
    }

    @DynamicPropertySource
    public void mySQLProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.url", () -> mysqlContainer().getJdbcUrl());
        registry.add("spring.flyway.user", () -> mysqlContainer().getUsername());
        registry.add("spring.flyway.password", () -> mysqlContainer().getPassword());
    }
}
