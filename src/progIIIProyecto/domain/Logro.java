package progIIIProyecto.domain;

public class Logro {
	
	private int codigo;
	private String nombre;
	private boolean conseguido;
	private int requisitopuntos;
	private String nombreJuego;
	private Calidad calidad;
	
	private static int codigoNuevo = 1;

	public Logro(String nombre, boolean conseguido, int requisitopuntos, String nombreJuego, Calidad calidad) {
		super();
		this.codigo = codigoNuevo;
		this.nombre = nombre;
		this.conseguido = conseguido;
		this.requisitopuntos = requisitopuntos;
		this.nombreJuego = nombreJuego;
		this.calidad = calidad;
		
		codigoNuevo += 1;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public boolean isConseguido() {
		return conseguido;
	}

	public void setConseguido(boolean conseguido) {
		this.conseguido = conseguido;
	}

	public int getRequisitopuntos() {
		return requisitopuntos;
	}

	public void setRequisitopuntos(int requisitopuntos) {
		this.requisitopuntos = requisitopuntos;
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

	public static int getCodigoNuevo() {
		return codigoNuevo;
	}

	@Override
	public String toString() {
		return "Logro [codigo=" + codigo + ", nombre=" + nombre + ", conseguido=" + conseguido + ", requisitopuntos="
				+ requisitopuntos + ", nombreJuego=" + nombreJuego + ", calidad=" + calidad + "]";
	}
	
	
	
	
}
