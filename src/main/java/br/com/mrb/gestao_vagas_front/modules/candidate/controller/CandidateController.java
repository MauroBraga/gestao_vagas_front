package br.com.mrb.gestao_vagas_front.modules.candidate.controller;

import br.com.mrb.gestao_vagas_front.modules.candidate.dto.CreateCandidateDTO;
import br.com.mrb.gestao_vagas_front.modules.candidate.exception.FormatErrorMessage;
import br.com.mrb.gestao_vagas_front.modules.candidate.service.*;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.UUID;

@Controller
@RequestMapping("/candidate")
public class CandidateController {

    final CandidateService candidateService;
    final ProfileCandidateService profileCandidateService;
    final FindJobService findJobService;
    final ApplyJobService applyJobService;
    final CreateCandidateService createCandidateService;

    public CandidateController(CandidateService candidateService, ProfileCandidateService profileCandidateService, FindJobService findJobService, ApplyJobService applyJobService, CreateCandidateService createCandidateService) {
        this.candidateService = candidateService;
        this.profileCandidateService = profileCandidateService;
        this.findJobService = findJobService;
        this.applyJobService = applyJobService;
        this.createCandidateService = createCandidateService;
    }

    @GetMapping("/login")
    public String loginCandidate() {
        return "candidate/login";
    }

    @GetMapping("/jobs")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String  jobs(Model model,String filter){
        try {
            if(filter != null && !filter.isEmpty()){
                var token = getToken();
                //model.addAttribute("filter", filter);
                var jobs = findJobService.execute(token,filter);
                model.addAttribute("jobs", jobs);
            }
        }catch (HttpClientErrorException e){
            return "redirect:/candidate/login";
        }
        return "candidate/jobs";
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

            var result = profileCandidateService.execute(getToken());
            model.addAttribute("profile", result);
            return "candidate/profile";
        }catch(HttpClientErrorException e){
            return "redirect:/candidate/login";
        }


    }

    @PostMapping("/jobs/apply")
    @PreAuthorize("hasRole('CANDIDATE')")
    public String applyJob(@RequestParam("jobId") UUID jobId){
        this.applyJobService.execute(getToken(), jobId);
        return "redirect:/candidate/jobs";
    }

    @GetMapping("/create")
    public String create(Model model){
        model.addAttribute("candidate", new CreateCandidateDTO());
        return "candidate/create";
    }

    @PostMapping("/create")
    public String save(CreateCandidateDTO candidate, Model model){
        try {
            createCandidateService.execute(candidate);
        }catch (HttpClientErrorException e){
            model.addAttribute("error_message", FormatErrorMessage.formatErrorMessage(e.getResponseBodyAsString()));
        }
        model.addAttribute("candidate", candidate);

        return "/candidate/create";
    }

    private String getToken(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (String) authentication.getDetails();
    }
}
