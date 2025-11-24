package progIIIProyecto.ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class BuscaMinas extends JFrame {

    private static final int FILAS = 10; 
    private static final int COLUMNAS = 10; 
    private static final int NUM_MINAS = 15; 

    private JButton[][] botones;
    private int[][] tableroLogico;
    private boolean juegoTerminado = false;
    
    private JFrame ventanaPrevia;

    public BuscaMinas(JFrame previo) {
        super("BuscaMinas");
        this.ventanaPrevia = previo;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        inicializarTableroLogico();
        
        JPanel panelTablero = new JPanel(new GridLayout(FILAS, COLUMNAS));
        inicializarBotones(panelTablero);
        
        add(panelTablero, BorderLayout.CENTER);
        
        pack();
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                int response = JOptionPane.showConfirmDialog(BuscaMinas.this, "¿Deseas volver a la ventana principal?", "Salir del juego", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    BuscaMinas.this.dispose();
                }
            }
            @Override
            public void windowClosed(WindowEvent e) {
                ventanaPrevia.setVisible(true);
            }
        });
        
        setVisible(true);
    }
    
    //1. LÓGICA DEL JUEGO: Inicialización de Minas y Números
    private void inicializarTableroLogico() {
        tableroLogico = new int[FILAS][COLUMNAS];
        colocarMinas();
        calcularNumeros();
    }
    
    private void colocarMinas() {
        Random rand = new Random();
        int minasColocadas = 0;
        
        while (minasColocadas < NUM_MINAS) {
            int fila = rand.nextInt(FILAS);
            int columna = rand.nextInt(COLUMNAS);
            
            if (tableroLogico[fila][columna] != -1) {
                tableroLogico[fila][columna] = -1;
                minasColocadas++;
            }
        }
    }
    
    private void calcularNumeros() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tableroLogico[i][j] != -1) {
                    tableroLogico[i][j] = contarMinasAdyacentes(i, j);
                }
            }
        }
    }
    
    private int contarMinasAdyacentes(int fila, int columna) {
        int contador = 0;
        for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                if (i == 0 && j == 0) continue; 
                
                int nuevaFila = fila + i;
                int nuevaColumna = columna + j;
                
                if (nuevaFila >= 0 && nuevaFila < FILAS && 
                    nuevaColumna >= 0 && nuevaColumna < COLUMNAS) {
                    
                    if (tableroLogico[nuevaFila][nuevaColumna] == -1) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }

    //2. GUI Y EVENTOS: Creación de Botones y Manejo de Clicks
    private void inicializarBotones(JPanel panelTablero) {
        botones = new JButton[FILAS][COLUMNAS];
        
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                JButton boton = new JButton(" ");
                boton.setPreferredSize(new Dimension(50, 50)); 
                
                boton.putClientProperty("fila", i);
                boton.putClientProperty("columna", j);
                
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (juegoTerminado) return;
                        
                        JButton celda = (JButton) e.getSource();
                        int fila = (int) celda.getClientProperty("fila");
                        int columna = (int) celda.getClientProperty("columna");

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            destaparCelda(fila, columna);
                        } else if (e.getButton() == MouseEvent.BUTTON3) {
                            marcarCelda(celda);
                        }
                    }
                });
                
                botones[i][j] = boton;
                panelTablero.add(boton);
            }
        }
    }
    
    //3. LÓGICA DE INTERACCIÓN (DESTAPAR Y MARCAR)

    private void destaparCelda(int fila, int columna) {
        JButton celda = botones[fila][columna];
        
        if (!celda.isEnabled() || celda.getText().equals("🚩") || juegoTerminado) { 
            return;
        }

        celda.setEnabled(false);
        
        int valor = tableroLogico[fila][columna];
        
        if (valor == -1) {
            celda.setText("💣");
            celda.setBackground(Color.RED);
            juegoTerminado = true;
            JOptionPane.showMessageDialog(this, "¡BOOM! Has perdido.", "Fin del Juego", JOptionPane.ERROR_MESSAGE);
            mostrarTodasMinas();
        } else if (valor > 0) {
            celda.setText(String.valueOf(valor));
            celda.setForeground(getColorParaNumero(valor));
        } else {
            celda.setText("");
            destapeRecursivo(fila, columna);
        }
    }

    private void destapeRecursivo(int fila, int columna) {
        if (fila < 0 || fila >= FILAS || columna < 0 || columna >= COLUMNAS || !botones[fila][columna].isEnabled()) {
            return;
        }

        JButton celda = botones[fila][columna];
        
        if (celda.getText().equals("🚩")) {
            return;
        }
        
        celda.setEnabled(false);
        int valor = tableroLogico[fila][columna];

        if (valor > 0) {
            celda.setText(String.valueOf(valor));
            celda.setForeground(getColorParaNumero(valor));
            return;
        } else if (valor == 0) {
            celda.setText("");
            for (int i = -1; i <= 1; i++) {
                for (int j = -1; j <= 1; j++) {
                    if (i != 0 || j != 0) {
                        destapeRecursivo(fila + i, columna + j);
                    }
                }
            }
        }
    }
    
    private void marcarCelda(JButton celda) {
        if (!celda.isEnabled()) return; 
        
        if (celda.getText().equals("🚩")) {
            celda.setText(" ");
        } else {
            celda.setText("🚩");
        }
    }

    //4. FUNCIONES AUXILIARES

    private Color getColorParaNumero(int numero) {
        switch (numero) {
            case 1: return Color.BLUE;
            case 2: new Color(34, 139, 34);
            case 3: return Color.RED;
            case 4: new Color(0, 0, 128);
            case 5: new Color(128, 0, 0); 
            case 6: new Color(64, 224, 208); 
            default: return Color.BLACK;
        }
    }
    
    private void mostrarTodasMinas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tableroLogico[i][j] == -1 && botones[i][j].isEnabled()) {
                    if (!botones[i][j].getBackground().equals(Color.RED)) {
                        botones[i][j].setText("💣");
                    }
                }
                botones[i][j].setEnabled(false);
            }
        }
    }
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BuscaMinas(null); 
        });
    }
}