package progIIIProyecto.ventana;

import java.awt.Component;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.UIManager;

public class VentanaPerfil extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public VentanaPerfil(JFrame previo) {
		JFrame VentanaActual = this;
		JFrame VentanaAnterior = previo;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 350);
		setTitle("Perfil de usuario");
		setLocationRelativeTo(previo);
		
		//CREAMOS EL PANEL PRINCIPAL CON MARGEN Y LAYOUT VERTICAL
		JPanel panelP = new JPanel();
		panelP.setLayout(new BoxLayout(panelP, BoxLayout.Y_AXIS));
		panelP.setBorder(BorderFactory.createEmptyBorder(15,15,15,15));
		
		//CREAMOS EL ICONO INFORMATICO
		JLabel icono = new JLabel(UIManager.getIcon("OptionPane.informationIcon"));
		icono.setAlignmentX(Component.CENTER_ALIGNMENT);
		panelP.add(icono);
		
		JLabel titulo = new JLabel("PERFIL DE USUARIO");
		titulo.setAlignmentX(Component.LEFT_ALIGNMENT);
		titulo.setFont(titulo.getFont().deriveFont(Font.BOLD, 14f));
		panelP.add(titulo);
		panelP.add(Box.createVerticalStrut(10));
		
		VentanaActual.add(panelP);
		
		//TEXTO CON LOS DATOS 
		//(AUN NO TENEMOS FORMA DE GUARDAR LOS DATOS INGRESADOS
		StringBuilder sb = new StringBuilder("<html>");
		//ACA AÑADIMOS LOS DATOS INGRESADOS
		
		JLabel textoDatos = new JLabel(sb.toString());
	    textoDatos.setAlignmentX(Component.LEFT_ALIGNMENT);
	    panelP.add(textoDatos);
	    
	    panelP.add(Box.createVerticalStrut(15));
		
		//BOTON ACEPTAR
		JButton btnAceptar = new JButton("Aceptar");
		
		btnAceptar.addActionListener((e)->{
			VentanaActual.setVisible(false);
			VentanaAnterior.setVisible(true);
		});
		panelP.add(btnAceptar);
		
		setVisible(true);
	}

}
