/*PROGRAMA 4 NIM

INTEGRANTES:
MALDONADO ARRIETA JOHAN URIEL
TENA RODRIGUEZ EDSON ALEJANDRO

INTELIGENCIA ARTIFICIAL 1907
*/
package com.mycompany.juegodebaldes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * La clase JuegoDeBaldes representa la interfaz gráfica del usuario (GUI) para
 * el juego de los baldes de agua. Permite al usuario ingresar las capacidades
 * de los baldes y el objetivo, iniciar el juego y visualizar la solución.
 */
public class JuegoDeBaldes extends JFrame {
    // Campos de texto para las capacidades de los baldes y el objetivo
    private JTextField capacidad1Field;
    private JTextField capacidad2Field;
    private JTextField objetivoField;
    // Área de texto para mostrar el resultado
    private JTextArea resultadoArea;
    // Etiquetas para mostrar el estado actual de los baldes
    private JLabel balde1Label;
    private JLabel balde2Label;
    // Instancia del juego
    private JuegoBaldes juego;

    /**
     * Constructor de JuegoDeBaldes. Inicializa los componentes gráficos y
     * configura la interfaz de usuario.
     */
    public JuegoDeBaldes() {
        setTitle("Juego de Baldes de Agua");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel de entrada de datos
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(4, 2));

        // Campos para ingresar las capacidades y el objetivo
        inputPanel.add(new JLabel("Capacidad del Balde 1:"));
        capacidad1Field = new JTextField();
        inputPanel.add(capacidad1Field);

        inputPanel.add(new JLabel("Capacidad del Balde 2:"));
        capacidad2Field = new JTextField();
        inputPanel.add(capacidad2Field);

        inputPanel.add(new JLabel("Objetivo:"));
        objetivoField = new JTextField();
        inputPanel.add(objetivoField);

        // Botón para iniciar el juego
        JButton iniciarButton = new JButton("Guardar Variables");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        inputPanel.add(iniciarButton);

        // Botón para comenzar la solución
        JButton empezarButton = new JButton("Empezar Juego");
        empezarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                empezarJuego();
            }
        });
        inputPanel.add(empezarButton);

        add(inputPanel, BorderLayout.NORTH);

        // Área para mostrar los resultados
        resultadoArea = new JTextArea();
        resultadoArea.setEditable(false);
        add(new JScrollPane(resultadoArea), BorderLayout.CENTER);

        // Panel para mostrar el estado de los baldes
        JPanel baldesPanel = new JPanel();
        baldesPanel.setLayout(new GridLayout(1, 2));

        balde1Label = new JLabel("Balde 1: 0 litros");
        balde2Label = new JLabel("Balde 2: 0 litros");
        baldesPanel.add(balde1Label);
        baldesPanel.add(balde2Label);

        add(baldesPanel, BorderLayout.SOUTH);
    }

    /**
     * Método para iniciar el juego. Verifica las entradas del usuario y
     * crea una nueva instancia de JuegoBaldes.
     */
    private void iniciarJuego() {
        int capacidad1 = Integer.parseInt(capacidad1Field.getText());
        int capacidad2 = Integer.parseInt(capacidad2Field.getText());
        int objetivo = Integer.parseInt(objetivoField.getText());

        // Verifica si los valores ingresados son válidos
        if (capacidad1 == capacidad2 || objetivo == capacidad1 || objetivo == capacidad2 || objetivo >= capacidad1 + capacidad2 || objetivo < Math.min(capacidad1, capacidad2)) {
            JOptionPane.showMessageDialog(this, "Valores no válidos. Intenta de nuevo.");
        } else {
            juego = new JuegoBaldes(capacidad1, capacidad2, objetivo);
            juego.setGUI(this);
            resultadoArea.setText("");
            actualizarBaldes(0, 0);
        }
    }

    /**
     * Método para comenzar la búsqueda de la solución del juego en un nuevo hilo.
     */
    private void empezarJuego() {
        if (juego != null) {
            new Thread(() -> juego.resolver()).start();
        }
    }

    /**
     * Muestra la solución encontrada paso a paso en la interfaz gráfica.
     * 
     * @param camino Lista de estados que representan la secuencia de movimientos
     *               para resolver el juego.
     */
    public void mostrarSolucion(java.util.List<Estado> camino) {
        StringBuilder resultado = new StringBuilder("Solución encontrada en " + (camino.size() - 1) + " movimientos:\n");
        for (Estado estado : camino) {
            resultado.append(estado.getAccion()).append(" -> Balde 1: ").append(estado.getCantidadBalde1()).append(" litros, Balde 2: ").append(estado.getCantidadBalde2()).append(" litros\n");
            actualizarBaldes(estado.getCantidadBalde1(), estado.getCantidadBalde2());
            try {
                Thread.sleep(1000); // Simula el "pensamiento" de la IA con un retraso de 1 segundo
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        resultadoArea.setText(resultado.toString());
    }

    /**
     * Actualiza las etiquetas que muestran las cantidades actuales de los baldes.
     * 
     * @param cantidadBalde1 Cantidad de agua en el balde 1.
     * @param cantidadBalde2 Cantidad de agua en el balde 2.
     */
    private void actualizarBaldes(int cantidadBalde1, int cantidadBalde2) {
        balde1Label.setText("Balde 1: " + cantidadBalde1 + " litros");
        balde2Label.setText("Balde 2: " + cantidadBalde2 + " litros");
    }

    /**
     * Método principal para iniciar la aplicación GUI.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoDeBaldes().setVisible(true);
            }
        });
    }
}

