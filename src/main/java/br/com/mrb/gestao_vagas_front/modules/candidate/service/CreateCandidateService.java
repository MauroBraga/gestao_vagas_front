package br.com.mrb.gestao_vagas_front.modules.candidate.service;

import br.com.mrb.gestao_vagas_front.modules.candidate.dto.CreateCandidateDTO;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class CreateCandidateService {

    public void execute(CreateCandidateDTO createCandidateDTO) {

        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<CreateCandidateDTO> request = new HttpEntity<>(createCandidateDTO, headers);

        var result = restTemplate.postForEntity("http://localhost:8080/candidate", request, String.class);

        System.out.println(result);



    }
}
