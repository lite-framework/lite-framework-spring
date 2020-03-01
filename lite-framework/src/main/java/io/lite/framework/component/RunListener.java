package io.lite.framework.component;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.SpringApplicationRunListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Slf4j
public class RunListener implements SpringApplicationRunListener {

    public RunListener(SpringApplication application, String[] args) {
        log.info("RunListener.RunListener");
    }


    @Override
    public void starting() {
        log.info("RunListener.starting");
    }

    @Override
    public void environmentPrepared(ConfigurableEnvironment environment) {
        log.info("RunListener.environmentPrepared");
    }

    @Override
    public void contextPrepared(ConfigurableApplicationContext context) {
        log.info("RunListener.contextPrepared");

        //
        List<io.lite.framework.type.RunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.contextPrepared(context);
        });
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("RunListener.contextLoaded");

        //
        List<io.lite.framework.type.RunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.contextLoaded(context);
        });
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("RunListener.started");

        //
        List<io.lite.framework.type.RunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.started(context);
        });
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("RunListener.running");

        //
        List<io.lite.framework.type.RunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.running(context);
        });
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("RunListener.failed");

        //
        List<io.lite.framework.type.RunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.failed(context, exception);
        });
    }


    private List<io.lite.framework.type.RunListener> getListeners(ConfigurableApplicationContext context) {
        List<io.lite.framework.type.RunListener> list = new ArrayList<>();


        if (context.isActive()) {
            Map<String, io.lite.framework.type.RunListener> map = context.getBeansOfType(io.lite.framework.type.RunListener.class);

            // todo cc could sort by rules


            if (map.size() > 0) {
                for (String key : map.keySet()) {
                    io.lite.framework.type.RunListener listener = map.get(key);
                    list.add(listener);
                }
            }
        } else {
            log.info("context_not_active");
        }


        return list;
    }


}
