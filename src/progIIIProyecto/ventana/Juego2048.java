package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.datatransfer.Clipboard;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Juego2048  extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel pNorte,pCentro;
	private JLabel puntuacion;
	private JPanel matriz [][];
	private JLabel numeros[][];
	
	public Juego2048() {
		setTitle("2048");
		setBounds(500,200, 400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
		
		
		pNorte = new JPanel(new FlowLayout());
		pCentro = new JPanel(new GridLayout(4,4));		
		getContentPane().add(pNorte,BorderLayout.NORTH);
		getContentPane().add(pCentro,BorderLayout.CENTER);

		puntuacion = new JLabel("Puntos: 0");
		pNorte.add(puntuacion); 

		matriz = new JPanel[4][4];
		numeros = new JLabel[4][4];
		for (int i = 0; i < 4; i++) {
			 for (int j = 0; j < 4; j++) {
				matriz[i][j] = new JPanel(new GridLayout(1,1)); // Que ocupe todo, asi apareceria en todo el centro el numero
				matriz[i][j].setBorder(BorderFactory.createLineBorder(new Color(147,133,120),3));
				matriz[i][j].setOpaque(true);
				matriz[i][j].setBackground(new Color(197,183,170));
				
				numeros[i][j] = new JLabel("8");
				numeros[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				
		        matriz[i][j].add(numeros[i][j]);
				pCentro.add(matriz[i][j]);
				
				actualizarColorCelda(i, j);

			}
		}
		
		
	}
	public void actualizarColorCelda(int i, int j) {
	    int valor = Integer.parseInt(numeros[i][j].getText());

	    switch (valor) {
	        case 0:
	            matriz[i][j].setBackground(new Color(197,183,170));
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

	    }
	}

	
	

	public static void main(String[] args) {
		Juego2048 j2028 = new Juego2048();
	}

}
