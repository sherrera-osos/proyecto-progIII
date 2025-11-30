package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.Timer;

public class Juego2048  extends JFrame{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pNorte,pCentro,pSur;
	private JLabel puntuacion;
	private JPanel matriz [][];
	private JLabel numeros[][];
    private int tablero[][] = new int[4][4];
    private JButton btnSalir;
    // private static JFrame ventanaActual;
	//private JFrame ventanaAnterior;
    private JLabel cronometroLabel;
    private Timer timer;
    private int segundos = 0;
    
    	
	public Juego2048() {
		setTitle("2048");
		setBounds(500,200, 500, 500);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setFocusable(true);
			
		//ventanaActual = this;
		//ventanaAnterior = va;
		
		pSur = new JPanel();
		pNorte = new JPanel(new BorderLayout());
		pCentro = new JPanel(new GridLayout(4,4));		
		getContentPane().add(pNorte,BorderLayout.NORTH);
		getContentPane().add(pCentro,BorderLayout.CENTER);
		getContentPane().add(pSur,BorderLayout.SOUTH);
		pNorte.setBackground(new Color(169,229,131));
		pSur.setBackground(new Color(169,229,131));
		
		
		puntuacion = new JLabel("Puntos: 0");
		puntuacion.setFont(new Font("Serif", Font.BOLD, 18));
		pNorte.add(puntuacion, BorderLayout.WEST);

		cronometroLabel = new JLabel("Tiempo: 0s");
		cronometroLabel.setFont(new Font("Serif", Font.BOLD, 18));
		cronometroLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		pNorte.add(cronometroLabel, BorderLayout.EAST);

		JLabel lbl = new JLabel("HOLA");
		JLabel lbl1 = new JLabel("HOLA");

		pSur.add(lbl);
		pNorte.add(lbl1);
		
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode() == KeyEvent.VK_W || e.getKeyCode() == KeyEvent.VK_A ||
					e.getKeyCode() == KeyEvent.VK_S || e.getKeyCode() == KeyEvent.VK_D) {
					// Si el timer es null (nunca se ha iniciado)
					if (timer == null) { 
						iniciarCronometroLogic(); // Inicia el timer
					}
				}
			}
		});

		matriz = new JPanel[4][4];
		numeros = new JLabel[4][4];
		for (int i = 0; i < 4; i++) {
			 for (int j = 0; j < 4; j++) {
				matriz[i][j] = new JPanel(new GridLayout(1,1)); // Que ocupe todo, asi apareceria en todo el centro el numero
				matriz[i][j].setBorder(BorderFactory.createLineBorder(new Color(147,133,120),3));
				matriz[i][j].setOpaque(true);
				matriz[i][j].setBackground(new Color(197,183,170));
				
				numeros[i][j] = new JLabel("0");
				numeros[i][j].setFont(new Font("Serif",Font.BOLD,24));
				numeros[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				
		        matriz[i][j].add(numeros[i][j]);
				pCentro.add(matriz[i][j]);
				
				actualizarColorCelda(i, j);

			}
		}
		generarNuevoNumero();
		actualizarPantalla();
		
		btnSalir = new JButton("Salir");
		pSur.add(btnSalir);
		btnSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				
			}
		});
		
		setVisible(true);

		
	}

	public void iniciarCronometroLogic() {
		if (timer != null) {
			timer.stop();
			}
		segundos = 0;
		cronometroLabel.setText("Tiempo: 0s");
		timer = new Timer(1000, new ActionListener() {
		@Override
		public void actionPerformed(ActionEvent e) {
		segundos++;
		cronometroLabel.setText("Tiempo: " + segundos + "s");
			}
		});
		timer.start();
				}
		
	
	public void actualizarColorCelda(int i, int j) {
	    int valor = tablero[i][j];

	    switch (valor) {
	        case 0:
	            matriz[i][j].setBackground(new Color(205,193,180));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 2:
	            matriz[i][j].setBackground(new Color(238,228,218));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 4:
	            matriz[i][j].setBackground(new Color(237,224,200));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 8:
	            matriz[i][j].setBackground(new Color(242,177,121));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 16:
	            matriz[i][j].setBackground(new Color(245,149,99));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 32:
	            matriz[i][j].setBackground(new Color(246,124,95));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 64:
	            matriz[i][j].setBackground(new Color(246,94,59));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 128:
	            matriz[i][j].setBackground(new Color(237,207,114));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 256:
	            matriz[i][j].setBackground(new Color(237,204,97));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 512:
	            matriz[i][j].setBackground(new Color(237,200,80));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 1024:
	            matriz[i][j].setBackground(new Color(237,197,63));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 2048:
	            matriz[i][j].setBackground(new Color(237,194,46));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        default: // Para valores mayores
	            matriz[i][j].setBackground(new Color(60,58,50));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	    }
	}
	
	public boolean estaLleno() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void generarNuevoNumero() {
		if (estaLleno()) {
			return;
		}
		List<int[]> vacias = new ArrayList<int[]>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j]==0) {
					vacias.add(new int[] {i,j});
				}
			}
		}
		
		int [] pos = vacias.get((int) (Math.random()* vacias.size())); // Hay que hacer un casteo
		int j = pos[1];
		int i = pos[0];
		double probabilidad = Math.random(); // número aleatorio entre 0 y 1
		if (probabilidad < 0.9) { // el 90% de los casos, siempre aparece el numero 2 y no el 4
		    tablero[i][j] = 2;
		} else {
		    tablero[i][j] = 4;
		}
	}
	
	public void actualizarPantalla () {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j] == 0) {
				    numeros[i][j].setText("");
				} else {
				    numeros[i][j].setText(String.valueOf(tablero[i][j]));
				}
				actualizarColorCelda(i, j);
			}	
			
		}
	}	


	public static void main(String[] args) {
		Juego2048 j2028 = new Juego2048();
	}

}
