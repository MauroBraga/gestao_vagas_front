package br.com.mrb.gestao_vagas_front.controller;

import br.com.mrb.gestao_vagas_front.service.CandidateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    final
    CandidateService candidateService;

    public CandidateController(CandidateService candidateService) {
        this.candidateService = candidateService;
    }

    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }

    @PostMapping("/signIn")
    public String   signInCandidate(RedirectAttributes redirectAttributes,String username, String password) {
      try{
          var token = this.candidateService.login(username, password);

          return "candidate/profile";


      } catch (HttpClientErrorException e) {
          redirectAttributes.addFlashAttribute("error_message", "Invalid username or password");
          return "redirect:/candidate/login";
      }
    }

    @GetMapping("/profile")
    public String profile(){
        return "candidate/profile";
    }

}
