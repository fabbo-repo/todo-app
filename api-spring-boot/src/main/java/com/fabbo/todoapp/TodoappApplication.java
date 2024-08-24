package com.fabbo.todoapp;

import com.fabbo.todoapp.common.config.TimeZoneConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TodoappApplication {
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String INTEGRATION_TEST_PROFILE = "int-test";
    public static final String NON_TEST_PROFILE = "!test";

    public static void main(final String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneConfig.DEFAULT_TIME_ZONE));

        SpringApplication.run(TodoappApplication.class, args);
    }
}
