/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

/**
 *
 * @author johan
 */
public class Balde {
    private int capacidad;
    private int cantidadActual;

    public Balde(int capacidad) {
        this.capacidad = capacidad;
        this.cantidadActual = 0;
    }

    public int getCapacidad() {
        return capacidad;
    }

    public int getCantidadActual() {
        return cantidadActual;
    }

    public void llenar() {
        cantidadActual = capacidad;
    }

    public void vaciar() {
        cantidadActual = 0;
    }

    public void transferirA(Balde otroBalde) {
        int cantidadTransferible = Math.min(cantidadActual, otroBalde.capacidad - otroBalde.cantidadActual);
        cantidadActual -= cantidadTransferible;
        otroBalde.cantidadActual += cantidadTransferible;
    }

    public void verificar() {
        System.out.println("Balde de " + capacidad + " litros tiene " + cantidadActual + " litros.");
    }
}

