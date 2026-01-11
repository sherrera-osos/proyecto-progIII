package progIIIProyecto.main;

import javax.swing.SwingUtilities;
import progIIIProyecto.ventana.VentanaPrincipal;

public class Main {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			
			@Override
			public void run() {
				new VentanaPrincipal();				
			}
		});
	}

}
