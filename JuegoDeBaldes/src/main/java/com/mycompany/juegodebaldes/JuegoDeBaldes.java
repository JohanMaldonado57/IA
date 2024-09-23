/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juegodebaldes;

/**
 *
 * @author johan
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.ArrayList;
import java.util.Collections;

public class JuegoDeBaldes extends JFrame {
    private JTextField capacidad1Field;
    private JTextField capacidad2Field;
    private JTextField objetivoField;
    private JTextArea resultadoArea;
    private JLabel balde1Label;
    private JLabel balde2Label;
    private JuegoBaldes juego;

    public JuegoDeBaldes() {
        setTitle("Juego de Baldes de Agua");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        inputPanel.add(new JLabel("Capacidad del Balde 1:"));
        capacidad1Field = new JTextField();
        inputPanel.add(capacidad1Field);

        inputPanel.add(new JLabel("Capacidad del Balde 2:"));
        capacidad2Field = new JTextField();
        inputPanel.add(capacidad2Field);

        inputPanel.add(new JLabel("Objetivo:"));
        objetivoField = new JTextField();
        inputPanel.add(objetivoField);

        JButton iniciarButton = new JButton("Iniciar Juego Nuevo");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        inputPanel.add(iniciarButton);

        JButton empezarButton = new JButton("Empezar Juego");
        empezarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empezarJuego();
            }
        });
        inputPanel.add(empezarButton);

        add(inputPanel, BorderLayout.NORTH);

        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        JPanel baldesPanel = new JPanel();
        baldesPanel.setLayout(new GridLayout(1, 2));

        balde1Label = new JLabel();
        balde2Label = new JLabel();
        baldesPanel.add(balde1Label);
        baldesPanel.add(balde2Label);

        add(baldesPanel, BorderLayout.SOUTH);
    }

    private void iniciarJuego() {
        int capacidad1 = Integer.parseInt(capacidad1Field.getText());
        int capacidad2 = Integer.parseInt(capacidad2Field.getText());
        int objetivo = Integer.parseInt(objetivoField.getText());

        if (capacidad1 == capacidad2 || objetivo == capacidad1 || objetivo == capacidad2 || objetivo >= capacidad1 + capacidad2 || objetivo < Math.min(capacidad1, capacidad2)) {
            JOptionPane.showMessageDialog(this, "Valores no válidos. Intenta de nuevo.");
        } else {
            juego = new JuegoBaldes(capacidad1, capacidad2, objetivo);
            juego.setGUI(this);
            resultadoArea.setText("");
            actualizarBaldes(0, 0);
        }
    }

    private void empezarJuego() {
        if (juego != null) {
            juego.resolver();
        }
    }

    public void mostrarSolucion(java.util.List<Estado> camino) {
        StringBuilder resultado = new StringBuilder("Solución encontrada en " + (camino.size() - 1) + " movimientos:\n");
        for (Estado estado : camino) {
            resultado.append(estado.getAccion()).append(" -> Balde 1: ").append(estado.getCantidadBalde1()).append(" litros, Balde 2: ").append(estado.getCantidadBalde2()).append(" litros\n");
            actualizarBaldes(estado.getCantidadBalde1(), estado.getCantidadBalde2());
        }
        resultadoArea.setText(resultado.toString());
    }

    private void actualizarBaldes(int cantidadBalde1, int cantidadBalde2) {
        balde1Label.setIcon(new ImageIcon("balde" + cantidadBalde1 + ".png"));
        balde2Label.setIcon(new ImageIcon("balde" + cantidadBalde2 + ".png"));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoDeBaldes().setVisible(true);
            }
        });
    }
}

