package com.negocio;

import java.io.IOException;
import java.util.concurrent.Semaphore;


/**
 * Clase que utilizara el objeto <b>Semaphore</b> de Java.
 */
public class Semaforo implements Runnable {
 
    /**
     * Indica el numero de procesos que se pueden ejecutar al tiempo.
     */
    private static final Semaphore DISPONIBILIDAD = new Semaphore(1);
    /**
     * Nombre del proceso.
     */
    private final String nombre;
     
    public Semaforo(String nombre) {
        this.nombre = nombre;
    }
     
    @Override
    public void run() {
        try {
            // Solicita disponibilidad.
            DISPONIBILIDAD.acquire();
            Cliente cliente=Cliente.getInstance();
            try {
				cliente.enviarDato("Punto ("+this.nombre +")");
				System.out.println("response: "+cliente.recibirDato());
			} catch (IOException e) {
				System.err.println("error en la capa de negocio");
				System.exit(1);
			}
            // Libera disponibilidad.
            DISPONIBILIDAD.release();
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
    
}