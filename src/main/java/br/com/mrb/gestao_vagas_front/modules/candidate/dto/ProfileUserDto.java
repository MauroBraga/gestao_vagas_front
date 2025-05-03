package br.com.mrb.gestao_vagas_front.modules.candidate.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileUserDto {

    private UUID id;
    private String email;
    private String description;
    private String username;
    private String name;
}
