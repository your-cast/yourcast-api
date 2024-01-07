package com.yourcast.api;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.LogMessageWaitStrategy;
import org.testcontainers.images.builder.ImageFromDockerfile;
import org.testcontainers.utility.DockerImageName;

import java.nio.file.Path;

public class SpringRegistrableEnvironment extends GenericContainer<SpringRegistrableEnvironment> {

  private static final String DATABASE_NAME = "yourcast";
  private static final String WAIT_LOG_MSG_REGEX = ".*система бази даних готова до отримання підключення.*\\s";

  private static final ImageFromDockerfile POSTGRES_DOCKERFILE = new ImageFromDockerfile()
      .withDockerfile(Path.of("./src/test/resources/testcontainers/images/postgresql-uk-UA-utf8"));

  private static final DockerImageName POSTGRES_IMAGE = DockerImageName.parse(POSTGRES_DOCKERFILE.get())
      .asCompatibleSubstituteFor(PostgreSQLContainer.IMAGE);

  private static final PostgreSQLContainer<?> POSTGRESQL_CONTAINER = new PostgreSQLContainer<>(POSTGRES_IMAGE)
      .withDatabaseName(DATABASE_NAME)
      .withReuse(true)
      .waitingFor(new LogMessageWaitStrategy().withRegEx(WAIT_LOG_MSG_REGEX));

  @Override
  public void start() {
    POSTGRESQL_CONTAINER.start();
  }

  @Override
  public void stop() {
    POSTGRESQL_CONTAINER.stop();
  }

  public void registerDynamicProperties(DynamicPropertyRegistry registry) {
    registry.add("spring.datasource.username", POSTGRESQL_CONTAINER::getUsername);
    registry.add("spring.datasource.password", POSTGRESQL_CONTAINER::getPassword);
    registry.add("spring.datasource.url", POSTGRESQL_CONTAINER::getJdbcUrl);
  }
}