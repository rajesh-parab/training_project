package com.vpa.core.dws.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityResult;
import javax.persistence.FieldResult;
import javax.persistence.Id;
import javax.persistence.SqlResultSetMapping;
import javax.persistence.Table;


/**
 * The persistent class for the dim_product database table.
 * 
 */
@Entity
@Table(name = "vpadws.dim_authentication")
@SqlResultSetMapping(name = "DimAuthentication", 
entities = { 
        @EntityResult(entityClass = DimAuthentication.class, 
          fields = { 
           @FieldResult(name = "totalAuthentications", column = "totalAuthentications"),
           @FieldResult(name = "regionName", column = "regionName"),
           @FieldResult(name = "regionWiseGenuineCount", column = "regionWiseGenuineCount"),
           @FieldResult(name = "regionWiseSuspectedCount", column = "regionWiseSuspectedCount"),
           @FieldResult(name = "regionWiseTotalCount", column = "regionWiseTotalCount"),
           @FieldResult(name = "genuineEntitiesCount", column = "genuineEntitiesCount"),
           @FieldResult(name = "suspectedEntitiesCount", column = "suspectedEntitiesCount"),
           @FieldResult(name = "totalEntitiesCount", column = "totalEntitiesCount")
        }) })
public class DimAuthentication {

    @Id
    private int id;

    @Column(name = "final_result")
    private String finalResult;

    private Long totalAuthentications;

    private String regionName;

    private Long regionWiseGenuineCount;

    private Long regionWiseSuspectedCount;

    private Long regionWiseTotalCount;
    
    private Long  genuineEntitiesCount;
    
    private Long  suspectedEntitiesCount;
    
    private Long  totalEntitiesCount;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFinalResult() {
        return finalResult;
    }

    public void setFinalResult(String finalResult) {
        this.finalResult = finalResult;
    }

    public Long getTotalAuthentications() {
        return (null==totalAuthentications) ? 0L :totalAuthentications ;
    }

    public void setTotalAuthentications(Long totalAuthentications) {
        this.totalAuthentications = totalAuthentications;
    }

    public String getRegionName() {
        return regionName;
    }

    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }

    public Long getRegionWiseGenuineCount() {
        return regionWiseGenuineCount;
    }

    public void setRegionWiseGenuineCount(Long regionWiseGenuineCount) {
        this.regionWiseGenuineCount = regionWiseGenuineCount;
    }

    public Long getRegionWiseSuspectedCount() {
        return regionWiseSuspectedCount;
    }

    public void setRegionWiseSuspectedCount(Long regionWiseSuspectedCount) {
        this.regionWiseSuspectedCount = regionWiseSuspectedCount;
    }

    public Long getRegionWiseTotalCount() {
        return regionWiseTotalCount;
    }

    public void setRegionWiseTotalCount(Long regionWiseTotalCount) {
        this.regionWiseTotalCount = regionWiseTotalCount;
    }

	public Long getGenuineEntitiesCount() {
		return genuineEntitiesCount;
	}

	public void setGenuineEntitiesCount(Long genuineEntitiesCount) {
		this.genuineEntitiesCount = genuineEntitiesCount;
	}

	public Long getSuspectedEntitiesCount() {
		return suspectedEntitiesCount;
	}

	public void setSuspectedEntitiesCount(Long suspectedEntitiesCount) {
		this.suspectedEntitiesCount = suspectedEntitiesCount;
	}

	public Long getTotalEntitiesCount() {
		return totalEntitiesCount;
	}

	public void setTotalEntitiesCount(Long totalEntitiesCount) {
		this.totalEntitiesCount = totalEntitiesCount;
	}
 
 
    
}