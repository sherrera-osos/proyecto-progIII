package progIIIProyecto.ventana;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaRegistrate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNombreUsuario, lblContraseña, lblContraseñaRepetida, lblCorreo, lblTitulo;
	private JPanel pArriba, pCentro, pAbajo;
	private JTextField txtNombre,txtCorreo;
	private JPasswordField txtContraseña, txtContraseñaRepetida;
	private JButton btnVolver, btnCrearUsuario;
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
		
		lblTitulo = new JLabel("Requisitos pa registrate");
		pArriba.add(lblTitulo);
		
		
		lblNombreUsuario = new JLabel("Introduce tu nombre de usuario: ");
		txtNombre = new JTextField(38);
		lblContraseña = new JLabel("Introduce tu contraseña");
		txtContraseña = new JPasswordField(38);
		lblContraseñaRepetida = new JLabel("Vuelve a introducir tu contraseña");
		txtContraseñaRepetida = new JPasswordField(38);
		lblCorreo = new JLabel("Introduce tu correo electronico");
		txtCorreo = new JTextField(38);
		btnCrearUsuario = new JButton("Crear Usuario");
		
		pCentro.add(lblNombreUsuario);
		pCentro.add(txtNombre);
		pCentro.add(lblContraseña);
		pCentro.add(txtContraseña);
		pCentro.add(lblContraseñaRepetida);
		pCentro.add(txtContraseñaRepetida);
		pCentro.add(lblCorreo);
		pCentro.add(txtCorreo);
		pCentro.add(btnCrearUsuario);


		btnVolver  =new JButton("Volver a la pagina de iniciar sesion");
		pAbajo.add(btnVolver);

		
		btnVolver.addActionListener((e)->{
			ventanaActual.setVisible(false);
			ventanaAnterior.setVisible(true);
		});
		
		btnCrearUsuario.addActionListener((e)->{
			String nombre = txtNombre.getText();
			String correo = txtCorreo.getText();
			String c1 = txtContraseña.getText();
			String c2 = txtContraseñaRepetida.getText();
			
			boolean datos = true;
			
			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir el nombre de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);
				datos = false;
			}
			if (correo.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir el correo de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);
				datos = false;

			}
			if (c1.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir la contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);
				datos = false;

			}
			if (c2.isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir de nuevo la contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);
				datos = false;

			}
			
			if (datos) {
				JOptionPane.showMessageDialog(null, "!Te has creado un nuevo usuario en PROYECTO!");
				
				ventanaActual.setVisible(false);
				ventanaAnterior.setVisible(true);
			}
						
			
		});
		
		setVisible(true);
	}
}
