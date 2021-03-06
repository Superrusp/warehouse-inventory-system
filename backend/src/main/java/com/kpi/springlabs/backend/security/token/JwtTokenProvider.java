package com.kpi.springlabs.backend.security.token;

import com.kpi.springlabs.backend.config.properties.JwtTokenProperties;
import com.kpi.springlabs.backend.enums.TokenType;
import com.kpi.springlabs.backend.model.ConfirmationToken;
import com.kpi.springlabs.backend.model.JwtBlackList;
import com.kpi.springlabs.backend.model.RefreshToken;
import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.service.ConfirmationTokenService;
import com.kpi.springlabs.backend.service.JwtBlackListService;
import com.kpi.springlabs.backend.service.RefreshTokenService;
import com.kpi.springlabs.backend.utils.Constants;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Pair;
import org.springframework.http.HttpHeaders;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class JwtTokenProvider {

    public static final SimpleDateFormat EXPIRATION_DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssZ");

    private static Map<TokenType, Pair<Long, ChronoUnit>> TOKEN_TYPE_TO_EXPIRED_TIME;

    private final JwtTokenProperties jwtTokenProperties;
    private final JwtBlackListService jwtBlackListService;
    private final RefreshTokenService refreshTokenService;
    private final ConfirmationTokenService confirmationTokenService;

    @Autowired
    public JwtTokenProvider(JwtTokenProperties jwtTokenProperties, JwtBlackListService jwtBlackListService,
                            RefreshTokenService refreshTokenService, ConfirmationTokenService confirmationTokenService) {
        this.jwtTokenProperties = jwtTokenProperties;
        this.jwtBlackListService = jwtBlackListService;
        this.refreshTokenService = refreshTokenService;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String generateAccessToken(User user) {
        LOG.debug("Generate access token by user: {}", user.getUsername());
        List<SimpleGrantedAuthority> authorities = user.getRoles().stream()
                .map(role -> new SimpleGrantedAuthority(role.getName().name()))
                .collect(Collectors.toList());
        Map<String, Object> claims = Collections.singletonMap("authorities", authorities);
        String token = generateToken(user.getUsername(), claims, TokenType.ACCESS_TOKEN);
        LOG.debug("Access token was generated for user '{}'", user.getUsername());
        return token;
    }

    public String generateRefreshToken(User user) {
        String username = user.getUsername();
        LOG.debug("Generate refresh token by username: {}", username);
        String tokenValue = generateToken(username, null, TokenType.REFRESH_TOKEN);
        Date expirationDate = getClaimsFromToken(tokenValue).getExpiration();
        LOG.debug("Token's expiration date: {}", EXPIRATION_DATE_FORMAT.format(expirationDate));
        RefreshToken refreshToken = new RefreshToken(tokenValue, user, expirationDate.toInstant());
        refreshTokenService.save(refreshToken);
        LOG.debug("Refresh token was generated for user '{}'", username);
        return tokenValue;
    }

    public String generateMailConfirmationToken(User user) {
        String username = user.getUsername();
        LOG.debug("Generate mail token by username: {}", username);
        String tokenValue = generateToken(username, null, TokenType.MAIL_CONFIRMATION_TOKEN);
        Date expirationDate = getClaimsFromToken(tokenValue).getExpiration();
        LOG.debug("Token's expiration date: {}", EXPIRATION_DATE_FORMAT.format(expirationDate));
        ConfirmationToken confirmationToken = new ConfirmationToken(tokenValue, user, expirationDate.toInstant());
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        LOG.debug("Confirmation token was generated");
        return tokenValue;
    }

    public boolean validateToken(String subject, String token) {
        try {
            LOG.debug("Check if token is valid");
            Jwts.parser()
                    .setSigningKey(jwtTokenProperties.getSecretKey())
                    .parseClaimsJws(token);
            return isSubjectTokenValid(subject, token) && isExpirationDateValid(token) && isTokenNotInBlackList(token);
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

    public String getAccessTokenFromRequest(HttpServletRequest request) {
        LOG.debug("Get token from request");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(header) && header.startsWith(Constants.AccessTokenType.BEARER)) {
            LOG.debug("The token is being retrieved");
            return header.substring(Constants.AccessTokenType.BEARER.length() + 1);
        }
        LOG.debug("The token is not found");
        return null;
    }

    private String generateToken(String username, Map<String, Object> claims, TokenType tokenType) {
        Pair<Long, ChronoUnit> expiryDateAndTimeUnitPair = TOKEN_TYPE_TO_EXPIRED_TIME.get(tokenType);
        long expirationTime = expiryDateAndTimeUnitPair.getFirst();
        ChronoUnit timeUnit = expiryDateAndTimeUnitPair.getSecond();
        String token = Jwts.builder()
                .setId(username)
                .setSubject(tokenType.name())
                .addClaims(claims)
                .setIssuedAt(Date.from(Instant.now()))
                .setExpiration(Date.from(Instant.now().plus(expirationTime, timeUnit)))
                .signWith(SignatureAlgorithm.HS512, jwtTokenProperties.getSecretKey())
                .compact();
        LOG.debug("Token was generated for user '{}'", username);
        return token;
    }

    public String getUsernameFromToken(String token) {
        LOG.debug("Get username from token");
        return getClaimsFromToken(token).getId();
    }

    public boolean isTokenNotInBlackList(String token) {
        JwtBlackList jwtTokenInBlackList = jwtBlackListService.getJwtTokenFromBlackList(token);
        if (jwtTokenInBlackList != null) {
            LOG.debug("Jwt token is in black list");
            return false;
        }
        LOG.debug("Jwt token is not in black list");
        return true;
    }

    public Claims getClaimsFromToken(String token) {
        LOG.debug("Get claims from token");
        return Jwts.parser()
                .setSigningKey(jwtTokenProperties.getSecretKey())
                .parseClaimsJws(token)
                .getBody();
    }

    private boolean isSubjectTokenValid(String subject, String token) {
        String tokenSubject = getClaimsFromToken(token).getSubject();
        if (subject != null && !subject.equals(tokenSubject)) {
            LOG.debug("JWT token's subject is invalid");
            return false;
        }
        LOG.debug("JWT token's subject is valid");
        return true;
    }

    private boolean isExpirationDateValid(String token) {
        final Date expirationDate = getClaimsFromToken(token).getExpiration();
        if (expirationDate == null || expirationDate.before(new Date())) {
            LOG.debug("JWT token is expired: {}", token);
            return false;
        }
        LOG.debug("JWT token is not expired");
        return true;
    }

    @PostConstruct
    private void init() {
        LOG.debug("token_type_to_expired_time map initialization");
        TOKEN_TYPE_TO_EXPIRED_TIME = new EnumMap<>(TokenType.class);
        TOKEN_TYPE_TO_EXPIRED_TIME.put(TokenType.ACCESS_TOKEN, Pair.of(jwtTokenProperties.getExpiredTimeSecAccessToken(), ChronoUnit.MINUTES));
        TOKEN_TYPE_TO_EXPIRED_TIME.put(TokenType.REFRESH_TOKEN, Pair.of(jwtTokenProperties.getExpiredTimeSecRefreshToken(), ChronoUnit.DAYS));
        TOKEN_TYPE_TO_EXPIRED_TIME.put(TokenType.MAIL_CONFIRMATION_TOKEN, Pair.of(jwtTokenProperties.getExpiredTimeSecConfirmationToken(), ChronoUnit.HOURS));
    }
}
