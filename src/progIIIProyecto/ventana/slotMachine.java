package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;

import progIIIProyecto.domain.Usuario;

public class slotMachine extends JFrame{
private static final long serialVersionUID = 1L;

	// CREAMES EL OBJETO DEL USUARIO QUE ESTARA JUGANDO
	private Usuario usuario;
	
	//CREAMOS UNA LISTA CON LOS SLOTS QUE USAREMOS
	private JLabel[] slots;
	private JButton startButton = new JButton("START");
	private JButton stopButton = new JButton("STOP");
	private JButton btnVolver = new JButton("EXIT");
	
	private ArrayList<String> pokemonesN = new ArrayList<String>();
	private ArrayList<String> pokemonesF = new ArrayList<String>();
	private ArrayList<String> pokemonesA = new ArrayList<String>();
	private ArrayList<String> pokemonesE = new ArrayList<String>();
	private ArrayList<String> pokemonesG = new ArrayList<String>();
	private ArrayList<String> pokemonesB = new ArrayList<String>();
	private ArrayList<String> pokemonesP = new ArrayList<String>();
	
	private int tipo = 0;
	
	//INICIALIZAMOS EL HILO COMO VARIABLE Y HACEMOS UNA LISTA DE HILOS
	private Thread t;
	private ArrayList<Thread> hilos;
	
	public slotMachine(JFrame previo, Usuario usuario) {
		this.usuario = usuario;
		JFrame VentanaActual = this;
		JFrame VentanaAnterior = previo;
		pokemonesE.add("zekrom");
		pokemonesE.add("pikachu");
		pokemonesE.add("luxray");
		pokemonesE.add("emolga");
		pokemonesE.add("electivire");
		pokemonesE.add("eelektross"); 

		pokemonesN.add("snorlax");
		pokemonesN.add("eevee");
		pokemonesN.add("furret");
		pokemonesN.add("persian");
		pokemonesN.add("pidgeot");
		pokemonesN.add("tauros");
		
		pokemonesA.add("magikarp");
		pokemonesA.add("samurott");
		pokemonesA.add("blastoise");
		pokemonesA.add("piplup");
		pokemonesA.add("lapras");
		pokemonesA.add("mudkip");
		
		pokemonesF.add("groudon");
		pokemonesF.add("tepig");
		pokemonesF.add("reshiram");
		pokemonesF.add("charizard");
		pokemonesF.add("infernape");
		pokemonesF.add("arcanine");
		
		pokemonesG.add("lunala");
		pokemonesG.add("gengar");
		pokemonesG.add("banette");
		pokemonesG.add("cofagrigus");
		pokemonesG.add("giratina");
		pokemonesG.add("sableye");
		
		pokemonesB.add("volcarona");
		pokemonesB.add("venomoth");
		pokemonesB.add("heracross");
		pokemonesB.add("scizor");
		pokemonesB.add("leavanny");
		pokemonesB.add("vespiquen");
		
		pokemonesP.add("venusaur-f");
		pokemonesP.add("servine");
		pokemonesP.add("breloom");
		pokemonesP.add("maractus");
		pokemonesP.add("roserade");
		pokemonesP.add("tropius");
		
		//CREAMOS LA VENTANA PRINCIPAL
		this.setLayout(new BorderLayout());
		this.setTitle("Maquina De La Suerte");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setSize(1000, 700);
		this.setLocationRelativeTo(null);
		this.setBackground(new Color(28, 0, 51));
		
		//CREAMOS EL PANEL PRINCIPAL, DONDE ESTARÁ LA MAQUINA
		JPanel panelPrinci = new JPanel(new GridLayout(2, 3, 40, 15));
		slots = new JLabel[6];
		panelPrinci.setBackground(new Color(28, 0, 51));
		
		//AÑADIMOS EL PANEL PRINCIPAL A LA VENTANA
		this.add(panelPrinci);
		
		//COMO SON 6 ESPACIOS DE SLOTS
		for (int i = 0; i < 6; i++) {
			//HACEMOS QUE EL SLOT ELEGIDO SE ACTUALICE
			slots[i] = new JLabel();
			Random random = new Random();
			
			if(tipo==1) { //FUEGO
				this.actualizarLabel(slots[i], pokemonesF.get(random.nextInt(pokemonesF.size())));
			}else if(tipo==2) { //AGUA
				this.actualizarLabel(slots[i], pokemonesA.get(random.nextInt(pokemonesA.size())));
			}else if(tipo==3) { //ELECTRICO
				this.actualizarLabel(slots[i], pokemonesE.get(random.nextInt(pokemonesE.size())));
			}else if(tipo==4) { //NORMAL
				this.actualizarLabel(slots[i], pokemonesN.get(random.nextInt(pokemonesN.size())));
			}else if (tipo == 5){ //BICHO
				this.actualizarLabel(slots[i], pokemonesB.get(random.nextInt(pokemonesB.size())));
			}else if (tipo == 6){ //PLANTA
				this.actualizarLabel(slots[i], pokemonesP.get(random.nextInt(pokemonesP.size())));
			}else { //GHOST
				this.actualizarLabel(slots[i], pokemonesG.get(random.nextInt(pokemonesG.size())));
			}
			
			//AÑADIMOS EL SLOT ELEGIDO AL PANEL PRINCIPAL
			panelPrinci.add(slots[i]);
		}
		
		
		
		//CREAMOS UN MENU EN EL CUAL PODREMOS AÑADIR DIFERENTES FUNCIONALIDADES
		JMenuBar barraMenu = new JMenuBar();
		barraMenu.setBackground(new Color(28, 0, 51));
		barraMenu.setBorderPainted(false);//PARA QUE NO APAREZCA EL BORDE
		this.setJMenuBar(barraMenu);
		add(barraMenu, BorderLayout.NORTH);
		
		
		
		JMenu fichero = new JMenu("Opciones");
		fichero.setForeground(Color.WHITE);
		barraMenu.add(fichero);
		
		JMenuItem verInfo = new JMenuItem("Ver Información");
		JMenuItem verPuntaje = new JMenuItem("¿Cómo funciona el puntaje?");
		JMenuItem verEstadisticas = new JMenuItem("Ver Estadisticas");		
		
		JButton btnOk = new JButton("Entendido!");
		btnOk.setBackground(new Color(61, 0, 76));
		btnOk.setForeground(Color.WHITE);
		btnOk.setFont(new Font("Monospaced", Font.BOLD, 20));
		
		
		//LE QUITAMOS EL BORDE AL BOTÓN
		btnOk.setBorderPainted(false);
		btnOk.setFocusPainted(false);
		
		verInfo.addActionListener((e)->{
			JFrame ventVerInfo = new JFrame();
			ventVerInfo.setSize(300, 400);
			ventVerInfo.setLocationRelativeTo(null);
			ventVerInfo.setTitle("Información sobre La Maquina De La Suerte");
			
			JTextArea txt = new JTextArea();
			txt.setEditable(false);
			txt.setLineWrap(true);//AJUSTA LA LINEA AUTOMATICO
			txt.setWrapStyleWord(true);//HACE QUE SEPARE POR PALABRAS Y NO POR CARACTERES
			txt.setFocusable(false);//NO MUESTRA EL CURSOR
			txt.setText(
				    "La máquina cuenta con 6 rodillos independientes, cada uno girando a velocidad constante hasta que presiones STOP."
		    	    + "Al iniciar un giro con START, cada rodillo comienza a mostrar símbolos aleatorios."
		    	    + "Cuando detienes la máquina, los 6 rodillos se congelan y forman la jugada final."
		    	    + "¡Que la suerte esté de tu lado!");
			
			
			Font retroFont = new Font("Monospaced", Font.BOLD, 17);
	        txt.setFont(retroFont);
			txt.setForeground(new Color(210, 160, 255));
			
			JPanel aPanel = new JPanel();
			
			
			btnOk.addActionListener((e2)->{
				ventVerInfo.setVisible(false);
			});
			if(tipo == 1) { //FUEGO
				txt.setBackground(new Color(78, 26, 4));
				aPanel.setBackground(new Color(78, 26, 4));
				btnOk.setBackground(new Color(78, 26, 4));
			} else if(tipo == 2) { //AGUA
				txt.setBackground(new Color(1, 58, 99));
				aPanel.setBackground(new Color(1, 58, 99));
				btnOk.setBackground(new Color(1, 58, 99));
			}else if(tipo == 3) { //ELECTRICO
				txt.setBackground(new Color(1, 58, 99));
				aPanel.setBackground(new Color(1, 58, 99));
				btnOk.setBackground(new Color(1, 58, 99));
			}else if(tipo == 4) { //NORMAL
				txt.setBackground(Color.GRAY);
				aPanel.setBackground(Color.GRAY);
				btnOk.setBackground(Color.GRAY);
			}else if(tipo == 5) { //BICHO
 				txt.setBackground(new Color(121, 163, 29));
				aPanel.setBackground(new Color(121, 163, 29));
				btnOk.setBackground(new Color(121, 163, 29));
			}else if(tipo == 6) { //PLANTA
				txt.setBackground(new Color(31, 156, 84));
				aPanel.setBackground(new Color(31, 156, 84));
				btnOk.setBackground(new Color(31, 156, 84));
			} else { //GHOST
				txt.setBackground(new Color(28, 0, 51));
				aPanel.setBackground(new Color(28, 0, 51));
				btnOk.setBackground(new Color(28, 0, 51));
			}
			
			ventVerInfo.add(txt, BorderLayout.CENTER);
			ventVerInfo.add(aPanel, BorderLayout.SOUTH);

			
			aPanel.add(btnOk);
			
			
			ventVerInfo.setVisible(true);
		});
		//----------------------
		verPuntaje.addActionListener((e)->{
			JFrame ventVerPuntaje = new JFrame();
			ventVerPuntaje.setSize(400,500);
			ventVerPuntaje.setLocationRelativeTo(null);
			ventVerPuntaje.setTitle("Información del puntaje");
			
			JTable tabla = new JTable();
			
			
			
			JPanel pSur = new JPanel();
			pSur.setBackground(new Color(61, 0, 76));
			ventVerPuntaje.add(pSur, BorderLayout.SOUTH);
			pSur.add(btnOk);
			btnOk.addActionListener((e2)->{
				ventVerPuntaje.setVisible(false);
			});
			ventVerPuntaje.setVisible(true);
		});
		
		//----------------------
		//ESTADISTICAS
		verEstadisticas.addActionListener((e)->{
			new estadisticasSM(VentanaActual, tipo, null);
		});
		//----------------------

		
		fichero.add(verInfo);
		fichero.add(verPuntaje);
		fichero.add(verEstadisticas);		
		
		//CREAMOS EL PANEL PARA LOS BOTONES
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(btnVolver);
		
		startButton.setBackground(new Color(28, 0, 51));
		startButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		startButton.setForeground(Color.WHITE);
		startButton.setBorderPainted(false);
		startButton.setFocusPainted(false);
		
		stopButton.setBackground(new Color(28, 0, 51));
		stopButton.setFont(new Font("Monospaced", Font.BOLD, 20));
		stopButton.setForeground(Color.WHITE);
		stopButton.setBorderPainted(false);
		stopButton.setFocusPainted(false);
		
		btnVolver.setBackground(new Color(28, 0, 51));
		btnVolver.setFont(new Font("Monospaced", Font.BOLD, 20));
		btnVolver.setForeground(Color.WHITE);
		btnVolver.setBorderPainted(false);
		btnVolver.setFocusPainted(false);
		
		this.add(buttonPanel, BorderLayout.SOUTH);
		buttonPanel.setBackground(new Color(28, 0, 51));

		//--------------------------------------------------------------------------------
		// CERRADO DE VENTANA
		
		addWindowListener(new WindowAdapter() {
			
			public void windowClosing(WindowEvent e) {
				int response = JOptionPane.showConfirmDialog(slotMachine.this, "¿Deseas salir a la página anterior?", "Salir", JOptionPane.YES_NO_OPTION);
				if (response == JOptionPane.YES_OPTION) {
					slotMachine.this.dispose();
				}
			}
			
			public void windowClosed(WindowEvent e) {
				previo.setVisible(true);
			}
		});
		//--------------------------------------------------------------------------------

		
		JMenu estilo = new JMenu("Apariencia");
		estilo.setForeground(Color.WHITE);
		barraMenu.add(estilo);
		
		JMenuItem fuego = new JMenuItem("FUEGO");
		JMenuItem agua = new JMenuItem("AGUA");
		JMenuItem electrico = new JMenuItem("ELECTRICO");
		JMenuItem ghost = new JMenuItem("FANTASMA");
		JMenuItem normal = new JMenuItem("NORMAL");
		JMenuItem planta = new JMenuItem("PLANTA");
		JMenuItem bicho = new JMenuItem("BICHO");
		
		fuego.addActionListener((e)->{
			VentanaActual.setBackground(new Color(200, 55, 40));
			barraMenu.setBackground(new Color(200, 55, 40));
			buttonPanel.setBackground(new Color(78, 26, 4));
			panelPrinci.setBackground(new Color(200, 55, 40));
			startButton.setBackground(new Color(255, 106, 0));
			stopButton.setBackground(new Color(255, 143, 51));
			btnVolver.setBackground(new Color(255, 106, 0));
			tipo=1;
		});
		
		
		agua.addActionListener((e)->{
			VentanaActual.setBackground(new Color(0, 168, 232));
			barraMenu.setBackground(new Color(0, 168, 232));
			buttonPanel.setBackground(new Color(1, 58, 99));
			panelPrinci.setBackground(new Color(0, 168, 232));
			startButton.setBackground(new Color(0, 168, 232));
			stopButton.setBackground(new Color(0, 119, 182));
			btnVolver.setBackground(new Color(0, 168, 232));
			tipo=2;
		});
		
		electrico.addActionListener((e)->{
			VentanaActual.setBackground(new Color(255, 234, 0));
			barraMenu.setBackground(new Color(45, 45, 45));
			buttonPanel.setBackground(new Color(45, 45, 45));
			panelPrinci.setBackground(new Color(255, 234, 0));
			startButton.setBackground(new Color(255, 234, 0));
			stopButton.setBackground(new Color(255, 234, 0));
			btnVolver.setBackground(new Color(255, 234, 0));
			startButton.setForeground(new Color(45, 45, 45));
			stopButton.setForeground(new Color(45, 45, 45));
			btnVolver.setForeground(new Color(45, 45, 45));
			tipo=3;
		});
		
		ghost.addActionListener((e)->{
			VentanaActual.setBackground(new Color(28, 0, 51));
			barraMenu.setBackground(new Color(28, 0, 51));
			buttonPanel.setBackground(new Color(28, 0, 51));
			panelPrinci.setBackground(new Color(28, 0, 51));
			startButton.setBackground(new Color(28, 0, 51));
			stopButton.setBackground(new Color(28, 0, 51));
			btnVolver.setBackground(new Color(28, 0, 51));
			startButton.setForeground(Color.WHITE);
			stopButton.setForeground(Color.WHITE);
			btnVolver.setForeground(Color.WHITE);
			tipo=0;
		});
		
		normal.addActionListener((e)->{
			VentanaActual.setBackground(Color.LIGHT_GRAY);
			barraMenu.setBackground(Color.LIGHT_GRAY);
			buttonPanel.setBackground(Color.GRAY);
			panelPrinci.setBackground(Color.LIGHT_GRAY);
			startButton.setBackground(Color.LIGHT_GRAY);
			stopButton.setBackground(Color.LIGHT_GRAY);
			btnVolver.setBackground(Color.LIGHT_GRAY);
			startButton.setForeground(Color.BLACK);
			stopButton.setForeground(Color.BLACK);
			btnVolver.setForeground(Color.BLACK);
			tipo=4;
		});
		
		bicho.addActionListener((e)->{
			VentanaActual.setBackground(new Color(121, 163, 29));
			barraMenu.setBackground(new Color(121, 163, 29));
			buttonPanel.setBackground(new Color(121, 163, 29));
			panelPrinci.setBackground(new Color(121, 163, 29));
			startButton.setBackground(new Color(121, 163, 29));
			stopButton.setBackground(new Color(121, 163, 29));
			btnVolver.setBackground(new Color(121, 163, 29));
			startButton.setForeground(Color.BLACK);
			stopButton.setForeground(Color.BLACK);
			btnVolver.setForeground(Color.BLACK);
			tipo=5;
		});
		
		planta.addActionListener((e)->{
			VentanaActual.setBackground(new Color(31, 156, 84));
			barraMenu.setBackground(new Color(31, 156, 84));
			buttonPanel.setBackground(new Color(31, 156, 84));
			panelPrinci.setBackground(new Color(31, 156, 84));
			startButton.setBackground(new Color(31, 156, 84));
			stopButton.setBackground(new Color(31, 156, 84));
			btnVolver.setBackground(new Color(31, 156, 84));
			startButton.setForeground(Color.WHITE);
			stopButton.setForeground(Color.WHITE);
			btnVolver.setForeground(Color.WHITE);
			tipo=6;
		});
		
		
		estilo.add(fuego);
		estilo.add(agua);
		estilo.add(electrico);
		estilo.add(ghost);
		estilo.add(normal);
		estilo.add(bicho);
		estilo.add(planta);

		//--------------------------------------------------------------------------------

		
		//LE DAMOS A LOS BOTONES UNA FUNCIONALIDAD;

		btnVolver.addActionListener((e)->{
			VentanaActual.setVisible(false);
			VentanaAnterior.setVisible(true);
		});
		
		
		//HACEMOS QUE CUANDO PULSE EL BOTON DE START, SE ACTIVE
		//Y SE DESACTIVE EL DE STOP
		startButton.addActionListener(e -> {
			startButton.setEnabled(false);
			stopButton.setEnabled(true);
			estilo.setEnabled(false);
			
			//HACEMOS QUE EL BOTON DE START INICE EL JUEGO
			startGame();
		});
		
		//HACEMOS LO MISMO CON EL BOTON DE STOP
		
		stopButton.addActionListener(e -> {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			estilo.setEnabled(true);
			
			//HACEMOS QUE EL BOTON DE STOP PARE EL JUEGO
			stopGame();
		});
		
		//POR DEFECTO DEJAMOS QUE EL BOTON DE STOP ESTE ACTIVADO
		stopButton.setEnabled(false);
		
		
		setVisible(true);
	}
	
	//#########################################################################################################################//
	//INICIAMOS EL JUEGO
	private void startGame() {
		hilos = new ArrayList<Thread>();
		
		//HACEMOS EL FOR PARA LOS 6 HILOS
		for (int i = 0; i < 6; i++) {
			final int num = i;
			
			t = new Thread(new Runnable() {
				
				//RUNNABLE POR DEFECTO YA TRAE ESTE METODO INCORPORAO
				@Override
				public void run() {
					//MIENTRAS NO SE HAYA INTERRUMPIDO EL HILO ACTUAL
					while(!Thread.currentThread().isInterrupted()) {
						
						//CREAMOS LA VARIABLE RANDOM
						Random random = new Random();
						
						//HACEMOS EL INVOKELATER USANDO LA EXPRESION DEL 
						//EJERCICIO INICIAL
						if(tipo==0) { //GHOST
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesG.get(random.nextInt(pokemonesG.size()))));
						}else if(tipo==1) { //FUEGO
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesF.get(random.nextInt(pokemonesF.size()))));
						}else if(tipo==2) { //AGUA
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesA.get(random.nextInt(pokemonesA.size()))));
						}else if(tipo==3) { //ELECTRICO
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesE.get(random.nextInt(pokemonesE.size()))));
						}else if(tipo==4) { //NORMAL
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesN.get(random.nextInt(pokemonesN.size()))));
						}else if(tipo==5) { //BICHO
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesB.get(random.nextInt(pokemonesB.size()))));
						}else if(tipo==6) { //PLANTA
							SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],pokemonesP.get(random.nextInt(pokemonesP.size()))));
						}
						//HACEMOS EL TRY/CATCH
						try {
							Thread.sleep(100);
						} catch (InterruptedException ex) {
							Thread.currentThread().interrupt();
						}
					}
				}
			});
			//AÑADIMOS EL HILO t A LA LISTA DE HILOS
			hilos.add(t);
			//INICIAMOS EL HILO t
			t.start();
		}
	}
	
	
	//PARAMOS EL JUEGO
	private void stopGame() {
		//REVISAMOS CADA HILO EN EL FOR EN EL ARRAY DE LOS HILOS
		for(Thread hilo : hilos) {
			hilo.interrupt();
			
			//HACEMOS EL t.join, BLOQUEA EL HILO HASTA QUE TERMINE
			try {
				hilo.join();
			} catch (InterruptedException e) {
				//NO VAMOS A HACER NADA
			}
		}
	}
	
	
	//#########################################################################################################################//
	private void actualizarLabel(JLabel label, String pokemon) {
		
		java.net.URL imagePath = getClass().getResource("/imagenes/"+pokemon+".png");
		
		label.setToolTipText(pokemon);
		
		if(imagePath != null) {
			
			
			ImageIcon originalIcon = new ImageIcon(imagePath);
	        //ESCALAMOS LA IMAGEN AL TAMAÑO DEL LABEL
	        Image scaledImage = originalIcon.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
	        
	        label.setIcon(new ImageIcon(scaledImage));
	        
	        
		}else {
			//SI NO ENCUENTRA NADA, MUESTRA EL NOMBRE DEL POKEMON EN LUGAR DE LA IMAGEN
			label.setText(pokemon);
		}
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
	}
	
}
