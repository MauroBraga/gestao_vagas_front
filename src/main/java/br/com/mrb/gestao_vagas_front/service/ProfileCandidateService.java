package br.com.mrb.gestao_vagas_front.service;

import br.com.mrb.gestao_vagas_front.dto.ProfileUserDto;
import br.com.mrb.gestao_vagas_front.dto.Token;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ProfileCandidateService {

    public ProfileUserDto execute(String token) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        var result= restTemplate.exchange("http://localhost:8080/candidate", HttpMethod.GET,request, ProfileUserDto.class);
        System.out.println(result);
        return result.getBody();
    }
}
