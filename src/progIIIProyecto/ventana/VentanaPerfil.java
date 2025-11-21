package progIIIProyecto.ventana;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

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
