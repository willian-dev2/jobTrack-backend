package com.vinicius.jobTrack.business.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobRequestDto {

    private String companyName; // empresa
    private String jobTitle; // titulo

}
