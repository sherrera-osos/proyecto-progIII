package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.domain.Usuario;

public class BlackJack extends JFrame {
	private static final long serialVersionUID = 1L;

	private Usuario usuario;

	
	private class Carta {
		String valor;

		public int getValor() {
			if ("AJQK".contains(valor)) {
				if (valor == "A") {
					return 11;
				}
				return 10;
			} else {
				return Integer.parseInt(valor);
			}
		}

		String tipo;

		public Carta(String valor, String tipo) {
			super();
			this.valor = valor;
			this.tipo = tipo;
		}

		@Override
		public String toString() {
			return valor + "-" + tipo;
		}

		public boolean isAs() {
			return valor == "A";
		}

		public String getImagePath() {
			return "resources/cartas/" + toString() + ".png";
		}

	}

	ArrayList<Carta> mazo;
	Random random = new Random();

	// Dealer
	private Carta cartaEscondida;
	private ArrayList<Carta> manoDealer;
	private int sumaDealer;
	private int cantAsDealer;

	// Jugador
	private ArrayList<Carta> manoJugador;
	private int sumaJugador;
	private int cantAsJugador;

	private int contadorVictorias = 0;
	private boolean puntuacionSumada = false;
	
	private int marcadorMaximo = 0; // Puntaje 1 (Record)
	private int partidasTotales = 0; // Puntaje 2 (Esfuerzo)

	// Ventana
	int anchoVentana = 600;
	int altoVentana = anchoVentana;

	int anchoCarta = 110;
	int altoCarta = 154;

	JPanel panelJuego = new JPanel() {

		private static final long serialVersionUID = 1L;

		@Override
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			try {
				// Dibujar la carta escondida
				Image imgCartaEscondida = new ImageIcon("resources/cartas/BACK.png").getImage();

				if (!btnStay.isEnabled()) {
					imgCartaEscondida = new ImageIcon(cartaEscondida.getImagePath()).getImage();
				}

				g.drawImage(imgCartaEscondida, 20, 20, anchoCarta, altoCarta, null);

				// Dibujar mano del dealer
				for (int i = 0; i < manoDealer.size(); i++) {
					Carta carta = manoDealer.get(i);
					Image imgCarta = new ImageIcon(carta.getImagePath()).getImage();
					g.drawImage(imgCarta, anchoCarta + 25 + (anchoCarta + 5) * i, 20, anchoCarta, altoCarta, null);
				}

				// Dibujar mano del jugador
				for (int j = 0; j < manoJugador.size(); j++) {
					Carta carta = manoJugador.get(j);
					Image imgCarta = new ImageIcon(carta.getImagePath()).getImage();
					g.drawImage(imgCarta, 20 + (anchoCarta + 5) * j, 320, anchoCarta, altoCarta, null);
				}

				// Dibujar puntajes
				g.setFont(new Font("Arial", Font.BOLD, 20));
				g.setColor(Color.WHITE);

				// Puntaje jugador
				g.drawString("Jugador: " + reduceAsJugador(), 20, 500);

				// Puntaje Dealer
				if (btnStay.isEnabled()) {
					int sumaVisible = 0;
					for (Carta c : manoDealer) {
						sumaVisible += c.getValor();
					}
					g.drawString("Dealer: " + sumaVisible + "+ ?", 20, 200);
				} else {
					g.drawString("Dealer: " + reduceAsDealer(), 20, 200);
				}

				if (!btnStay.isEnabled()) {
					sumaDealer = reduceAsDealer();
					sumaJugador = reduceAsJugador();
					System.out.println("STAY: ");
					System.out.println(sumaDealer);
					System.out.println(sumaJugador);

					String mensaje = "";
					if (sumaJugador > 21) {
						mensaje = "Has perdido";
					} else if (sumaDealer > 21) {
						mensaje = "¡HAS GANADO!";
					} else if (sumaJugador == sumaDealer) {
						mensaje = "Empate";
					} else if (sumaJugador > sumaDealer) {
						mensaje = "¡HAS GANADO!";
					} else if (sumaDealer > sumaJugador) {
						mensaje = "Has perdido";
					}

					if (!puntuacionSumada) {
						partidasTotales++; // Sumamos una partida cada vez que termina una mano
						
						if (mensaje.equals("¡HAS GANADO!")) {
							contadorVictorias++;
							// Si el record ahora es mejor que el maximo, lo actualizamos
							if (contadorVictorias > marcadorMaximo) {
								marcadorMaximo = contadorVictorias;
							}
						} else if (mensaje.equals("Has perdido")) {
							contadorVictorias--;
						}

						puntuacionSumada = true;
						comprobarLogros();
					}

					g.setFont(new Font("Arial", Font.PLAIN, 30));
					g.setColor(Color.white);
					g.drawString(mensaje, 220, 250);

				}

				// MARCADOR
				int xRecuadro = anchoVentana - 140 - 20;
				int yRecuadro = 20;

				g.setColor(new Color(0, 0, 0, 180));
				g.fillRect(xRecuadro, yRecuadro, 140, 45);

				g.setColor(new Color(200, 200, 200)); // Blanco grisáceo
				g.drawRect(xRecuadro, yRecuadro, 140, 45);
				g.setFont(new Font("Arial", Font.BOLD, 22));

				// 4. LÓGICA DE COLOR
				if (contadorVictorias < 0) {
					g.setColor(new Color(255, 50, 50));
				} else {
					g.setColor(Color.WHITE);
				}
				String textoMarcador = "Marcador: " + contadorVictorias;
				g.drawString(textoMarcador, xRecuadro + 1, yRecuadro + 30);

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	JPanel panelBotones = new JPanel();
	JButton btnHit = new JButton("Hit");
	JButton btnStay = new JButton("Stay");
	JButton btnReset = new JButton("Volver a Jugar");

	BlackJack(VentanaConJuegos ventanaConJuegos, Usuario usuario) {
		empezarJuego();

		this.usuario = usuario;

		this.setVisible(true);
		this.setSize(anchoVentana, altoVentana);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);

		this.addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				int respuesta = JOptionPane.showConfirmDialog(null, "¿Quieres salis del juego y volver al menú?",
						"Salir", JOptionPane.YES_NO_OPTION);

				if (respuesta == JOptionPane.YES_OPTION) {
					if (usuario != null && usuario.getCodigo() != 1) {
						GestorBD gestor = new GestorBD();
						
						int siguienteID = gestor.obtenerSiguienteCodigoPuntaje();
						
						try {
		                    // Esperamos 400 milisegundos para que la conexión anterior se cierre bien
		                    Thread.sleep(400); 
		                } catch (InterruptedException ex) {
		                    ex.printStackTrace();
		                }
						System.out.println("DEBUG - Usuario: " + usuario.getCodigo());
						System.out.println("DEBUG - Marcador: " + marcadorMaximo);
						System.out.println("DEBUG - Partidas: " + partidasTotales);
						
						Puntaje p = new Puntaje(siguienteID, "BlackJack", marcadorMaximo, partidasTotales, usuario.getCodigo());
						
						
						gestor.subirPuntaje(p);
						System.out.println("Record guardado: " + marcadorMaximo);
					}
					
					
					
					dispose(); // Solo cierra BlackJack

					ventanaConJuegos.setVisible(true);
				}
			}
		});

		panelJuego.setLayout(new BorderLayout());
		panelJuego.setBackground(new Color(138, 0, 20));
		this.add(panelJuego);

		btnHit.setFocusable(false);
		panelBotones.add(btnHit);
		btnStay.setFocusable(false);
		panelBotones.add(btnStay);
		btnReset.setFocusable(false);
		btnReset.setEnabled(false);
		panelBotones.add(btnReset);
		this.add(panelBotones, BorderLayout.SOUTH);

		// LISTENERS de los botones
		// Hit
		btnHit.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				Carta carta = mazo.remove(mazo.size() - 1);
				sumaJugador += carta.getValor();
				cantAsJugador += carta.isAs() ? 1 : 0;
				manoJugador.add(carta);
				panelJuego.repaint();

				if (reduceAsJugador() > 21) { // Por ejemplo: A + 2 + J --> 1 + 2 + J
					btnHit.setEnabled(false);
					btnStay.setEnabled(false);
					btnReset.setEnabled(true);
				}
			}
		});

		// Stay

		btnStay.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				btnHit.setEnabled(false);
				btnStay.setEnabled(false);

				while (sumaDealer < 17) {
					Carta carta = mazo.remove(mazo.size() - 1);
					sumaDealer += carta.getValor();
					cantAsDealer += carta.isAs() ? 1 : 0;
					manoDealer.add(carta);
				}

				btnReset.setEnabled(true);

				panelJuego.repaint();

			}
		});

		// Reset
		btnReset.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				empezarJuego();
				btnHit.setEnabled(true);
				btnStay.setEnabled(true);
				btnReset.setEnabled(false);
				panelJuego.repaint();

			}
		});

		panelJuego.repaint();
	}

	private void empezarJuego() {
		// Puntuación
		puntuacionSumada = false;
		// Mazo
		construirMazo();
		mezclarMazo();

		// Dealer
		manoDealer = new ArrayList<BlackJack.Carta>();
		sumaDealer = 0;
		cantAsDealer = 0;

		cartaEscondida = mazo.remove(mazo.size() - 1); // Quita la ultima carta
		sumaDealer += cartaEscondida.getValor();
		cantAsDealer += cartaEscondida.isAs() ? 1 : 0; // Devuelve 1 si es as y 0 si no lo es

		Carta carta = mazo.remove(mazo.size() - 1);
		sumaDealer += carta.getValor();
		cantAsDealer += carta.isAs() ? 1 : 0;
		manoDealer.add(carta);

		System.out.println("DEALER:");
		System.out.println(cartaEscondida);
		System.out.println(manoDealer);
		System.out.println(sumaDealer);
		System.out.println(cantAsDealer);

		// Jugador
		manoJugador = new ArrayList<BlackJack.Carta>();
		sumaJugador = 0;
		cantAsJugador = 0;

		for (int i = 0; i < 2; i++) {
			carta = mazo.remove(mazo.size() - 1);
			sumaJugador += carta.getValor();
			cantAsJugador += carta.isAs() ? 1 : 0;
			manoJugador.add(carta);
		}

		System.out.println("JUGADOR:");
		System.out.println(manoJugador);
		System.out.println(sumaJugador);
		System.out.println(cantAsJugador);
	}

	private void construirMazo() {
		mazo = new ArrayList<BlackJack.Carta>();
		String[] valor = { "A", "2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K" };
		String[] tipo = { "C", "D", "H", "S" };

		for (int i = 0; i < tipo.length; i++) {
			for (int j = 0; j < valor.length; j++) {
				Carta carta = new Carta(valor[j], tipo[i]);
				mazo.add(carta);
			}
		}

		System.out.println("Mazo creado");
		System.out.println(mazo);
	}

	private void mezclarMazo() {
		for (int i = 0; i < mazo.size(); i++) {
			int r = random.nextInt(mazo.size());
			Carta estaCarta = mazo.get(i);
			Carta rCarta = mazo.get(r);
			mazo.set(i, rCarta);
			mazo.set(r, estaCarta);
		}

		System.out.println("Despues de mezclar");
		System.out.println(mazo);
		System.out.println("a");
	}

	private int reduceAsJugador() {
		while (sumaJugador > 21 && cantAsJugador > 0) {
			sumaJugador -= 10;
			cantAsJugador -= 1;
		}

		return sumaJugador;
	}

	private int reduceAsDealer() {
		while (sumaDealer > 21 && cantAsDealer > 0) {
			sumaDealer -= 10;
			cantAsDealer -= 1;
		}

		return sumaDealer;
	}

	public static void main(String[] args) throws Exception {
		BlackJack blackJack = new BlackJack(null, null);
		blackJack.setVisible(true);
	}

	private void comprobarLogros() {
		if (usuario == null || usuario.getCodigo() == 1)
			return; // Por si acaso no hay usuario logueado

		GestorBD gestor = new GestorBD();

		// Juego 3
		if (contadorVictorias == 3) {
			gestor.asignarLogroAUsuario(usuario.getCodigo(), 7);
			JOptionPane.showMessageDialog(null, "¡Logro de Bronce desbloqueado!");
		}
		if (contadorVictorias == 7) {
			gestor.asignarLogroAUsuario(usuario.getCodigo(), 8);
			JOptionPane.showMessageDialog(null, "¡Logro de Plata desbloqueado!");
		}
		if (contadorVictorias == 15) {
			gestor.asignarLogroAUsuario(usuario.getCodigo(), 9);
			JOptionPane.showMessageDialog(null, "Logro de Oro desbloqueado!");
		}

	}

}

// Codigo adaptado del tutorial de BlackJack: https://www.youtube.com/watch?v=bMYCWccL-3U&t=10s
