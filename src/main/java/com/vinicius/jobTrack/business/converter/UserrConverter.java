package com.vinicius.jobTrack.business.converter;

import com.vinicius.jobTrack.business.dtos.UserrDto;
import com.vinicius.jobTrack.model.Userr;
import org.springframework.stereotype.Component;

@Component
public class UserrConverter {

    // Aqui será realiazada a conversão de dados do nosso DTO para nossa Entity
    public Userr forUser (UserrDto userrDto) {
        return Userr.builder()
                .email(userrDto.getEmail())
                .name(userrDto.getName())
                .password(userrDto.getPassword())
                .build();
    }

    // Aqui será realizada a conversão de dados da nossa Entity para nosso DTO
    public UserrDto forUserDto (Userr userr) {
        return UserrDto.builder()
                .email(userr.getEmail())
                .name(userr.getName())
                .password("*********")
                .build();
    }

}
