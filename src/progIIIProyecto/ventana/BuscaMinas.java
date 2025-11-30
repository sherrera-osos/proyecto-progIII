package progIIIProyecto.ventana;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

public class BuscaMinas extends JFrame {

	private static final long serialVersionUID = 1L;
	private static final int FILAS = 10; 
    private static final int COLUMNAS = 10; 
    private static final int NUM_MINAS = 15; 

    private JButton[][] botones;
    private int[][] tableroLogico;
    private boolean juegoTerminado = false;
    
    private int casillasSinMinasRestantes;
    
    private JFrame ventanaPrevia;

    public BuscaMinas(JFrame previo) {
        super("BuscaMinas");
        this.ventanaPrevia = previo;
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        
        this.casillasSinMinasRestantes = (FILAS * COLUMNAS) - NUM_MINAS;
        
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
            	if (ventanaPrevia != null) {
                    ventanaPrevia.setVisible(true);
                } else {
                    System.exit(0);
                }
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
                
                if (isValid(nuevaFila, nuevaColumna)) {
                    if (tableroLogico[nuevaFila][nuevaColumna] == -1) {
                        contador++;
                    }
                }
            }
        }
        return contador;
    }
    private boolean isValid(int f, int c) {
        return f >= 0 && f < FILAS && c >= 0 && c < COLUMNAS;
    }

    //2. GUI Y EVENTOS: Creación de Botones y Manejo de Clicks
    private void inicializarBotones(JPanel panelTablero) {
        botones = new JButton[FILAS][COLUMNAS];
        
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                JButton boton = new JButton(" ");
                
                boton.setPreferredSize(new Dimension(50, 50)); 
                boton.setMargin(new Insets(0,0,0,0)); 
                boton.setFont(new Font("Arial", Font.BOLD, 18)); 
                boton.setFocusPainted(false); 
                
                boton.putClientProperty("fila", i);
                boton.putClientProperty("columna", j);
                
                boton.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (juegoTerminado) return;
                        
                        JButton celda = (JButton) e.getSource();
                        
                        if (!celda.isEnabled()) return;

                        int fila = (int) celda.getClientProperty("fila");
                        int columna = (int) celda.getClientProperty("columna");

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!celda.getText().equals("🚩")) {
                                destaparCelda(fila, columna);
                            }
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
    	if (!isValid(fila, columna)) return;
        
        JButton celda = botones[fila][columna];
        if (!celda.isEnabled()) return; 

        celda.setEnabled(false); 
        celda.setBackground(Color.WHITE); 
        
        int valor = tableroLogico[fila][columna];
        if (valor == -1) {
            celda.setText("💣");
            celda.setBackground(Color.RED);
            perderJuego();
        } else {
            casillasSinMinasRestantes--;

            if (valor > 0) {
                celda.setText(String.valueOf(valor));
                celda.setForeground(getColorParaNumero(valor));
            } else {
                celda.setText("");
                destapeRecursivo(fila, columna);
            }
            
            verificarVictoria();
        }
    }

    private void destapeRecursivo(int fila, int columna) {
    	for (int i = -1; i <= 1; i++) {
            for (int j = -1; j <= 1; j++) {
                int f = fila + i;
                int c = columna + j;

                if (isValid(f, c)) {
                    JButton celda = botones[f][c];
                    
                    if (celda.isEnabled() && !celda.getText().equals("🚩")) {
                        
                        int valorVecino = tableroLogico[f][c];
                        celda.setEnabled(false);
                        celda.setBackground(Color.WHITE);
                        
                        casillasSinMinasRestantes--; 
                        
                        if (valorVecino == 0) {
                            celda.setText("");
                            destapeRecursivo(f, c);
                        } else {
                            celda.setText(String.valueOf(valorVecino));
                            celda.setForeground(getColorParaNumero(valorVecino));
                        }
                    }
                }
            }
        }
    }
    
    private void verificarVictoria() {
        if (casillasSinMinasRestantes == 0 && !juegoTerminado) {
            juegoTerminado = true;
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS; j++) {
                    if (tableroLogico[i][j] == -1) {
                        botones[i][j].setText("🚩");
                        botones[i][j].setForeground(Color.RED);
                    }
                }
            }
            JOptionPane.showMessageDialog(this, "¡FELICIDADES! Has despejado el campo minado.", "¡Victoria!", JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    private void perderJuego() {
        juegoTerminado = true;
        mostrarTodasMinas();
        JOptionPane.showMessageDialog(this, "¡BOOM! Has perdido.", "Fin del Juego", JOptionPane.ERROR_MESSAGE);
    }
    
    private void mostrarTodasMinas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tableroLogico[i][j] == -1) {
                    botones[i][j].setText("💣");
                    
                }
                botones[i][j].setEnabled(false);
            }
        }
    }
    
    private void marcarCelda(JButton celda) {
        if (!celda.isEnabled()) return; 
        
        if (celda.getText().equals("🚩")) {
            celda.setText(" ");
            celda.setForeground(null);
        } else {
            celda.setText("🚩");
            celda.setForeground(Color.BLACK);
        }
    }

    //4. FUNCIONES AUXILIARES

    private Color getColorParaNumero(int numero) {
        switch (numero) {
            case 1: return Color.BLUE;
            case 2: return new Color(34, 139, 34);
            case 3: return Color.RED;
            case 4: return new Color(0, 0, 128);
            case 5: return new Color(128, 0, 0); 
            case 6: return new Color(64, 224, 208);
            case 7: return Color.BLACK;
            case 8: return Color.GRAY;
            default: return Color.BLACK;
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BuscaMinas(null); 
        });
    }
}