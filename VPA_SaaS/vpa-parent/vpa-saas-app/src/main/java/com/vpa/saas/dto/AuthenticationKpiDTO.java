package com.vpa.saas.dto;

public class AuthenticationKpiDTO {

    private String authenticationType;

    private Long totalAuthetncationCount;

    private Long totalAuthenticationInLast24Hrs;

    public String getAuthenticationType() {
        return authenticationType;
    }

    public void setAuthenticationType(String authenticationType) {
        this.authenticationType = authenticationType;
    }

    public Long getTotalAuthetncationCount() {
        return totalAuthetncationCount;
    }

    public void setTotalAuthetncationCount(Long totalAuthetncationCount) {
        this.totalAuthetncationCount = totalAuthetncationCount;
    }

    public Long getTotalAuthenticationInLast24Hrs() {
        return totalAuthenticationInLast24Hrs;
    }

    public void setTotalAuthenticationInLast24Hrs(
            Long totalAuthenticationInLast24Hrs) {
        this.totalAuthenticationInLast24Hrs = totalAuthenticationInLast24Hrs;
    }

	@Override
	public String toString() {
		return "AuthenticationKpiDTO [authenticationType=" + authenticationType
				+ ", totalAuthetncationCount=" + totalAuthetncationCount
				+ ", totalAuthenticationInLast24Hrs="
				+ totalAuthenticationInLast24Hrs + "]";
	}

    
}
