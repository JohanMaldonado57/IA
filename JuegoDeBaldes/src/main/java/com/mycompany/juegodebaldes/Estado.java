/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

import java.util.Objects;

/**
 *
 * @author johan
 */
public class Estado {
    private int cantidadBalde1;
    private int cantidadBalde2;
    private String accion;

    public Estado(int cantidadBalde1, int cantidadBalde2, String accion) {
        this.cantidadBalde1 = cantidadBalde1;
        this.cantidadBalde2 = cantidadBalde2;
        this.accion = accion;
    }

    public int getCantidadBalde1() {
        return cantidadBalde1;
    }

    public int getCantidadBalde2() {
        return cantidadBalde2;
    }

    public String getAccion() {
        return accion;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estado estado = (Estado) obj;
        return cantidadBalde1 == estado.cantidadBalde1 && cantidadBalde2 == estado.cantidadBalde2;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cantidadBalde1, cantidadBalde2);
    }
}