package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import progIIIProyecto.domain.Pais;

public class VentanaRegistrate extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JLabel lblNombreUsuario, lblContraseña, lblContraseñaRepetida, lblCorreo;
	private JPanel pAbajo;
	private JTextField txtNombre,txtCorreo;
	private JPasswordField txtContraseña, txtContraseñaRepetida;
	private JButton btnVolver, btnCrearUsuario;
	private JFrame ventanaActual, ventanaAnterior;
	
	public VentanaRegistrate(JFrame va) {
		ventanaActual = this;
		ventanaAnterior = va;
		
		setTitle("Registrarse");
		setBounds(300,200,400,400);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		JPanel info = new JPanel();
		info.setLayout(new BoxLayout(info, BoxLayout.Y_AXIS));
		
		JPanel subInfo = new JPanel();
		subInfo.setLayout(new GridLayout(4,2));
		subInfo.setBorder(new TitledBorder("Requisitos para registrarse"));
		
		lblNombreUsuario = new JLabel("Introduce tu nombre de usuario: ");
		txtNombre = new JTextField(10);
		subInfo.add(lblNombreUsuario);
		subInfo.add(txtNombre);

		lblContraseña = new JLabel("Introduce tu contraseña");
		txtContraseña = new JPasswordField(10);
		subInfo.add(lblContraseña);
		subInfo.add(txtContraseña);
		
		lblContraseñaRepetida = new JLabel("Vuelve a introducir tu contraseña");
		txtContraseñaRepetida = new JPasswordField(10);
		subInfo.add(lblContraseñaRepetida);
		subInfo.add(txtContraseñaRepetida);
		
		lblCorreo = new JLabel("Introduce tu correo electronico");
		txtCorreo = new JTextField(10);
		btnCrearUsuario = new JButton("Crear Usuario");
		subInfo.add(lblCorreo);
		subInfo.add(txtCorreo);
		
		info.add(subInfo);
		
		//--------PANEL DE INFORMACIÓN ADICIONAL----------
		JPanel infoA =  new JPanel();
		infoA.setLayout(new GridLayout(2,2));
		infoA.setBorder(new TitledBorder("Información Adicional"));
		
		JLabel tel = new JLabel("Teléfono");
		JTextField aTel = new JTextField(10);
		infoA.add(tel);
		infoA.add(aTel);
		
		JLabel pais = new JLabel("País:");
		// CREAMOS LAS OPCIONES DEL COMBOBOX
		Pais[] opciones = Pais.values();
		JComboBox<Pais> sel = new JComboBox<Pais>(opciones);
		infoA.add(pais);
		infoA.add(sel);
		
		info.add(infoA);
		
		//-----------------PANEL DE GÉNERO----------
		
		JPanel gen = new JPanel();
		gen.setBorder(new TitledBorder("Género"));
		JRadioButton Mas = new JRadioButton("Masculino");
		JRadioButton Fem = new JRadioButton("Femenino");
		JRadioButton Otr = new JRadioButton("Otro");
		ButtonGroup group = new ButtonGroup();
		group.add(Mas);
		group.add(Fem);
		group.add(Otr);
		gen.add(Mas);
		gen.add(Fem);
		gen.add(Otr);
		
		info.add(gen);

		getContentPane().add(info, BorderLayout.CENTER);
		
		pAbajo = new JPanel();
		getContentPane().add(pAbajo,BorderLayout.SOUTH);


		btnVolver  =new JButton("Volver");
		pAbajo.add(btnVolver);
		pAbajo.add(btnCrearUsuario);
		
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
