package com.vpa.core.models;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * The persistent class for the monthlyrevenue database table.
 * 
 */
@Entity
@Table(name = "monthlyrevenue")
public class MonthlyRevenue  extends BaseModel {

    @Temporal(TemporalType.TIMESTAMP)
    private Date monthEndDate;

    @Temporal(TemporalType.TIMESTAMP)
    private Date monthStartDate;

    private double revenue;

    // bi-directional many-to-one association to Product
    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;

    // bi-directional many-to-one association to Region
    @ManyToOne
    @JoinColumn(name = "regionId")
    private Region region;

    public MonthlyRevenue() {
    }

  
    public Date getMonthEndDate() {
        return this.monthEndDate;
    }

    public void setMonthEndDate(Date monthEndDate) {
        this.monthEndDate = monthEndDate;
    }

    public Date getMonthStartDate() {
        return this.monthStartDate;
    }

    public void setMonthStartDate(Date monthStartDate) {
        this.monthStartDate = monthStartDate;
    }

    public double getRevenue() {
        return this.revenue;
    }

    public void setRevenue(double revenue) {
        this.revenue = revenue;
    }

    public Product getProduct() {
        return this.product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Region getRegion() {
        return this.region;
    }

    public void setRegion(Region region) {
        this.region = region;
    }

}