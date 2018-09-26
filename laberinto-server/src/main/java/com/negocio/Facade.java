package com.negocio;


public class Facade {
	
	public UserABM getUserABM() {
		return new UserABM();
	}
	
}