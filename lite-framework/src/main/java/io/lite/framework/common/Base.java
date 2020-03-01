package io.lite.framework.common;

import brave.Span;
import brave.Tracer;
import brave.propagation.TraceContext;
import io.lite.framework.Def;
import io.lite.framework.component.TransactionHelper;
import io.lite.framework.type.Callback;
import io.lite.framework.type.DebugControl;
import io.lite.framework.type.IResponseCode;
import io.lite.framework.helper.*;
import io.micrometer.core.instrument.MeterRegistry;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.springframework.amqp.rabbit.core.RabbitMessageOperations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisOperations;

import java.io.*;
import java.util.*;


public abstract class Base implements DebugControl {


    @Value("${spring.application.name}")
    protected String appName;

    @Value("${logging.debug.on:false}")
    protected Boolean debug;

    public void setDebug(Boolean debug) {
        this.debug = debug;
    }

    @Autowired
    protected MeterRegistry meterRegistry;


    protected static final Long NOID = Def.NOID;


    // should be an interface
    @Autowired
    protected Tracer tracer;

    // ------------------------------- message ---------------------------
    @Autowired
    protected RabbitMessageOperations rabbitMessageOperations;

    protected void sendMessage(String dest, Object payload) {
        rabbitMessageOperations.convertAndSend(dest, payload);
    }


    // ------------------------------- logger ---------------------------
    protected abstract org.slf4j.Logger getLogger();

    protected void info(String s) {
        getLogger().info(s);
    }

    protected void debug(String s) {
        if (debug)
            info("[debug]" + s);
    }

    protected boolean autoLogResponse() {
        return true;
    }

    protected boolean autoTraceResponse() {
        return false;
    }


    // ------------------------------- template ---------------------------
    protected Response exec(Callback callback) {

        tracer.newTrace();

        Response response = new Response();


        try {

            if (false)
                err(ResponseCode.SYSTEM_ERROR);

            response.setData(callback.exec());
        } catch (BizError bizError) {
            response.setCode(bizError.getResponseCode().getCode());
            response.setMessage(bizError.getResponseCode().getMessage());
            info("biz_err [" + bizError.toString() + "]");
        } catch (Throwable t) {

            String message = t.getMessage();

            Throwable causeBy = t.getCause();

            if (causeBy instanceof io.lettuce.core.RedisException) {
                response.setCode(ResponseCode.REDIS_ERROR.getCode());
            } else if (causeBy instanceof org.hibernate.HibernateException) {

                if (StringUtils.contains(message, Def.IDEM_MARK)) {
                    response.setCode(ResponseCode.DUP_REQUEST.getCode());
                } else {
                    response.setCode(ResponseCode.JPA_ERROR.getCode());
                }
            } else if (causeBy instanceof org.springframework.amqp.AmqpException) {
                response.setCode(ResponseCode.RABBIT_MQ_ERROR.getCode());
            } else {
                response.setCode(ResponseCode.SYSTEM_ERROR.getCode());
            }

            getLogger().error("sys_err", t);
        }

        if (autoLogResponse())
            info(response.toString());

        counterIncrement(appName, "responseCode", response.getCode());

        // todo 添加对外方法级别的elapse 到influx

//        response.setTraceId(MDC.get("X-B3-TraceId"));

        if (tracer != null) {
            Span span = tracer.currentSpan();
            if (span != null) {
                TraceContext traceContext = span.context();
                if (traceContext != null) {
                    response.setTraceId(traceContext.traceIdString());
                }
            }
        }

        return response;
    }


    protected void newTrace() {
        tracer.newTrace();
    }

    // ------------------------------- metric ---------------------------
    protected void counterIncrement(String name, String... tags) {
        if (autoTraceResponse())
            noErrors(() -> {
                meterRegistry.counter(name, tags).increment();
                return null;
            });
    }

    // ------------------------------- error ---------------------------
    protected void err(IResponseCode responseCode) {
        ErrorHelper.err(responseCode);
    }

    protected void errIf(boolean cond, IResponseCode responseCode) {
        ErrorHelper.errIf(cond, responseCode);
    }


    protected void errIf(boolean cond, String message) {
        ErrorHelper.errIf(cond, message);
    }


    protected void paramErr(String message) {
        ErrorHelper.paramErr(message);
    }

    protected void paramErrIf(boolean cond, String message) {
        ErrorHelper.paramErrIf(cond, message);
    }


    protected Map map(Object key, Object value) {
        return CollectionHelper.map(key, value);
    }

    protected Map map(Object... ojbs) {
        return CollectionHelper.map(ojbs);
    }

    protected List list() {
        return CollectionHelper.list();
    }


    protected List list(Object... ojbs) {
        return CollectionHelper.list(ojbs);
    }

    // ------------------------------- transaction ---------------------------

    @Autowired
    private TransactionHelper transactionHelper;

    protected Object withTransaction(Callback callback) {
        return transactionHelper.requireTransaction(callback);
    }

    protected Object withNewTransaction(Callback callback) {
        return transactionHelper.newTransaction(callback);
    }


    // ------------------------------- serialize ---------------------------
    public byte[] serialize(Object o) {
        return SerializeHelper.serialize(o);
    }

    public <T extends Serializable> T deserialize(byte[] bytes) {
        return SerializeHelper.deserialize(bytes);
    }


    // ------------------------------- redis ---------------------------
    @Autowired
    @Qualifier(Def.CUST_REDIS_OPS_BEAN_NAME)
    protected RedisOperations redisOperations;


    private static final Long SUCCESS = 1L;

    /**
     * 获取锁
     *
     * @param lockKey
     * @param expireTime：单位-秒
     * @return
     */
    protected boolean getLock(String lockKey, int expireTime) {
        return RedisHelper.getLock(redisOperations, lockKey, expireTime);
    }


    protected boolean withDistributedLock(String lockKey, Callback callback, int expireTime) {
        return RedisHelper.withDistributedLock(redisOperations, lockKey, callback, expireTime);
    }

    protected Boolean withDistributedConcurrency(RedisOperations redisOperations, String key, Callback callback, int concurrency) {
        return RedisHelper.withDistributedConcurrency(redisOperations, key, callback, concurrency);
    }

    protected Boolean withDistributedConcurrency(RedisOperations redisOperations, String key, Callback callback, int concurrency, int expireTimeSecond) {
        return RedisHelper.withDistributedConcurrency(redisOperations, key, callback, concurrency, expireTimeSecond);
    }

    // ------------------------------- id ---------------------------

    protected long nextId() {
        return nextId(appName);
    }

    protected long nextId(String type) {
        return RedisHelper.nextId(redisOperations, type);
    }

    // ------------------------------- sugars ---------------------------

    protected long now() {
        return System.currentTimeMillis();
    }

    protected Date nowDate() {
        return new Date();
    }

    protected Integer nowDateInteger() {
        return dateInteger(nowDate());
    }

    protected Integer dateInteger(Date date) {
        return Integer.valueOf(DateFormatUtils.format(date, "YYYYMMdd"));
    }


    protected void parkSeconds(long second) {
        ParkHelper.parkSeconds(second);
    }

    protected boolean noErrors(Callback callback) {

        try {
            callback.exec();

            return true;
        } catch (Throwable t) {
            getLogger().error("noerror", t);

            return false;
        }
    }

    protected <T> T convert(Object o, Class<T> clazz) {
        return ObjectHelper.convert(o, clazz);
    }


}
