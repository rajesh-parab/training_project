package com.vpa.core.bo;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

/**
 * This class convert the database object to business object which will be send
 * to UI in JSON format.
 * 
 * @author NS60097
 *
 */
public class RevenueLossBO {

	private final Long revenueLoss;

	private final Long projectedRevenueLoss;

	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd.MM.yy", timezone = "UTC")
	private final Date glpUpdatedDate;

	public RevenueLossBO(Long revenueLoss, Long projectedRevenueLoss,
			Date glpUpdatedDate) {
		this.revenueLoss = revenueLoss;
		this.projectedRevenueLoss = projectedRevenueLoss;
		this.glpUpdatedDate = glpUpdatedDate;
	}

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public Long getProjectedRevenueLoss() {
		return projectedRevenueLoss;
	}

	public Date getGlpUpdatedDate() {
		return glpUpdatedDate;
	}

	public static class Builder {
		private Long revenueLoss;

		private Long projectedRevenueLoss;

		private Date glpUpdatedDate;
		
		private Builder(){
			
		}
		
		public static  Builder revenueLossBO(){
			return new Builder();
		}
		
		public Builder withrevenueLoss(Long revenueLoss){
			this.revenueLoss = revenueLoss;
			return this;
		}
		public Builder withProjectedRevenueLoss(Long projectedRevenueLoss){
			this.projectedRevenueLoss=projectedRevenueLoss;
			return this;
		}
		
		public Builder withglpDate(Date glpDate){
			this.glpUpdatedDate= glpDate;
			return this;
		}
		
		public RevenueLossBO build(){
			return new RevenueLossBO(revenueLoss, projectedRevenueLoss, glpUpdatedDate);
		}
	}

}
