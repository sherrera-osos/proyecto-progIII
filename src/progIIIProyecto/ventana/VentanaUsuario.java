package progIIIProyecto.ventana;

import java.awt.*;
import javax.swing.*;

import progIIIProyecto.BD.GestorBD;

public class VentanaUsuario extends JFrame {

    private static final long serialVersionUID = 1L;

    private JTextField txtnombreUsuario;
    private JPasswordField txtContraseña;
    private JButton btnIniciarSesion, btnRegistrate, btnVolver;
    private JPanel pTitulo, pCampos, pBotones, pCentro, pAbajo;
    private JLabel lblUsuario, lblPass, titulo;
    private JFrame ventanaActual, ventanaAnterior;
    
    
    public VentanaUsuario(JFrame va) {

        ventanaActual = this;
        ventanaAnterior = va;

        setTitle("Iniciar Sesión");
        setSize(420, 260);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);

        getContentPane().setBackground(new Color(245, 245, 245));
        setLayout(new BorderLayout());
        

        pCentro = new JPanel();
        pCentro.setBackground(Color.WHITE);
        pCentro.setLayout(new GridLayout(3, 1, 10, 10));
        add(pCentro, BorderLayout.CENTER);

        
        pTitulo = new JPanel();
        pTitulo.setBackground(Color.WHITE);
        titulo = new JLabel("Iniciar Sesión");
        titulo.setFont(new Font("Arial", Font.BOLD, 20));
        pTitulo.add(titulo);

        
        pCampos = new JPanel(new GridLayout(2, 2, 10, 10));
        pCampos.setBackground(Color.WHITE);

        lblUsuario = new JLabel("Usuario:");
        txtnombreUsuario = new JTextField(10);

        lblPass = new JLabel("Contraseña:");
        txtContraseña = new JPasswordField(10);

        txtnombreUsuario.setBackground(new Color(250, 250, 250));
        txtContraseña.setBackground(new Color(250, 250, 250));

        pCampos.add(lblUsuario);
        pCampos.add(txtnombreUsuario);
        pCampos.add(lblPass);
        pCampos.add(txtContraseña);

        pBotones = new JPanel();
        pBotones.setBackground(Color.WHITE);

        btnIniciarSesion = new JButton("Acceder");
        btnRegistrate = new JButton("Registrarse");

        btnIniciarSesion.setBackground(new Color(66, 133, 244));
        btnIniciarSesion.setForeground(Color.WHITE);

        btnRegistrate.setBackground(new Color(230, 230, 230));

        pBotones.add(btnIniciarSesion);
        pBotones.add(btnRegistrate);
        
        
        pCentro.add(pTitulo);
        pCentro.add(pCampos);
        pCentro.add(pBotones);

        
        pAbajo = new JPanel();
        pAbajo.setBackground(new Color(245, 245, 245));
        btnVolver = new JButton("Volver");
        btnVolver.setBackground(new Color(230, 230, 230));
        pAbajo.add(btnVolver);
        add(pAbajo, BorderLayout.SOUTH);
        
        btnIniciarSesion.addActionListener(e -> {
            String nombre = txtnombreUsuario.getText();
            String contraseña = new String(txtContraseña.getPassword());

            if (nombre.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce tu usuario.");
                return;
            }
            if (contraseña.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Introduce tu contraseña.");
                return;
            }
            
            GestorBD gestorBD = new GestorBD();
            
            int comprobante = gestorBD.comprobarUsuario(nombre, contraseña, this);
            
            if (comprobante != -1) {
            JOptionPane.showMessageDialog(this, "Bienvenido " + nombre);
            
            SwingUtilities.invokeLater(() -> new VentanaConJuegos(gestorBD.obtenerUsuario(comprobante)));
            
            ventanaActual.dispose();
            ventanaAnterior.dispose();
            } else {
            	return;
            }
            
        });

        btnRegistrate.addActionListener(e -> {
            ventanaActual.setVisible(false);
            SwingUtilities.invokeLater(() -> new VentanaRegistrate(ventanaActual));
        });

        btnVolver.addActionListener(e -> {
        	ventanaAnterior.setVisible(true);
            ventanaActual.dispose();
        });

        setVisible(true);
    }
}
