package com.vpa.saas.dto;

/**
 * Error message
 */
public class ErrorWsDTO {

	/**
	 * Descriptive, human readable error message.<br/>
	 * <br/>
	 * <i>Generated property</i> for <code>ErrorWsDTO.message</code> property
	 * defined at extension <code>commercewebservicescommons</code>.
	 */
	private String message;
	/**
	 * <i>Generated property</i> for <code>ErrorWsDTO.reason</code> property
	 */
	private String reason;
	/**
	 * Identifier of the related object e.g. '1'.<br/>
	 * <br/>
	 * <i>Generated property</i> for <code>ErrorWsDTO.subject</code> property 
	 */
	private Integer errorCode;

	public ErrorWsDTO() {
		// default constructor
	}

	public void setMessage(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setReason(final String reason) {
		this.reason = reason;
	}

	public String getReason() {
		return reason;
	}

	public Integer getErrorCode() {
		return errorCode;
	}

	public void setErrorCode(Integer errorCode) {
		this.errorCode = errorCode;
	}



}
