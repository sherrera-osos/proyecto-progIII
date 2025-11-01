package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.midi.SysexMessage;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class VentanaUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnRegistrate, btnVolver, btnIniciarSesion;
	private JPanel pCentro,pAbajo,pArriba;
	private JFrame ventanaActual,ventanaAnterior;
	private JTextField txtnombreUsuario;
	private JPasswordField txtContraseña;
	private JLabel lblIniciarSesion, lblNombre, lblContrasenia;
	
	public VentanaUsuario(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;
		
		setTitle("Panatalla de usuario");
		setBounds(300,200,400,400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		pAbajo = new JPanel();
		getContentPane().add(pAbajo,BorderLayout.SOUTH);
		
		pCentro = new JPanel();
		pCentro.setLayout(new GridLayout(3,2,5,5));
		getContentPane().add(pCentro,BorderLayout.CENTER);
		
		pArriba = new JPanel();
		getContentPane().add(pArriba,BorderLayout.NORTH);
		
		
		lblContrasenia = new JLabel("Introduce la contraseña");
		lblNombre = new JLabel("Introduce tu nombre de usuario");
		lblIniciarSesion = new JLabel("Inicia sesion");
		
		txtContraseña = new JPasswordField(10);
		txtnombreUsuario = new JTextField(5);
		
		btnVolver = new JButton("Volver a la pagina de inicio");
		btnIniciarSesion  =new JButton("Iniciar sesion");
		btnRegistrate = new JButton("!REGISTRATE!");
		
		pAbajo.add(btnVolver);
		pAbajo.add(btnRegistrate);
		
		pArriba.add(lblIniciarSesion);
		
		pCentro.add(lblNombre);
		pCentro.add(txtnombreUsuario);
		pCentro.add(lblContrasenia);
		pCentro.add(txtContraseña);
		pCentro.add(btnIniciarSesion);

		
		
		
		btnIniciarSesion.addActionListener((e)->{
			String nombre = txtnombreUsuario.getText();
			String contraseña = txtContraseña.getText();
			
			boolean datos = true;

			if (nombre.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Te falta poner tu nombre de usuario","Error", JOptionPane.ERROR_MESSAGE);
				datos = false;

			}
			if (contraseña.isEmpty()) {
				JOptionPane.showMessageDialog(null, "Te falta poner tu contraseña","Error", JOptionPane.ERROR_MESSAGE);
				datos = false;

			}
			
			if (datos) {
				JOptionPane.showMessageDialog(null, "Bienvenido "+ nombre);
				ventanaActual.setVisible(false);
				ventanaAnterior.setVisible(true);
			}
			
		});
		
		btnRegistrate.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false);
				new VentanaRegistrate(ventanaActual);
			}
		});
		
		
		
		btnVolver.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setVisible(false);
				ventanaAnterior.setVisible(true);
			}
		});
		
		setVisible(true);
	}
	
}
