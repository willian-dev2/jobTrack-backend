package com.vinicius.jobTrack.business.service;

import com.vinicius.jobTrack.business.converter.UserrConverter;
import com.vinicius.jobTrack.business.dtos.UserrDto;
import com.vinicius.jobTrack.exception.ConflictException;
import com.vinicius.jobTrack.model.Userr;
import com.vinicius.jobTrack.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserrService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserrConverter userrConverter;

    // método para salvar usuarios
    public UserrDto saveUser(UserrDto userrDto) {
        // chamaremos nosso método emailExists para verificar se nosso email existe, chamando get email.
        emailExists(userrDto.getEmail());

        // Aqui iremos criptografar nossa senha chamando Enconde e getSenha dentro do setPassword
        userrDto.setPassword(passwordEncoder.encode(userrDto.getPassword()));

        // converte DTO recebido da requisição em uma entity
        Userr userr = userrConverter.forUser(userrDto);

        // salva a entidade no banco de dados e pega a entidade que acabou de ser salva e converte em dto
        return userrConverter.forUserDto(
                userRepository.save(userr));
    }

    public void emailExists (String email) {
        // método try / cath
        try {
            // retona um boolean pegando nosso método para chegar se existe==true ou naoExiste==false
            boolean existe = checkExistingEmail(email);

            // se existe==true
            if (existe) {
                throw new ConflictException("Email já cadastrado " + email);
            }
        } catch (ConflictException e) {
            throw new ConflictException("Email já cadastrado " + e.getCause());
        }
    }

    public boolean checkExistingEmail(String email) {
        // retorna um boolean true or false verificando no nosso banco de dados se existe "email"
        return userRepository.existsByEmail(email);
    }

    // método para listar usuarios através de qualquer usuário cadastrado
    public List<UserrDto> listUser() {
        return userRepository.findAll()
                .stream()
                .map(userrConverter::forUserDto)
                .toList();
    }

    public void deleteUserByEmail(String email) {
        userRepository.deleteByEmail(email);
    }


}
