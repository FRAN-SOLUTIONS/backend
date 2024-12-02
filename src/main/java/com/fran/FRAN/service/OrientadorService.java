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

import com.fran.FRAN.dto.request.EditarPerfilOrientadorRequest;
import com.fran.FRAN.dto.response.OrientadorResponseDTO;
import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.entity.Orientador;

@Service
public class OrientadorService {

    @Autowired
    private OrientadorRepository orientadorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversão do hash para char[]
    private char[] hashToCharArray(String hash) {
        return hash.toCharArray();
    }
   
    public Orientador salvarOrientador(Orientador orientador) {
        Optional<Orientador> existingOrientador = orientadorRepository.findByProntuario(orientador.getProntuario());
        if (existingOrientador.isPresent()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "prontuario de orientador já cadastrado.");
        }
        String encodedPassword = passwordEncoder.encode(orientador.getSenha());
        char[] hashedPassword = hashToCharArray(encodedPassword);
    
        orientador.setSenha(new String(hashedPassword));  // Armazena o hash como String (pois o banco de dados não suporta char[])
        Arrays.fill(hashedPassword, '\0');  // Zera o array de char após uso
    
        return orientadorRepository.save(orientador);
    }

     public Orientador editarPerfil(EditarPerfilOrientadorRequest editarPerfilRequest) {
        Orientador orientador = orientadorRepository.findByProntuario(editarPerfilRequest.getProntuario())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Orientador não encontrado."));

        orientador.setNome(editarPerfilRequest.getNome());
        orientador.setProntuario(editarPerfilRequest.getProntuario().toLowerCase());
        orientador.setEmail(editarPerfilRequest.getEmail());
        return orientadorRepository.save(orientador);
    }
    

    public boolean validarSenha(String prontuario, String password) {
        Optional<Orientador> optionalOrientador = orientadorRepository.findByProntuario(prontuario);
    
        if (optionalOrientador.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Orientador não encontrado.");
        }

        Orientador orientador = optionalOrientador.get();
        return passwordEncoder.matches(password, orientador.getSenha());
    }


    public List<OrientadorResponseDTO> getAllOrientadores() {
        return orientadorRepository.findAll().stream()
                .map(OrientadorResponseDTO::new)
                .collect(Collectors.toList());
    }

    public String forgotPassword(String email) {

          Orientador orientador =  orientadorRepository.findByEmail(email).orElseThrow(
                ()-> new RuntimeException("Orientador não encontrado com esse email"));
            return null;
    }
}
