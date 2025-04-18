package br.com.mrb.gestao_vagas_front;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PrimeiraPaginaController {

    @GetMapping("/home")
    public String primeiraPagina() {
        return "primeiraPagina";
    }


    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }
}
