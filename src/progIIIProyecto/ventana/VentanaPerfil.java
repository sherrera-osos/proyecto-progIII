package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.border.TitledBorder;

import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Genero;
import progIIIProyecto.domain.Pais;
import progIIIProyecto.domain.Usuario;

public class VentanaPerfil extends JFrame{

	private static final long serialVersionUID = 1L;
	
	private JFrame ventanaActual;
	
	public VentanaPerfil(JFrame previo, Usuario usuario) {
		
		ventanaActual = this;
		
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(300, 500);
		setTitle("Perfil de usuario");
		setLocationRelativeTo(previo);
		
		JPanel panelDatos = new JPanel();
		panelDatos.setBorder(new TitledBorder("Información sobre el usuario"));
		panelDatos.setLayout(new GridLayout(3, 2));
		
		
		JLabel lblNombreUsuario = new JLabel("Nombre: ");
		JTextField txtNombreUsuario = new JTextField(usuario.getNombre(), 20);
		txtNombreUsuario.setEditable(false);
		panelDatos.add(lblNombreUsuario);
		panelDatos.add(txtNombreUsuario);
		
		JLabel lblContraseña = new JLabel("Contraseña ");
		JPasswordField txtContraseña = new JPasswordField(usuario.getContr(), 20);
		txtContraseña.setEditable(false);
		panelDatos.add(lblContraseña);
		panelDatos.add(txtContraseña);
		
		JLabel lblCorreo = new JLabel("Correo: ");
		JTextField txtCorreo = new JTextField(usuario.getCorreo(), 20);
		txtCorreo.setEditable(false);
		panelDatos.add(lblCorreo);
		panelDatos.add(txtCorreo);
		
		this.add(panelDatos, BorderLayout.NORTH);
		
		JPanel panelDatosExtra = new JPanel();
		panelDatosExtra.setBorder(new TitledBorder("Información adicional"));
		panelDatosExtra.setLayout(new GridLayout(3, 2));
		
		JLabel lblTelefono = new JLabel("Teléfono: ");
		JTextField txtTelefono = new JTextField(usuario.getTlf() + "", 20);
		txtTelefono.setEditable(false);
		panelDatosExtra.add(lblTelefono);
		panelDatosExtra.add(txtTelefono);
		
		JLabel lblPais = new JLabel("País: ");
		JTextField txtPais = new JTextField(usuario.getPais().toString(), 20);
		txtPais.setEditable(false);
		panelDatosExtra.add(lblPais);
		panelDatosExtra.add(txtPais);
		
		JComboBox<Pais> jcbPaises = new JComboBox<Pais>(Pais.values());
		jcbPaises.setSelectedItem(usuario.getPais());
		
		JLabel lblGenero = new JLabel("Género: ");
		JTextField txtGenero = new JTextField(usuario.getGenero().toString(), 20);
		txtGenero.setEditable(false);
		panelDatosExtra.add(lblGenero);
		panelDatosExtra.add(txtGenero);
		
		JComboBox<Genero> jcbGenero = new JComboBox<Genero>(Genero.values());
		jcbGenero.setSelectedItem(usuario.getGenero());
		
		
		this.add(panelDatosExtra, BorderLayout.CENTER);
		
		JPanel panelBotones = new JPanel();
		
		JButton btnModificar  =new JButton("Modificar usuario");
		btnModificar.setFont(new Font("Arial", Font.PLAIN, 12));
		
		JButton btnGuardar  =new JButton("Guardar cambios");
		btnGuardar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnGuardar.setVisible(false);
		
		JButton btnCancelar  =new JButton("Cancelar");
		btnCancelar.setFont(new Font("Arial", Font.PLAIN, 12));
		btnCancelar.setVisible(false);
		
		btnModificar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				ventanaActual.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
				
				btnModificar.setVisible(false);
				btnGuardar.setVisible(true);
				btnCancelar.setVisible(true);
				
				txtNombreUsuario.setEditable(true);
				txtContraseña.setEditable(true);
				txtTelefono.setEditable(true);
				txtCorreo.setEditable(true);
				
				panelDatosExtra.remove(3);
				panelDatosExtra.remove(4);
				
				panelDatosExtra.add(jcbPaises,3);
				panelDatosExtra.add(jcbGenero,5);
				
			}
		});
		
		btnGuardar.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {

				int response = JOptionPane.showConfirmDialog(VentanaPerfil.this, "¿Seguro que quieres guardar los cambios realizados? Saldras a la página de inicio", "Guardar cambios", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {

					// He tenido que sacar las contraseñas a String aparte porque sino no se puede

					String contraseña = "";
					for (char caracter : txtContraseña.getPassword()) {
						contraseña += caracter;
					}

					/// Aquí sacamos el telefono para poder ponerle un valor predeterminado en caso de que no se haya metido ninguno y comprobar que sea un int

					boolean esTlfInt = true;
					int tlf = 0;
					if(!txtTelefono.getText().isEmpty()) {
						try {
							System.out.println(txtTelefono.getText());
							tlf = Integer.parseInt(txtTelefono.getText());
						} catch (Exception e1) {
							esTlfInt = false;
						}
					} 

					// Las condiciones para que todo vaya bien y se cree usuario y los errores en caso contrario

					if (txtNombreUsuario.getText().isEmpty() && !contraseña.isEmpty() && !txtCorreo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,"Falta introducir el nombre de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);

					} else if (!txtNombreUsuario.getText().isEmpty() && contraseña.isEmpty() && !txtCorreo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,"Falta introducir la contraseña.", "ERROR", JOptionPane.ERROR_MESSAGE);

					} else if (!txtNombreUsuario.getText().isEmpty() && !contraseña.isEmpty() && txtCorreo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,"Falta introducir el correo de usuario.", "ERROR", JOptionPane.ERROR_MESSAGE);

					} else if (txtNombreUsuario.getText().isEmpty() || contraseña.isEmpty() || txtCorreo.getText().isEmpty()) {
						JOptionPane.showMessageDialog(null,"Falta introducir más de uno de los campos obligatorios.", "ERROR", JOptionPane.ERROR_MESSAGE);

					} else if (!esTlfInt) {
						JOptionPane.showMessageDialog(null,"Valores ilegales en campo 'Telefono'", "ERROR", JOptionPane.ERROR_MESSAGE);

					} else {
						Usuario usuarioModificado = new Usuario(usuario.getCodigo(),txtNombreUsuario.getText(), contraseña, tlf, txtCorreo.getText(), (Pais) jcbPaises.getSelectedItem(), (Genero) jcbGenero.getSelectedItem());
						GestorBD gestorBD = new GestorBD();
						gestorBD.modificarUsuario(usuarioModificado);
						JOptionPane.showMessageDialog(null, "Has modificado los valores de tu usuario");
						
						ventanaActual.dispose(); // En caso de que se modifique el usuario, se cierra la ventana actual
						previo.dispose(); // y la ventanaConJuegos de la que vienes para volver a la página de inicio, 
						                  // porque si no es demasiado complicado modificar el usuario con el que esta abierto 
										  // ventanaConJuegos y ventanaPerfil

					}
				}
			}
		});

		btnCancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				int response = JOptionPane.showConfirmDialog(VentanaPerfil.this, "¿Seguro que quieres cancelar los cambios realizados?", "Cancelar cambios", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {

					ventanaActual.dispose();
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							new VentanaPerfil(previo, usuario);							
						}
					});

				}
			}
		});


		panelBotones.add(btnModificar);
		panelBotones.add(btnGuardar);
		panelBotones.add(btnCancelar);
		
		this.add(panelBotones, BorderLayout.SOUTH);
		
		this.pack();
		
		setVisible(true);
	}

}
