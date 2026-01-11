
package progIIIProyecto.ventana;

import java.awt.BorderLayout;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.domain.Usuario;


// PARA LA CREACION DEL TABLERO ME BASE EN ESTE VIDEO DE YOUTUBE:
// https://www.youtube.com/watch?v=hT663vtxWdA

public class Juego2048  extends JFrame {
	
	private static final long serialVersionUID = 1L;
	private Usuario usuario;
	private JPanel pNorte,pCentro,pSur;
	private JLabel puntuacion;
	private JPanel matriz [][];
	private JLabel numeros[][];
    private int tablero[][] = new int[4][4];
    private JButton btnSalir,btnInformacion, btnReiniciar;
    private Thread cronometro;
    private final int tiempoMaximo = 1000000;
    private JLabel lblTiempo;
    private int ptuTotal;
    private boolean victoria;
    private int segundosPasados =0;
    private VentanaConJuegos ventanaAnterior;
    	
	public Juego2048(VentanaConJuegos ventanaConJuegos, Usuario usuario) {
		this.usuario = usuario;
		setTitle("2048");
		setBounds(500,200, 400, 400);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		this.ventanaAnterior = ventanaConJuegos;
		
		pNorte = new JPanel();
		pNorte.setLayout(new FlowLayout(FlowLayout.CENTER, 45, 10));
		pCentro = new JPanel(new GridLayout(4,4));
		pSur = new JPanel();
		getContentPane().add(pSur,BorderLayout.SOUTH);
		getContentPane().add(pNorte,BorderLayout.NORTH);
		getContentPane().add(pCentro,BorderLayout.CENTER);

		
		btnInformacion = new JButton("Informacion");
		btnInformacion.setBackground(new Color(255, 244, 229));
		btnInformacion.setBorderPainted(false);
		btnInformacion.setFocusPainted(false);

		
		pNorte.add(btnInformacion,BorderLayout.WEST);
		puntuacion = new JLabel("Puntos: 0");
		pNorte.add(puntuacion);
		actualizarPuntuacion();
		
		
		btnSalir = new JButton("SALIR");
		btnSalir.setBackground(new Color(180, 120, 80));
		btnSalir.setForeground(Color.WHITE);
		btnSalir.setBorderPainted(false);
		pSur.add(btnSalir);
	
		
		btnReiniciar = new JButton("REINICIAR");
		btnReiniciar.setBackground(new Color(180, 120, 80));
		btnReiniciar.setForeground(Color.WHITE);
		btnReiniciar.setBorderPainted(false);
		pSur.add(btnReiniciar);
		
		
		
		
		pNorte.setBackground(new Color(255, 244, 229));
		pSur.setBackground(new Color(219, 204, 184));

		matriz = new JPanel[4][4];
		numeros = new JLabel[4][4];
		
		for (int i = 0; i < 4; i++) {	
			 for (int j = 0; j < 4; j++) {
				 
				matriz[i][j] = new JPanel(new GridLayout(1,1)); // Que ocupe todo, asi apareceria en todo el centro el numero
				matriz[i][j].setBorder(BorderFactory.createLineBorder(new Color(147,133,120),3));
				matriz[i][j].setOpaque(true);
				matriz[i][j].setBackground(new Color(197,183,170));
				
				numeros[i][j] = new JLabel("0");
				numeros[i][j].setFont(new Font("Serif",Font.BOLD,27));
				numeros[i][j].setHorizontalAlignment(SwingConstants.CENTER);
				
		        matriz[i][j].add(numeros[i][j]);
				pCentro.add(matriz[i][j]);
				
				actualizarColorCelda(i, j);

			}
		}
		
		btnInformacion.addActionListener((e)->{
			JFrame ventanaInformacion = new JFrame();
			ventanaInformacion.setSize(500,500);
			ventanaInformacion.setTitle("Informacion del juego");
		    ventanaInformacion.setLayout(new BorderLayout());
			
			
			JTextArea txtInformacion = new JTextArea();
			txtInformacion.setEditable(false);
			txtInformacion.setLineWrap(true);//AJUSTA LA LINEA AUTOMATICO
			txtInformacion.setWrapStyleWord(true);//HACE QUE SEPARE POR PALABRAS Y NO POR CARACTERES
			txtInformacion.setFocusable(false);//NO MUESTRA EL CURSOR
			txtInformacion.setText(
					"■ EL OBJETIVO ■\n" +
						    "Tu misión es deslizar las baldosas sobre el tablero para combinar \n" +
						    "números iguales. Cuando dos fichas con el mismo valor chocan, \n" +
						    "se funden en una sola con la suma de ambas (2+2=4, 4+4=8...). \n" +
						    "¡El desafío máximo es alcanzar la mítica baldosa 2048!\n\n" +
						    
						    "■ CONTROLES (Teclado) ■\n" +
						    "Utiliza las siguientes teclas para mover todo el tablero:\n" +
						    " • [ W ] - Deslizar hacia ARRIBA\n" +
						    " • [ S ] - Deslizar hacia ABAJO\n" +
						    " • [ A ] - Deslizar hacia la IZQUIERDA\n" +
						    " • [ D ] - Deslizar hacia la DERECHA\n\n" +
						    
						    "■ SISTEMA DE LOGROS 🏆 ■\n" +
						    "Demuestra tu valía desbloqueando estas insignias especiales:\n" +
						    " 1. Aprendiz: Alcanza una baldosa de 1024.\n" +
						    " 2. Maestro del 2048: Consigue la baldosa de 2048.\n" +
						    " 3. Velocista: Acumula 2048 puntos totales en menos de\n" +
						    "    120 segundos (2 minutos).\n\n" +
						    
						    "■ PUNTUACIÓN Y TIEMPO ■\n" +
						    " • Tu puntuación sube con cada fusión que realices.\n" +
						    " • El cronómetro se inicia con tu primer movimiento.\n" +
						    " • Tu récord personal se guardará automáticamente al finalizar.\n\n" +
						    
						    "■ CONSEJOS ESTRATÉGICOS ■\n" +
						    " • LA TÉCNICA DE LA ESQUINA: Intenta mantener tu número más alto\n" +
						    "   siempre en una de las esquinas (preferiblemente una inferior).\n" +
						    " • EFECTO CADENA: Organiza tus números de mayor a menor en \n" +
						    "   forma de 'S' para facilitar fusiones múltiples.\n" +
						    " • NO LLENES EL CENTRO: Mantener espacios vacíos es vital para \n" +
						    "   tener capacidad de maniobra.\n\n" +
						    "¡Prepárate para desafiar tu ingenio y superar tu propio récord!"
						);
			
			txtInformacion.setBackground(Color.BLACK);
			txtInformacion.setFont(new Font("Arial",Font.ITALIC,20));
			txtInformacion.setForeground(Color.WHITE);
			
			JScrollPane scroll = new JScrollPane(txtInformacion);
			scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
			scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);

			ventanaInformacion.add(scroll,BorderLayout.CENTER);			
			
			JButton btnJugar = new JButton("¡ENTENDIDO, A JUGAR!");
			ventanaInformacion.add(btnJugar,BorderLayout.SOUTH);
			btnJugar.setBackground(new Color(170,180,190));
			
			btnJugar.addActionListener((e2)->{
				ventanaInformacion.setVisible(false);
				
			});
			ventanaInformacion.setLocationRelativeTo(null); // Centrar en pantalla
			ventanaInformacion.setVisible(true);
			
		});
		
		lblTiempo = new JLabel("Tiempo: 00:00",SwingConstants.CENTER);
		pNorte.add(lblTiempo);
		
		btnReiniciar.addActionListener((e)->{
			
			for (int i = 0; i < 4; i++) { 
				for (int j = 0; j < 4; j++) { 
					tablero[i][j] = 0; // Reiniciamos el tablero
					} 
				}
			
			if(cronometro !=null) {
				cronometro.interrupt();
			}
			lblTiempo.setText("Tiempo: 00:00");
			
			ptuTotal = 0;
			segundosPasados = 0;
			victoria = false;
			actualizarPuntuacion();	
			generarNuevoNumero();
			actualizarPantalla();
			pCentro.requestFocusInWindow(); // El panel necesita un foco, asi recive el keyListener
			movimientosNumeros();
			
		});
		
		btnSalir.addActionListener((e)->{
			if (cronometro != null && cronometro.isAlive()) {
		        cronometro.interrupt();
		    }
		    
		    if (ventanaAnterior != null) {
		        ventanaAnterior.setVisible(true);
		    }
		});
		
		generarNuevoNumero();
		actualizarPantalla();
		actualizarPuntuacion();
		movimientosNumeros();

		setVisible(true);
		pCentro.setFocusable(true); // Asi el panel recive un foco
		pCentro.requestFocusInWindow(); // El panel necesita un foco, asi recive el keyListener
		
	}
	public void actualizarColorCelda(int i, int j) {
		
	    int valor = tablero[i][j];

	    switch (valor) {
	        case 0:
	            matriz[i][j].setBackground(new Color(205,193,180));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 2:
	            matriz[i][j].setBackground(new Color(238,228,218));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 4:
	            matriz[i][j].setBackground(new Color(237,224,200));
	            numeros[i][j].setForeground(Color.BLACK);
	            break;
	        case 8:
	            matriz[i][j].setBackground(new Color(242,177,121));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 16:
	            matriz[i][j].setBackground(new Color(245,149,99));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 32:
	            matriz[i][j].setBackground(new Color(246,124,95));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 64:
	            matriz[i][j].setBackground(new Color(246,94,59));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 128:
	            matriz[i][j].setBackground(new Color(237,207,114));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 256:
	            matriz[i][j].setBackground(new Color(237,204,97));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 512:
	            matriz[i][j].setBackground(new Color(237,200,80));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 1024:
	            matriz[i][j].setBackground(new Color(237,197,63));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        case 2048:
	            matriz[i][j].setBackground(new Color(237,194,46));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	        default: // Para valores mayores
	            matriz[i][j].setBackground(new Color(60,58,50));
	            numeros[i][j].setForeground(Color.WHITE);
	            break;
	    }
	}
	
	public boolean estaLleno() {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j]==0) {
					return false;
				}
			}
		}
		return true;
	}
	
	public void actualizarPuntuacion() {
		puntuacion.setText("Puntos:" + ptuTotal);
	}
	
	public void generarNuevoNumero() {
		if (estaLleno()) {
			return;
		}
		List<int[]> vacias = new ArrayList<int[]>();
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j]==0) {
					vacias.add(new int[] {i,j});
				}
			}
		}
		
		int [] pos = vacias.get((int) (Math.random()* vacias.size())); // Hay que hacer un casteo
		int j = pos[1];
		int i = pos[0];
		double probabilidad = Math.random(); // número aleatorio entre 0 y 1
		if (probabilidad < 0.9) { // el 90% de los casos, siempre aparece el numero 2 y no el 4
		    tablero[i][j] = 2;
		} else {
		    tablero[i][j] = 4;
		}
	}
	
	public void actualizarPantalla () {
		for (int i = 0; i < 4; i++) {
			for (int j = 0; j < 4; j++) {
				if (tablero[i][j] == 0) {
				    numeros[i][j].setText("");
				} else {
				    numeros[i][j].setText(String.valueOf(tablero[i][j])); // Que se ponga este valor
				}
				actualizarColorCelda(i, j);
			}
			
		}
	}	
	
	public void movimientosNumeros() {
		pCentro.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if (e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_UP
		            || e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_LEFT
		            || e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_DOWN
		            || e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_RIGHT) {
					if (cronometro == null || !cronometro.isAlive()) { // Evitamos asi que se inicie mas de una vez
						crearCronometro();
					    cronometro.start();
					}
					
					if(e.getKeyCode()==KeyEvent.VK_W || e.getKeyCode()==KeyEvent.VK_UP){
						for(int i=1;i<tablero.length;i++) {
							for(int j=0;j<tablero[i].length;j++) {
								
								if(tablero[i][j]!=0) {
									int f = i-1;
									while(f>=0 && tablero[f][j]==0) {
										f--;
									}
									if(f<0) {
										
										tablero[0][j] = tablero[i][j];
										tablero[i][j] = 0;
										actualizarColorCelda(0, j);
										actualizarColorCelda(i, j);
										
									}else {
										
										if (tablero[f][j] == tablero[i][j]) {
											tablero[f][j] = tablero[f][j] + tablero[i][j];
											ptuTotal += tablero[f][j];
											
											actualizarPuntuacion();
											tablero[i][j] = 0;
											
											actualizarColorCelda(f, j);
											actualizarColorCelda(i, j);
											
										}else {
												tablero[f+1][j] = tablero[i][j];
												if(f+1!=i) {
													tablero[i][j] = 0;
												}
												
												actualizarColorCelda(f+1, j);
												actualizarColorCelda(i, j);
										}
									}
								}
							}
						}
					}
					
					if (e.getKeyCode()==KeyEvent.VK_S || e.getKeyCode()==KeyEvent.VK_DOWN) {
						for (int i = tablero.length - 2; i >= 0; i--) {  // Ahora comenzamos desde la penultima fila
					        for (int j = 0; j < tablero[i].length; j++) {
					        	
					            if (tablero[i][j] != 0) {
					            	int f = i + 1;
					                while (f < tablero.length && tablero[f][j] == 0) { // Ahora el limite es hacia abajp
					                    f++;
					                }
					                
					                if (f >= tablero.length) {
					                    tablero[tablero.length - 1][j] = tablero[i][j];
					                    tablero[i][j] = 0;
					                    actualizarColorCelda(tablero.length - 1, j);
					                    actualizarColorCelda(i, j);

					                } else {					                	
					                    if (tablero[f][j] == tablero[i][j]) {
					                    	
					                        tablero[f][j] += tablero[i][j];
					                        ptuTotal += tablero[f][j];
					                        
											actualizarPuntuacion();
					                        tablero[i][j] = 0;
					                        
					                        actualizarColorCelda(f, j);
					                        actualizarColorCelda(i, j);

					                    } else {					                    	
					                        tablero[f - 1][j] = tablero[i][j];
					                        if (f - 1 != i) {
					                        	
					                            tablero[i][j] = 0;
					                        }
					                        actualizarColorCelda(f - 1, j);
					                        actualizarColorCelda(i, j);
					                    }
					                }
					            }
					        }
					    }
					}
					
					if(e.getKeyCode()==KeyEvent.VK_A || e.getKeyCode()==KeyEvent.VK_LEFT){
                        
						for(int i=0; i<tablero.length; i++) {
                            // Recorremos las columnas (j) empezando en la segunda (j=1)
							for(int j=1; j<tablero[i].length; j++) {
								
								if(tablero[i][j]!=0) {
									
									int c = j-1; 
                                    
									while(c>=0 && tablero[i][c]==0) {
										c--;
									}
									
									if(c<0) {
										
										tablero[i][0] = tablero[i][j];
										tablero[i][j] = 0;
										actualizarColorCelda(i, 0);
										actualizarColorCelda(i, j);
										
									}else {
										
										if (tablero[i][c] == tablero[i][j]) {
											
											tablero[i][c] = tablero[i][c] + tablero[i][j];
											ptuTotal += tablero[i][c]; 
											
											actualizarPuntuacion();
											tablero[i][j] = 0;
											
											actualizarColorCelda(i, c);
											actualizarColorCelda(i, j);
											
										}else {
											
											tablero[i][c+1] = tablero[i][j];
											if(c+1!=j) {
												tablero[i][j] = 0;
											}
												
											actualizarColorCelda(i, c+1);
											actualizarColorCelda(i, j);
										}
									}
								}
							}
						}
					}
					
					if (e.getKeyCode()==KeyEvent.VK_D || e.getKeyCode()==KeyEvent.VK_RIGHT) {						
                        
						for (int i = 0; i < tablero.length; i++) {  
                            // Recorremos las columnas (j) desde la penúltima hacia la izquierda (j=2, 1, 0)
					        for (int j = tablero[i].length - 2; j >= 0; j--) {
					        	
					            if (tablero[i][j] != 0) {
					            	
					            	int c = j + 1;
                                    
					                while (c < tablero[i].length && tablero[i][c] == 0) { 
					                    c++;
					                }
					                
					                if (c >= tablero[i].length) {
					                	
					                    tablero[i][tablero[i].length - 1] = tablero[i][j];
					                    tablero[i][j] = 0;
					                    actualizarColorCelda(i, tablero[i].length - 1);
					                    actualizarColorCelda(i, j);

					                } else {
					                	
					                    if (tablero[i][c] == tablero[i][j]) {
					                    	
					                        tablero[i][c] += tablero[i][j];
					                        ptuTotal += tablero[i][c]; 
					                        
											actualizarPuntuacion();
					                        tablero[i][j] = 0;
					                        
					                        actualizarColorCelda(i, c);
					                        actualizarColorCelda(i, j);

					                    } else {
					                    	
					                        tablero[i][c - 1] = tablero[i][j];
					                        if (c - 1 != j) {
					                        	
					                            tablero[i][j] = 0;
					                        }
					                        
					                        actualizarColorCelda(i, c - 1);
					                        actualizarColorCelda(i, j);
					                    }
					                }
					            }
					        }
					    }
					}
					
				}
				
				generarNuevoNumero();
				actualizarPantalla();
				comprombarLogros();
				if (!hayMovimientosPosible()) {
					if (cronometro !=null) {
						cronometro.interrupt();
					}
					finalizarGuardarPartida();
					JOptionPane.showMessageDialog(Juego2048.this, "¡Juego Terminado! No hay más movimientos posibles.\nPuntuación Final: " + ptuTotal, 
							"GAME OVER", JOptionPane.INFORMATION_MESSAGE);
					}
				}
		});
	}
	
	public void crearCronometro() {
		segundosPasados = 0; // empezar mejor siempre de 0		
		cronometro = new Thread(()->{
			
			for (int segundosPasados = 0; segundosPasados<=tiempoMaximo; segundosPasados++) {
				this.segundosPasados = segundosPasados; // guardamos el timepo exacto
				int minutos = segundosPasados / 60;
	            int segundos = segundosPasados % 60;
	            
	            String tiempoFormateado = String.format("%02d:%02d", minutos, segundos);
				
				SwingUtilities.invokeLater(() -> lblTiempo.setText("Tiempo: " + tiempoFormateado));
				
				try {
					cronometro.sleep(1000);
				} catch (InterruptedException e1) {
					return; // Al reiniciar, nos salimos
				}
			}
		});
	}
	
	public boolean hayMovimientosPosible() {
		if (!estaLleno()) {
			return true;
		}
		
		for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 4; j++) {
	            int valorActual = tablero[i][j];
	            
	            // Nos aseguramos de que no estamos en el borde derecho
	            if (j < 3) { 
	                if (tablero[i][j + 1] == valorActual) {
	                    return true; 
	                }
	            }
	            // Nos aseguramos de que no estamos en el borde de abajo
	            if (i < 3) { 
	                if (tablero[i + 1][j] == valorActual) {
	                    return true;
	                }
	            }
	        }
	    }
		return false; // No tenemos mas posibilidades
	}
	
	public void finalizarGuardarPartida() {
		GestorBD bd = new GestorBD();
		
		int idUsuarioActual;
		if (this.usuario !=null) {
			idUsuarioActual = this.usuario.getCodigo();
		}else {
			idUsuarioActual = 0;
		}
		
//		int nuevoPunID =bd.obtenerSiguienteCodigoPuntaje();	
		
		int recordAnterior = bd.obtenerRecordPersonal(idUsuarioActual);
		
		Puntaje partida = new Puntaje( "2048",this.ptuTotal, this.segundosPasados, idUsuarioActual);
		bd.subirPuntaje(partida);
		
		if(ptuTotal>recordAnterior && idUsuarioActual !=0) {
			JOptionPane.showMessageDialog(this, "¡NUEVO RÉCORD PERSONAL: " + ptuTotal + "!");
		}
	}
	
	
	public void comprombarLogros() {
		if (this.usuario == null || usuario.getCodigo() ==1) {
	        return; 
	    }
		
		boolean tiene2048 = false;
		boolean tiene1024 = false;
	    for (int i = 0; i < 4; i++) {
	        for (int j = 0; j < 4; j++) {
	            if (tablero[i][j] >= 1024) {
	                tiene1024 = true;

	            }
	            if (tablero[i][j]>=2048) {
	                tiene2048 = true;

	            }
	        }
	    }
	    GestorBD bd = new GestorBD();
    	int id = this.usuario.getCodigo();
    	
    	
	    if (tiene1024) {
	    	bd.asignarLogroAUsuario(id, 1);
	    }
	    
	    if (tiene2048) {
	    	bd.asignarLogroAUsuario(id, 2);
	    	
	    	if (!victoria) {
	            JOptionPane.showMessageDialog(this, "¡Felicidades! Has alcanzado el 2048.");
	            victoria = true;
	    	}
	    }
	    
	    if(ptuTotal >=2048 && segundosPasados< 120) {
	    	bd.asignarLogroAUsuario(id, 3);
	    }
	}
}
