package com.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Server extends Thread{
	private static String host = "127.0.0.1";
	private static int port = 8081;
	protected Socket echoSocket;
	private ServerSocket echoServer;
	private SocketAddress localaddr;
	private PrintWriter out;
	private BufferedReader in;
	
	private static Server singleton = new Server();

	public Server() {
		try {
			this.echoServer=new ServerSocket();
			this.localaddr = new InetSocketAddress(host, port);
			this.echoServer.bind(localaddr);
		} catch (IOException e) {
			System.out.println("error en la capa de negocio");
		}

	}
	
	public static Server getInstance() {
		return singleton;
	}
	
	public Server(Socket socket) throws IOException {
		this.echoSocket = socket;
		this.out=new PrintWriter(this.echoSocket.getOutputStream(), true);
		this.in = new BufferedReader(new InputStreamReader(this.echoSocket.getInputStream()));
        System.out.println("Nuevo cliente conectado desde: " + socket.getInetAddress().getHostAddress());
       // start();		
	}
	
	public void run() {
        try {
    		in = new BufferedReader(new InputStreamReader(this.echoSocket.getInputStream()));
    		out = new PrintWriter(this.echoSocket.getOutputStream(), true);
            String request;
            while ((request = in.readLine()) != null) {
                System.out.println("Mensaje recibido:" + request);
                //request += '\n';
                out.println(request);
            }

        } catch (IOException ex) {
            System.out.println("No se pueden opterner transmisiones del cliente: "+echoSocket.getInetAddress().getHostAddress());
        } finally {
            try {
                in.close();
                desconectar();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
		
	}

	public void clear() throws IOException {
		this.echoSocket.close();
	}

	public Socket conectar() throws IOException {
		return this.echoServer.accept();
	}



	public void desconectar() throws IOException {
		System.out.println("desconectado");
		this.out.println(ipCliente() + " se ha desconectado.");
		this.echoSocket.close();
		this.out.close();
	}

	public void enviarDato(String request) throws IOException {
		// this.out.flush();
		this.out.println(request);
		// System.out.print("request: " + request);
	}

	public String recibirDato() throws IOException {
		String response;		
		response = this.in.readLine();
		// System.out.print("response: " + response);
		return response;
	}

	
	
	public Socket getEchoSocket() {
		return echoSocket;
	}

	public ServerSocket getEchoServer() {
		return echoServer;
	}

	public String ipCliente() throws SocketException {
		String ip = "S/D";
		String linea = adpatadorEthernet().get(7);
		ip = linea.substring(linea.lastIndexOf(':') + 1).substring(1);
		ip = ip.substring(0, ip.lastIndexOf('('));
		return ip;
	}

	public static ArrayList<String> adpatadorEthernet() {
		ArrayList<String> lista = new ArrayList<String>();
		boolean adaptador = false;
		int i = 0;
		try {

			Process start = Runtime.getRuntime().exec("ipconfig /all");
			BufferedReader r = new BufferedReader(new InputStreamReader(start.getInputStream()));
			String line = null;
			while ((line = r.readLine()) != null) {
				if (!line.isEmpty()) {

					if (line.compareTo("Adaptador de Ethernet Ethernet:") == 0
							|| line.compareTo("Adaptador de Ethernet Ethernet 2:") == 0) {
						adaptador = true;
						i++;
					}

					if (i > 0 && i < 18 && adaptador == true) {
						i++;
						lista.add(line);
					}
				}
			}

		} catch (

		IOException e) {
			e.printStackTrace();
		}
		return lista;
	}

}
