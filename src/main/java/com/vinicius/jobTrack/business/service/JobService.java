package com.vinicius.jobTrack.business.service;

import com.vinicius.jobTrack.business.converter.JobConverter;
import com.vinicius.jobTrack.business.dtos.JobRequestDto;
import com.vinicius.jobTrack.business.dtos.JobResponseDto;
import com.vinicius.jobTrack.exception.ConflictException;
import com.vinicius.jobTrack.model.ApplicationStatus;
import com.vinicius.jobTrack.model.Job;
import com.vinicius.jobTrack.model.Userr;
import com.vinicius.jobTrack.repository.JobRepository;
import com.vinicius.jobTrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final JobConverter jobConverter;

    // Método para buscar usuario através do ID
    public List<Job> findByUser(Long userId) {
        return jobRepository.findByUserId(userId);
    }

    // Criar candidatura em um Job
    public JobResponseDto newJob(String email, JobRequestDto dto) {
        // verifica se o usuario existe no banco de dados
        Userr usuario = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Usuário não encontrado"));

        // verifica no banco de dados se não tem um Job igual já cadastrado no usuário requisitado.
        if (jobRepository.existsByUserAndCompanyNameAndJobTitle(usuario, dto.getCompanyName(), dto.getJobTitle())) {
            throw new RuntimeException("Você já fez uma candidatura para esse emprego nessa mesma empresa "
                    + usuario.getName());
        }

        Job job = jobConverter.forJobRequest(dto);
        job.setUser(usuario);
        job.setStatus(ApplicationStatus.APPLIED);
        job.setAppliedAt(LocalDateTime.now().toString());

        Job saved = jobRepository.save(job);

        // retorna a convesao de entity em responseDto
        return jobConverter.forJobResponse(saved);
    }

    public List<JobResponseDto> listJobsByUsers(String email) {
        Userr usuario = userRepository.findByEmail(email).orElseThrow(() ->
                new RuntimeException("Usuário não encontrado"));

        List<Job> jobs = jobRepository.findByUser(usuario);

        // convertemos cada job para jobResponse
        return jobs.stream()
                .map(jobConverter::forJobResponse)
                .toList();
    }

    public JobResponseDto updateStatus(Long jobId, String newStatus, Long user) {
        // busca o Job no banco de dados
        Job trabalho = jobRepository.findById(jobId).orElseThrow(() ->
                new RuntimeException("Trabalho não encontrado"));

        // verifica se o Job está relacionado com o usuario
        boolean pertence = jobRepository.existsByIdAndUserId(jobId, user);
        if (!pertence) {
            throw new ConflictException("O trabalho " + trabalho.getJobTitle() + " não está associado ao usuário requisitado");

        }
        // Como estamos trabalhando com Enum e recebendo uma Strig, usamos o método "valueOf" para receber a String e
        //...retornar um valor correspondente Enum, precisa do UpperCase para deixar tudo em maiusculo.
        ApplicationStatus statusEnum;
        // valida e converte o status
        if (newStatus == null || newStatus.isBlank()) {
            throw new ConflictException("Status não pode ser vazio");
        }
        try {
            statusEnum = ApplicationStatus.valueOf(newStatus.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ConflictException("Status " + newStatus + " é inválido no sistema");
        }

        // Sett no Status da nossa Entity
        trabalho.setStatus(statusEnum);

        // salvamos no banco de dados a mudança
        jobRepository.save(trabalho);

        // retornamos na nossa converter "trabalho" com o status ja atualizado
        return jobConverter.forJobResponse(trabalho);

    }

    public void deleteJob (String jobTitle, String companyName) {
        jobRepository.deleteByJobTitleAndCompanyName(jobTitle, companyName);
    }

}
