package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;

import progIIIProyecto.BD.GestionSMBD;
import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.domain.Usuario;

import javax.swing.AbstractAction;

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
	
	private JPanel panelPrinci;
	private JMenuBar barraMenu;
	private JPanel buttonPanel;
	private JFrame ventVerPuntaje;
	private JTextArea txtArea;
	
	private int tipo = 0;
	
	//PARA LA PUNTUACIÓN
	private int score = 0;
	private JLabel lblScore;
	private int intentos = 0;
	private JLabel lblIntentos;
	
	//INICIALIZAMOS EL HILO COMO VARIABLE Y HACEMOS UNA LISTA DE HILOS
	private Thread t;
	private ArrayList<Thread> hilos;
	
	private void aplicarTheme(String themeName) {
		
		GestionSMBD gestor = new GestionSMBD();
		Map<String, Color> c = gestor.cargarTheme(themeName);
		
		if(c.isEmpty()) {
			System.err.println("Theme vacio o no encontrado: "+themeName);
			return;
		}
		
		getContentPane().setBackground(c.get("background_main"));
	    panelPrinci.setBackground(c.get("panel_principal"));
	    buttonPanel.setBackground(c.get("panel_botones"));
	    barraMenu.setBackground(c.get("menu_bar"));

	    startButton.setBackground(c.get("button_primary"));
	    stopButton.setBackground(c.get("button_secondary"));
	    btnVolver.setBackground(c.get("button_primary"));
	    
	    Color textColor = c.get("button_text");
	    startButton.setForeground(textColor);
	    stopButton.setForeground(textColor);
	    btnVolver.setForeground(textColor);
	}
	
	public slotMachine(JFrame previo, Usuario usuario) {
		this.usuario = usuario;
		int codUsuario = usuario.getCodigo();
		
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
		panelPrinci = new JPanel(new GridLayout(2, 3, 40, 15));
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
		barraMenu = new JMenuBar();
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
		JMenuItem guardarP = new JMenuItem("Guardar puntaje");
		
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
			txt.setForeground(Color.WHITE);
			
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
				txt.setBackground(new Color(45, 45, 45));
				aPanel.setBackground(new Color(45, 45, 45));
				btnOk.setBackground(new Color(45, 45, 45));
			}else if(tipo == 4) { //NORMAL
				txt.setBackground(Color.LIGHT_GRAY);
				aPanel.setBackground(Color.LIGHT_GRAY);
				btnOk.setBackground(Color.LIGHT_GRAY);
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
			ventVerPuntaje = new JFrame();
			ventVerPuntaje.setSize(400,300);
			ventVerPuntaje.setLocationRelativeTo(null);
			ventVerPuntaje.setTitle("Información del puntaje");

			txtArea = new JTextArea();
			txtArea.setEditable(false);
			txtArea.setLineWrap(true);//AJUSTA LA LINEA AUTOMATICO
			txtArea.setWrapStyleWord(true);//HACE QUE SEPARE POR PALABRAS Y NO POR CARACTERES
			txtArea.setFocusable(false);//NO MUESTRA EL CURSOR
			txtArea.setText(
					"SISTEMA DE PUNTUACIÓN\n\n 6 iguales  → 1000 puntos (JACKPOT)\n 5 iguales  → 500 puntos\n 4 iguales  → 200 puntos\n" +
						    " 3 iguales  → 100 puntos\n 2 iguales  → 30 puntos\n Menos de 2 → 0 puntos\n");
			
			Font retroFont = new Font("Monospaced", Font.BOLD, 17);
	        txtArea.setFont(retroFont);
			txtArea.setForeground(Color.WHITE);
			txtArea.setBackground(new Color(61, 0, 76));
			
			ventVerPuntaje.add(txtArea, BorderLayout.CENTER);
			
			JPanel pSur = new JPanel();
			ventVerPuntaje.add(pSur, BorderLayout.SOUTH);
			pSur.add(btnOk);
			if(tipo == 1) { //FUEGO
				txtArea.setBackground(new Color(78, 26, 4));
				pSur.setBackground(new Color(78, 26, 4));
				btnOk.setBackground(new Color(78, 26, 4));
			} else if(tipo == 2) { //AGUA
				txtArea.setBackground(new Color(1, 58, 99));
				pSur.setBackground(new Color(1, 58, 99));
				btnOk.setBackground(new Color(1, 58, 99));
			}else if(tipo == 3) { //ELECTRICO
				txtArea.setBackground(new Color(45, 45, 45));
				pSur.setBackground(new Color(45, 45, 45));
				btnOk.setBackground(new Color(45, 45, 45));
			}else if(tipo == 4) { //NORMAL
				txtArea.setBackground(Color.LIGHT_GRAY);
				pSur.setBackground(Color.LIGHT_GRAY);
				btnOk.setBackground(Color.LIGHT_GRAY);
			}else if(tipo == 5) { //BICHO
				txtArea.setBackground(new Color(121, 163, 29));
				pSur.setBackground(new Color(121, 163, 29));
				btnOk.setBackground(new Color(121, 163, 29));
			}else if(tipo == 6) { //PLANTA
				txtArea.setBackground(new Color(31, 156, 84));
				pSur.setBackground(new Color(31, 156, 84));
				btnOk.setBackground(new Color(31, 156, 84));
			} else { //GHOST
				txtArea.setBackground(new Color(28, 0, 51));
				pSur.setBackground(new Color(28, 0, 51));
				btnOk.setBackground(new Color(28, 0, 51));
			}
			btnOk.addActionListener((e2)->{
				ventVerPuntaje.setVisible(false);
			});
			ventVerPuntaje.setVisible(true);
		});
		
		//----------------------
		//ESTADISTICAS
		verEstadisticas.addActionListener((e)->{
			new estadisticasSM(VentanaActual, tipo);
		});
		
		//----------------------
		guardarP.addActionListener(e->{
			Puntaje puntaje = new Puntaje("slotMachine", score, intentos, codUsuario);
			GestorBD gestor = new GestorBD();
			gestor.subirPuntaje(puntaje);
			
			JOptionPane.showMessageDialog(this, 
					"Puntaje guardado:\nPuntaje: "+score+"\nIntentos: "+intentos,
					"Guardado", JOptionPane.INFORMATION_MESSAGE);
		});
		//----------------------
		
		fichero.add(verInfo);
		fichero.add(verPuntaje);
		fichero.add(verEstadisticas);	
		fichero.add(guardarP);
		
		//CREAMOS EL PANEL PARA LOS BOTONES
		buttonPanel = new JPanel();
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
			aplicarTheme("fuego");
			tipo=1;
		});
		
		
		agua.addActionListener((e)->{
			aplicarTheme("agua");
			tipo=2;
		});
		
		electrico.addActionListener((e)->{
			aplicarTheme("electrico");
			tipo=3;
		});
		
		ghost.addActionListener((e)->{
			aplicarTheme("ghost");
			tipo=0;
		});
		
		normal.addActionListener((e)->{
			aplicarTheme("normal");
			tipo=4;
		});
		
		bicho.addActionListener((e)->{
			aplicarTheme("bicho");
			tipo=5;
		});
		
		planta.addActionListener((e)->{
			aplicarTheme("planta");
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
			intentos++;
			actualizarIntentos();
			
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
		
		
		lblScore = new JLabel("PUNTOS: 0");
		lblScore.setForeground(Color.WHITE);
		lblScore.setFont(new Font("Impact", Font.BOLD, 18));
		lblScore.setHorizontalAlignment(JLabel.CENTER);
		barraMenu.add(lblScore);
		
		lblIntentos = new JLabel("		INTENTOS: 0");
		lblIntentos.setForeground(Color.WHITE);
		lblIntentos.setFont(new Font("Impact", Font.BOLD, 18));
		lblIntentos.setHorizontalAlignment(JLabel.CENTER);
		barraMenu.add(javax.swing.Box.createHorizontalGlue());
		barraMenu.add(lblIntentos);
		
		//--------------------------------------------------//
		//--------------------------------------------------//
		//EVENTO DE TECLADO PARA CERRAR LA VENTANA CTR+E
		btnVolver.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_E , KeyEvent.CTRL_DOWN_MASK), "exit");

		btnVolver.getActionMap().put("exit", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				btnVolver.doClick();
			}
		});
		
		//EVENTO DE TECLADO PARA EMPEZAR A JUGAR CTR+SPACE
		startButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE , KeyEvent.CTRL_DOWN_MASK), "start");

		startButton.getActionMap().put("start", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				startButton.doClick(); 
			}
		});
		
		//EVENTO DE TECLADO PARA EMPEZAR A JUGAR CTR+ENTER
		stopButton.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ENTER , KeyEvent.CTRL_DOWN_MASK), "stop");

		stopButton.getActionMap().put("stop", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				stopButton.doClick();
			}
		});
		
		//EVENTO DE TECLADO PARA CAMBIAR LA APARIENCIA CON CTR+T
		this.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_T, KeyEvent.CTRL_DOWN_MASK), "changeTheme");
		//getRootPane() HACE QUE SEA ESTABLE Y DETECTE EL JCOMPONENT
		this.getRootPane().getActionMap().put("changeTheme", new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent e) {
				cambiarTheme();
			}
		});
		
		//--------------------------------------------------//
		//--------------------------------------------------//
		setVisible(true);
	}
	
	//METODO PARA EVENTO DE TECLADO DE ACTUALIZAR EL THEME
	private String[] themes = {
		    "ghost", "fuego", "agua", "electrico", "normal", "bicho", "planta"};
	
	private int themeIndex = 0;
	private void cambiarTheme() {
		    themeIndex = (themeIndex + 1) % themes.length;
		    String nuevoTheme = themes[themeIndex];
		    aplicarTheme(nuevoTheme);
		    tipo=themeIndex;
	}
	
	//METODO PARA ACTUALIZAR LOS INTENTOS
	private void actualizarIntentos() {
		lblIntentos.setText("INTENTOS: "+intentos);
	}
	
	//METODO PARA ACTUALIZAR EL PUNTAJE
	private void actualizarScore() {
	    lblScore.setText("SCORE: " + score);
	}
	
	//METODO PARA CALCULAR LOS PUNTOS OBTENIDOS
	private int calcularPuntos() {
		//CREAMOS UN MAPA COMO CONTADOR PARA IR SUMANDO LOS PUNTOS
	    Map<String, Integer> contador = new HashMap<>();

	    for (JLabel slot : slots) {
	        String pokemon = slot.getToolTipText();//CON EL TOOLTIPTEXT OBTENEMOS EL NOMBRE DEL POKEMON
	        contador.put(pokemon, contador.getOrDefault(pokemon, 0) + 1);
	    }

	    int maxIguales = 0;
	    for (int cantidad : contador.values()) {
	        if (cantidad > maxIguales) {
	            maxIguales = cantidad;
	        }
	    }
	    switch (maxIguales) {
	        case 6: return 1000;//SI TODOS LAS IMAGENES SON IGUALES
	        case 5: return 500;
	        case 4: return 200;
	        case 3: return 100;
	        case 2: return 30;
	        default: return 0;//SI LAS IMAGENES NO COINCIDEN
	    }
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
		int puntosGanados = calcularPuntos();
		score += puntosGanados;
		actualizarScore();
		if (puntosGanados == 1000) {
		    JOptionPane.showMessageDialog(this,
		        "¡Has ganado +" + puntosGanados + " puntos!",
		        "WOW",
		        JOptionPane.INFORMATION_MESSAGE);
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
