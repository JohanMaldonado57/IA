/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

import java.util.Objects;
/**
 * La clase Estado representa un estado del juego, incluyendo las cantidades de
 * agua en los dos baldes y la acción realizada para llegar a ese estado.
 */
public class Estado {
    // Cantidad de agua en el balde 1
    private int cantidadBalde1;
    // Cantidad de agua en el balde 2
    private int cantidadBalde2;
    // Acción que produjo este estado
    private String accion;

    /**
     * Constructor de la clase Estado.
     * 
     * @param cantidadBalde1 Cantidad de agua en el balde 1.
     * @param cantidadBalde2 Cantidad de agua en el balde 2.
     * @param accion         Descripción de la acción realizada.
     */
    public Estado(int cantidadBalde1, int cantidadBalde2, String accion) {
        this.cantidadBalde1 = cantidadBalde1;
        this.cantidadBalde2 = cantidadBalde2;
        this.accion = accion;
    }

    /**
     * Retorna la cantidad de agua en el balde 1.
     * 
     * @return Cantidad de agua en el balde 1.
     */
    public int getCantidadBalde1() {
        return cantidadBalde1;
    }

    /**
     * Retorna la cantidad de agua en el balde 2.
     * 
     * @return Cantidad de agua en el balde 2.
     */
    public int getCantidadBalde2() {
        return cantidadBalde2;
    }

    /**
     * Retorna la acción que produjo este estado.
     * 
     * @return Descripción de la acción.
     */
    public String getAccion() {
        return accion;
    }

    /**
     * Compara este estado con otro estado para ver si son iguales. Dos estados
     * son iguales si las cantidades de agua en ambos baldes son iguales.
     * 
     * @param obj El objeto a comparar.
     * @return true si los estados son iguales, false de lo contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Estado estado = (Estado) obj;
        return cantidadBalde1 == estado.cantidadBalde1 && cantidadBalde2 == estado.cantidadBalde2;
    }

    /**
     * Genera un código hash basado en las cantidades de agua en los baldes.
     * 
     * @return Código hash del estado.
     */
    @Override
    public int hashCode() {
        return Objects.hash(cantidadBalde1, cantidadBalde2);
    }
}
