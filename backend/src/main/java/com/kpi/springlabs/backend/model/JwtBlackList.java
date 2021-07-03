package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = Constants.DocumentNames.JWT_BLACK_LIST)
@Setter
@Getter
public class JwtBlackList {

    @Id
    private String id;

    private String token;

    private Instant expirationDate;

    public JwtBlackList(String token, Instant expirationDate) {
        this.token = token;
        this.expirationDate = expirationDate;
    }
}
