package progIIIProyecto.ventana;

import java.util.ArrayList;
import java.util.Random;

public class BlackJack {

	private class Carta{
		String valor;
		public int getValor() {
			if ("AJQK".contains(valor)){
				if (valor == "A") {
					return 11;
				}
				return 10;
			} else {
				return Integer.parseInt(valor);
			}
		}

		public void setValor(String valor) {
			this.valor = valor;
		}

		public String getTipo() {
			return tipo;
		}

		public void setTipo(String tipo) {
			this.tipo = tipo;
		}

		String tipo;
		
		public Carta(String valor, String tipo) {
			super();
			this.valor = valor;
			this.tipo = tipo;
		}

		@Override
		public String toString() {
			return "Carta [valor=" + valor + ", tipo=" + tipo + "]";
		}

		public boolean isAs() {
			return valor == "A";
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
	
	
	
	BlackJack(){
		empezarJuego();
	}
	
	
	
	
	
	
	
	
	
	private void empezarJuego() {
		// Mazo
		construirMazo();
		mezclarMazo();
		
		// Dealer
		manoDealer = new ArrayList<BlackJack.Carta>();
		sumaDealer = 0;
		cantAsDealer = 0;
		
		cartaEscondida = mazo.remove(mazo.size()-1);  // Quita la ultima carta
		sumaDealer += cartaEscondida.getValor();
		cantAsDealer += cartaEscondida.isAs() ? 1 : 0; // Devuelve 1 si es as y 0 si no lo es
		
		Carta carta = mazo.remove(mazo.size()-1);
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
			carta = mazo.remove(mazo.size()-1);
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
		String[] valor = {"A", "2", "3", "4", "5", "6", "7", "8", "9", "J", "Q", "K"};
		String[] tipo = {"C", "D", "H", "S"};
		
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
		System.out.println("hola");
	}






	public static void main(String[] args) throws Exception{
		BlackJack blackJack = new BlackJack();
	}
}


