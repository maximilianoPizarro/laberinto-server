package com.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.google.gson.Gson;
import com.test.Sprite;

public class Laberinto {
	private char[][] celdas;
	
	public  void rellenarLaberinto() throws URISyntaxException {
		this.celdas = new char[10][10]; 
		File f = new File(getClass().getResource("/views/laberinto.config").toString().substring(6));
		if(!f.exists()){
			f= new File(getClass().getResource("/views/laberinto.config").toString().substring(10,(getClass().getResource("/views/laberinto.config").toString().lastIndexOf("laberinto-server-0.0.1-SNAPSHOT.jar!")))+"\\views\\laberinto.config");
		}
		//System.out.println("-->"+getClass().getResource("/views/laberinto.config").toString().substring(10, (getClass().getResource("/views/laberinto.config").toString().lastIndexOf("laberinto-server-0.0.1-SNAPSHOT.jar!"))));
				
        try  (Scanner entrada = new Scanner(f)) {
        	int i = 0;
        	int j = 0;
            while (entrada.hasNext()) { 
               String tipoCelda = entrada.next();
               this.celdas[j][i] = tipoCelda.charAt(0);
               j++;
               if ( j == 10 ) {
            	   i++;
            	   j=0;
               };
            }
        } catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

	public char[][] getCeldas() {
		return celdas;
	}

	public void setCeldas(char[][] celdas) {
		this.celdas = celdas;
	}

	public void dibujar(){
		for (int i = 0 ; i<10 ; i++) {
			for (int j = 0 ; j<10 ; j++) {
				System.out.print(celdas[j][i]); 
			}
			System.out.println();
		}
	}
	
	public String dibujarString(){
		String salida = "";
		for (int i = 0 ; i<10 ; i++) {
			for (int j = 0 ; j<10 ; j++) {
				salida=salida+celdas[j][i];
			}
			salida=salida+"\n";
		}
		return salida;
	}
	
	public String dibujarJson(){
		return new Gson().toJson(celdas);
	}
	
	public String laberintoToJson(){
		return new Gson().toJson(this);
	}
	
	public void guardarLaberinto() {
		
	}

	//public char[][] crearMatrizCercana(Punto p1) {
	public Laberinto crearMatrizCercana(Punto p1) {
		int distanciaVisible = 2;
		//char [][] matrizCercana = new char[10][10] ;
		
		Laberinto laberintoCercano = new Laberinto();
		laberintoCercano.celdas = new char[10][10];
		
		for (int i = 0; i < 10 ; i++) {
			for (int j = 0; j < 10 ; j++) {
	            if ((Math.abs((p1.getPositionX()/50) - j) < distanciaVisible) 
	            		&& (Math.abs((p1.getPositionY()/50) - i) < distanciaVisible)) {
	            	//matrizCercana[j][i] = this.celdas[j][i];
	            	laberintoCercano.getCeldas()[j][i] = this.celdas[j][i];
	            }
	            else {
	            	//matrizCercana[j][i] = 'X';
	            	laberintoCercano.getCeldas()[j][i] = 'X';
	            }
			}
		}		
		//System.out.println("matriz cercana en funcion Laberinto.crearMatrizCercana" + new Gson().toJson(matrizCercana));
		//return matrizCercana;
		return laberintoCercano;
	}
		
}
