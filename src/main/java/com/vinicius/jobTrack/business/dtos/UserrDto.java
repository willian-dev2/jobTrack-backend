package com.vinicius.jobTrack.business.dtos;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserrDto {

    private String email;
    private String password;
    private String name;

}
