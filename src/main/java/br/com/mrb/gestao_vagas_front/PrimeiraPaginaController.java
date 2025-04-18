package br.com.mrb.gestao_vagas_front;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class PrimeiraPaginaController {

    @GetMapping("/home")
    public String primeiraPagina(Model model) {
        model.addAttribute("mensagemDaController", "Primeira Pagina");

        return "primeiraPagina";
    }


    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }
}
