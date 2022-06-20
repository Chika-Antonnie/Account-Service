package com.chika.accountservice.rest;

import lombok.extern.log4j.Log4j2;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;


@Component
@Log4j2
public class RestConnector {


    private static final String TIME_OUT = "30000";

    public ResponseEntity<String> sendPostRequest(String url, String request, MediaType contentType,MediaType accept) {
        return sendPostRequest(url, request,"", contentType, accept, TIME_OUT, TIME_OUT);
    }


    public ResponseEntity<String> sendPostRequest(String url, String request,String authorization, MediaType contentType,MediaType accept, String readTimeout, String socketTimeout) {
        String connectionUrl = url;
        log.info(">>>>>>>>>POST URL::::" + url);

        //Build header request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        if(!authorization.isEmpty()) {
            headers.set("Authorization",authorization);
        }
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restConnectorProcessor(restTemplate,connectionUrl, HttpMethod.POST,entity);

    }


    public ResponseEntity<String> sendPutRequest(String url, String request,MediaType contentType,MediaType accept) {

        //Build header request
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(contentType);
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>(request, headers);

        RestTemplate restTemplate = new RestTemplate();
        return restConnectorProcessor(restTemplate,url, HttpMethod.PUT,entity);

    }


    public ResponseEntity<String> sendGetRequest(String url, MediaType contentType,MediaType accept,HttpHeaders headers ) {

        String connectionUrl = url;
        headers.setContentType(contentType);
        headers.setAccept(Collections.singletonList(accept));
        HttpEntity<String> entity = new HttpEntity<>("", headers);

        RestTemplate restTemplate = new RestTemplate();
        log.info(">>>>>>>>>GET REQUEST::::" + url);
        return restConnectorProcessor(restTemplate,connectionUrl, HttpMethod.GET,entity);
    }

    private  ResponseEntity<String> restConnectorProcessor(RestTemplate restTemplate, String url, HttpMethod method, HttpEntity<String> entity){
        ResponseEntity<String> responseFromServer = null;
        String result="";
        try{
            responseFromServer = restTemplate.exchange(url, method, entity, String.class);
            result = responseFromServer.getBody();
            log.info("<<<<<<<REST CONNECTOR  RESPONSE>>>>>>>>>" + result);
            return ResponseEntity.status(responseFromServer.getStatusCode()).body(result);
        }catch(ResourceAccessException e){
            log.error("ResourceAccessException"+e.getMessage());
            return ResponseEntity.status(HttpStatus.REQUEST_TIMEOUT).body("");
        } catch(HttpStatusCodeException exception){
            log.error("HttpStatusCodeException::" + exception.getMessage());
            int statusCode = exception.getStatusCode().value();
            log.info("<<<<<<<REST CONNECTOR  RESPONSE::::" + exception.getResponseBodyAsString());
            return ResponseEntity.status(statusCode).body(exception.getResponseBodyAsString());
        } catch(Exception e){
            log.error("<<<<<<<<<<<<Exception occurred during posting request" + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("");
        }

    }

}