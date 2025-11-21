package progIIIProyecto.ventana;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class VentanaCambiarApariencia extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaCambiarApariencia(JFrame previo) {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setSize(320, 480);
		setTitle("Cambiar apariencia");
		setLocationRelativeTo(previo);
		
		// CERRADO DE VENTANA
		
				addWindowListener(new WindowAdapter() {
					
					public void windowClosing(WindowEvent e) {
						int response = JOptionPane.showConfirmDialog(VentanaCambiarApariencia.this, "¿Desea guardar los cambios antes de salir?", "Salir", JOptionPane.YES_NO_OPTION); //Provisional (Asier)
						if (response == JOptionPane.YES_OPTION) {
							VentanaCambiarApariencia.this.dispose(); //Si dice que si habra que aplicar los cambios que haya hecho
						} else if (response == JOptionPane.NO_OPTION) {
							VentanaCambiarApariencia.this.dispose();
						}
					}

				});
				
		setVisible(true);
	}
	
	

}
