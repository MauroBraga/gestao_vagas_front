package br.com.mrb.gestao_vagas_front.modules.company.service;


import br.com.mrb.gestao_vagas_front.modules.candidate.dto.JobDTO;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.Map;

@Service
public class ListAllJobsCompanyService {

    public List<JobDTO> execute(String token){
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<Map<String, String>> request = new HttpEntity<>(headers);

        UriComponentsBuilder builder = UriComponentsBuilder.fromHttpUrl("http://localhost:8080/job");

        ParameterizedTypeReference<List<JobDTO>> responseType = new ParameterizedTypeReference<List<JobDTO>>() {};

        try {
            var result= restTemplate.exchange(builder.toUriString(), HttpMethod.GET,request, responseType);
            System.out.println(result);
            return result.getBody();
        }catch(HttpClientErrorException.Unauthorized ex){
            throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
        }
    }
}
