package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.Image;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class slotMachine extends JFrame{
private static final long serialVersionUID = 1L;
	
	//CREAMOS UNA LISTA CON LOS SLOTS QUE USAREMOS
	private JLabel[] slots;
	private JButton startButton = new JButton("START");
	private JButton stopButton = new JButton("STOP");
	private JButton btnVolver = new JButton("VOLVER");
	
	private ArrayList<String> figuras = new ArrayList<String>();
	
	private Thread t;
	private ArrayList<Thread> hilos;
	
	public slotMachine(JFrame previo) {
		JFrame VentanaActual = this;
		JFrame VentanaAnterior = previo;
		figuras.add("diamante");
		figuras.add("7");
		figuras.add("Cereza");
		figuras.add("trebol");
		figuras.add("corona");
		figuras.add("cerdo");
		
		
		//CREAMOS LA VENTANA PRINCIPAL
		this.setLayout(new BorderLayout());
		this.setTitle("Maquina De La Suerte");
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		this.setResizable(false);
		this.setSize(700, 500);
		this.setLocationRelativeTo(null);

		
		//CREAMOS EL PANEL PRINCIPAL, DONDE ESTARÁ LA MAQUINA
		JPanel panelPrinci = new JPanel(new GridLayout(1, 3, 50, 100));
		slots = new JLabel[3];
		panelPrinci.setBackground(new Color(75, 0, 130));
		
		//AÑADIMOS EL PANEL PRINCIPAL A LA VENTANA
		this.add(panelPrinci);
		
		//COMO SON SOLO 3 ESPACIOS DE SLOTS
		for (int i = 0; i < 3; i++) {
			//HACEMOS QUE EL SLOT ELEGIDO SE ACTUALICE
			slots[i] = new JLabel();
			Random random = new Random();
			
			this.actualizarLabel(slots[i], figuras.get(random.nextInt(figuras.size())));
			
			
			//AÑADIMOS EL SLOT ELEGIDO AL PANEL PRINCIPAL
			panelPrinci.add(slots[i]);
		}
		
		
		
		//CREAMOS EL PANEL PARA LOS BOTONES
		JPanel buttonPanel = new JPanel();
		buttonPanel.add(startButton);
		buttonPanel.add(stopButton);
		buttonPanel.add(btnVolver);
		buttonPanel.setBackground(new Color(75, 0, 130));
		this.add(buttonPanel, BorderLayout.SOUTH);
		
		
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
			
			//HACEMOS QUE EL BOTON DE START INICE EL JUEGO
			startGame();
		});
		
		//HACEMOS LO MISMO CON EL BOTON DE STOP
		
		stopButton.addActionListener(e -> {
			stopButton.setEnabled(false);
			startButton.setEnabled(true);
			
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
		
		//HACEMOS EL FOR PARA LOS 3 HILOS
		for (int i = 0; i < 3; i++) {
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
						SwingUtilities.invokeLater(() -> actualizarLabel(slots[num],figuras.get(random.nextInt(figuras.size()))));
						
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
	private void actualizarLabel(JLabel label, String figura) {
		
		java.net.URL imagePath = getClass().getResource("/imagenes/"+figura+".jpg");
		
		if(imagePath != null) {
			
			
			ImageIcon originalIcon = new ImageIcon(imagePath);
	        //ESCALAMOS LA IMAGEN AL TAMAÑO DEL LABEL
	        Image scaledImage = originalIcon.getImage().getScaledInstance(230, 230, Image.SCALE_SMOOTH);
	        
	        label.setIcon(new ImageIcon(scaledImage));
	        
	        
		}else {
			//SI NO ENCUENTRA NADA, MUESTRA EL NOMBRE DEL POKEMON EN LUGAR DE LA IMAGEN
			label.setText(figura);
		}
		
		label.setHorizontalAlignment(JLabel.CENTER);
		label.setOpaque(true);
	}
}
