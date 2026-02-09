package com.artichourey.ecommerce.userservice.exception;

public class ResourceNotFoundException extends RuntimeException {

	public ResourceNotFoundException(String msg) {
		super(msg);
		
	}
	public ResourceNotFoundException() {
		super();
		
	}

}
