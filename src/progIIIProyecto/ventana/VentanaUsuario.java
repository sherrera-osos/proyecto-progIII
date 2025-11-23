package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

public class VentanaUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JButton btnRegistrate, btnVolver, btnIniciarSesion;
	private JPanel pData,pAbajo;
	private JFrame ventanaActual,ventanaAnterior;
	private JTextField txtnombreUsuario;
	private JPasswordField txtContraseña;
	private JLabel lblNombre, lblContrasenia;
	
	public VentanaUsuario(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;
		
		setTitle("Pantalla de usuario");
		setBounds(300,200,400,400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		
		pAbajo = new JPanel();
		getContentPane().add(pAbajo,BorderLayout.SOUTH);
		
		//CREAMOS EL PANEL DONDE METEREMOS LOS SUBPANELES CON LA INFO
		pData = new JPanel();
		getContentPane().add(pData, BorderLayout.CENTER);
		//DE ESTA MANERA PERMITE TAMAÑOS DIFERENTES DE SUBPANELES
		pData.setLayout(new BoxLayout(pData, BoxLayout.Y_AXIS));
		
		//-----------------1ER SUB PANEL-------------------
		JPanel infoP = new JPanel();
		infoP.setLayout(new GridLayout(3,2));
		infoP.setBorder(new TitledBorder("Datos Personales"));
		
		lblNombre = new JLabel("Introduce tu nombre de usuario");
		txtnombreUsuario = new JTextField(10);
		infoP.add(lblNombre);
		infoP.add(txtnombreUsuario);
		
		lblContrasenia = new JLabel("Introduce la contraseña");
		txtContraseña = new JPasswordField(10);
		infoP.add(lblContrasenia);
		infoP.add(txtContraseña);
		
		btnIniciarSesion  =new JButton("ACCEDER");
		btnRegistrate = new JButton("REGISTRARSE");
		infoP.add(btnIniciarSesion);
		infoP.add(btnRegistrate);
		pData.add(infoP);
	
			
		
		
		btnVolver = new JButton("Volver");
		pAbajo.add(btnVolver);
		
		
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
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaRegistrate(ventanaActual);
					}
				});
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
