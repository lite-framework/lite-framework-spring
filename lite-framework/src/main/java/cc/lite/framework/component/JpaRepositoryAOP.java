package cc.lite.framework.component;

import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Tags;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;


@Slf4j
@Aspect
public class JpaRepositoryAOP {

    @Autowired
    private MeterRegistry meterRegistry;

    @Value("${spring.application.name}")
    private String appName;

    @Pointcut("execution(* *..*.*Repository.*(..))")
    public void queryAspect() {
    }

    @Before("queryAspect()")
    public void doBefore(JoinPoint joinPoint) {
    }

    @After("queryAspect()")
    public void doAfter(JoinPoint joinPoint) {
    }

    @AfterReturning("queryAspect()")
    public void doAfterReturning(JoinPoint joinPoint) {
    }

    @AfterThrowing("queryAspect()")
    public void deAfterThrowing(JoinPoint joinPoint) {
    }


    private Map<String, AtomicLong> gaugeDataHolder = new HashMap();

    private AtomicLong getGaugeData(String key) {

        AtomicLong data = gaugeDataHolder.get(key);
        if (data == null) {
            data = setGaugeData(key);
        }
        return data;
    }

    private synchronized AtomicLong setGaugeData(String key) {
        AtomicLong data = gaugeDataHolder.get(key);
        if (data != null)
            return data;

        data = new AtomicLong(0L);
        gaugeDataHolder.put(key, data);

        return data;
    }

    @Around("queryAspect()")
    public Object doAround(ProceedingJoinPoint joinPoint) throws Throwable {


        long start = System.currentTimeMillis();
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw throwable;
        } finally {
            long elapse = System.currentTimeMillis() - start;
            String method = joinPoint.getSignature().getName();

            Object className = joinPoint.getTarget().getClass().getSimpleName();

            String repository = className.toString();

            String gaugeKey = repository + "_" + method;

            AtomicLong atomicLong = getGaugeData(gaugeKey);
            atomicLong.set(elapse);

            meterRegistry.gauge(appName, Tags.of("repository", repository, "query", method), atomicLong);

        }


    }

}
