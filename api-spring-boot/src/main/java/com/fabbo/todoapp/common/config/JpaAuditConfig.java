package com.fabbo.todoapp.common.config;

import com.fabbo.todoapp.TodoappApplication;
import com.fabbo.todoapp.common.audit.AuditorAwareImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@Configuration
@Profile(
        {
                TodoappApplication.NON_TEST_PROFILE,
                TodoappApplication.NON_WEB_TEST_PROFILE
        }
)
@EnableJpaAuditing(auditorAwareRef = JpaAuditConfig.AUDITOR_BEAN_NAME)
public class JpaAuditConfig {
    public static final String AUDITOR_BEAN_NAME = "auditorBean";

    @Bean(name = AUDITOR_BEAN_NAME)
    @Profile(
            {
                    TodoappApplication.NON_TEST_PROFILE,
                    TodoappApplication.NON_CONTAINER_TEST_PROFILE,
            }
    )
    public AuditorAware<String> auditorAware() {
        return new AuditorAwareImpl();
    }
}