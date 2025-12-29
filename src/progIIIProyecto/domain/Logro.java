package progIIIProyecto.domain;

import java.util.Random;

import progIIIProyecto.BD.GestorBD;

public class Logro {
	
	private int codigo;
	private String nombre;
	private String nombreJuego;
	private Calidad calidad;
	
	// Este es el constructor que hay que utilizar de normal

	public Logro(String nombre, String nombreJuego, Calidad calidad) {
		super();
		this.nombre = nombre;
		this.nombreJuego = nombreJuego;
		this.calidad = calidad;
		
		Random r = new Random();
		int codigoNuevo = r.nextInt(2, 1000000000);
		GestorBD gestorBD = new GestorBD();
		while (gestorBD.existeCodLog(codigoNuevo)) {
			codigoNuevo = r.nextInt(2, 1000000000);
		}
		
		this.codigo = codigoNuevo;
		
	}
	
	// Este constructor es el que utilizamos para al bajar logros de la bd poder saber su código real

	public Logro(int codigo, String nombre, String nombreJuego, Calidad calidad) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.nombreJuego = nombreJuego;
		this.calidad = calidad;
	}



	public int getCodigo() {
		return codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getNombreJuego() {
		return nombreJuego;
	}

	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
	}

	public Calidad getCalidad() {
		return calidad;
	}

	public void setCalidad(Calidad calidad) {
		this.calidad = calidad;
	}

	@Override
	public String toString() {
		return "Logro [codigo=" + codigo + ", nombre=" + nombre + ", nombreJuego=" + nombreJuego + ", calidad=" + calidad + "]";
	}
	
	
	
	
}
