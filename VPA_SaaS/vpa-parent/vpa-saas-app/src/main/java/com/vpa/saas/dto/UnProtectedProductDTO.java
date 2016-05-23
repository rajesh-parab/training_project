package com.vpa.saas.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vpa.core.dws.models.UnprotectedProductViewAll;

public class UnProtectedProductDTO {

	private int unProtectedProductPercentage;

	private Long totalRevenueAtRisk;

	private int totalProduct;

	private int totalUnprotectedProduct;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yy",timezone="UTC")
	private Date glpDate;

	private List<Top3ProductDTO> top3UnProtectedProduct;

	private List<UnprotectedProductViewAll> viewAll;

	public int getUnProtectedProductPercentage() {
		return unProtectedProductPercentage;
	}

	public void setUnProtectedProductPercentage(int unProtectedProductPercentage) {
		this.unProtectedProductPercentage = unProtectedProductPercentage;
	}

	public Long getTotalRevenueAtRisk() {
		return totalRevenueAtRisk;
	}

	public void setTotalRevenueAtRisk(Long totalRevenueAtRisk) {
		this.totalRevenueAtRisk = totalRevenueAtRisk;
	}

	public List<UnprotectedProductViewAll> getViewAll() {
		return viewAll;
	}

	public List<Top3ProductDTO> getTop3UnProtectedProduct() {
		return top3UnProtectedProduct;
	}

	public void setTop3UnProtectedProduct(
			List<Top3ProductDTO> top3UnProtectedProduct) {
		this.top3UnProtectedProduct = top3UnProtectedProduct;
	}

	public void setViewAll(List<UnprotectedProductViewAll> viewAll) {
		this.viewAll = viewAll;
	}

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public int getTotalUnprotectedProduct() {
		return totalUnprotectedProduct;
	}

	public void setTotalUnprotectedProduct(int totalUnprotectedProduct) {
		this.totalUnprotectedProduct = totalUnprotectedProduct;
	}

	public Date getGlpDate() {
		return glpDate;
	}

	public void setGlpDate(Date glpDate) {
		this.glpDate = glpDate;
	}

}
