package com.kpi.springlabs.backend.security.token;

import com.kpi.springlabs.backend.model.JwtBlackList;
import com.kpi.springlabs.backend.model.User;
import com.kpi.springlabs.backend.service.JwtBlackListService;
import com.kpi.springlabs.backend.service.RefreshTokenService;
import com.kpi.springlabs.backend.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
@Slf4j
public class JwtLogoutHandler implements LogoutHandler {

    private final UserService userService;
    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlackListService jwtBlackListService;

    @Autowired
    @Lazy
    public JwtLogoutHandler(UserService userService, RefreshTokenService refreshTokenService,
                            JwtTokenProvider jwtTokenProvider, JwtBlackListService jwtBlackListService) {
        this.userService = userService;
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LOG.debug("Call JwtLogoutHandler");
        String token = jwtTokenProvider.getTokenFromRequest(request);

        String username = jwtTokenProvider.getUsernameFromToken(token);
        User user = userService.getByUsername(username);
        refreshTokenService.deleteByUserIfExists(user);

        if (jwtTokenProvider.isTokenNotInBlackList(token)) {
            JwtBlackList jwtBlackList = new JwtBlackList(token);
            JwtBlackList saveJwtToBlackList = jwtBlackListService.saveJwtTokenToBlackList(jwtBlackList);
            LOG.debug("Jwt token {} was added to black list", saveJwtToBlackList);
        }
    }
}
