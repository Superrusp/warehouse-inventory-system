package com.kpi.springlabs.backend.model;

import com.kpi.springlabs.backend.utils.Constants;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;

@Entity(name = Constants.TableNames.WAREHOUSES)
@Setter
@Getter
@NoArgsConstructor
@ToString(callSuper = true)
public class Warehouse extends BaseEntity {

    private String name;

    public Warehouse(long id, String name) {
        super(id);
        this.name = name;
    }
}
