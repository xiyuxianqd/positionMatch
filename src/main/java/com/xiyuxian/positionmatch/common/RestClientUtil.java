package com.xiyuxian.positionmatch.common;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.StandardCharsets;
import java.util.*;

@Slf4j
@Component
public class RestClientUtil {

    private final RestTemplate restTemplate;

    public RestClientUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    private HttpHeaders createJsonHeaders() {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }

    public <T> T post(String url, Object requestBody, Class<T> responseType) {
        String response = request(url, HttpMethod.POST, requestBody);
        return JSON.parseObject(response, responseType);
    }

    public String post(String url, Object requestBody) {
        return request(url, HttpMethod.POST, requestBody);
    }

    public String get(String url) {
        return request(url, HttpMethod.GET, null);
    }

    private String request(String url, HttpMethod method, Object requestBody) {
        HttpHeaders headers = createJsonHeaders();
        HttpEntity<Object> requestEntity = new HttpEntity<>(requestBody, headers);

        ResponseEntity<String> response = restTemplate.exchange(
                url,
                method,
                requestEntity,
                String.class
        );

        log.info("请求URL:{},请求方法:{},请求参数:{},响应结果:{}", url, method, requestBody, response.getBody());
        return response.getBody();
    }
}
