package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.Insets;

import javax.swing.JFrame;
import javax.swing.JTextArea;


public class VentanaInformacion extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaInformacion(JFrame previo) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(480, 660);
		setTitle("Información del programa");
		setLocationRelativeTo(previo);
		
		JTextArea txt = new JTextArea();
		txt.setEditable(false);
		txt.setLineWrap(true);
		txt.setWrapStyleWord(true);
		txt.setFocusable(false);
		txt.setMargin(new Insets(10, 20, 10, 20));
		txt.setText(
			    "Bienvenido a 16-BITS GAMES, donde podrás pasártelo en"
			    + " grande mientras juegas a tus juegos favoritos desarrollados"
			    + " por las mentes más excelsas de nuestra generación.\n\n16-BITS"
			    + "GAMES ofrece un avanzadísmo sistema de registro de usuario mediante "
			    + "el cual, tu progreso en cada uno de los juegos no se pierde entre sesiones. "
			    + "Este progreso se ve reflejado en nuestro sistema de logros, los cuales irás "
			    + "obteniendo a medida que juegues. Puedes acceder a la información sobre los "
			    + "logros y ver cuales has obtenido mediante la pestaña de 'Logros'."
			    + "\n\n Además, si eres especialmente competitivo, siempre puedes acceder a las estadísticas "
			    + "globales de cada uno de los juegos mediante la opción de 'Estadísticas', donde podrás ver "
			    + "tus mejores marcas en cada uno de los juegos y el ranking global de todos los jugadores."
			    + "\n\n\n\nProgramadores:\n\nAdrián Cámara Uriarte\n\nMikel Goenaga Peñagarikano\n\n"
			    + "Samuel David Herrera Villalobos\n\nMarkel Pérez García\n\nAsier Zamalloa Torés"
				
				);
		
		
		Font retroFont = new Font("Arial", Font.PLAIN, 16);
        txt.setFont(retroFont);
        
        add(txt, BorderLayout.CENTER);
		
		setVisible(true);
	}

}
