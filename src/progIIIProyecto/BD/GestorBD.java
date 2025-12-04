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
    	
    	gestorBD.subirUsuario(new Usuario("usuarioPruebaRandom", "Contra", 666, "correo", Pais.Alemania, Genero.Femenino));
    	
    	ArrayList<Usuario> listaUsuarios = gestorBD.bajarUsuarios();
		for (Usuario usuario : listaUsuarios) {
			System.out.println(usuario.toString());
		}
	}

}
