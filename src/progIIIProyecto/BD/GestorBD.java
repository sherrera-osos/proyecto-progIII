package progIIIProyecto.BD;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import progIIIProyecto.domain.Genero;
import progIIIProyecto.domain.Pais;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.domain.Usuario;

public class GestorBD {
	private static final String SQLITE_FILE = "resources/db/BaseDatosProyecto.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:" + SQLITE_FILE;

	public GestorBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\n", e.getMessage());
		}
	}

	// Aqui irian todas la funciones que llaman a la base de datos, una que meta un usuario que recibe en la base, 
	// otra que devuelva una lista con todos los logros de un juego etc.

	// Por ahora solo está esta funcion loadUsuarios, no se me ha ocurrido todavía un uso para ella, la he utilizado
	// para comprobrar si funciona la base de datos

	public ArrayList<Usuario> bajarUsuarios(){
		ArrayList<Usuario> listaUsuarios = new ArrayList<Usuario>();
		String sql = "SELECT * FROM USUARIO";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
			ResultSet rs = pst.executeQuery(sql);

			while(rs.next()) {
				int codigo = rs.getInt("COD_USU");
				String nombre = rs.getString("NOM_USU");
				String contrasenya = rs.getString("CONTRA");
				int tlf = rs.getInt("TLF");
				String correo = rs.getString("CORREO");
				Pais pais = Pais.valueOf(rs.getString("PAIS").replace(" ", "_"));
				Genero genero = Genero.valueOf(rs.getString("GENERO"));

				Usuario usuario = new Usuario(codigo, nombre, contrasenya, tlf, correo, pais, genero);

				listaUsuarios.add(usuario);
			}

		} catch (Exception e) {
			System.err.format("* Error recuperando usuarios: %s.\n", e.getMessage());
			e.printStackTrace();
		}

		return listaUsuarios;

	}
	
	// FUNCION PARA BAJAR TODOS LOS PUNTAJES DE UN JUEGO PARA LAS ESTADÍSTICAS
	
	public ArrayList<Puntaje> bajarPuntajesDeJuego(String nombreJuego){
		ArrayList<Puntaje> listaPuntajes = new ArrayList<Puntaje>();
		String sql = "SELECT * FROM PUNTAJE WHERE NOM_JUEGO = '"+nombreJuego+"'";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
			ResultSet rs = pst.executeQuery(sql);

			while(rs.next()) {
				int codigo = rs.getInt("COD_PUN");
				int puntos1 = rs.getInt("PUNTOS1");
				int codigoUsuario = rs.getInt("COD_USU");
				int puntos2 = rs.getInt("PUNTOS2");

				Puntaje puntaje = new Puntaje(codigo, nombreJuego, puntos1, puntos2, codigoUsuario);

				listaPuntajes.add(puntaje);
			}

		} catch (Exception e) {
			System.err.format("* Error recuperando puntajes: %s.\n", e.getMessage());
			e.printStackTrace();
		}

		return listaPuntajes;

	}
	
	// FUNCION PARA AÑADIR UN USUARIO A LA BASE DE DATOS

	public void subirUsuario(Usuario usuario) {
		String sqlInsert = "INSERT INTO USUARIO VALUES (?, ?, ?, ?, ?, ?, ?)";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstInsert = con.prepareStatement(sqlInsert)){

			pstInsert.setInt(1, usuario.getCodigo());
			pstInsert.setString(2, usuario.getNombre());
			pstInsert.setString(3, usuario.getGenero().toString());
			pstInsert.setString(4, usuario.getPais().toString());
			pstInsert.setString(5, usuario.getContr());
			pstInsert.setInt(6, usuario.getTlf());
			pstInsert.setString(7, usuario.getCorreo());

			pstInsert.executeUpdate();

		} catch (SQLException e) {
			System.err.format("* Error carga: %s.\n", e.getMessage());
			e.printStackTrace();
		}
	}
	
	// FUNCION PARA AÑADIR UN PUNTAJE A LA BASE DE DATOS

		public void subirPuntaje(Puntaje puntaje) {
			String sqlInsert = "INSERT INTO PUNTAJE VALUES (?, ?, ?, ?, ?)";

			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
					PreparedStatement pstInsert = con.prepareStatement(sqlInsert)){

				pstInsert.setInt(1, puntaje.getCodigo());
				pstInsert.setString(2, puntaje.getNombreJuego());
				pstInsert.setInt(3, puntaje.getPuntos1());
				pstInsert.setInt(4, puntaje.getCodigoDelUsuario());
				pstInsert.setInt(5, puntaje.getPuntos2());
				
				pstInsert.executeUpdate();

			} catch (SQLException e) {
				System.err.format("* Error carga: %s.\n", e.getMessage());
				e.printStackTrace();
			}
		}
		
	// FUNCIÓN PARA OBTENER UN USUARIO A PARTIR DE SU CÓDIGO (UTILIZADO EN ESTADÍSTICAS)
		
		public Usuario obtenerUsuario(int codigo){
			Usuario usuario = null;
			String sql = "SELECT * FROM USUARIO WHERE COD_USU = '"+codigo+"'";

			try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
				ResultSet rs = pst.executeQuery(sql);

				while(rs.next()) {
					String nombre = rs.getString("NOM_USU");
					String contrasenya = rs.getString("CONTRA");
					int tlf = rs.getInt("TLF");
					String correo = rs.getString("CORREO");
					Pais pais = Pais.valueOf(rs.getString("PAIS").replace(" ", "_"));
					Genero genero = Genero.valueOf(rs.getString("GENERO"));

					usuario = new Usuario(codigo, nombre, contrasenya, tlf, correo, pais, genero);
				}

			} catch (Exception e) {
				System.err.format("* Error recuperando el usuario: %s.\n", e.getMessage());
				e.printStackTrace();
			}

			return usuario;

		}
	
	// FUNCIÓN PARA COMPROBAR SI YA EXISTE ESTE CODIGO DE USUARIO
	
	public boolean existeCodUsu(int codigoAComprobar) {
		String sql = "SELECT COD_USU FROM USUARIO";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
			ResultSet rs = pst.executeQuery(sql);
			
		while(rs.next()) {
			int codigo = rs.getInt("COD_USU");
			if (codigoAComprobar == codigo) {
				return true;
			}
				
		}
			
		} catch (SQLException e) {
			System.err.format("* Error recuperando códigos de usuario: %s.\n", e.getMessage());
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	// FUNCIÓN PARA COMPROBAR SI YA EXISTE ESTE CODIGO DE LOGRO (sin probar)
	
	public boolean existeCodLog(int codigoAComprobar) {
		String sql = "SELECT COD_LOG FROM LOGRO";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
			ResultSet rs = pst.executeQuery(sql);
			
		while(rs.next()) {
			int codigo = rs.getInt("COD_LOG");
			if (codigoAComprobar == codigo) {
				return true;
			}
				
		}
			
		} catch (SQLException e) {
			System.err.format("* Error recuperando códigos de logro: %s.\n", e.getMessage());
			e.printStackTrace();
		}
		
		return false;
		
	}
	
	// FUNCIÓN PARA COMPROBAR SI YA EXISTE ESTE CODIGO DE PUNTAJE (sin probar)
	
	public boolean existeCodPun(int codigoAComprobar) {
		String sql = "SELECT COD_PUN FROM PUNTAJE";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
			ResultSet rs = pst.executeQuery(sql);
			
		while(rs.next()) {
			int codigo = rs.getInt("COD_PUN");
			if (codigoAComprobar == codigo) {
				return true;
			}
				
		}
			
		} catch (SQLException e) {
			System.err.format("* Error recuperando códigos de puntaje: %s.\n", e.getMessage());
			e.printStackTrace();
		}
		
		return false;
		
	}
    
	// Este main se quita después

	public static void main(String[] args) {
		GestorBD gestorBD = new GestorBD();
		
		System.out.println(gestorBD.obtenerUsuario(940119315));

//		ArrayList<Puntaje> listaPuntajes = gestorBD.bajarPuntajesDeJuego("Juego1");
//		for (Puntaje puntaje : listaPuntajes) {
//			System.out.println(puntaje.toString());
//		}

		//    	Puntaje puntajePrueba = new Puntaje("Juego1", 320, 60, 940119315);
		//    	System.out.println(puntajePrueba);
		//    	gestorBD.subirPuntaje(puntajePrueba);

		//    	ArrayList<Usuario> listaUsuarios = gestorBD.bajarUsuarios();
		//		for (Usuario usuario : listaUsuarios) {
		//			System.out.println(usuario.toString());
		//		}
	}
}
