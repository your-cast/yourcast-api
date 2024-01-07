package com.yourcast.api;

import io.restassured.RestAssured;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.init.ScriptUtils;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.junit.jupiter.Testcontainers;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import java.sql.Connection;
import java.sql.SQLException;
import javax.sql.DataSource;

@Testcontainers
@SpringBootTest(webEnvironment = RANDOM_PORT)
public class AbstractIntegrationTest {

    protected final static String BASE_URI = "http://localhost";

    private static final SpringRegistrableEnvironment environment;

    static {
        environment = new SpringRegistrableEnvironment();
        environment.start();
    }

    @DynamicPropertySource
    public static void registerDynamicProperties(DynamicPropertyRegistry registry) {
        environment.registerDynamicProperties(registry);
    }

    @LocalServerPort
    private int port;

    @BeforeAll
    static void clearDb(@Autowired DataSource dataSource) throws SQLException {
        try (Connection connection = dataSource.getConnection()) {
            ScriptUtils.executeSqlScript(connection,
                    new ClassPathResource("/testcontainers/sql-scripts/truncate_all_tables.sql"));
        }
    }

    @BeforeEach
    void beforeEach() {
        RestAssured.baseURI = BASE_URI;
        RestAssured.port = port;
    }
}