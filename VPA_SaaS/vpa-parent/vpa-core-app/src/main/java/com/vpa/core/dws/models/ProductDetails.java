package com.vpa.core.dws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by anatoly on 11/3/15.
 */
@Entity
public class ProductDetails {

    @Id
    @Column(name = "id")
    private long id;

    @Column(name = "name")
    private String name;

    @Column(name = "family")
    private String family;

    @Column(name = "business_unit")
    private String business_unit;

    @Column(name = "glp")
    private Double glp;

    @Column(name = "compromised")
    private Integer compromised;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFamily() {
        return family;
    }

    public void setFamily(String family) {
        this.family = family;
    }

    public String getBusiness_unit() {
        return business_unit;
    }

    public void setBusiness_unit(String business_unit) {
        this.business_unit = business_unit;
    }

    public Double getGlp() {
        return glp;
    }

    public void setGlp(Double glp) {
        this.glp = glp;
    }

    public Integer getCompromised() {
        return compromised;
    }

    public void setCompromised(Integer compromised) {
        this.compromised = compromised;
    }
}
