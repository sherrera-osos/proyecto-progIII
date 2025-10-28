package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
public class VentanaPrincipal extends JFrame{
	private static final long serialVersionUID = 1L;
	
	// En esta ventana iran los juegos, la ventanaPrincipal queda como una portada (Asier)
	class ventanaConJuegos extends JFrame implements WindowListener {
		/**
		 *
		 */
		private static final long serialVersionUID = 1L;
		
		private JFrame previo;
		
       public ventanaConJuegos (JFrame previo) {
           setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
           this.previo = previo;
           addWindowListener(this);
   		setTitle("VentanaPrincipal con Juegos"); //Provisional (Asier)
   		setSize(800, 600);
   		setLocationRelativeTo(null);
           setVisible(true);
       }		
		@Override
		public void windowOpened(WindowEvent e) {
		}
		@Override
		public void windowClosing(WindowEvent e) {
			int response = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "¿Deseas salir a la página principal?", "Salir", JOptionPane.YES_NO_OPTION); //Provisional (Asier)
       	if (response == JOptionPane.YES_OPTION) {
               this.dispose();
           }     
		}
		@Override
		public void windowClosed(WindowEvent e) {
			previo.setVisible(true);
		}
		@Override
		public void windowIconified(WindowEvent e) {
		}
		@Override
		public void windowDeiconified(WindowEvent e) {
		}
		@Override
		public void windowActivated(WindowEvent e) {
		}
		@Override
		public void windowDeactivated(WindowEvent e) {
		}
		
	}
	
	public VentanaPrincipal() {
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Proyecto ProgIII"); //NOMBRE PROVISIONAL (Samuel)
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		setLayout(new BorderLayout());
		
		JLabel titulo = new JLabel("NOMBRE"); //Obviamente Provisional (Asier)
		titulo.setFont(new Font("Arial", Font.BOLD,30));
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.add(titulo, BorderLayout.CENTER);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		add(panelTitulo, BorderLayout.CENTER);
		
		JButton botonEntrar = new JButton("Entrar");
		
		botonEntrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
               // se hace invisible la ventana actual (Asier)
               setVisible(false);
               // se crea la ventana donde iran los juegos pasando la referencia a la actual (Asier)
               new ventanaConJuegos(VentanaPrincipal.this);
              
               // en el futuro se podria añadir una funcionalidad para meter el usuario después de darle a este boton
				
			}
		});
		
		JPanel panelBotonEntrar = new JPanel();
		panelBotonEntrar.add(botonEntrar);
		add(panelBotonEntrar, BorderLayout.SOUTH);
		
		// Confirmación para salir (Asier)
		
		addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
           	int response = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
           	if (response == JOptionPane.YES_OPTION) {
                   System.exit(0);
               }        
           }
		});
		setVisible(true);
	}
}

