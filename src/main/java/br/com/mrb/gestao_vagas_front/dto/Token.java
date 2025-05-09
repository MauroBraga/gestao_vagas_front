package br.com.mrb.gestao_vagas_front.dto;

import lombok.Data;

import java.util.List;

@Data
public class Token {
    private String accessToken;
    private List<String> roles;
    private Long expires_in;

}
