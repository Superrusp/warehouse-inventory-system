package com.kpi.springlabs.backend.security.token;

import com.kpi.springlabs.backend.enums.TokenType;
import com.kpi.springlabs.backend.security.user.CustomUserDetailsService;
import com.kpi.springlabs.backend.security.user.SecurityUser;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class JwtTokenFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;
    private final CustomUserDetailsService userDetailsService;

    @Autowired
    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider, CustomUserDetailsService userDetailsService) {
        this.jwtTokenProvider = jwtTokenProvider;
        this.userDetailsService = userDetailsService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest,
                                    HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            LOG.debug("Execute token filter");
            String token = getTokenFromRequest(httpServletRequest);
            if (token != null && jwtTokenProvider.validateToken(TokenType.ACCESS_TOKEN.name(), token)) {
                String username = jwtTokenProvider.getUsernameFromToken(token);
                LOG.debug("The token contains username '{}'", username);
                SecurityUser securityUser = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        securityUser, null, securityUser.getAuthorities());

                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(httpServletRequest));
                SecurityContextHolder.getContext().setAuthentication(authentication);
                LOG.debug("User '{}' authenticated for uri: {}", username, httpServletRequest.getRequestURI());
            }
        } catch (Exception e) {
            LOG.error("Cannot set user authentication: ", e);
            httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }

    private String getTokenFromRequest(HttpServletRequest request) {
        LOG.debug("Get token from request");
        String header = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (StringUtils.hasText(header) && header.startsWith(Constants.AccessTokenType.BEARER)) {
            LOG.debug("The token is being retrieved");
            return header.substring(Constants.AccessTokenType.BEARER.length() + 1);
        }
        LOG.debug("The token is not found");
        return null;
    }
}
