package com.vpa.saas.errors;

import java.util.List;

import com.vpa.saas.dto.ErrorWsDTO;

public class ErrorListWsDTO
{

	private List<ErrorWsDTO> errors;
		
	public ErrorListWsDTO()
	{
		// default constructor
	}
	
		
	public void setErrors(final List<ErrorWsDTO> errors)
	{
		this.errors = errors;
	}
	
		
	public List<ErrorWsDTO> getErrors() 
	{
		return errors;
	}
		
	
}
