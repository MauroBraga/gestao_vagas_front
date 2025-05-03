package br.com.mrb.gestao_vagas_front.modules.company.controller;

import br.com.mrb.gestao_vagas_front.modules.candidate.exception.FormatErrorMessage;
import br.com.mrb.gestao_vagas_front.modules.company.dto.CreateCompanyDTO;
import br.com.mrb.gestao_vagas_front.modules.company.service.CreateCompanyService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;

@Controller
@RequestMapping("/company")
public class CompanyController {

    private final CreateCompanyService createCompanyService;

    public CompanyController(CreateCompanyService createCompanyService) {
        this.createCompanyService = createCompanyService;
    }


    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("company", new CreateCompanyDTO());
        return "company/create";
    }

    @PostMapping("/create")
    public String save(Model model, CreateCompanyDTO createCompanyDTO) {
        try {
            createCompanyService.execute(createCompanyDTO);
            model.addAttribute("company", new CreateCompanyDTO());
        }catch (HttpClientErrorException e){
            e.printStackTrace();
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString()));
            model.addAttribute("company", createCompanyDTO);
        }


        return "/company/create";
    }
}
