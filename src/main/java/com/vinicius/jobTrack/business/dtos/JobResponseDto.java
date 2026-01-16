package com.vinicius.jobTrack.business.dtos;

import com.vinicius.jobTrack.model.ApplicationStatus;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobResponseDto {

    private Long id;
    private String companyName;
    private String jobTitle;
    private ApplicationStatus status;
    private String appliedAt;

}
