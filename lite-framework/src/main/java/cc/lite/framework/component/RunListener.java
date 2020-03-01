package cc.lite.framework.component;

import cc.lite.framework.type.CommonRunListener;
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
        List<CommonRunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.contextPrepared(context);
        });
    }

    @Override
    public void contextLoaded(ConfigurableApplicationContext context) {
        log.info("RunListener.contextLoaded");

        //
        List<CommonRunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.contextLoaded(context);
        });
    }

    @Override
    public void started(ConfigurableApplicationContext context) {
        log.info("RunListener.started");

        //
        List<CommonRunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.started(context);
        });
    }

    @Override
    public void running(ConfigurableApplicationContext context) {
        log.info("RunListener.running");

        //
        List<CommonRunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.running(context);
        });
    }

    @Override
    public void failed(ConfigurableApplicationContext context, Throwable exception) {
        log.info("RunListener.failed");

        //
        List<CommonRunListener> list = getListeners(context);
        list.forEach((listener) -> {
            listener.failed(context, exception);
        });
    }


    private List<CommonRunListener> getListeners(ConfigurableApplicationContext context) {
        List<CommonRunListener> list = new ArrayList<>();


        if (context.isActive()) {
            Map<String, CommonRunListener> map = context.getBeansOfType(CommonRunListener.class);

            // todo cc could sort by rules


            if (map.size() > 0) {
                for (String key : map.keySet()) {
                    CommonRunListener listener = map.get(key);
                    list.add(listener);
                }
            }
        } else {
            log.info("context_not_active");
        }


        return list;
    }


}
