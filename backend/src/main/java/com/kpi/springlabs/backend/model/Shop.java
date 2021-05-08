package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.SequenceGenerator;


@Entity(name = Constants.TableNames.SHOPS)
@SequenceGenerator(name = "base_sequence", sequenceName = "shops_sequence", allocationSize = 1)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Shop extends BaseEntity {

    @Column(nullable = false, length = 50)
    private String name;

    public Shop(long id, String name) {
        super(id);
        this.name = name;
    }
}
