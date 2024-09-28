/*PROGRAMA 3 NIM

INTEGRANTES:
MALDONADO ARRIETA JOHAN URIEL
TENA RODRIGUEZ EDSON ALEJANDRO

INTELIGENCIA ARTIFICIAL 1907
*/

package com.mycompany.juegodelnim;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

/**
 * Esta clase implementa el juego del Nim con una interfaz gráfica.
 * Los jugadores pueden jugar en modo Jugador vs Jugador o Jugador vs IA.
 * La IA sigue una estrategia donde toma un porcentaje del total de cerillos restantes
 * con algunos ajustes de dificultad.
 */
public class JuegoDelNim extends JFrame {
    private int cerillos; // Total de cerillos en el juego
    private boolean turnoJugador = true; // Indica si es el turno del jugador
    private int modoDeJuego; // 1 para Jugador vs Jugador, 2 para Jugador vs IA
    private int[] jugadasJugador; // Registro de jugadas del jugador
    private Random random = new Random(); // Generador de números aleatorios para la IA

    // Elementos de la interfaz gráfica
    private JLabel cerillosLabel;
    private JLabel maxCerillosLabel;
    private JTextField inputField;
    private JTextField cantidadCerillosField;
    private JButton tomarButton;
    private JButton iniciarButton;
    private JComboBox<String> modoComboBox;
    private JPanel cerillosPanel;
    private JPanel controlPanel;

    /**
     * Constructor del juego del Nim.
     * Configura la interfaz gráfica y los componentes del juego.
     */
    public JuegoDelNim() {
        setTitle("Juego del Nim");
        setSize(1000, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel para mostrar los cerillos restantes
        JPanel cerillosLabelPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        cerillosLabel = new JLabel("Cerillos restantes: " + cerillos);
        cerillosLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Ajustar el tamaño de la fuente
        cerillosLabelPanel.add(cerillosLabel);
        add(cerillosLabelPanel, BorderLayout.NORTH);

        // Panel para mostrar los iconos de los cerillos
        cerillosPanel = new JPanel();
        cerillosPanel.setLayout(new GridLayout(5, 8)); // Ajusta el layout según sea necesario
        add(cerillosPanel, BorderLayout.CENTER);

        // Panel de control donde se encuentra la entrada de datos y los botones
        controlPanel = new JPanel();
        controlPanel.setLayout(new FlowLayout());

        inputField = new JTextField(5); // Campo para ingresar la cantidad de cerillos a tomar
        inputField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarCerillos(); // Acción de tomar cerillos
            }
        });
        controlPanel.add(inputField);

        // Botón para tomar cerillos
        tomarButton = new JButton("Tomar cerillos");
        tomarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tomarCerillos();
            }
        });
        controlPanel.add(tomarButton);
        
        // Botón para iniciar el juego
        iniciarButton = new JButton("Iniciar Juego");
        iniciarButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarJuego(); // Acción para iniciar el juego
            }
        });
        controlPanel.add(iniciarButton);

        // ComboBox para seleccionar el modo de juego
        String[] modos = {"Jugador vs Jugador", "Jugador vs IA"};
        modoComboBox = new JComboBox<>(modos);
        controlPanel.add(modoComboBox);

        // Campo para ingresar la cantidad inicial de cerillos
        cantidadCerillosField = new JTextField(5);
        controlPanel.add(new JLabel("Cantidad de cerillos:"));
        controlPanel.add(cantidadCerillosField);

        // Etiqueta para mostrar el número máximo de cerillos que se pueden tomar por turno
        maxCerillosLabel = new JLabel();
        controlPanel.add(maxCerillosLabel);

        add(controlPanel, BorderLayout.SOUTH);

        actualizarCerillos();
    }

    /**
     * Inicializa el juego configurando el número de cerillos y el modo de juego.
     * Si se selecciona IA, se puede elegir quién empieza.
     */
    private void iniciarJuego() {
        try {
            cerillos = Integer.parseInt(cantidadCerillosField.getText()); // Establecer la cantidad de cerillos
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, ingresa un número válido de cerillos.");
            return;
        }

        cerillosLabel.setText("Cerillos restantes: " + cerillos);
        inputField.setText("");
        modoDeJuego = modoComboBox.getSelectedIndex() + 1; // Determina el modo de juego
        jugadasJugador = new int[cerillos]; // Inicializa las jugadas del jugador
        turnoJugador = true; // El turno inicial es del jugador

        actualizarMaxCerillos(); // Actualiza el número máximo de cerillos que se pueden tomar por turno
        if (modoDeJuego == 2) {
            int quienInicia = JOptionPane.showOptionDialog(this, "¿Quién debería comenzar?", "Inicio del Juego",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, new String[]{"Jugador", "IA"}, "Jugador");
            turnoJugador = quienInicia == 0; // Determina si empieza el jugador o la IA

            if (!turnoJugador) {
                tomarTurnoIA(); // La IA toma su turno si fue seleccionada para empezar
            }
        }

        actualizarCerillos(); // Actualiza la visualización de los cerillos
    }

    /**
     * Permite al jugador tomar cerillos, asegurando que se respete el límite por turno.
     * Cambia el turno al jugador o a la IA.
     */
    private void tomarCerillos() {
        int cerillosTomados;
        try {
            cerillosTomados = Integer.parseInt(inputField.getText()); // Obtiene la cantidad de cerillos tomada
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Entrada inválida. Por favor, ingresa un número válido.");
            return;
        }

        int maxCerillosPorTurno = (int) Math.ceil(cerillos * 0.1); // Calcula el máximo de cerillos permitidos por turno
        if (cerillosTomados < 1 || cerillosTomados > maxCerillosPorTurno) {
            JOptionPane.showMessageDialog(this, "Número inválido. Debes tomar entre 1 y " + maxCerillosPorTurno + " cerillos.");
            return;
        }

        cerillos -= cerillosTomados; // Reduce la cantidad de cerillos
        cerillosLabel.setText("Cerillos restantes: " + cerillos);
        inputField.setText("");

        if (cerillos <= 1) { // Si queda 1 o menos cerillos, el juego termina
            JOptionPane.showMessageDialog(this, turnoJugador ? "¡Tú Ganas! La IA Pierde." : "¡Jugador 2 Gana! Jugador 1 pierde.");
            iniciarJuego();
            return;
        }

        turnoJugador = !turnoJugador; // Cambia de turno

        if (modoDeJuego == 2 && !turnoJugador) {
            tomarTurnoIA(); // Si es el turno de la IA, toma su jugada
        }

        actualizarMaxCerillos();
        actualizarCerillos();
    }

    /**
     * Turno de la IA, el cual toma un número de cerillos siguiendo una estrategia avanzada.
     */
    private void tomarTurnoIA() {
        int cerillosIA = calcularMovimientoIAAvanzada(cerillos, jugadasJugador, random); // Determina la jugada de la IA
        JOptionPane.showMessageDialog(this, "La IA toma " + cerillosIA + " cerillos.");
        cerillos -= cerillosIA;
        cerillosLabel.setText("Cerillos restantes: " + cerillos);

        if (cerillos <= 1) { // Si queda 1 cerillo o menos, el juego termina
            JOptionPane.showMessageDialog(this, "¡La IA Gana! Tú pierdes ;( .");
            iniciarJuego();
            return;
        }

        turnoJugador = true; // Devuelve el turno al jugador
        actualizarMaxCerillos();
        actualizarCerillos();
    }

    /**
     * Actualiza la visualización de los cerillos en el panel central.
     * Muestra una representación gráfica de los cerillos restantes.
     */
    private void actualizarCerillos() {
        cerillosPanel.removeAll(); // Limpia el panel de cerillos
        for (int i = 0; i < cerillos; i++) {
            JLabel cerilloLabel = new JLabel(new ImageIcon(getClass().getResource("/cerillo.png"))); // Agrega iconos de cerillos
            cerillosPanel.add(cerilloLabel);
        }
        cerillosPanel.revalidate();
        cerillosPanel.repaint();
    }

    /**
     * Actualiza el mensaje que muestra el máximo número de cerillos que se pueden tomar por turno.
     */
    private void actualizarMaxCerillos() {
        int maxCerillosPorTurno = (int) Math.ceil(cerillos * 0.1); // Calcula el máximo de cerillos permitidos
        maxCerillosLabel.setText("Máximo cerillos que puedes tomar: " + maxCerillosPorTurno);
    }

    /**
     * Calcula la cantidad de cerillos que la IA tomará en su turno, basado en la estrategia avanzada.
     * @param cerillosRestantes Número de cerillos restantes en el juego.
     * @param jugadasJugador Arreglo con las jugadas del jugador.
     * @param random Generador de números aleatorios para decisiones no determinísticas.
     * @return Número de cerillos que la IA tomará.
     */
    public static int calcularMovimientoIAAvanzada(int cerillosRestantes, int[] jugadasJugador, Random random) {
        int cerillosIA;
        int maxCerillosPorTurno = (int) Math.ceil(cerillosRestantes * 0.1); // 10% del total de cerillos

        // 10% de probabilidad de hacer una jugada aleatoria
        boolean hacerJugadaAleatoria = random.nextInt(100) < 10;

        // Caso donde quedan pocos cerillos, IA toma para dejar al jugador en desventaja
        if (cerillosRestantes <= 10 && cerillosRestantes > 7) {
            cerillosIA = Math.min(cerillosRestantes - 7, maxCerillosPorTurno);
        } else if (cerillosRestantes == 7) {
            cerillosIA = hacerJugadaAleatoria ? random.nextInt(maxCerillosPorTurno) + 1 : 1;
        } else if (cerillosRestantes < 7) {
            cerillosIA = Math.min(cerillosRestantes - 1, maxCerillosPorTurno);
        } else if (hacerJugadaAleatoria) {
            int ultimaJugadaJugador = cerillosRestantes > 0 ? jugadasJugador[cerillosRestantes - 1] : 0;
            boolean jugadorEsArriesgado = ultimaJugadaJugador == 5;
            boolean jugadorEsConservador = ultimaJugadaJugador <= 2;

            if (jugadorEsArriesgado) {
                cerillosIA = random.nextInt(2) + 1;
            } else if (jugadorEsConservador) {
                cerillosIA = random.nextInt(2) + 4;
            } else {
                cerillosIA = random.nextInt(maxCerillosPorTurno) + 1;
            }
        } else {
            int cerillosMod = cerillosRestantes % (maxCerillosPorTurno + 1);
            cerillosIA = cerillosMod == 0 ? 1 : cerillosMod;
        }

        // Asegurar que la IA no tome más de lo permitido
        if (cerillosIA > maxCerillosPorTurno) {
            cerillosIA = maxCerillosPorTurno;
        }

        return cerillosIA;
    }

    /**
     * Método principal para ejecutar el juego del Nim.
     * @param args Argumentos de la línea de comandos.
     */
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new JuegoDelNim().setVisible(true); // Inicia la interfaz gráfica del juego
            }
        });
    }
}
