package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;


public class VentanaConJuegos extends JFrame{
	private static final long serialVersionUID = 1L;
	private JPanel panelJuegos;
	private JTextField campoBusqueda;
	private ArrayList<JButton> listaBotones; 
	
	private JList<String> listaSugerencias; 
	private JScrollPane scrollListaSugerencias; 
	private DefaultListModel<String> modeloLista;
	private String[] todosLosNombres = {"SERPIENTE","BUSCAMINAS","BLACKJACK","SLOT MACHINE","Juego5","Juego6","Juego7","Juego8","Juego9","Juego10"};
	private String[] listaIconos = {"/imagenes/serpiente.jpg","/imagenes/buscaminas.png","/imagenes/blackjack.jpg","/imagenes/SlotMachine.jpg","","","","","",""};
	
	public VentanaConJuegos (JFrame previo) {
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
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
			public void actionPerformed(ActionEvent e) {JButton b = (JButton)e.getSource();
			
			// IDENTIFICACIÓN DEL BOTÓN POR ÍNDICE
			int indiceBoton = listaBotones.indexOf(b);
			
			if(indiceBoton == 1) { //BUSCAMINAS
				setVisible(false);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new BuscaMinas(VentanaConJuegos.this); // Lanza Buscaminas
					}
				});
			} else if (indiceBoton == 2) { // BLACKJACK
				setVisible(true);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new BlackJack(VentanaConJuegos.this);	
					}	
				});
			} else if(indiceBoton == 3) { // SLOT MACHINE 
				setVisible(false);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new slotMachine(VentanaConJuegos.this);	
					}	
				});
				
			} else if(indiceBoton == 0) { // 2048
				setVisible(false);
				SwingUtilities.invokeLater(new Runnable() {
					@Override
					public void run() {
						new Juego2048(VentanaConJuegos.this);	
					}	
				});
			} else {
				// Comportamiento por defecto para otros botones
				JOptionPane.showMessageDialog(VentanaConJuegos.this, "Iniciando " + todosLosNombres[indiceBoton]);
				}
			}
		};
		
		// CONSTRUCTOR DE BOTONES
		
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
		
		// MENU
		
		JMenuBar barraMenu = new JMenuBar();
		setJMenuBar(barraMenu);
		
		JMenu menuConfiguracion = new JMenu("Configuración");
		
		JMenuItem itemCambiarApariencia = new JMenuItem("Cambiar apariencia");
		itemCambiarApariencia.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaCambiarApariencia(VentanaConJuegos.this);				
					}
				});
			}
		});
		
		menuConfiguracion.add(itemCambiarApariencia);
		
		JMenuItem itemInformacion = new JMenuItem("Información del programa");
		itemInformacion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaInformacion(VentanaConJuegos.this);
					}
				});	
			}
		});
		
		menuConfiguracion.add(itemInformacion);
		
		menuConfiguracion.addSeparator();
		
		JMenuItem itemSalir = new JMenuItem("Salir");
		itemSalir.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				int response = JOptionPane.showConfirmDialog(VentanaConJuegos.this, "¿Deseas salir a la página principal?", "Salir", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					VentanaConJuegos.this.dispose();
				}
			}
		});
		
		menuConfiguracion.add(itemSalir);
		
		JMenu menuUsuario = new JMenu("Usuario");
		
		JMenuItem itemIniciarSesion = new JMenuItem("Iniciar sesión");
		itemIniciarSesion.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaUsuario(VentanaConJuegos.this);	
					}
				});
			}
		});
		
		menuUsuario.add(itemIniciarSesion);
		
		JMenuItem itemRegistrarse = new JMenuItem("Registrarse");
		itemRegistrarse.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaRegistrate(VentanaConJuegos.this);
					}
				});
			}
		});
		
		menuUsuario.add(itemRegistrarse);
		
		menuUsuario.addSeparator();
		
		JMenuItem itemVerPerfil = new JMenuItem("Ver perfil");
		itemVerPerfil.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaPerfil(VentanaConJuegos.this);
					}
				});
				
			}
		});
		itemVerPerfil.setEnabled(true); //Cuando tengamos el sistema de usuarios, este item estara desactivado a menos que hayas metido un usuario
		
		menuUsuario.add(itemVerPerfil);
		
		JMenu menuEstadisticas = new JMenu("Estadísticas");
		
		for (int i = 0; i<todosLosNombres.length; i++) {
			final int ii = i;
			JMenuItem itemEstadisticasJuego = new JMenuItem(todosLosNombres[i]);
			itemEstadisticasJuego.addActionListener(new ActionListener() {
				
				@Override
				public void actionPerformed(ActionEvent e) {
					SwingUtilities.invokeLater(new Runnable() {
						
						@Override
						public void run() {
							switch (ii) {
							case 0:
								new VentanaEjemploEstadisticas(VentanaConJuegos.this);
								break;
							default:
								System.out.println("No hay estadísticas para este juego");
							}
							
						}
					});
					
				}
			});
			menuEstadisticas.add(itemEstadisticasJuego);
		}
		
		JMenu menuLogros = new JMenu("Logros");
		
		JMenuItem itemLogrosPorJuego = new JMenuItem("Logros por juego");
		itemLogrosPorJuego.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				SwingUtilities.invokeLater(new Runnable() {
					
					@Override
					public void run() {
						new VentanaLogros(VentanaConJuegos.this);
					}
				});
				
			}
		});
		menuLogros.add(itemLogrosPorJuego);
		
		menuLogros.setEnabled(true); //Se activara si metes tu usuario
		
		barraMenu.add(menuConfiguracion);
		barraMenu.add(menuUsuario);
		barraMenu.add(menuEstadisticas);
		barraMenu.add(menuLogros);
		
		// CERRADO DE VENTANA
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				int response = JOptionPane.showConfirmDialog(VentanaConJuegos.this, "¿Deseas salir a la página principal?", "Salir", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					VentanaConJuegos.this.dispose();
				}
			}
			
			public void windowClosed(WindowEvent e) {
				previo.setVisible(true);
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

}


