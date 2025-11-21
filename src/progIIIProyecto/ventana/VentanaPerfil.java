package progIIIProyecto.ventana;

import javax.swing.JFrame;

public class VentanaPerfil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaPerfil(JFrame previo) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(480, 480);
		setTitle("Perfil de usuario");
		setLocationRelativeTo(previo);
		
		setVisible(true);
	}

}
