package com.fran.FRAN.service;

import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.security.core.token.KeyBasedPersistenceTokenService;
import org.springframework.security.core.token.SecureRandomFactoryBean;
import org.springframework.security.core.token.Token;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.fran.FRAN.model.dao.OrientadorRepository;
import com.fran.FRAN.model.entity.Orientador;
import com.fran.FRAN.model.entity.PasswordTokenPublicData;

import java.time.Duration;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;

@Service
@RequiredArgsConstructor
public class OrientadorPasswordService {

    private final OrientadorRepository orientadorRepository;
    private final PasswordEncoder passwordEncoder;

    @SneakyThrows
    public String generateToken(Orientador orientador) {
        KeyBasedPersistenceTokenService tokenService = getInstanceFor(orientador);
        Token token = tokenService.allocateToken(orientador.getEmail());
        return token.getKey();
    }

    @SneakyThrows
    public void changePassword(String newPassword, String rawToken) {
        PasswordTokenPublicData publicData = readPublicData(rawToken);

        if(isExpired(publicData)) {
            throw new RuntimeException("Token expirado");
        }

        Orientador orientador = orientadorRepository.findByEmail(publicData.getEmail())
        .orElseThrow(() -> new RuntimeException("Orientador não encontrado"));

        KeyBasedPersistenceTokenService tokenService = this.getInstanceFor(orientador);
        tokenService.verifyToken(rawToken);

        orientador.setSenha(this.passwordEncoder.encode(newPassword));
        orientadorRepository.save(orientador);
    }

    private boolean isExpired(PasswordTokenPublicData publicData) {
        Instant createdAt = new Date(publicData.getCreateAtTimestamp()).toInstant();
        Instant now = new Date().toInstant();
        return createdAt.plus(Duration.ofMinutes(5)).isBefore(now);
    }

    private KeyBasedPersistenceTokenService getInstanceFor(Orientador Orientador) throws Exception {
        KeyBasedPersistenceTokenService tokenService = new KeyBasedPersistenceTokenService();

        tokenService.setServerSecret(Orientador.getSenha());
        tokenService.setServerInteger(16);
        tokenService.setSecureRandom(new SecureRandomFactoryBean().getObject());
        return tokenService;
    }

    private PasswordTokenPublicData readPublicData(String rawToken) {
        String rawTokenDecoded = new String(Base64.getDecoder().decode(rawToken));
        String[] tokenParts = rawTokenDecoded.split(":");
        Long timestamp = Long.parseLong(tokenParts[0]);
        String email = tokenParts[2];
        return new PasswordTokenPublicData(email, timestamp);
    }

}