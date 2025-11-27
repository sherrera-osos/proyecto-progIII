package progIIIProyecto.domain;

public enum Pais {
	Colombia,
	España,
	Francia,
	Alemania,
	Italia,
	Portugal,
	Países_Bajos,
	Bélgica,
	Suiza,
	Suecia,
	Noruega,
	Dinamarca;

	@Override
	public String toString() {
		return super.toString().replace("_", " ");
	}
}
