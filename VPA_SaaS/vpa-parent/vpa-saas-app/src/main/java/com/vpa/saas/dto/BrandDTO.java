package com.vpa.saas.dto;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@JsonInclude(Include.NON_EMPTY)
public class BrandDTO extends BaseDTO {
 
    private Long id;
 
    private String description;

    private byte enable;

    private String name;
    
    private List<ProductDTO> products = new ArrayList<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public byte getEnable() {
        return enable;
    }

    public void setEnable(byte enable) {
        this.enable = enable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public List<ProductDTO> getProducts() {
		return products;
	}

	public void setProducts(List<ProductDTO> products) {
		this.products = products;
	}
	public void addProduct(Long producId,String productName) {
		ProductDTO product = new ProductDTO();
		product.setId(producId);
		product.setName(productName);
		products.add(product);
	}

	@Override
	public String toString() {
		return "BrandDTO [id=" + id + ", description=" + description
				+ ", enable=" + enable + ", name=" + name + ", products="
				+ products + "]";
	}

	

}
