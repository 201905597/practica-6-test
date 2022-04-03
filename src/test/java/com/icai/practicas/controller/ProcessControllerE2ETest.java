package com.icai.practicas.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import static org.assertj.core.api.BDDAssertions.then;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ProcessControllerE2ETest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void given_app_when_login_using_correct_credentials_then_ok() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";
        ProcessController.DataRequest dataPrueba = new ProcessController.DataRequest("Serena García", "52366481N", "918153719");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(dataPrueba, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getBody()).isEqualTo("{\"result\":\"OK\"}");
    }

    @Test
    public void given_app_when_login_using_incorrect_credentials_then_not_ok() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1";

        //Prueba 1: número de telefono no válido
        ProcessController.DataRequest dataPrueba = new ProcessController.DataRequest("Serena García", "52366481N", "91815371A");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<ProcessController.DataRequest> request = new HttpEntity<>(dataPrueba, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getBody()).isEqualTo("{\"result\":\"KO\"}");


        //Given
        address = "http://localhost:" + port + "/api/v1/process-step1";

        //Prueba 2: DNI no válido

        dataPrueba = new ProcessController.DataRequest("Serena García", "00000001R", "918153719");
        headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        request = new HttpEntity<>(dataPrueba, headers);

        //When
        result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getBody()).isEqualTo("{\"result\":\"KO\"}");
    }

    @Test
    public void given_app_when_login_using_correct_credentials_then_ok_legacy() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";
        MultiValueMap<String, String> datos = new LinkedMultiValueMap<>();
        datos.add("fullName", "Serena García");
        datos.add("dni", "52366481N"); // DNI válido
        datos.add("telefono", "918153719");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(datos, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getBody()).contains("Muchas gracias por enviar los datos");
    }

    @Test
    public void given_app_when_login_using_incorrect_credentials_then_not_ok_legacy() {

        //Given
        String address = "http://localhost:" + port + "/api/v1/process-step1-legacy";
        MultiValueMap<String, String> datos = new LinkedMultiValueMap<>();
        datos.add("fullName", "Serena García");
        datos.add("dni", "52366481P"); // DNI no válido (dígito control)
        datos.add("telefono", "918153719");
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(datos, headers);

        //When
        ResponseEntity<String> result = this.restTemplate.postForEntity(address, request, String.class);

        //Then
        then(result.getBody()).contains("Hemos tenido un problema con su solicitud");
    }
}