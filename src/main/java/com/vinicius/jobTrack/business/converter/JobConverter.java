package com.vinicius.jobTrack.business.converter;

import com.vinicius.jobTrack.business.dtos.JobRequestDto;
import com.vinicius.jobTrack.business.dtos.JobResponseDto;
import com.vinicius.jobTrack.business.dtos.UpdateJobStatusDto;
import com.vinicius.jobTrack.model.ApplicationStatus;
import com.vinicius.jobTrack.model.Job;
import org.springframework.stereotype.Component;

@Component
public class JobConverter {

    // conversão de dados do nosso dto JobRequest para nossa entity
    public Job forJobRequest(JobRequestDto jobRequestDto) {
        return Job.builder()
                .companyName(jobRequestDto.getCompanyName())
                .jobTitle(jobRequestDto.getJobTitle())
                .build();
    }

    // conversão de dados do nosso dto JobResponse para nossa entity
    public Job forJobResponse(JobResponseDto jobResponseDto) {
        return Job.builder()
                .id(jobResponseDto.getId())
                .companyName(jobResponseDto.getCompanyName())
                .jobTitle(jobResponseDto.getJobTitle())
                .status(jobResponseDto.getStatus())
                .appliedAt(jobResponseDto.getAppliedAt())
                .build();
    }

    // Conversão de dados da nossa entity JOB para nossa DTO JobRequest
    public JobRequestDto forJobRequestDto(Job job) {
        return JobRequestDto.builder()
                .companyName(job.getCompanyName())
                .jobTitle(job.getJobTitle())
                .build();
    }

    // Conversão de dados da nossa entity JOB para nossa DTO JobResponse
    public JobResponseDto forJobResponse(Job job) {
        return JobResponseDto.builder()
                .id(job.getId())
                .companyName(job.getCompanyName())
                .jobTitle(job.getJobTitle())
                .status(job.getStatus())
                .appliedAt(job.getAppliedAt())
                .build();
    }

    public JobResponseDto updateStatus (UpdateJobStatusDto update) {
        // Como aqui sett direta para um Enum no JobResponse, temos que transformar nossa String para atribuir a Enum
        ApplicationStatus statusEnum = ApplicationStatus.valueOf(update.getStatus().toUpperCase());
        return JobResponseDto.builder()
                .status(statusEnum)
                .build();
    }

}
