package com.kpi.springlabs.backend.security.token;

import com.kpi.springlabs.backend.config.properties.JwtTokenProperties;
import com.kpi.springlabs.backend.enums.TokenType;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;

@Service
@Slf4j
public class JwtTokenProvider {

    private final JwtTokenProperties jwtTokenProperties;

    @Autowired
    public JwtTokenProvider(JwtTokenProperties jwtTokenProperties) {
        this.jwtTokenProperties = jwtTokenProperties;
    }

    public String generateToken(UserDetails userDetails) {
        LOG.debug("Generate token by userDetails: {}", userDetails);
        long expirationTime = jwtTokenProperties.getExpiredTimeSecAuthToken();
        String token = Jwts.builder()
                .setId(userDetails.getUsername())
                .setSubject(TokenType.ACCESS_TOKEN.name())
                .claim("authorities", userDetails.getAuthorities().toString())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationTime)))
                .signWith(SignatureAlgorithm.HS512, jwtTokenProperties.getSecretKey())
                .compact();
        LOG.debug("Token was generated for user '{}'", userDetails);
        return token;
    }

    public String generateMailConfirmationToken(String username) {
        LOG.debug("Generate mail token by username: {}", username);
        long expirationTime = jwtTokenProperties.getExpiredTimeSecConfirmationToken();
        String token = Jwts.builder()
                .setId(username)
                .setSubject(TokenType.MAIL_CONFIRMATION_TOKEN.name())
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plusSeconds(expirationTime)))
                .signWith(SignatureAlgorithm.HS512, jwtTokenProperties.getSecretKey())
                .compact();
        LOG.debug("Confirmation token was generated");
        return token;
    }

    public boolean validateToken(String subject, String token) {
        try {
            LOG.debug("Check if token is valid");
            Jwts.parser()
                    .setSigningKey(jwtTokenProperties.getSecretKey())
                    .parseClaimsJws(token);
            return isSubjectTokenValid(subject, token) && isExpirationDateValid(token);
        } catch (SignatureException e) {
            LOG.error("Invalid JWT signature: {}", e.getMessage());
        } catch (MalformedJwtException e) {
            LOG.error("Invalid JWT token: {}", e.getMessage());
        } catch (ExpiredJwtException e) {
            LOG.error("JWT token is expired: {}", e.getMessage());
        } catch (UnsupportedJwtException e) {
            LOG.error("JWT token is unsupported: {}", e.getMessage());
        } catch (IllegalArgumentException e) {
            LOG.error("JWT claims string is empty: {}", e.getMessage());
        }
        return false;
    }

    public String getUsername(String token) {
        LOG.debug("Get username from token");
        return Jwts.parser()
                .setSigningKey(jwtTokenProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getId();
    }

    public String getSubject(String token) {
        LOG.debug("Get subject from token");
        return Jwts.parser()
                .setSigningKey(jwtTokenProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public Date getExpirationDate(String token) {
        LOG.debug("Get expiration date from token");
        return Jwts.parser()
                .setSigningKey(jwtTokenProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody()
                .getExpiration();
    }

    private boolean isSubjectTokenValid(String subject, String token) {
        if (subject != null && !subject.equals(getSubject(token))) {
            LOG.debug("JWT token's subject is invalid");
            return false;
        }
        LOG.debug("JWT token's subject is valid");
        return true;
    }

    private boolean isExpirationDateValid(String token) {
        final Date expirationDate = getExpirationDate(token);
        if (expirationDate == null || expirationDate.before(new Date())) {
            LOG.debug("JWT token is expired: {}", token);
            return false;
        }
        LOG.debug("JWT token is not expired");
        return true;
    }
}
