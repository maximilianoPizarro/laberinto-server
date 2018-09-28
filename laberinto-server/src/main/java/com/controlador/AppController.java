/*
 * Copyright (c) 2011, 2012 Oracle and/or its affiliates.
 * All rights reserved. Use is subject to license terms.
 *
 * This file is available and licensed under the following license:
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *  - Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 *  - Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in
 *    the documentation and/or other materials provided with the distribution.
 *  - Neither the name of Oracle nor the names of its
 *    contributors may be used to endorse or promote products derived
 *    from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT
 * LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
 * A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT
 * OWNER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
 * LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE,
 * DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY
 * THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.controlador;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.concurrent.Semaphore;

import javax.swing.JOptionPane;

import com.modelo.Laberinto;
import com.modelo.User;
import com.negocio.Server;
import com.negocio.Cliente;
import com.negocio.Facade;
import com.negocio.UserABM;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

public class AppController extends Thread implements Initializable {

	@FXML
	private Text actiontarget;

	@FXML
	private Text titulo;

	@FXML
	private Label usuarioLabel;

	@FXML
	private Text username;

	@FXML
	private TextField usuarioText;

	@FXML
	private PasswordField passwordText;

	@FXML
	private Label errorLogin;

	@FXML
	private Label copyright;

	@FXML
	private ListView<String> list = new ListView<String>();

	@FXML
	private Pane root;

	@FXML
	private AnchorPane container;

	@FXML
	private TextArea laberinto;

	private ArrayList<String> buffer = new ArrayList<String>();

	@FXML
	protected void handleSubmitButtonAction(ActionEvent event) {

	}

	@Override
	public void initialize(URL url, ResourceBundle rb) { // TODO }
		Laberinto l = new Laberinto();
		try {
			l.rellenarLaberinto();
			laberinto.setText(l.dibujarString());
		} catch (URISyntaxException e) {
			System.out.println("error al cargar el archivo");
		}
		//list.setItems(FXCollections.observableArrayList(buffer));
		start();



	}

	@Override
	public void run() {
		//errorLogin.setText("");
		Server server = Server.getInstance();

		try {		
			while (server.getEchoServer().isBound()) {
				 Cliente nuevoCliente = new Cliente(server.conectar(),server.cantidadDeClientes); 
		         Thread proceso = new Thread(nuevoCliente); 
		         Server.clientes.add(nuevoCliente); //lista de clientes del servidor
		         server.cantidadDeClientes++; 	
		         System.out.println("clientes: "+server.cantidadDeClientes);
		         proceso.start();
		 		Platform.runLater(new Runnable(){
				     @Override
		            public void run() {
							list.setItems(FXCollections.observableArrayList(server.getClientesString()));
		            }
				}
				);
				
			}

		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	protected void salir(ActionEvent event) {

		Stage stage = (Stage) root.getScene().getWindow();
		stage.close();
		System.exit(1);

	}

	@FXML
	protected void acercaDe(ActionEvent event) {
		copyright.setVisible(true);
		errorLogin.setVisible(true);
		errorLogin.setText("UNLa 1.0");
		errorLogin.setAlignment(Pos.CENTER);
		copyright.setText("Universidad Nacional de Lan�s, Redes y Comunicaci�nes 2018");
		copyright.setAlignment(Pos.CENTER);
	}

}

class Actualizar implements Runnable{
	
	 private static final Semaphore DISPONIBILIDAD = new Semaphore(1);
	
	 private final ListView<String> list;
	 private final ArrayList<String> buffer;
	
	public Actualizar(ListView<String> list,ArrayList<String> buffer){
		this.list=list;
		this.buffer=buffer;		
		
	}
	
	@Override
	public void run(){
		try {
			DISPONIBILIDAD.acquire();
			list.setItems(FXCollections.observableArrayList(buffer));
			DISPONIBILIDAD.release();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
	}
	
}
