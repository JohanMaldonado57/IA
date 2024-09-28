/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

import java.util.*;

/**
 * Clase principal que implementa la lógica del juego de los baldes de agua.
 * El objetivo es medir una cantidad específica de agua usando dos baldes de capacidades dadas.
 */
public class JuegoBaldes {
    private Balde balde1; // El primer balde
    private Balde balde2; // El segundo balde
    private int objetivo; // La cantidad de agua objetivo
    private JuegoDeBaldes gui; // Interfaz gráfica opcional para mostrar los resultados

    /**
     * Constructor del juego de los baldes.
     * @param capacidad1 Capacidad del balde 1.
     * @param capacidad2 Capacidad del balde 2.
     * @param objetivo Cantidad de agua objetivo que se quiere medir.
     */
    public JuegoBaldes(int capacidad1, int capacidad2, int objetivo) {
        this.balde1 = new Balde(capacidad1);
        this.balde2 = new Balde(capacidad2);
        this.objetivo = objetivo;
    }

    /**
     * Establece la GUI para el juego de los baldes.
     * @param gui Objeto de la interfaz gráfica para mostrar la solución.
     */
    public void setGUI(JuegoDeBaldes gui) {
        this.gui = gui;
    }

    /**
     * Resuelve el problema de alcanzar la cantidad objetivo de agua.
     * Utiliza un algoritmo de búsqueda en anchura (BFS) para explorar todos los estados posibles.
     * Si encuentra una solución, la muestra en la GUI.
     */
    public void resolver() {
        Set<Estado> visitados = new HashSet<>(); // Conjunto para mantener un registro de los estados visitados
        Queue<java.util.List<Estado>> cola = new LinkedList<>(); // Cola para realizar la búsqueda por niveles

        // Estado inicial con los baldes vacíos
        Estado estadoInicial = new Estado(balde1.getCantidadActual(), balde2.getCantidadActual(), "Inicio");
        cola.add(Collections.singletonList(estadoInicial)); // Agregar el estado inicial a la cola
        visitados.add(estadoInicial); // Marcar el estado inicial como visitado

        // Algoritmo BFS
        while (!cola.isEmpty()) {
            java.util.List<Estado> camino = cola.poll(); // Obtener el camino actual desde la cola
            Estado estadoActual = camino.get(camino.size() - 1); // Estado actual (último del camino)

            // Verificar si se alcanzó el objetivo
            if ((estadoActual.getCantidadBalde1() == objetivo && estadoActual.getCantidadBalde2() == 0) ||
                (estadoActual.getCantidadBalde2() == objetivo && estadoActual.getCantidadBalde1() == 0)) {
                if (gui != null) {
                    gui.mostrarSolucion(camino); // Mostrar la solución si se encuentra
                }
                return; // Terminar el método si se alcanzó el objetivo
            }

            // Generar todos los posibles estados a partir del estado actual
            for (Estado siguienteEstado : generarSiguientesEstados(estadoActual)) {
                if (!visitados.contains(siguienteEstado)) { // Solo explorar estados no visitados
                    visitados.add(siguienteEstado); // Marcar como visitado
                    java.util.List<Estado> nuevoCamino = new ArrayList<>(camino); // Crear una nueva lista de estados
                    nuevoCamino.add(siguienteEstado); // Agregar el siguiente estado
                    cola.add(nuevoCamino); // Agregar el nuevo camino a la cola
                }
            }
        }

        // Si no se encuentra una solución, mostrar un mensaje indicando el fallo
        if (gui != null) {
            gui.mostrarSolucion(Collections.singletonList(new Estado(0, 0, "No se pudo alcanzar el objetivo.")));
        }
    }

    /**
     * Genera todos los posibles estados válidos a partir de un estado actual.
     * @param estado Estado actual de los baldes.
     * @return Una lista de los posibles estados siguientes.
     */
    private java.util.List<Estado> generarSiguientesEstados(Estado estado) {
        java.util.List<Estado> siguientesEstados = new ArrayList<>();

        // Llenar balde 1
        if (estado.getCantidadBalde1() < balde1.getCapacidad()) {
            siguientesEstados.add(new Estado(balde1.getCapacidad(), estado.getCantidadBalde2(), "Llenar balde 1"));
        }

        // Llenar balde 2
        if (estado.getCantidadBalde2() < balde2.getCapacidad()) {
            siguientesEstados.add(new Estado(estado.getCantidadBalde1(), balde2.getCapacidad(), "Llenar balde 2"));
        }

        // Vaciar balde 1
        if (estado.getCantidadBalde1() > 0) {
            siguientesEstados.add(new Estado(0, estado.getCantidadBalde2(), "Vaciar balde 1"));
        }

        // Vaciar balde 2
        if (estado.getCantidadBalde2() > 0) {
            siguientesEstados.add(new Estado(estado.getCantidadBalde1(), 0, "Vaciar balde 2"));
        }

        // Transferir de balde 1 a balde 2
        if (estado.getCantidadBalde1() > 0 && estado.getCantidadBalde2() < balde2.getCapacidad()) {
            int cantidadTransferible = Math.min(estado.getCantidadBalde1(), balde2.getCapacidad() - estado.getCantidadBalde2());
            siguientesEstados.add(new Estado(estado.getCantidadBalde1() - cantidadTransferible, estado.getCantidadBalde2() + cantidadTransferible, "Transferir de balde 1 a balde 2"));
        }

        // Transferir de balde 2 a balde 1
        if (estado.getCantidadBalde2() > 0 && estado.getCantidadBalde1() < balde1.getCapacidad()) {
            int cantidadTransferible = Math.min(estado.getCantidadBalde2(), balde1.getCapacidad() - estado.getCantidadBalde1());
            siguientesEstados.add(new Estado(estado.getCantidadBalde1() + cantidadTransferible, estado.getCantidadBalde2() - cantidadTransferible, "Transferir de balde 2 a balde 1"));
        }

        return siguientesEstados; // Retorna todos los estados generados
    }
}


