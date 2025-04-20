package br.com.mrb.gestao_vagas_front.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class CandidateController {

    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }

}
