package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Constants.DocumentNames.JWT_BLACK_LISTS)
@Setter
@Getter
public class JwtBlackList {

    @Id
    private String id;

    private String token;

    public JwtBlackList(String token) {
        this.token = token;
    }
}
