package cc.lite.framework.config;

import cc.lite.framework.component.JpaRepositoryAOP;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JpaAopConfig {

    @Bean
    public JpaRepositoryAOP frameworkJpaRepositoryAOP() {
        return new JpaRepositoryAOP();
    }

}