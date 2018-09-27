package com.test;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.negocio.Server;

public class TestServer {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		System.out.println("Server Socket");
        Server server = Server.getInstance();

    /*    while (true) {
        	new Server(server.conectar());
        }
    */
        Server cliente= new Server(server.conectar());
        while(true){
        cliente.enviarDato("en sesion");
        System.out.println(cliente.recibirDato());
        }
        //server.enviarDato("en sesion-");
       // new Server(server.conectar());
	}
	  
}
