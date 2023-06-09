package com.moviebookingapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 
 * class whose fields are used for the response for token
 *         validation. When we call the method getValidity() in
 *         {@link AuthController} then it will return {@link AuthResponse} type
 *         object
 */

public class AuthResponse {
	/**
	 * This is a private field which is used to represent the userid for user login
	 * credentials. The data type is String.
	 */
	private String uid;
	/**
	 * This is a private field which is used to represent whethet the token is valid
	 * or not. The data type is boolean.
	 */
	private boolean isValid;
	
	
	
	public AuthResponse() {
		super();
	}
	public AuthResponse(String uid, boolean isValid) {
		super();
		this.uid = uid;
		this.isValid = isValid;
	}
	public String getUid() {
		return uid;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	
	
	}
