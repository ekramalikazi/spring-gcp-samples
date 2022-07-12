package com.samples.spanner;

import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.containers.wait.strategy.Wait;

import java.time.Duration;

public class AbstractIntegrationTest {

    private static GenericContainer<?> emulator;

    static {
        System.setProperty("SPANNER_EMULATOR_HOST", "spanner:9010");
        System.setProperty("TESTCONTAINERS_HOST_OVERRIDE", "172.17.0.1");

        String SPANNER_EMULATOR_IMAGE = "gcr.io/cloud-spanner-emulator/emulator:latest";
        emulator = new GenericContainer<>(SPANNER_EMULATOR_IMAGE).withCommand()
                .withExposedPorts(9010, 9020).withStartupTimeout(Duration.ofSeconds(10))
                .withEnv("SPANNER_EMULATOR_HOST", "spanner:9010")
                .waitingFor(Wait.forLogMessage(".*gRPC server listening at 0.0.0.0:9010*\\n", 1));
        emulator.start();
    }

    @DynamicPropertySource
    static void overrideTestProperties(DynamicPropertyRegistry registry) {
        //registry.add("spring.jpa.show-sql", () -> true);
        registry.add("spring.datasource.url", () ->
                String.format(
                        // autoConfigEmulator=true ensures that the connection will use plain text, and it will
                        // automatically create the instance and database that is named in the connection string (if
                        // they do not already exist).
                        "jdbc:cloudspanner://%s:%d/projects/test-project/instances/test-instance/databases/testdb;autoConfigEmulator=true",
                        emulator.getHost(),
                        emulator.getMappedPort(9010)));
    }
}
