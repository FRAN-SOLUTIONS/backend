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

import com.fran.FRAN.dto.response.CoordenadorResponseDTO;
import com.fran.FRAN.model.dao.CoordenadorRepository;
import com.fran.FRAN.model.entity.Coordenador;

@Service
public class CoordenadorService { // Lida com regras de negócios

    @Autowired
    private CoordenadorRepository coordenadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversão do hash para char[]
    private char[] hashToCharArray(String hash) {
        return hash.toCharArray();
    }

    // Salva o coordenador com a senha criptografada usando char[]
    public Coordenador salvarCoordenador(Coordenador coordenador) {
        // Verifica se o email já existe no banco de dados
        Optional<Coordenador> existingCoordenador = coordenadorRepository.findByProntuario(coordenador.getProntuario());
        if (existingCoordenador.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Prontuário já cadastrado.");
        }
    
        // Codifica a senha e salva o coordenador
        String encodedPassword = passwordEncoder.encode(coordenador.getSenha());
        char[] hashedPassword = hashToCharArray(encodedPassword);
    
        coordenador.setSenha(new String(hashedPassword)); // Armazena o hash como String
        Arrays.fill(hashedPassword, '\0'); // Zera o array de char após uso
    
        return coordenadorRepository.save(coordenador);
    }

    public boolean validarSenha(String prontuario, String password) {
        Optional<Coordenador> optionalCoordenador = coordenadorRepository.findByProntuario(prontuario);
    
        if (optionalCoordenador.isEmpty()) {
            // Nenhum coordenador encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Coordenador não encontrado.");
        }
    
        // Um único coordenador encontrado, verificar a senha
        Coordenador coordenador = optionalCoordenador.get();
        return passwordEncoder.matches(password, coordenador.getSenha());
    }

    public List<CoordenadorResponseDTO> getAllCoordenadores() {
        return coordenadorRepository.findAll().stream()
                .map(CoordenadorResponseDTO::new)
                .collect(Collectors.toList());
    }
}
