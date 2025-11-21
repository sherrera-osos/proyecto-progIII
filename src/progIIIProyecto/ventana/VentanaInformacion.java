package progIIIProyecto.ventana;

import javax.swing.JFrame;


public class VentanaInformacion extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaInformacion(JFrame previo) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(480, 480);
		setTitle("Información del programa");
		setLocationRelativeTo(previo);
		
		setVisible(true);
	}

}
