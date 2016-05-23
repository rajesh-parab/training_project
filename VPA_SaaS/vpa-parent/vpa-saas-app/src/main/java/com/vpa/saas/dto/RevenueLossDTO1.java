package com.vpa.saas.dto;

//TODO this dto class will be override the existing revenueLoss DTo once the existing code is remove in revenue loss page.

public class RevenueLossDTO1 {

	private Long revenueLoss;

	private Long projectedRevenueLoss;

	private int glpUpdatedDate;

	public Long getRevenueLoss() {
		return revenueLoss;
	}

	public void setRevenueLoss(Long revenueLoss) {
		this.revenueLoss = revenueLoss;
	}

	public Long getProjectedRevenueLoss() {
		return projectedRevenueLoss;
	}

	public void setProjectedRevenueLoss(Long projectedRevenueLoss) {
		this.projectedRevenueLoss = projectedRevenueLoss;
	}

	public int getGlpUpdatedDate() {
		return glpUpdatedDate;
	}

	public void setGlpUpdatedDate(int glpUpdatedDate) {
		this.glpUpdatedDate = glpUpdatedDate;
	}

}
