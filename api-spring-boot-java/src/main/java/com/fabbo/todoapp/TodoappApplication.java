package com.fabbo.todoapp;

import com.fabbo.todoapp.common.config.TimeZoneConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.TimeZone;

@SpringBootApplication
public class TodoappApplication {
    public static final String DEV_PROFILE = "dev";
    public static final String TEST_PROFILE = "test";
    public static final String WEB_TEST_PROFILE = "web-test";
    public static final String CONTAINER_TEST_PROFILE = "cont-test";
    public static final String NON_TEST_PROFILE = "!test";
    public static final String NON_WEB_TEST_PROFILE = "!web-test";
    public static final String NON_CONTAINER_TEST_PROFILE = "!cont-test";


    public static void main(final String[] args) {
        TimeZone.setDefault(TimeZone.getTimeZone(TimeZoneConfig.DEFAULT_TIME_ZONE));

        SpringApplication.run(TodoappApplication.class, args);
    }
}
