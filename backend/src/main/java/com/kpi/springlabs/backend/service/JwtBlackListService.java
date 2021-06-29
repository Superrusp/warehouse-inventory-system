package com.kpi.springlabs.backend.service;

import com.kpi.springlabs.backend.model.JwtBlackList;

import java.util.Date;

public interface JwtBlackListService {

    JwtBlackList getJwtTokenFromBlackList(String token);

    JwtBlackList saveJwtTokenToBlackList(JwtBlackList jwtBlackList);

    Long deleteAllExpiredSince(Date date);
}
