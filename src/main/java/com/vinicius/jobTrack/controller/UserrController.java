package com.vinicius.jobTrack.controller;

import com.vinicius.jobTrack.business.dtos.UserrDto;
import com.vinicius.jobTrack.business.service.UserrService;
import com.vinicius.jobTrack.config.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuario")
@RequiredArgsConstructor
public class UserrController {

    private final UserrService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    // Esse Post irá criar e salvar em uma lista os usuarios e enviar para o banco de dados
    @PostMapping
    public ResponseEntity<UserrDto> saveUser(@RequestBody UserrDto userrDto) {
        return ResponseEntity.ok(userService.saveUser(userrDto));
    }

    // Aqui é onde será feito o Login de usuário ja cadastrado
    @PostMapping("/auth/login")
    // no corpo da requisição entrará o nosso Dto
    public ResponseEntity<String> login(@RequestBody UserrDto userrDto) {

        // método para a autenticação do nosso email e da nossa senha
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(userrDto.getEmail(),
                        userrDto.getPassword())
        );
        // retornará um token JWT já formatado para ser chamado nas nossas autenticações
        return ResponseEntity.ok("Bearer " + jwtUtil.generateToken(authentication.getName()));

    }

    // Esse Get irá retornar a lista de usuarios cadastrados.OBS: precisa estar logado a um usuario e passar o token
    @GetMapping
    public List<UserrDto> listUsers() {
        return userService.listUser();
    }

}
