package progIIIProyecto.ventana;

import javax.swing.JFrame;

public class VentanaPrincipal extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public VentanaPrincipal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Proyecto ProgIII"); //NOMBRE PROVISIONAL
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		System.out.println("soy muy guapo");
		System.out.println("soy aun más guapo");
		
		
		setVisible(true);
	}

}
