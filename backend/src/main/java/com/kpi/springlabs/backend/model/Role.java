package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.enums.RoleEnum;
import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = Constants.DocumentNames.ROLES)
@Setter
@Getter
public class Role {

    @Id
    private String id;

    private RoleEnum name;

    public Role(RoleEnum name) {
        this.name = name;
    }
}
