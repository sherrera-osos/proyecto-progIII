package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class VentanaConJuegos extends JFrame implements WindowListener {
	private static final long serialVersionUID = 1L;
	
	private JFrame previo;
	

	private JPanel panelJuegos;
	private JTextField campoBusqueda;
	private ArrayList<JButton> listaBotones; 
	

	private JList<String> listaSugerencias; 
	private JScrollPane scrollListaSugerencias; 
	private DefaultListModel<String> modeloLista; 
	private String[] todosLosNombres = {"SERPIENTE","BUSCAMINAS","BLACKJACK","Juego4","Juego5","Juego6","Juego7","Juego8","Juego9","Juego10"};
	private String[] listaIconos = {"/imagenes/serpiente.jpg","/imagenes/buscaminas.png","/imagenes/blackjack.jpg","","","","","","",""};
	
	public VentanaConJuegos (JFrame previo) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.previo = previo;
		addWindowListener(this);
		setTitle("VentanaPrincipal con Juegos"); //Provisional 
		setSize(800, 600);
		setLocationRelativeTo(null);
		
		JPanel panelBusquedaCompleto = new JPanel(new BorderLayout());

		JPanel panelBarra = new JPanel(new BorderLayout(5, 5));
		panelBarra.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		panelBarra.add(new JLabel("Buscar Juego: "), BorderLayout.WEST);
		campoBusqueda = new JTextField();
		panelBarra.add(campoBusqueda, BorderLayout.CENTER);

		panelBusquedaCompleto.add(panelBarra, BorderLayout.NORTH);

		modeloLista = new DefaultListModel<>();
		listaSugerencias = new JList<>(modeloLista);
		scrollListaSugerencias = new JScrollPane(listaSugerencias);
		scrollListaSugerencias.setVisible(false); 

		panelBusquedaCompleto.add(scrollListaSugerencias, BorderLayout.CENTER);
		
		add(panelBusquedaCompleto, BorderLayout.NORTH);
		
		panelJuegos = new JPanel();
		panelJuegos.setLayout(new GridLayout(5, 2, 10, 10));
		
		Color moradoOscuro = new Color(75,0,130);
		panelJuegos.setBackground(moradoOscuro);	
		
		listaBotones = new ArrayList<>();

		ActionListener listenerJuego = new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JButton b = (JButton)e.getSource();
				JOptionPane.showMessageDialog(VentanaConJuegos.this, "Iniciando " + b.getText());
			}
		};
		
		for (int i = 0; i<todosLosNombres.length; i++) {
			ImageIcon icono = new ImageIcon(VentanaConJuegos.class.getResource(listaIconos[i]));
			JButton botonJuego = new JButton(icono);
			botonJuego.setToolTipText(todosLosNombres[i]);
			botonJuego.addActionListener(listenerJuego);
			listaBotones.add(botonJuego);
			panelJuegos.add(botonJuego);
			
		}
		
		add(panelJuegos, BorderLayout.CENTER);
		
		campoBusqueda.getDocument().addDocumentListener(new DocumentListener() {
			@Override
			public void insertUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> actualizarSugerencias());
			}
			@Override
			public void removeUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> actualizarSugerencias());
			}
			@Override
			public void changedUpdate(DocumentEvent e) {
				SwingUtilities.invokeLater(() -> actualizarSugerencias());
			}
		});

		listaSugerencias.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent evt) {
				if (evt.getClickCount() == 2) { 
					String nombreJuego = listaSugerencias.getSelectedValue();
					if (nombreJuego != null) {
						JOptionPane.showMessageDialog(VentanaConJuegos.this, "Iniciando " + nombreJuego);
						campoBusqueda.setText(""); 
						scrollListaSugerencias.setVisible(false); 
					}
				}
			}
		});
		
		setVisible(true);
	}
		
	private void actualizarSugerencias() {
		String textoBusqueda = campoBusqueda.getText().toLowerCase().trim();
		
		modeloLista.clear(); 
		
		if (textoBusqueda.isEmpty()) {
			scrollListaSugerencias.setVisible(false); 
		} else {
			for (String nombre : todosLosNombres) {
				if (nombre.toLowerCase().contains(textoBusqueda)) {
					modeloLista.addElement(nombre); 
				}
			}
			scrollListaSugerencias.setVisible(modeloLista.getSize() > 0);
		}
		scrollListaSugerencias.getParent().revalidate();
		scrollListaSugerencias.getParent().repaint();
	}

	@Override
	public void windowOpened(WindowEvent e) {
	}
	@Override
	public void windowClosing(WindowEvent e) {
		int response = JOptionPane.showConfirmDialog(VentanaConJuegos.this, "¿Deseas salir a la página principal?", "Salir", JOptionPane.YES_NO_OPTION); //Provisional (Asier)
		if (response == JOptionPane.YES_OPTION) {
			this.dispose();
		}
	}
	
	@Override
	public void windowClosed(WindowEvent e) {
		previo.setVisible(true);
	}
	@Override
	public void windowIconified(WindowEvent e) {
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
	}
	@Override
	public void windowActivated(WindowEvent e) {
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
	}
	
}


