package br.com.mrb.gestao_vagas_front.modules.company.service;

import br.com.mrb.gestao_vagas_front.dto.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.HashMap;
import java.util.Map;

@Service
public class LoginCompanyService {

    public Token execute(String username, String password){

        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        Map<String, String> data = new HashMap<>();
        data.put("username", username);
        data.put("password", password);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(data, headers);

        var result = rt.postForObject("http://localhost:8080/company/auth", request, Token.class);

        System.out.println(result);

        return result;
    }
}
