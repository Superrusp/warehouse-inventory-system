package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.JwtBlackList;

public interface JwtBlackListService {

    JwtBlackList getJwtTokenFromBlackList(String token);

    JwtBlackList saveJwtTokenToBlackList(JwtBlackList jwtBlackList);
}
