package com.neeraj.preperation.java;

import com.fasterxml.jackson.core.type.TypeReference;
import com.neeraj.preperation.utils.JsonHelper;
import com.neeraj.preperation.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

@Slf4j
public class HttpCaller {

    static final long DEFAULT_TIMOUT = 5;
    static final int DEFAULT_PARALLEL_PROCESS = 5;
    HttpClient client;
    Map<String, String> headers = Map.of(
            "Content-Type", "application/json",
            "Accept", "application/json");

    public HttpCaller() {
        this.client = getHttpClient(DEFAULT_TIMOUT, DEFAULT_PARALLEL_PROCESS);
    }

    public HttpCaller(int parallelProcess) {
        this.client = getHttpClient(DEFAULT_TIMOUT, parallelProcess);
    }

    public HttpCaller(long timeoutInSeconds, int parallelProcess) {
        this.client = getHttpClient(timeoutInSeconds, parallelProcess);
    }

    public HttpCaller(long timeoutInSeconds, int parallelProcess, boolean disableSSL) {
        log.info("Creating a Http Client with a {} SSL connection.", disableSSL);
        this.client = getHttpClient(timeoutInSeconds, parallelProcess, disableSSL);
    }

    public HttpCaller(long timeoutInSeconds, ExecutorService executors) {
        this.client = getHttpClient(timeoutInSeconds, executors);
    }

    private ExecutorService getExecutors(int parallelProcess) {
        return Executors.newFixedThreadPool(parallelProcess);
    }

    private HttpClient getHttpClient(long timeoutInSeconds, int parallelProcess) {
        return getHttpClient(timeoutInSeconds, getExecutors(parallelProcess));
    }

    private HttpClient getHttpClient(long timeoutInSeconds, ExecutorService executors) {
        return HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(timeoutInSeconds))
                .executor(executors)
                .build();
    }

    private HttpClient getHttpClient(long timeoutInSeconds, int parallelProcess, boolean disableSSL) {
        var insecureClient = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_2)
                .followRedirects(HttpClient.Redirect.ALWAYS)
                .connectTimeout(Duration.ofSeconds(timeoutInSeconds))
                .executor(getExecutors(parallelProcess))
                .sslContext(insecureContext())
                .build();
        return disableSSL ? insecureClient : getHttpClient(timeoutInSeconds, parallelProcess);
    }

    private SSLContext insecureContext() {
        TrustManager[] noopTrustManager = new TrustManager[]{
                new X509TrustManager() {
                    public void checkClientTrusted(X509Certificate[] xcs, String string) {
                        log.debug("Skipping the Client Trust check. As the API call is to insecure context.");
//                        Skip the SSL check
                    }

                    public void checkServerTrusted(X509Certificate[] xcs, String string) {
                        log.debug("Skipping the Server Trust check. As the API call is to insecure context.");
//                        Skip the SSL check
                    }

                    public X509Certificate[] getAcceptedIssuers() {
                        return null;
                    }
                }
        };
        try {
            SSLContext sc = SSLContext.getInstance("TLSv1.2");
            sc.init(null, noopTrustManager, null);
            return sc;
        } catch (KeyManagementException | NoSuchAlgorithmException ex) {
            log.error("Error Creating HttpClient by disabling SSL Certificate verification.");
            log.error("Error Message : {}", ex.getMessage());
            throw new RuntimeException(ex.getLocalizedMessage());
        }
    }

    public String makeCall(HttpRequest request) {
        String responseString = null;
        try {
            var response = client.send(request, HttpResponse.BodyHandlers.ofString());
            if (response.statusCode() > 299) {
                throw new RuntimeException(response.body());
            } else {
                return response.body();
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public <A> A getCall(String url, Map<String, String> headers, TypeReference<A> valueTypeRef) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef);
    }

    public <A> A postCall(String url, Map<String, String> headers, Map<String, String> formData, TypeReference<A> valueTypeRef) {
        String formDataString = StringUtils.getFormData(formData);
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(formDataString))
                .uri(URI.create(url))
                .headers(formatHeaders(headers, false))
                .build();
        return makeCall(request, valueTypeRef);
    }

    public <A> A postCall(String url, Map<String, String> headers, Object body, TypeReference<A> valueTypeRef) {
        String jsonString = JsonHelper.stringify(body);
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(jsonString))
                .uri(URI.create(url))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef);
    }

    public <A> A postCall(String url, Map<String, String> headers, Object body, TypeReference<A> valueTypeRef, String jsonfield) {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(JsonHelper.serialize(body)))
                .uri(URI.create(url))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef, jsonfield);
    }

    public <A> A postCall(String url, Map<String, String> headers, Object body, TypeReference<A> valueTypeRef, String... jsonfield) {
        var request = HttpRequest.newBuilder()
                .POST(HttpRequest.BodyPublishers.ofString(JsonHelper.serialize(body)))
                .uri(URI.create(url))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef, jsonfield);
    }

    public <A> A getCall(String url, Map<String, String> headers, TypeReference<A> valueTypeRef, String jsonfield) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(URI.create(url))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef, jsonfield);
    }

    public <A> A getCall(String url, Map<String, String> queryParams, Map<String, String> headers, TypeReference<A> valueTypeRef) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURL(url, queryParams))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef);
    }

    public <A> A getCall(String url, Map<String, String> queryParams, Map<String, String> headers, TypeReference<A> valueTypeRef, String... jsonfield) {
        var request = HttpRequest.newBuilder()
                .GET()
                .uri(buildURL(url, queryParams))
                .headers(formatHeaders(headers))
                .build();
        return makeCall(request, valueTypeRef, jsonfield);
    }

    public <A> A makeCall(HttpRequest request, TypeReference<A> valueTypeRef, String... jsonfield) {
        var responseString = makeCall(request);
        if (jsonfield == null) {
            return JsonHelper.fromJson(responseString, valueTypeRef);
        } else {
            return JsonHelper.fromJson(responseString, valueTypeRef, jsonfield);
        }
    }

    private String[] formatHeaders(Map<String, String> customHeaders) {
        return formatHeaders(customHeaders, true);
    }

    private String[] formatHeaders(Map<String, String> customHeaders, boolean includeDefault) {
        var combinedHeaders = new HashMap<>();
        if (includeDefault) {
            combinedHeaders.putAll(headers);
        }
        Optional.ofNullable(customHeaders).ifPresent(combinedHeaders::putAll);
        return combinedHeaders.entrySet().stream()
                .flatMap(entry -> Stream.of(entry.getKey(), entry.getValue()))
                .toArray(String[]::new);
    }

    public URI buildURL(String url, Map<String, String> queryParams) {
        var queries = new LinkedMultiValueMap<String, String>();
        queryParams.forEach((key, value) -> queries.put(key, List.of(value)));
        var uriBuilder = UriComponentsBuilder
                .fromHttpUrl(url)
                .queryParams(queries)
                .build();
        return uriBuilder.toUri();
    }

}
