package progIIIProyecto.ventana;

import javax.swing.*;

import progIIIProyecto.domain.Usuario;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.BD.GestorBD;

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
    
    private Timer timer;
    private int segundosTranscurridos = 0;
    private JLabel lblTiempo;
    private boolean juegoIniciado = false;
    
    private int casillasSinMinasRestantes;
    
    private int puntuacion = 0;
    private JLabel lblPuntuacion;
    
    private JFrame ventanaPrevia;
    private Usuario usuario; 
    private GestorBD gestorBD;
    
    private boolean reiniciando = false;
    
    private boolean logroVictoriaDesbloqueado = false;
    private boolean logroVelocistaDesbloqueado = false;
    private boolean logroPuntuacionDesbloqueado = false;
    
    private static final int ID_LOGRO_PUNTUACION = 4; 
    private static final int ID_LOGRO_VICTORIA = 5;  
    private static final int ID_LOGRO_VELOCISTA = 6;
    
    public BuscaMinas(JFrame previo, Usuario usuario) {
    	super("BuscaMinas");
    	this.ventanaPrevia = previo;
    	this.usuario = usuario;         
        this.gestorBD = new GestorBD();
        
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        
        this.casillasSinMinasRestantes = (FILAS * COLUMNAS) - NUM_MINAS;
        setLayout(new BorderLayout());
        inicializarTableroLogico();
        JPanel panelInfo = new JPanel();
        panelInfo.setBackground(Color.LIGHT_GRAY);
        panelInfo.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));

        lblPuntuacion = new JLabel("Puntuación: 0");
        lblPuntuacion.setFont(new Font("Arial", Font.BOLD, 20));
        lblPuntuacion.setForeground(Color.BLACK);
        panelInfo.add(lblPuntuacion);
        panelInfo.add(Box.createHorizontalStrut(20)); 

        lblTiempo = new JLabel("Tiempo: 0s");
        lblTiempo.setFont(new Font("Arial", Font.BOLD, 18));
        lblTiempo.setForeground(Color.BLUE); 
        panelInfo.add(lblTiempo);
        
        add(panelInfo, BorderLayout.NORTH);
        
        JPanel panelTablero = new JPanel(new GridLayout(FILAS, COLUMNAS));
        inicializarBotones(panelTablero);
        
        add(panelTablero, BorderLayout.CENTER);
        
        timer = new Timer(1000, e -> {
            segundosTranscurridos++;
            lblTiempo.setText("Tiempo: " + segundosTranscurridos + "s");
        });
        
        pack();
        setLocationRelativeTo(null);
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
            	if (!reiniciando) {
            		if (timer != null) timer.stop();
                    if (ventanaPrevia != null) {
                        ventanaPrevia.setVisible(true);
                        ventanaPrevia.toFront();
                    } else {
                        System.exit(0);
                    }
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
                        
                        if (!juegoIniciado) {
                            juegoIniciado = true;
                            timer.start();
                        }

                        int fila = (int) celda.getClientProperty("fila");
                        int columna = (int) celda.getClientProperty("columna");

                        if (e.getButton() == MouseEvent.BUTTON1) {
                            if (!celda.getText().equals("P")) {
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
            celda.setText("B");
            celda.setBackground(Color.RED);
            perderJuego();
        } else {
        	sumarPuntos();
            casillasSinMinasRestantes--;

            if (valor > 0) {
                celda.setText(String.valueOf(valor));
                celda.setForeground(Color.BLACK);
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
                    
                    if (celda.isEnabled() && !celda.getText().equals("P")) {
                        
                        int valorVecino = tableroLogico[f][c];
                        celda.setEnabled(false);
                        celda.setBackground(Color.WHITE);
                        
                        sumarPuntos();
                        casillasSinMinasRestantes--; 
                        
                        if (valorVecino == 0) {
                            celda.setText("");
                            destapeRecursivo(f, c);
                        } else {
                            celda.setText(String.valueOf(valorVecino));
                            celda.setForeground(Color.BLACK);
                        }
                    }
                }
            }
        }
    }
    
    private void sumarPuntos() {
        puntuacion += 10;
        lblPuntuacion.setText("Puntuación: " + puntuacion);
        
        if (puntuacion >= 500 && !logroPuntuacionDesbloqueado) {
            mostrarLogro("¡GRAN PUNTUADOR!", "Has conseguido 500 puntos.");
            logroPuntuacionDesbloqueado = true;
            
            guardarLogroBD(ID_LOGRO_PUNTUACION);
        }
        
        lblPuntuacion.paintImmediately(lblPuntuacion.getVisibleRect()); 
    }
    
    private void verificarVictoria() {
        if (casillasSinMinasRestantes == 0 && !juegoTerminado) {
            juegoTerminado = true;
            timer.stop();
            for (int i = 0; i < FILAS; i++) {
                for (int j = 0; j < COLUMNAS; j++) {
                    if (tableroLogico[i][j] == -1) {
                        botones[i][j].setText("P");
                        botones[i][j].setForeground(Color.RED);
                    }
                }
            }
            
            if (!logroVictoriaDesbloqueado) {
                mostrarLogro("¡PRIMERA VICTORIA!", "Has despejado el campo con éxito.");
                logroVictoriaDesbloqueado = true;
                
                guardarLogroBD(ID_LOGRO_VICTORIA);
            }

            if (segundosTranscurridos < 40 && !logroVelocistaDesbloqueado) {
                mostrarLogro("¡VELOCISTA!", "Has ganado en menos de 40 segundos.");
                logroVelocistaDesbloqueado = true;
                
                guardarLogroBD(ID_LOGRO_VELOCISTA);
            }
            guardarPuntajeBD();
            
            gestionarFinalJuego("¡FELICIDADES! Has despejado el campo.\nPuntuación Final: " + puntuacion, "¡Victoria!");
        }
    }
    
    private void perderJuego() {
        juegoTerminado = true;
        timer.stop();
        mostrarTodasMinas();
        guardarPuntajeBD();
        this.getRootPane().paintImmediately(0, 0, getWidth(), getHeight());
        gestionarFinalJuego("¡BOOM! Has perdido.\nPuntuación Final: " + puntuacion, "Fin del Juego");
    }
    
    private void mostrarLogro(String titulo, String descripcion) {
        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, 
                descripcion, 
                "LOGRO DESBLOQUEADO: " + titulo, 
                JOptionPane.INFORMATION_MESSAGE);
        });
    }
    
    private void gestionarFinalJuego(String mensaje, String titulo) {
        SwingUtilities.invokeLater(() -> {
            
            int eleccion = JOptionPane.showConfirmDialog(
                    this,
                    mensaje + "\n\n¿Desea volver a jugar?",
                    titulo,
                    JOptionPane.YES_NO_OPTION 
            );

            if (eleccion == JOptionPane.YES_OPTION) {
                reiniciando = true;
                dispose();
                new BuscaMinas(ventanaPrevia, this.usuario);
            } else {
            }
        });
    }
    
	private void mostrarTodasMinas() {
        for (int i = 0; i < FILAS; i++) {
            for (int j = 0; j < COLUMNAS; j++) {
                if (tableroLogico[i][j] == -1) {
                    botones[i][j].setText("B");
                    
                }
                botones[i][j].setEnabled(false);
            }
        }
    }
    
    private void marcarCelda(JButton celda) {
        if (!celda.isEnabled()) return; 
        
        if (celda.getText().equals("P")) {
            celda.setText(" ");
            celda.setForeground(null);
        } else {
            celda.setText("P");
            celda.setForeground(Color.BLACK);
        }
    }

    //4. FUNCIONES AUXILIARES Y GESTION BASE DE DATOS
    private void guardarPuntajeBD() {
        if (usuario != null) {
            try {
 //               int codPuntaje = gestorBD.obtenerSiguienteCodigoPuntaje();
                
                Puntaje p = new Puntaje("BuscaMinas", puntuacion, segundosTranscurridos, usuario.getCodigo());
                
                gestorBD.subirPuntaje(p);
                System.out.println("Puntuación guardada para usuario: " + usuario.getNombre());
                
            } catch (Exception e) {
                System.err.println("Error al guardar puntaje: " + e.getMessage());
            }
        }
    }

    private void guardarLogroBD(int codigoLogro) {
        if (usuario != null) {
            try {
                gestorBD.asignarLogroAUsuario(usuario.getCodigo(), codigoLogro);
            } catch (Exception e) {
                System.err.println("Error al guardar logro: " + e.getMessage());
            }
        }
    }
    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BuscaMinas(null, null); 
        });
    }
}