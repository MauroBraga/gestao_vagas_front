package br.com.mrb.gestao_vagas_front.modules.company.service;

import br.com.mrb.gestao_vagas_front.modules.company.dto.CreateJobsDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

@Service
public class CreateJobService {
    public String execute(CreateJobsDTO createJobsDTO, String token) {
        RestTemplate rt = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(token);

        HttpEntity<CreateJobsDTO> request = new HttpEntity<>(createJobsDTO, headers);

        var result = rt.postForObject("http://localhost:8080/job", request, String.class);

        System.out.println(result);

        return result;
    }
}
