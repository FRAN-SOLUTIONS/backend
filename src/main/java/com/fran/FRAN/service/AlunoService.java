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

import com.fran.FRAN.dto.response.AlunoResponseDTO;
import com.fran.FRAN.model.dao.AlunoRepository;
import com.fran.FRAN.model.entity.Aluno;

@Service
public class AlunoService { // Lida com regras de negócios

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // Conversão do hash para char[]
    private char[] hashToCharArray(String hash) {
        return hash.toCharArray();
    }

    // Salva o aluno com a senha criptografada usando char[]
    public Aluno salvarAluno(Aluno aluno) {
        if (aluno.getSenha() != null) {
            String encodedPassword = passwordEncoder.encode(aluno.getSenha());
            char[] hashedPassword = hashToCharArray(encodedPassword);
            aluno.setSenha(new String(hashedPassword));
            Arrays.fill(hashedPassword, '\0');  // Limpa o array de senha após uso
        } else {
            aluno.setSenha(null); // Se senha não for fornecida, salva como nulo
        }
    
        return alunoRepository.save(aluno);
    }
    

    public boolean validarSenha(String email, String password) {
        Optional<Aluno> optionalAluno = alunoRepository.findByEmail(email);
    
        if (optionalAluno.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        }
 
        Aluno aluno = optionalAluno.get();
        return passwordEncoder.matches(password, aluno.getSenha());
    }

    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
