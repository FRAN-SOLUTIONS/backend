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
public class AlunoService {

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
        String encodedPassword = passwordEncoder.encode(aluno.getPassword());
        char[] hashedPassword = hashToCharArray(encodedPassword);

        aluno.setPassword(new String(hashedPassword));  // Armazena o hash como String (pois o banco de dados não suporta char[])
        Arrays.fill(hashedPassword, '\0');  // Zera o array de char após uso

        return alunoRepository.save(aluno);
    }

    public boolean validarSenha(String email, String password) {
        Optional<Aluno> optionalAluno = alunoRepository.findByEmail(email);

        if (optionalAluno.isEmpty()) {
            // Nenhum aluno encontrado
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Aluno não encontrado.");
        } else if (optionalAluno.stream().count() > 1) {
            // Múltiplos alunos encontrados
            throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Erro no banco de dados: múltiplos alunos encontrados.");
        } else {
            // Um único aluno encontrado
            Aluno aluno = optionalAluno.get();
            // Verifique se a senha fornecida corresponde ao hash armazenado
            return passwordEncoder.matches(password, aluno.getPassword());
        }
    }


    public List<AlunoResponseDTO> getAllAlunos() {
        return alunoRepository.findAll().stream()
                .map(AlunoResponseDTO::new)
                .collect(Collectors.toList());
    }
}
