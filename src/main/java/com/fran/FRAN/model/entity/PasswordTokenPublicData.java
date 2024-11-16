package com.fran.FRAN.model.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PasswordTokenPublicData {
    private final String email;
    private final Long createAtTimestamp;
}