package com.virtusa.jsm.dto;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonProperty;

@Component
public class VaildatingDTO {
   
    @JsonProperty
    private boolean validStatus;

	public VaildatingDTO() {
		super();
	}

	public VaildatingDTO(boolean validStatus) {
		super();
		this.validStatus = validStatus;
	}

	public boolean isValidStatus() {
		return validStatus;
	}

	public void setValidStatus(boolean validStatus) {
		this.validStatus = validStatus;
	}

	@Override
	public String toString() {
		return "VaildatingDTO [validStatus=" + validStatus + "]";
	}
	

    
    
}
