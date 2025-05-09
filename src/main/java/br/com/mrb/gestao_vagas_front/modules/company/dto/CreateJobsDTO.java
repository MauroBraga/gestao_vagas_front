package br.com.mrb.gestao_vagas_front.modules.company.dto;

import lombok.Data;

@Data
public class CreateJobsDTO {

    private String description;
    private String benefits;
    private String level;
}
