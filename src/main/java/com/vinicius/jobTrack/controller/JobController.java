package com.vinicius.jobTrack.controller;

import com.vinicius.jobTrack.business.dtos.JobRequestDto;
import com.vinicius.jobTrack.business.dtos.JobResponseDto;
import com.vinicius.jobTrack.business.dtos.UpdateJobStatusDto;
import com.vinicius.jobTrack.business.service.JobService;
import com.vinicius.jobTrack.model.Userr;
import com.vinicius.jobTrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;
    private final UserRepository userRepository;

    @PostMapping("/jobs")
    public ResponseEntity<JobResponseDto> criarJob (@RequestBody JobRequestDto dto,
                                                    Authentication authentication) {
        // pega o username do usuario logado do token
        String email = authentication.getName();

        // implementamos o método de criar job da nossa service passando os parametros username e dto
        JobResponseDto response = jobService.newJob(email, dto);

        //return jobService.newJob(user, job);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/candidaturas")
    public ResponseEntity<List<JobResponseDto>> candidaturas (Authentication authentication) {
        // Pegamos o nosso email do token e transformamos novamente em um String
        String email = authentication.getName();

        // Nesse método pegamos nossa lista da service e injetamos essa lista aqui para retornarmos essa resposta
        List<JobResponseDto> jobs = jobService.listJobsByUsers(email);

        // retorna a lista jobs
        return ResponseEntity.ok(jobs);
    }

    @PatchMapping("/jobs/{id}/status")
    public ResponseEntity<JobResponseDto> atualizacaoStatus (@RequestBody  UpdateJobStatusDto dto,
                                                  @PathVariable("id") Long jobId,
                                                  Authentication authentication) {
        // pegamos o nome ja autenticando nosso usuario atraves do token e puxamos o Id
        String nome = authentication.getName();
        Userr usuario = userRepository.findByEmail(nome).orElseThrow(()->
                new RuntimeException("Usuário não encontrado"));

        // joga os dados para nosso metodo na service
        JobResponseDto response =
                jobService.updateStatus(jobId, dto.getStatus(), usuario.getId());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/deleteJob/{companyName}/{jobTitle}")
    public ResponseEntity<Void> deleteJob (@PathVariable String companyName,
                                           @PathVariable String jobTitle) {
        jobService.deleteJob(jobTitle, companyName);
        return ResponseEntity.ok().build();
    }
}
