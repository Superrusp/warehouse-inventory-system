package com.kpi.springlabs.backend.security.token;

import com.kpi.springlabs.backend.model.JwtBlackList;
import com.kpi.springlabs.backend.service.JwtBlackListService;
import com.kpi.springlabs.backend.service.RefreshTokenService;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

@Component
@Slf4j
public class JwtLogoutHandler implements LogoutHandler {

    private final RefreshTokenService refreshTokenService;
    private final JwtTokenProvider jwtTokenProvider;
    private final JwtBlackListService jwtBlackListService;

    @Autowired
    public JwtLogoutHandler(RefreshTokenService refreshTokenService, JwtTokenProvider jwtTokenProvider,
                            JwtBlackListService jwtBlackListService) {
        this.refreshTokenService = refreshTokenService;
        this.jwtTokenProvider = jwtTokenProvider;
        this.jwtBlackListService = jwtBlackListService;
    }

    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        LOG.debug("Call JwtLogoutHandler");
        String accessToken = jwtTokenProvider.getAccessTokenFromRequest(request);

        String refreshTokenValue = request.getParameter(Constants.TokenFields.REFRESH_TOKEN);
        refreshTokenService.deleteByTokenValue(refreshTokenValue);

        if (jwtTokenProvider.isTokenNotInBlackList(accessToken)) {
            Date accessTokenExpirationDate = jwtTokenProvider.getClaimsFromToken(accessToken).getExpiration();
            LOG.debug("Access token's expiration date: {}", JwtTokenProvider.EXPIRATION_DATE_FORMAT.format(accessTokenExpirationDate));
            JwtBlackList jwtBlackList = new JwtBlackList(accessToken, accessTokenExpirationDate.toInstant());
            JwtBlackList saveJwtToBlackList = jwtBlackListService.saveJwtTokenToBlackList(jwtBlackList);
            LOG.debug("Jwt token {} was added to black list", saveJwtToBlackList);
        }
    }
}
