package progIIIProyecto.domain;

public class Usuario {
	
	private int codigo;
	private String nombre;
	private String contr;
	private int tlf;
	private String correo;
	private Pais pais;
	private Genero genero;
	
	public Usuario(int codigo, String nombre, String contr, int tlf, String correo, Pais pais, Genero genero) {
		super();
		this.codigo = codigo;
		this.nombre = nombre;
		this.contr = contr;
		this.tlf = tlf;
		this.correo = correo;
		this.pais = pais;
		this.genero = genero;

	}

	@Override
	public String toString() {
		return "Usuario [codigo=" + codigo + ", nombre=" + nombre + ", contr=" + contr + ", tlf=" + tlf + ", correo="
				+ correo + ", pais=" + pais + ", genero=" + genero + "]";
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getContr() {
		return contr;
	}

	public void setContr(String contr) {
		this.contr = contr;
	}

	public int getTlf() {
		return tlf;
	}

	public void setTlf(int tlf) {
		this.tlf = tlf;
	}

	public String getCorreo() {
		return correo;
	}

	public void setCorreo(String correo) {
		this.correo = correo;
	}

	public Pais getPais() {
		return pais;
	}

	public void setPais(Pais pais) {
		this.pais = pais;
	}

	public Genero getGenero() {
		return genero;
	}

	public void setGenero(Genero genero) {
		this.genero = genero;
	}

	public int getCodigo() {
		return codigo;
	}
	
	
	
	

}
