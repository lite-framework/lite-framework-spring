package cc.lite.framework.config;

import cc.lite.framework.component.TransactionHelper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TransactionConfig {
    @Bean
    public TransactionHelper frameworkTransactionHelper() {
        return new TransactionHelper();
    }

}