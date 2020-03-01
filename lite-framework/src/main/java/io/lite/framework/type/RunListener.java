package io.lite.framework.type;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public interface RunListener {

    void starting();


    void environmentPrepared(ConfigurableEnvironment environment);


    void contextPrepared(ConfigurableApplicationContext context);


    void contextLoaded(ConfigurableApplicationContext context);


    void started(ConfigurableApplicationContext context);


    void running(ConfigurableApplicationContext context);


    void failed(ConfigurableApplicationContext context, Throwable exception);
}
