package io.lite.framework.config;

import io.lite.framework.component.JpaRepositoryAOP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaAopConfig {

    @Bean
    public JpaRepositoryAOP frameworkJpaRepositoryAOP() {
        return new JpaRepositoryAOP();
    }

}