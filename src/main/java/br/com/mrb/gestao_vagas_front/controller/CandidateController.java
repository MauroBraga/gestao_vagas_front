package br.com.mrb.gestao_vagas_front.controller;

import br.com.mrb.gestao_vagas_front.service.CandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
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
    public String   signInCandidate(RedirectAttributes redirectAttributes, HttpSession session,String username, String password) {
      try{
          var token = this.candidateService.login(username, password);
          var grants = token.getRoles().stream().map(role -> new SimpleGrantedAuthority("ROLE_" + role.toString().toUpperCase())).toList();
          var authentication = new UsernamePasswordAuthenticationToken(username, password, grants);
          authentication.setDetails(token);
          SecurityContextHolder.getContext().setAuthentication(authentication);
          SecurityContext securityContext = SecurityContextHolder.getContext();
          session.setAttribute("SPRING_SECURITY_CONTEXT", securityContext);
          session.setAttribute("token", token);

          return "redirect:/candidate/profile";


      } catch (HttpClientErrorException e) {
          redirectAttributes.addFlashAttribute("error_message", "Invalid username or password");
          return "redirect:/candidate/login";
      }
    }

    @GetMapping("/profile")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String profile(){
        return "candidate/profile";
    }

}
