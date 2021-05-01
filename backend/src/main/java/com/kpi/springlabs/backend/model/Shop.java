package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;

@Entity(name = Constants.TableNames.SHOPS)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Shop extends BaseEntity {

    private String name;

    public Shop(long id, String name) {
        super(id);
        this.name = name;
    }
}
