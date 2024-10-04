package com.fran.FRAN.config;

import java.util.Date;
import java.util.List;
import java.util.Arrays;


public class JWTObject {
        private String subject;
        private Date issuedAt; //data de criação do token
        private Date expiration; //data de expiração do token
        private List<String> roles;

        public void setRoles(String...roles){
            this.roles = Arrays.asList(roles);
        }

        public Date getIssuedAt() {
            return issuedAt;
        }

        public void setIssuedAt(Date issuedAt) {
            this.issuedAt = issuedAt;
        }

        public Date getExpiration() {
            return expiration;
        }

        public void setExpiration(Date expiration) {
            this.expiration = expiration;
        }

        public List<String> getRoles() {
            return roles;
        }

        public void setRoles(List<String> roles) {
            this.roles = roles;
        }

        public String getSubject() {
            return subject;
        }

        public void setSubject(String subject) {
            this.subject = subject;
        }
}
