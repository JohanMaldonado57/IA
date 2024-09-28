/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

/**
 * La clase Balde representa un balde con una capacidad y una cantidad actual
 * de agua. Incluye métodos para llenar, vaciar y transferir agua entre baldes.
 */
public class Balde {
    // Capacidad máxima del balde
    private int capacidad;
    // Cantidad actual de agua en el balde
    private int cantidadActual;

    /**
     * Constructor de la clase Balde.
     * 
     * @param capacidad Capacidad máxima del balde.
     */
    public Balde(int capacidad) {
        this.capacidad = capacidad;
        this.cantidadActual = 0;
    }

    /**
     * Retorna la capacidad máxima del balde.
     * 
     * @return Capacidad del balde.
     */
    public int getCapacidad() {
        return capacidad;
    }

    /**
     * Retorna la cantidad actual de agua en el balde.
     * 
     * @return Cantidad actual de agua.
     */
    public int getCantidadActual() {
        return cantidadActual;
    }

    /**
     * Llena el balde a su capacidad máxima.
     */
    public void llenar() {
        cantidadActual = capacidad;
    }

    /**
     * Vacía completamente el balde.
     */
    public void vaciar() {
        cantidadActual = 0;
    }

    /**
     * Transfiere agua de este balde a otro balde hasta que uno de los dos esté
     * lleno o vacío.
     * 
     * @param otroBalde El balde destino al que se transferirá agua.
     */
    public void transferirA(Balde otroBalde) {
        int cantidadTransferible = Math.min(cantidadActual, otroBalde.capacidad - otroBalde.cantidadActual);
        cantidadActual -= cantidadTransferible;
        otroBalde.cantidadActual += cantidadTransferible;
    }

    /**
     * Imprime en consola el estado actual del balde.
     */
    public void verificar() {
        System.out.println("Balde de " + capacidad + " litros tiene " + cantidadActual + " litros.");
    }
}

