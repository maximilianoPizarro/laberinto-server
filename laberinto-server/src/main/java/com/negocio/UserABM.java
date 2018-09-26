package com.negocio;


import com.modelo.User;
import com.rest.UserRest;

public class UserABM {
	
	private UserRest restA=new UserRest();
	
	public User existeUsuario(String user, String pass) throws Exception  {
		User respuesta=restA.existeUsuario(user, pass);

		if (respuesta == null) {
			throw new Exception("Usuario o contraseña incorrectos.");
		}
		return respuesta;
	}
	
	public User traerUser(int idUser) throws Exception {
		User respuesta=restA.traerUser(idUser);
		if (respuesta == null) {
			throw new Exception("No existe Usuario con ese id");
		}
		return respuesta;
		}
	
	
}
