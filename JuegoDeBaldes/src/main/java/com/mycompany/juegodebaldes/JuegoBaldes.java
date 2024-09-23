/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.mycompany.juegodebaldes;

/**
 *
 * @author johan
 */
import java.util.*;

public class JuegoBaldes {
    private Balde balde1;
    private Balde balde2;
    private int objetivo;
    private JuegoDeBaldes gui;

    public JuegoBaldes(int capacidad1, int capacidad2, int objetivo) {
        this.balde1 = new Balde(capacidad1);
        this.balde2 = new Balde(capacidad2);
        this.objetivo = objetivo;
    }

    public void setGUI(JuegoDeBaldes gui) {
        this.gui = gui;
    }

    public void resolver() {
        Set<Estado> visitados = new HashSet<>();
        Queue<java.util.List<Estado>> cola = new LinkedList<>();

        Estado estadoInicial = new Estado(balde1.getCantidadActual(), balde2.getCantidadActual(), "Inicio");
        cola.add(Collections.singletonList(estadoInicial));
        visitados.add(estadoInicial);

        while (!cola.isEmpty()) {
            java.util.List<Estado> camino = cola.poll();
            Estado estadoActual = camino.get(camino.size() - 1);

            if (estadoActual.getCantidadBalde1() == objetivo || estadoActual.getCantidadBalde2() == objetivo) {
                if (gui != null) {
                    gui.mostrarSolucion(camino);
                }
                return;
            }

            for (Estado siguienteEstado : generarSiguientesEstados(estadoActual)) {
                if (!visitados.contains(siguienteEstado)) {
                    visitados.add(siguienteEstado);
                    java.util.List<Estado> nuevoCamino = new ArrayList<>(camino);
                    nuevoCamino.add(siguienteEstado);
                    cola.add(nuevoCamino);
                }
            }
        }

        if (gui != null) {
            gui.mostrarSolucion(Collections.singletonList(new Estado(0, 0, "No se pudo alcanzar el objetivo.")));
        }
    }

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

        return siguientesEstados;
    }
}

