package com.test;

import java.io.IOException;
import java.net.URISyntaxException;

import com.controlador.AppController;
import com.funciones.Funciones;
import com.negocio.Server;

public class testUser {
	public static void main(String[] args) throws IOException, URISyntaxException {
		// TODO Auto-generated method stub
		System.out.println("Test User password");

		Funciones aC = new Funciones();
		if ( aC.validarLogin("admin", "admin")) {
			System.out.println("usuario encontrado");
		}
		else
			System.out.println("usuario NO encontrado");
		
        
        //server.enviarDato("en sesion-");
       // new Server(server.conectar());
	}
	  
}

