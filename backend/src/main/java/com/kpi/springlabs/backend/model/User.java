package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Set;

@Document(collection = Constants.DocumentNames.USERS)
@Builder
@Setter
@Getter
public class User {

    @Id
    private String id;

    @Indexed(unique = true)
    private String username;

    @Indexed(unique = true)
    private String email;

    private String password;

    @DBRef
    private Set<Role> roles;

    private boolean enabled;
}
