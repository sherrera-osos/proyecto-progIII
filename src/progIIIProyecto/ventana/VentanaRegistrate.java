package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

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

import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Genero;
import progIIIProyecto.domain.Pais;
import progIIIProyecto.domain.Usuario;

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
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		
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
			this.dispose();
			ventanaAnterior.setVisible(true);
		});
		
		btnCrearUsuario.addActionListener((e)->{
			
			// He tenido que sacar las contraseñas a String aparte porque sino no se puede
			
			String contraseña = "";
			for (char caracter : txtContraseña.getPassword()) {
				contraseña += caracter;
			}
			
			String contraseñaRepetida = "";
			for (char caracter : txtContraseñaRepetida.getPassword()) {
				contraseñaRepetida += caracter;
			}
			
			// Aquí sacamos el género de los JRadioButton
			
			Genero generoElegido = Genero.Otro;
			
			if (Mas.isSelected()) {
				generoElegido = Genero.Masculino;
			} else if (Fem.isSelected()) {
				generoElegido = Genero.Femenino;
			}
			
			/// Aquí sacamos el telefono para poder ponerle un valor predetrminado en caso de que no se haya metido ninguno
			int tlf = 0;
			if(!aTel.getText().isEmpty()) {
				tlf = Integer.parseInt(aTel.getText());
			} 
			
			// Las condiciones para que todo vaya bien y se cree usuario y los errores en caso contrario
							
			if (txtNombre.getText().isEmpty() && !contraseña.isEmpty() && !contraseñaRepetida.isEmpty() && !txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir el nombre de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);
				
			} else if (!txtNombre.getText().isEmpty() && contraseña.isEmpty() && !contraseñaRepetida.isEmpty() && !txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir la contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else if (!txtNombre.getText().isEmpty() && !contraseña.isEmpty() && contraseñaRepetida.isEmpty() && !txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir de nuevo la contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);

			} else if (!txtNombre.getText().isEmpty() && !contraseña.isEmpty() && !contraseñaRepetida.isEmpty() && txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir el correo de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);
				
			} else if (txtNombre.getText().isEmpty() || contraseña.isEmpty() || contraseñaRepetida.isEmpty() || txtCorreo.getText().isEmpty()) {
				JOptionPane.showMessageDialog(null,"Falta introducir más de uno de los campos obligatorios.", "ERROR", JOptionPane.ERROR_MESSAGE);
			
			} else if (!contraseña.equals(contraseñaRepetida)) {
				JOptionPane.showMessageDialog(null,"Las contraseñas introducidas no coinciden.", "ERROR", JOptionPane.ERROR_MESSAGE);

				
			} else {
				Usuario usuarioNuevo = new Usuario(txtNombre.getText(), contraseña, tlf, txtCorreo.getText(),(Pais) sel.getSelectedItem(), generoElegido);
				GestorBD gestorBD = new GestorBD();
				gestorBD.subirUsuario(usuarioNuevo);
				
				JOptionPane.showMessageDialog(null, "¡Te has creado un nuevo usuario en PROYECTO!");
				
				ventanaActual.dispose();
				ventanaAnterior.setVisible(true);
				
			}
						
			
		});
		
		// CERRADO DE VENTANA
		
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				VentanaRegistrate.this.dispose();
				ventanaAnterior.setVisible(true);
			}
		});
		
		setVisible(true);
	}
}
