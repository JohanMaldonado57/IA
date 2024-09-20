/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */

package com.mycompany.juegodelnim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class JuegoDelNim extends JFrame {
    private int cerillos = 40;
    private boolean turnoJugador = true;
    private int modoDeJuego;
    private int nivelDificultad;
    private int[] jugadasJugador = new int[cerillos];
    private Random random = new Random();

    private JLabel cerillosLabel;
    private JTextField inputField;
    private JButton tomarButton;
    private JButton iniciarButton;
    private JComboBox<String> modoComboBox;
    private JComboBox<String> dificultadComboBox;
    private JPanel cerillosPanel;
    private JPanel controlPanel;

    public JuegoDelNim() {
        setTitle("Juego del Nim");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para centrar el texto
        JPanel cerillosLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cerillosLabel = new JLabel("Cerillos restantes: " + cerillos);
        cerillosLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ajustar el tamaño de la fuente
        cerillosLabelPanel.add(cerillosLabel);
        add(cerillosLabelPanel, BorderLayout.NORTH);

        cerillosPanel = new JPanel();
        cerillosPanel.setLayout(new GridLayout(5, 8)); // Ajusta el layout según sea necesario
        add(cerillosPanel, BorderLayout.CENTER);

        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        inputField = new JTextField(5);
        controlPanel.add(inputField);

        tomarButton = new JButton("Tomar cerillos");
        tomarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarCerillos();
            }
        });
        controlPanel.add(tomarButton);
        
        iniciarButton = new JButton("Iniciar Juego");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego();
            }
        });
        controlPanel.add(iniciarButton);

        String[] modos = {"Jugador vs Jugador", "Jugador vs IA"};
        modoComboBox = new JComboBox<>(modos);
        controlPanel.add(modoComboBox);

        String[] dificultades = {"Fácil", "Medio", "Difícil"};
        dificultadComboBox = new JComboBox<>(dificultades);
        controlPanel.add(dificultadComboBox);

        add(controlPanel, BorderLayout.SOUTH);

        actualizarCerillos();
    }

    private void iniciarJuego() {
        cerillos = 40;
        cerillosLabel.setText("Cerillos restantes: " + cerillos);
        inputField.setText("");
        modoDeJuego = modoComboBox.getSelectedIndex() + 1;
        nivelDificultad = dificultadComboBox.getSelectedIndex() + 1;
        turnoJugador = true;

        if (modoDeJuego == 2) {
            int quienInicia = JOptionPane.showOptionDialog(this, "¿Quién debería comenzar?", "Inicio del Juego",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Jugador", "IA"}, "Jugador");
            turnoJugador = quienInicia == 0;

            if (!turnoJugador) {
                tomarTurnoIA();
            }
        }

        actualizarCerillos();
    }

    private void tomarCerillos() {
        int cerillosTomados = Integer.parseInt(inputField.getText());
        if (cerillosTomados < 1 || cerillosTomados > 5) {
            JOptionPane.showMessageDialog(this, "Número inválido. Debes tomar entre 1 y 5 cerillos.");
            return;
        }

        cerillos -= cerillosTomados;
        cerillosLabel.setText("Cerillos restantes: " + cerillos);

        if (cerillos <= 1) {
            JOptionPane.showMessageDialog(this, turnoJugador ? "¡Tú Ganas! La IA Pierde." : "¡Jugador 2 Gana! Jugador 1 pierde.");
            iniciarJuego();
            return;
        }

        turnoJugador = !turnoJugador;

        if (modoDeJuego == 2 && !turnoJugador) {
            tomarTurnoIA();
        }

        actualizarCerillos();
    }

    private void tomarTurnoIA() {
        int cerillosIA = calcularMovimientoIAAvanzada(cerillos, nivelDificultad, jugadasJugador, random);
        JOptionPane.showMessageDialog(this, "La IA toma " + cerillosIA + " cerillos.");
        cerillos -= cerillosIA;
        cerillosLabel.setText("Cerillos restantes: " + cerillos);

        if (cerillos <= 1) {
            JOptionPane.showMessageDialog(this, "¡La IA Gana! Tú pierdes ;( .");
            iniciarJuego();
            return;
        }

        turnoJugador = true;
        actualizarCerillos();
    }

    private void actualizarCerillos() {
        cerillosPanel.removeAll();
        for (int i = 0; i < cerillos; i++) {
            JLabel cerilloLabel = new JLabel(new ImageIcon(getClass().getResource("/cerillo.png")));
            cerillosPanel.add(cerilloLabel);
        }
        cerillosPanel.revalidate();
        cerillosPanel.repaint();
    }





    public static int calcularMovimientoIAAvanzada(int cerillosRestantes, int nivelDificultad, int[] jugadasJugador, Random random) {
        int cerillosIA;
        boolean hacerJugadaAleatoria = false;

        switch (nivelDificultad) {
            case 1:
                hacerJugadaAleatoria = random.nextInt(100) < 70;
                break;
            case 2:
                hacerJugadaAleatoria = random.nextInt(100) < 40;
                break;
            case 3:
                hacerJugadaAleatoria = random.nextInt(100) < 10;
                break;
            default:
                hacerJugadaAleatoria = random.nextInt(100) < 10;
        }

        if (cerillosRestantes <= 10 && cerillosRestantes > 7) {
            cerillosIA = cerillosRestantes - 7;
        } else if (cerillosRestantes == 7) {
            cerillosIA = hacerJugadaAleatoria ? random.nextInt(5) + 1 : 1;
        } else if (cerillosRestantes < 7) {
            cerillosIA = cerillosRestantes - 1;
        } else if (hacerJugadaAleatoria) {
            int ultimaJugadaJugador = cerillosRestantes > 0 ? jugadasJugador[cerillosRestantes - 1] : 0;
            boolean jugadorEsArriesgado = ultimaJugadaJugador == 5;
            boolean jugadorEsConservador = ultimaJugadaJugador <= 2;

            if (jugadorEsArriesgado) {
                cerillosIA = random.nextInt(2) + 1;
            } else if (jugadorEsConservador) {
                cerillosIA = random.nextInt(2) + 4;
            } else {
                cerillosIA = random.nextInt(5) + 1;
            }
        } else {
            int cerillosMod = cerillosRestantes % 6;
            cerillosIA = cerillosMod == 0 ? 1 : cerillosMod;
        }

        if (cerillosIA > cerillosRestantes) {
            cerillosIA = cerillosRestantes - 1;
        }

        return cerillosIA;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoDelNim().setVisible(true);
            }
        });
    }
}
