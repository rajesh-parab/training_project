package com.vpa.saas.dto;

public enum ScanResultType {
	//Note if there is a change in table design or records in scanresulttype table
	// This enum may need to change
    Genuine(1), Counterfeit(2),Theft(3),Serial_Number_Tampered(4),Counterfeit_Upgrade(5),Incomplete(6);

    private Integer id;

    private ScanResultType(Integer id) {
        this.id = id;
    }
    public boolean IsScanComplete(Integer id){
    	return !this.id.equals(id);
    }
    public static ScanResultType getType(Integer id) {

        if (id == null) {
            return null;
        }

        for (ScanResultType scanResultType : ScanResultType.values()) {
            if (id.equals(scanResultType.getId())) {
                return scanResultType;
            }
        }
        throw new IllegalArgumentException("No matching type for id " + id);
    }

    public Integer getId() {
        return id;
    }

}
