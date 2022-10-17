package com.virtusa.jsm.dto;

public class AuthenticationResponse {
	String email;
	String jwtAuthToken;
	public AuthenticationResponse() {
		super();
	}
	public AuthenticationResponse(String email, String jwtAuthToken) {
		super();
		this.email = email;
		this.jwtAuthToken = jwtAuthToken;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getJwtAuthToken() {
		return jwtAuthToken;
	}
	public void setJwtAuthToken(String jwtAuthToken) {
		this.jwtAuthToken = jwtAuthToken;
	}
	@Override
	public String toString() {
		return "AuthenticationResponse [email=" + email + ", jwtAuthToken=" + jwtAuthToken + "]";
	}
	
	
	
}
