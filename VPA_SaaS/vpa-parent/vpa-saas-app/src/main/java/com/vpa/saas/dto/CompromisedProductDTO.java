package com.vpa.saas.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vpa.core.dws.models.CompromisedProductViewAll;

public class CompromisedProductDTO {

	private int totalProduct;

	private int totalCompromisedProduct;

	private Long projectedRevenueLoss;

	private int compromisedProductPercentage;

	@JsonFormat(shape=JsonFormat.Shape.STRING, pattern="dd.MM.yy",timezone="UTC")
	private Date glpDate;

	private List<Top3ProductDTO> top3CompromisedProduct;

	private List<CompromisedProductViewAll> viewAll;

	public int getTotalProduct() {
		return totalProduct;
	}

	public void setTotalProduct(int totalProduct) {
		this.totalProduct = totalProduct;
	}

	public int getTotalCompromisedProduct() {
		return totalCompromisedProduct;
	}

	public void setTotalCompromisedProduct(int totalCompromisedProduct) {
		this.totalCompromisedProduct = totalCompromisedProduct;
	}

	public Long getProjectedRevenueLoss() {
		return projectedRevenueLoss;
	}

	public void setProjectedRevenueLoss(Long projectedRevenueLoss) {
		this.projectedRevenueLoss = projectedRevenueLoss;
	}

	public List<Top3ProductDTO> getTop3CompromisedProduct() {
		return top3CompromisedProduct;
	}

	public void setTop3CompromisedProduct(
			List<Top3ProductDTO> top3CompromisedProduct) {
		this.top3CompromisedProduct = top3CompromisedProduct;
	}

	public List<CompromisedProductViewAll> getViewAll() {
		return viewAll;
	}

	public void setViewAll(List<CompromisedProductViewAll> viewAll) {
		this.viewAll = viewAll;
	}

	public int getCompromisedProductPercentage() {
		return compromisedProductPercentage;
	}

	public void setCompromisedProductPercentage(int compromisedProductPercentage) {
		this.compromisedProductPercentage = compromisedProductPercentage;
	}

	public Date getGlpDate() {
		return glpDate;
	}

	public void setGlpDate(Date glpDate) {
		this.glpDate = glpDate;
	}

}
