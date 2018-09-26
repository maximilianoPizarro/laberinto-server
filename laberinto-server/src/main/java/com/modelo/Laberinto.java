package com.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

import com.test.Sprite;

public class Laberinto {
	private char[][] celdas;
	
	public  void rellenarLaberinto() throws URISyntaxException {
		this.celdas = new char[10][10]; 
		File f = new File(getClass().getResource("/views/laberinto.config").toURI().toString().substring(6));
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
	
}
