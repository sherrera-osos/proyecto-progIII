package progIIIProyecto.domain;

import java.util.Random;

import progIIIProyecto.BD.GestorBD;

public class Puntaje {
	
	private int codigo;
	private String nombreJuego;
	private int puntos1;
	private int puntos2;
	private int codigoDelUsuario;
	
	public Puntaje(String nombreJuego, int puntos1, int puntos2, int codigoDelUsuario) {
		super();
		
		this.nombreJuego = nombreJuego;
		this.puntos1 = puntos1;
		this.puntos2 = puntos2;
		this.codigoDelUsuario = codigoDelUsuario;
		
		Random r = new Random();
		int codigoNuevo = r.nextInt(2, 1000000000);
		GestorBD gestorBD = new GestorBD();
		while (gestorBD.existeCodUsu(codigoNuevo)) {
			codigoNuevo = r.nextInt(2, 1000000000);
		}
		
		this.codigo = codigoNuevo;
	}

	public Puntaje(int codigo, String nombreJuego, int puntos1, int puntos2, int codigoDelUsuario) {
		super();
		this.codigo = codigo;
		this.nombreJuego = nombreJuego;
		this.puntos1 = puntos1;
		this.puntos2 = puntos2;
		this.codigoDelUsuario = codigoDelUsuario;
	}

	public String getNombreJuego() {
		return nombreJuego;
	}

	public void setNombreJuego(String nombreJuego) {
		this.nombreJuego = nombreJuego;
	}

	public int getPuntos1() {
		return puntos1;
	}

	public void setPuntos1(int puntos1) {
		this.puntos1 = puntos1;
	}

	public int getPuntos2() {
		return puntos2;
	}

	public void setPuntos2(int puntos2) {
		this.puntos2 = puntos2;
	}

	public int getCodigoDelUsuario() {
		return codigoDelUsuario;
	}

	public void setCodigoDelUsuario(int codigoDelUsuario) {
		this.codigoDelUsuario = codigoDelUsuario;
	}

	public int getCodigo() {
		return codigo;
	}

	@Override
	public String toString() {
		return "Puntaje [codigo=" + codigo + ", nombreJuego=" + nombreJuego + ", puntos1=" + puntos1 + ", puntos2="
				+ puntos2 + ", codigoDelUsuario=" + codigoDelUsuario + "]";
	}
	
	
	
	

}
