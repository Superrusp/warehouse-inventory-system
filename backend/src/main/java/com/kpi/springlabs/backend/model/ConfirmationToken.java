package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Document(collection = Constants.DocumentNames.CONFIRMATION_TOKENS)
@Setter
@Getter
public class ConfirmationToken {

    @Id
    private String id;

    private String tokenValue;

    private Instant createdDate;

    @DBRef
    private User user;

    public ConfirmationToken(String tokenValue, User user) {
        this.tokenValue = tokenValue;
        this.createdDate = Instant.now();
        this.user = user;
    }
}
