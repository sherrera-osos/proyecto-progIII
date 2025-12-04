package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent; 
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

public class VentanaPrincipal extends JFrame{
	private static final long serialVersionUID = 1L;
	private JButton btnUsuario;
	private JPanel pAbajo;
	private JFrame ventanaActual;
	public VentanaPrincipal() {
		ventanaActual = this;
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setTitle("Proyecto ProgIII"); //NOMBRE PROVISIONAL 
		setSize(800, 600);
		setLocationRelativeTo(null);

		String imagePath = "/imagenes/fondoo.jpg";
		PanelConFondo panelConFondo = new PanelConFondo(imagePath);
		
		panelConFondo.setLayout(new BorderLayout());
		//panelConFondo.setBackground(Color.CYAN); PARA VERIFICAR QUE SI SE ESTABA MOSTRANDO EL PANEL
		
		setContentPane(panelConFondo);
		
		
		
		
		JLabel titulo = new JLabel("16-BITS GAMES");
		titulo.setFont(new Font("Impact", Font.BOLD, 48));
		titulo.setForeground(Color.WHITE);
		
		
		JPanel panelTitulo = new JPanel();
		panelTitulo.setOpaque(false);
		panelTitulo.add(titulo, BorderLayout.CENTER);
		titulo.setHorizontalAlignment(SwingConstants.CENTER);
		
		panelConFondo.add(panelTitulo, BorderLayout.CENTER);
		
		
		JButton botonEntrar = new JButton("Entrar");
		botonEntrar.setBackground(new Color(0, 153, 255));
		
		botonEntrar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
               // se hace invisible la ventana actual 
               setVisible(false);
               // se crea la ventana donde iran los juegos pasando la referencia a la actual
               SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaConJuegos(VentanaPrincipal.this, null);
					}
				});
                         
               // en el futuro se podria añadir una funcionalidad para meter el usuario después de darle a este boton
			}
		});
		
		JPanel panelBotonEntrar = new JPanel();
		panelBotonEntrar.add(botonEntrar);
		panelConFondo.add(panelBotonEntrar, BorderLayout.SOUTH);
		
		// Confirmación para salir 
		
		addWindowListener(new WindowAdapter() {
           public void windowClosing(WindowEvent e) {
           	int response = JOptionPane.showConfirmDialog(VentanaPrincipal.this, "¿Seguro que deseas salir?", "Salir", JOptionPane.YES_NO_OPTION);
           	if (response == JOptionPane.YES_OPTION) {
                   System.exit(0);
               }        
           }
		});
		
		
		pAbajo = new JPanel();
		pAbajo.setBackground(new Color(75, 0, 130));
		getContentPane().add(pAbajo,BorderLayout.SOUTH);
		
		btnUsuario = new JButton("Usuario");
		btnUsuario.setBackground(new Color(0, 153, 255));
		pAbajo.add(btnUsuario);
		pAbajo.add(botonEntrar);
		
//		btnUsuario.addActionListener(new ActionListener() {
//			
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
		
		btnUsuario.addActionListener((e)->{
			SwingUtilities.invokeLater(new Runnable() {
				
				@Override
				public void run() {
					new VentanaUsuario(ventanaActual);
				}
			});
		});
		
		
		setVisible(true);
	}
}

