//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.intesasanpaolo.bear.connector.rest.connector;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.intesasanpaolo.bear.config.LoggerUtils;
import com.intesasanpaolo.bear.connector.rest.RestInitializer;
import com.intesasanpaolo.bear.connector.rest.model.RestConnectorRequest;
import com.intesasanpaolo.bear.connector.rest.model.RestConnectorResponse;
import com.intesasanpaolo.bear.connector.rest.tracing.RestAuditInterceptor;
import com.intesasanpaolo.bear.connector.rest.tracing.RestTracingInterceptor;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestRequestTransformer;
import com.intesasanpaolo.bear.connector.rest.transformer.IRestResponseTransformer;
import com.intesasanpaolo.bear.core.interceptors.RequestContextHolder;
import com.intesasanpaolo.bear.hystrix.BaseHystrixConnector;
import com.intesasanpaolo.bear.layers.connector.http.HttpConnectionPool;
import com.intesasanpaolo.bear.layers.connector.http.HttpConnectionPoolSweeperScheduler;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService;
import com.intesasanpaolo.bear.rest.configuration.ArchRestConfigurationService.RestConfiguration;
import com.intesasanpaolo.bear.tracing.config.TracingConfiguration;
import com.intesasanpaolo.bear.util.ReflectionUtils;
import java.lang.reflect.ParameterizedType;
import java.net.URI;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import javax.annotation.PostConstruct;
import org.apache.http.auth.Credentials;
import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.ConnectionPoolTimeoutException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.hateoas.MediaTypes;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.hateoas.core.AnnotationRelProvider;
import org.springframework.hateoas.hal.CurieProvider;
import org.springframework.hateoas.hal.Jackson2HalModule;
import org.springframework.hateoas.hal.Jackson2HalModule.HalHandlerInstantiator;
import org.springframework.hateoas.mvc.TypeConstrainedMappingJackson2HttpMessageConverter;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.util.CollectionUtils;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

public abstract class BaseRestConnector<INPUT, OUTPUT, DTO, RESOURCE> extends BaseHystrixConnector<RestConnectorRequest<DTO>, RestConnectorResponse<RESOURCE>> {
    protected RestTemplate rt;
    @Autowired
    @Qualifier("RestConfiguration")
    private ArchRestConfigurationService configuration;
    @Autowired
    private BeanFactory beanFactory;
    @Autowired
    private RestTemplateBuilder restTemplateBuilder;
    @Autowired
    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;
    @Autowired
    private ObjectMapper objectMapper;
    private RestInitializer restInitializer;
    @Autowired(
        required = false
    )
    private RestTracingInterceptor restTracingInterceptor;
    private RestAuditInterceptor restAuditInterceptor;
    @Autowired
    private TracingConfiguration tracingConfiguration;
    @Autowired
    private HttpConnectionPoolSweeperScheduler httpConnectionPoolSweeperScheduler;
    private String url;
    private String name;
    private Object clazz;
    private HttpConnectionPool httpConnectionPool = null;
    private boolean randomMock;
    private String mockPath;
    private List<String> mockFiles;

    public BaseRestConnector() {
    }

    public final OUTPUT call(INPUT input, IRestRequestTransformer<INPUT, DTO> requestTransformer, IRestResponseTransformer<RESOURCE, OUTPUT> responseTransformer, Object... args) {
        RestConnectorRequest<DTO> restConnectorRequest = requestTransformer.transform(input, args);
        RestConnectorResponse<RESOURCE> restConnectorResponse = (RestConnectorResponse)this.execute(restConnectorRequest);
        return responseTransformer.transform(restConnectorResponse);
    }

    protected void doPreExecute(RestConnectorRequest<DTO> request) {
        super.doPreExecute(request);
        this.restInitializer.init(this.name, request.getDynamicUri() != null ? request.getDynamicUri() : this.url, this.mockPath, this.mockFiles, this.randomMock, this.rt, true, request.getParams());
    }

    @PostConstruct
    protected void init() {
        RestConfiguration restConfiguration = this.configuration.retrieveRestConfiguration(this.getClass().getSimpleName());
        if (restConfiguration == null) {
            throw new ExceptionInInitializerError();
        } else {
            this.configure(restConfiguration);
        }
    }

    protected void configure(RestConfiguration restConfiguration) {
        this.name = this.getClass().getSimpleName();
        this.logger.debug(LoggerUtils.formatArchRow("Configuration loaded for REST connector <{}>"), this.name);
        this.rt = this.restTemplateBuilder.setConnectTimeout(Duration.ofMillis((long)restConfiguration.getTimeout())).setReadTimeout(Duration.ofMillis((long)restConfiguration.getTimeout())).build();
        if (this.rt.getMessageConverters() != null) {
            int index = IntStream.range(0, this.rt.getMessageConverters().size()).filter((i) -> {
                return this.rt.getMessageConverters().get(i) instanceof MappingJackson2HttpMessageConverter;
            }).findFirst().orElse(this.rt.getMessageConverters().size());
            this.rt.getMessageConverters().add(index, this.getHalMessageConverter());
        } else {
            this.rt.setMessageConverters(Arrays.asList(this.getHalMessageConverter()));
        }

        List<ClientHttpRequestInterceptor> interceptors = this.rt.getInterceptors();
        if (CollectionUtils.isEmpty((Collection)interceptors)) {
            interceptors = new ArrayList();
            this.rt.setInterceptors((List)interceptors);
        }

        if (this.tracingConfiguration.isTracingEnabledFor(restConfiguration)) {
            ((List)interceptors).add(this.restTracingInterceptor);
        }

        if (this.tracingConfiguration.isAuditEnabledFor(restConfiguration)) {
            this.restAuditInterceptor = (RestAuditInterceptor)this.beanFactory.getBean(RestAuditInterceptor.class, new Object[]{restConfiguration.getCodService(), restConfiguration.getOperationName(), restConfiguration.getUrl(), restConfiguration.isAuditEnabled(), restConfiguration.isBfdEnabled()});
            ((List)interceptors).add(this.restAuditInterceptor);
        }

        if (restConfiguration.hasConnectionPoolConfiguration()) {
            this.httpConnectionPool = new HttpConnectionPool(restConfiguration.getConnectionPoolConfiguration(), (Credentials)null, this.httpConnectionPoolSweeperScheduler);
            this.rt.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory(this.httpConnectionPool.getHttpClient())));
        } else {
            this.rt.setRequestFactory(new BufferingClientHttpRequestFactory(new HttpComponentsClientHttpRequestFactory()));
        }

        this.setUrl(restConfiguration.getUrl());
        this.setClazz(ReflectionUtils.getGenericTypeClass(this.getClass(), 3));
        this.randomMock = restConfiguration.isRandomMock();
        this.mockPath = restConfiguration.getPath();
        this.mockFiles = restConfiguration.getFiles();
        this.restInitializer = (RestInitializer)this.beanFactory.getBean("RestInitializer" + (restConfiguration.isMocked() ? "Mock" : "Real"), RestInitializer.class);
        this.restInitializer.init(this.name, this.url, this.mockPath, this.mockFiles, this.randomMock, this.rt, false, (Map)null);
    }

    protected RestConnectorResponse<RESOURCE> doExecute(RestConnectorRequest<DTO> request) {
        request.addHeader("x-request-id", RequestContextHolder.getApplicationContext().getRequestId());
        request.addHeader("x-transaction-id", RequestContextHolder.getApplicationContext().getTransactionId());
        RestConnectorResponse response = new RestConnectorResponse();

        try {
            Object theClazz = this.getClazz();
            HttpEntity<DTO> httpEntity = new HttpEntity(request.getRequest(), request.getHttpHeaders());
            ResponseEntity responseEntity;
            if (theClazz instanceof ParameterizedType) {
                responseEntity = this.rt.exchange(this.buildUri(request.getDynamicUri(), request.getQueryParamsMultiValue(), request.getParams()), request.getMethod(), httpEntity, ParameterizedTypeReference.forType((ParameterizedType)theClazz));
            } else {
                responseEntity = this.rt.exchange(this.buildUri(request.getDynamicUri(), request.getQueryParamsMultiValue(), request.getParams()), request.getMethod(), httpEntity, (Class)theClazz);
            }

            this.logger.debug(LoggerUtils.formatArchRow("response HEADERS: {}"), responseEntity.getHeaders());
            response.setResponse(responseEntity);
            return response;
        } catch (AssertionError var6) {
            throw new HttpClientErrorException(HttpStatus.INTERNAL_SERVER_ERROR, var6.getMessage());
        }
    }

    /** @deprecated */
    @Deprecated
    protected String getUrl() {
        return this.url;
    }

    protected void setUrl(String url) {
        this.url = url;
    }

    protected URI buildUri(String dynamicUri, MultiValueMap<String, String> queryParam, Map<String, String> uriParam) {
        String requestUrl;
        if (dynamicUri != null) {
            requestUrl = dynamicUri;
        } else {
            requestUrl = this.url;
        }

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl(requestUrl);
        if (queryParam != null) {
            builder.queryParams(queryParam);
        }

        builder.uriVariables(new HashMap(uriParam));
        URI uri = builder.build().toUri();
        this.logger.debug(LoggerUtils.formatArchRow("URI: {}"), uri);
        return uri;
    }

    public Object getClazz() {
        return this.clazz;
    }

    public void setClazz(Object clazz) {
        this.clazz = clazz;
    }

    private MappingJackson2HttpMessageConverter getHalMessageConverter() {
        HalHandlerInstantiator instantiator = new HalHandlerInstantiator(new AnnotationRelProvider(), (CurieProvider)null, (MessageSourceAccessor)null);
        ObjectMapper mapper = this.objectMapper.copy();
        mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        mapper.registerModule(new Jackson2HalModule());
        mapper.setHandlerInstantiator(instantiator);
        TypeConstrainedMappingJackson2HttpMessageConverter converter = new TypeConstrainedMappingJackson2HttpMessageConverter(ResourceSupport.class);
        converter.setSupportedMediaTypes(Arrays.asList(MediaTypes.HAL_JSON));
        converter.setObjectMapper(mapper);
        return converter;
    }

    protected void setName(String name) {
        this.name = name;
    }

    protected String getConnectorType() {
        return "connectors.restConfigurations";
    }

    protected boolean isExceptionHandled(Throwable e) {
        if (!(e instanceof ResourceAccessException)) {
            return super.isExceptionHandled(e);
        } else {
            return super.isExceptionHandled(e) || super.isExceptionHandled(e.getCause());
        }
    }

    protected List<Class> setDefaultExceptions() {
        return (List)Stream.of(ConnectionPoolTimeoutException.class, ConnectTimeoutException.class).collect(Collectors.toList());
    }
}
