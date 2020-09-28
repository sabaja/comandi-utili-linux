//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.hystrix;

import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.core.interceptors.BaseContextHolder;
import com.intesasanpaolo.bear.core.interceptors.RequestContextHolder;
import com.intesasanpaolo.bear.hystrix.ArchHystrixConfigurationService.HystrixConfiguration;
import com.intesasanpaolo.bear.hystrix.exceptions.NoFallBackImplementedException;
import com.intesasanpaolo.bear.hystrix.exceptions.ShortCircuitException;
import com.intesasanpaolo.bear.layers.connector.BaseConnector;
import com.intesasanpaolo.bear.layers.connector.request.BaseConnectorRequest;
import com.intesasanpaolo.bear.layers.connector.response.BaseConnectorResponse;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import com.netflix.hystrix.HystrixCommand.Setter;
import com.netflix.hystrix.HystrixCommandGroupKey.Factory;
import com.netflix.hystrix.exception.HystrixBadRequestException;
import com.netflix.hystrix.exception.HystrixRuntimeException;
import com.netflix.hystrix.exception.HystrixRuntimeException.FailureType;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.validation.constraints.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.client.HttpServerErrorException.InternalServerError;

public abstract class BaseHystrixConnector<INPUT extends BaseConnectorRequest, OUTPUT extends BaseConnectorResponse> extends BaseConnector<INPUT, OUTPUT> {
    private static final String CONNECTOR_GROUP_KEY = "connector";
    private static final String LOG_PREFIX = "[CIRCUIT BREAKER]";
    @Autowired
    @Qualifier("HystrixConfiguration")
    private ArchHystrixConfigurationService configurationService;
    private HystrixConfiguration hystrixConfiguration;

    public BaseHystrixConnector() {
    }

    @PostConstruct
    private void initializeHystrix() {
        this.hystrixConfiguration = this.configurationService.retrieveHystrixConfiguration(this.getConnectorType(), this.getConnectorName());
        if (this.hystrixConfiguration != null && this.hystrixConfiguration.getHandledExceptions().size() == 0) {
            this.hystrixConfiguration.getHandledExceptions().addAll(this.setDefaultExceptions());
        }

    }

    protected OUTPUT execute(INPUT request) {
        this.doPreExecute(request);
        BaseConnectorResponse output;
        if (this.hystrixConfiguration != null) {
            output = this.doExecuteWithHystrix(request);
        } else {
            output = this.doExecute(request);
        }

        return this.doPostExecute(request, output);
    }

    private OUTPUT doExecuteWithHystrix(INPUT request) {
        BaseHystrixConnector<INPUT, OUTPUT>.ConnectorExecuteHystrixCommand wrappedCommand = new BaseHystrixConnector.ConnectorExecuteHystrixCommand("connector", this.getConnectorName(), request);
        HystrixContextHolder contextHolder = new HystrixContextHolder();
        contextHolder.usingContext(BaseContextHolder::getApplicationContext, BaseContextHolder::forceSetApplicationContext).usingContext(BaseContextHolder::getAuthorizationContext, BaseContextHolder::forceSetAuthorizationContext).usingContext(BaseContextHolder::getErrorContext, BaseContextHolder::forceSetErrorContext).usingContext(BaseContextHolder::getISPContext, BaseContextHolder::forceSetIspContext).usingContext(BaseContextHolder::getSessionContext, BaseContextHolder::forceSetSessionContext).usingContext(RequestContextHolder::getRequest, RequestContextHolder::forceSetRequest).afterCall(RequestContextHolder::clear);
        contextHolder.prepareContext();
        wrappedCommand.setContextHolder(contextHolder);
        if (wrappedCommand.isCircuitBreakerOpen()) {
            this.logger.warn(LoggerUtils.formatArchRow("{} Circuit is closed!"), "[CIRCUIT BREAKER]");
        }

        try {
            return (BaseConnectorResponse)wrappedCommand.execute();
        } catch (HystrixRuntimeException var5) {
            this.logger.warn(LoggerUtils.formatArchRow("{} Exception while executing circuit breaker connection."), "[CIRCUIT BREAKER]");
            if (var5.getFailureType().equals(FailureType.SHORTCIRCUIT)) {
                throw new ShortCircuitException("The circuit is closed and the fallback " + (var5.getFallbackException() != null ? "failed" : "is not enabled") + ".");
            } else {
                this.logger.info(LoggerUtils.formatArchRow("{} Exception added to circuit breaker statistics."), "[CIRCUIT BREAKER]");
                throw var5.getFallbackException() != null ? this.unwrapRuntime(var5.getFallbackException()) : this.unwrapRuntime(var5);
            }
        } catch (HystrixBadRequestException var6) {
            this.logger.warn(LoggerUtils.formatArchRow("{} Exception while executing circuit breaker connection."), "[CIRCUIT BREAKER]");
            this.logger.info(LoggerUtils.formatArchRow("{} Exception ignored from circuit breaker statistics."), "[CIRCUIT BREAKER]");
            throw this.unwrapRuntime(var6);
        }
    }

    private RuntimeException unwrapRuntime(Throwable e) {
        return e.getCause() instanceof RuntimeException ? (RuntimeException)e.getCause() : new RuntimeException(e.getCause());
    }

    protected String getConnectorName() {
        return this.getClass().getSimpleName();
    }

    protected abstract String getConnectorType();

    protected abstract List<Class> setDefaultExceptions();

    protected OUTPUT getConnectorFallback(INPUT input, Throwable error) {
        throw new NoFallBackImplementedException();
    }

    protected boolean isExceptionHandled(Throwable e) {
        return this.hystrixConfiguration.isHandleRemoteShortcircuits() && this.isRemoteShortCircuit(e) || this.isExceptionListed(e) && this.handleCustomExceptions(e);
    }

    private boolean isExceptionListed(Throwable e) {
        return this.hystrixConfiguration.getHandledExceptions().stream().anyMatch((le) -> {
            return le.isInstance(e);
        });
    }

    protected boolean handleCustomExceptions(Throwable e) {
        return true;
    }

    private boolean isRemoteShortCircuit(Throwable e) {
        if (e instanceof InternalServerError && ((InternalServerError)e).getResponseBodyAsString().contains("\"messageKey\":\"com.intesasanpaolo.bear.shortcircuit.error\"")) {
            this.logger.warn(LoggerUtils.formatArchRow("{} Remote ShortCircuit."), "[CIRCUIT BREAKER]");
            return true;
        } else {
            return false;
        }
    }

    private class ConnectorExecuteHystrixCommand extends HystrixCommand<OUTPUT> {
        INPUT request;
        HystrixContextHolder contextHolder;

        ConnectorExecuteHystrixCommand(@NotNull String groupKey, @NotNull String commandKey, INPUT request) {
            super(Setter.withGroupKey(Factory.asKey(groupKey)).andCommandKey(com.netflix.hystrix.HystrixCommandKey.Factory.asKey(commandKey)).andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(BaseHystrixConnector.this.hystrixConfiguration.getHystrixMode()).withExecutionTimeoutInMilliseconds(BaseHystrixConnector.this.hystrixConfiguration.getExecutionTimeOutMillis()).withFallbackEnabled(BaseHystrixConnector.this.hystrixConfiguration.isWithFallBack()).withCircuitBreakerRequestVolumeThreshold(BaseHystrixConnector.this.hystrixConfiguration.getCumulativeVolume()).withCircuitBreakerSleepWindowInMilliseconds(BaseHystrixConnector.this.hystrixConfiguration.getSleepingWindow()).withMetricsRollingStatisticalWindowInMilliseconds(BaseHystrixConnector.this.hystrixConfiguration.getStatisticsWindow()).withMetricsRollingPercentileEnabled(true).withRequestCacheEnabled(false).withRequestLogEnabled(false)).andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(BaseHystrixConnector.this.hystrixConfiguration.getThreadPoolSize())));
            this.request = request;
        }

        public void setContextHolder(HystrixContextHolder contextHolder) {
            this.contextHolder = contextHolder;
        }

        protected OUTPUT run() {
            if (this.isCircuitBreakerOpen()) {
                BaseHystrixConnector.this.logger.info(LoggerUtils.formatArchRow("{} Circuit is being checked for open."), "[CIRCUIT BREAKER]");
            }

            this.acceptContextNullSafe();

            BaseConnectorResponse ret;
            try {
                ret = BaseHystrixConnector.this.doExecute(this.request);
            } catch (Exception var6) {
                if (this.isCircuitBreakerOpen()) {
                    BaseHystrixConnector.this.logger.info(LoggerUtils.formatArchRow("{} Circuit remains closed."), "[CIRCUIT BREAKER]");
                }

                if (BaseHystrixConnector.this.isExceptionHandled(var6)) {
                    throw var6;
                }

                throw new HystrixBadRequestException("", var6);
            } finally {
                this.executeAfterContextHolder();
            }

            if (this.isCircuitBreakerOpen()) {
                BaseHystrixConnector.this.logger.info(LoggerUtils.formatArchRow("{} Circuit is opening."), "[CIRCUIT BREAKER]");
            }

            return ret;
        }

        protected OUTPUT getFallback() {
            this.acceptContextNullSafe();

            BaseConnectorResponse var1;
            try {
                var1 = BaseHystrixConnector.this.getConnectorFallback(this.request, this.getFailedExecutionException());
            } finally {
                this.executeAfterContextHolder();
            }

            return var1;
        }

        private void executeAfterContextHolder() {
            if (this.contextHolder != null) {
                this.contextHolder.executeAfter();
            }

        }

        private void acceptContextNullSafe() {
            if (this.contextHolder != null) {
                this.contextHolder.setContext();
            }

        }
    }
}
