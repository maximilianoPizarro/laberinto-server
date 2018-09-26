package com.negocio;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;
import java.net.SocketException;
import java.util.ArrayList;

public class Cliente {
	private static String host = "127.0.0.1";
	private static int port = 8081;
	private Socket echoSocket;
	private SocketAddress remoteaddr;
	private PrintWriter out;
	private BufferedReader in;

	private static Cliente singleton = new Cliente(host, port);

	public Cliente(String host, int port) {
		this.echoSocket = new Socket();
		this.remoteaddr = new InetSocketAddress(host, port);

	}

	public static Cliente getInstance() {
		return singleton;
	}

	
	public void clear() {
		singleton = new Cliente(host, port);
	}

	public void conectar() {
		try {
			this.echoSocket.connect(this.remoteaddr);
			enviarDato(ipCliente() + " se ha conectado.");
		} catch (IOException e) {
			System.err.println("no se pudo conectar con el servidor");
		//	System.exit(1);
		}
		System.out.println("conectado");

	}

	public boolean estaConectado() {
		return this.echoSocket.isConnected();
	}

	public void desconectar() throws IOException {
		System.out.println("desconectado");
		this.out.println(ipCliente() + " se ha desconectado.");
		this.echoSocket.close();
		this.out.close();
	}

	public void enviarDato(String request) throws IOException {
		// this.out.flush();
		this.out = new PrintWriter(this.echoSocket.getOutputStream(), true);
		this.out.println(request);
		// System.out.print("request: " + request);
	}

	public String recibirDato() throws IOException {
		String response;
		this.in = new BufferedReader(new InputStreamReader(this.echoSocket.getInputStream()));
		response = this.in.readLine();
		// System.out.print("response: " + response);
		return response;
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
