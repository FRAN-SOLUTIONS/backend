package com.fran.FRAN.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.fran.FRAN.dto.response.OrientadorResponseDTO;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.entity.Orientador;

@Service
public class OrientadorService { // Lida com regras de negócios

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversão do hash para char[]
    private char[] hashToCharArray(String hash) {
        return hash.toCharArray();
    }

    // Salva o orientador com a senha criptografada usando char[]
    public Orientador salvarOrientador(Orientador orientador) {
        // Verifica se o email já existe no banco de dados
        Optional<Orientador> existingOrientador = orientadorRepository.findByProntuario(orientador.getProntuario());
        if (existingOrientador.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prontuario de orientadorjá cadastrado.");
        }
    
        // Codifica a senha e salva o orientador
        String encodedPassword = passwordEncoder.encode(orientador.getSenha());
        char[] hashedPassword = hashToCharArray(encodedPassword);
    
        orientador.setSenha(new String(hashedPassword));  // Armazena o hash como String (pois o banco de dados não suporta char[])
        Arrays.fill(hashedPassword, '\0');  // Zera o array de char após uso
    
        return orientadorRepository.save(orientador);
    }
    

    public boolean validarSenha(String prontuario, String password) {
        Optional<Orientador> optionalOrientador = orientadorRepository.findByProntuario(prontuario);
    
        if (optionalOrientador.isEmpty()) {
            // Nenhum orientador encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orientador não encontrado.");
        }
    
        // Um único orientador encontrado, verificar a senha
        Orientador orientador = optionalOrientador.get();
        return passwordEncoder.matches(password, orientador.getSenha());
    }
    


    public List<OrientadorResponseDTO> getAllOrientadores() {
        return orientadorRepository.findAll().stream()
                .map(OrientadorResponseDTO::new)
                .collect(Collectors.toList());
    }
}
