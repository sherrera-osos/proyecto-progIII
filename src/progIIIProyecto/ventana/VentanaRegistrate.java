package progIIIProyecto.ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistrate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNombreUsuario, lblContraseña, lblContraseñaRepetida, lblCorreo;
	private JPanel pArriba, pCentro, pAbajo;
	private JTextField txtNombre,txtCorreo;
	private JPasswordField txtContraseña, txtContraseñaRepetida;
	private JButton btnVolver;
	private JFrame ventanaActual, ventanaAnterior;
	
	public VentanaRegistrate(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;
		
		setTitle("Ventana de registrase");
		setBounds(300,200,400,400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		pAbajo = new JPanel();
		getContentPane().add(pAbajo,BorderLayout.SOUTH);
		pArriba = new JPanel();
		getContentPane().add(pArriba,BorderLayout.NORTH);
		pCentro = new JPanel();
		getContentPane().add(pCentro,BorderLayout.CENTER);
		
		
		lblNombreUsuario = new JLabel("Introduce tu nombre de usuario: ");
		txtNombre = new JTextField(5);


		setVisible(true);
	}
}
