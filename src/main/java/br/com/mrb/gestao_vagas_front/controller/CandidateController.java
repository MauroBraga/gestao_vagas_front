package br.com.mrb.gestao_vagas_front.controller;

import br.com.mrb.gestao_vagas_front.dto.Token;
import br.com.mrb.gestao_vagas_front.service.CandidateService;
import br.com.mrb.gestao_vagas_front.service.ProfileCandidateService;
import jakarta.servlet.http.HttpSession;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    final ProfileCandidateService profileCandidateService;

    public CandidateController(CandidateService candidateService, ProfileCandidateService profileCandidateService) {
        this.candidateService = candidateService;
        this.profileCandidateService = profileCandidateService;
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
          authentication.setDetails(token.getAccessToken());
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
    public String profile(Model model){
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            var token = (String) authentication.getDetails();
            var result = profileCandidateService.execute(token);
            model.addAttribute("profile", result);
            return "candidate/profile";
        }catch(HttpClientErrorException e){
            return "redirect:/candidate/login";
        }


    }

}
