package progIIIProyecto.BD;

import java.awt.Component;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.swing.JOptionPane;

import progIIIProyecto.domain.Calidad;
import progIIIProyecto.domain.Genero;
import progIIIProyecto.domain.Logro;
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
	
	// FUNCION PARA BAJAR TODOS LOS LOGROS DE UN JUEGO
	
		public ArrayList<Logro> bajarLogrosDejuego(String nombreJuego){
			ArrayList<Logro> listaLogros = new ArrayList<Logro>();
			String sql = "SELECT * FROM LOGRO WHERE NOM_JUEGO = '"+nombreJuego+"'";

			try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
				ResultSet rs = pst.executeQuery(sql);

				while(rs.next()) {
					int codigo = rs.getInt("COD_LOG");
					String nombreLogro = rs.getString("NOM_LOG");
					Calidad calidad = Calidad.valueOf(rs.getString("CALIDAD"));

					Logro logro = new Logro(codigo, nombreLogro, nombreJuego, calidad);

					listaLogros.add(logro);
				}

			} catch (Exception e) {
				System.err.format("* Error recuperando logros: %s.\n", e.getMessage());
				e.printStackTrace();
			}

			return listaLogros;

		}
		
		// FUNCION PARA BAJAR LOS CODIGOS DE TODOS LOS LOGROS QUE TIENE UN USUARIO
		
		public ArrayList<Integer> bajarLogrosDeUsuario(int codigoUsuario){
			ArrayList<Integer> listaCodigosLogros = new ArrayList<Integer>();
			String sql = "SELECT * FROM TIENE WHERE COD_USU = "+codigoUsuario+"";

			try (Connection con = DriverManager.getConnection(CONNECTION_STRING); Statement pst = con.createStatement()){
				ResultSet rs = pst.executeQuery(sql);

				while(rs.next()) {
					int codigoLogro = rs.getInt("COD_LOG");

					listaCodigosLogros.add(codigoLogro);
				}

			} catch (Exception e) {
				System.err.format("* Error recuperando logros: %s.\n", e.getMessage());
				e.printStackTrace();
			}

			return listaCodigosLogros;

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
	
	// FUNCION PARA MODIFICAR LOS VALORES DE UN USUARIO DE LA BASE DE DATOS
	// Recibe un usuario y modifica los valores del usuario en bd que tenga el mismo código
	
	public void modificarUsuario(Usuario usuario) {
		String sqlUpdate = "UPDATE USUARIO SET NOM_USU = ?, GENERO = ?, PAIS = ?, CONTRA = ?, TLF = ?, CORREO = ? WHERE COD_USU = ?";

		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstUpdate = con.prepareStatement(sqlUpdate)){

			
			pstUpdate.setString(1, usuario.getNombre());
			pstUpdate.setString(2, usuario.getGenero().toString());
			pstUpdate.setString(3, usuario.getPais().toString());
			pstUpdate.setString(4, usuario.getContr());
			pstUpdate.setInt(5, usuario.getTlf());
			pstUpdate.setString(6, usuario.getCorreo());
			pstUpdate.setInt(7, usuario.getCodigo());

			pstUpdate.executeUpdate();

		} catch (SQLException e) {
			System.err.format("* Error al modificar el usuario: %s.\n", e.getMessage());
			e.printStackTrace();
		}
	}
	
	// FUNCION PARA AÑADIR UN PUNTAJE A LA BASE DE DATOS

	public void subirPuntaje(Puntaje puntaje) {
		if (puntaje.getCodigoDelUsuario() != 1) {
			String sqlSelect = "SELECT PUNTOS1, PUNTOS2 FROM PUNTAJE WHERE COD_USU = ? AND NOM_JUEGO = ? ORDER BY PUNTOS1 DESC, PUNTOS2 ASC";
			String sqlDelete = "DELETE FROM PUNTAJE WHERE COD_USU = ? AND NOM_JUEGO = ?";
			String sqlInsert = "INSERT INTO PUNTAJE VALUES (?, ?, ?, ?, ?)";

			try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
					PreparedStatement pstSelect = con.prepareStatement(sqlSelect);
					PreparedStatement pstDelete = con.prepareStatement(sqlDelete);
					PreparedStatement pstInsert = con.prepareStatement(sqlInsert)
					){

				int puntos1Record = 0;
				int puntos2Record = 0;

				pstSelect.setInt(1, puntaje.getCodigoDelUsuario());
				pstSelect.setString(2, puntaje.getNombreJuego());

				ResultSet rsSelect = pstSelect.executeQuery();

				if(rsSelect.next()) { // Solo necesitamos el primero pues la select ya los ordena según queremos
					puntos1Record = rsSelect.getInt("PUNTOS1");
					puntos2Record = rsSelect.getInt("PUNTOS2");
				}

				if (puntos1Record < puntaje.getPuntos1() || (puntos1Record == puntaje.getPuntos1() && puntos2Record > puntaje.getPuntos2())) {

					pstDelete.setInt(1, puntaje.getCodigoDelUsuario());
					pstDelete.setString(2, puntaje.getNombreJuego());

					pstDelete.executeUpdate(); //Borramos los anteriores puntos del juego que no son record

					pstInsert.setInt(1, puntaje.getCodigo());
					pstInsert.setString(2, puntaje.getNombreJuego());
					pstInsert.setInt(3, puntaje.getPuntos1());
					pstInsert.setInt(4, puntaje.getPuntos2());
					pstInsert.setInt(5, puntaje.getCodigoDelUsuario());

					pstInsert.executeUpdate(); //Añadimos el nuevo record del usuario

				}

			} catch (SQLException e) {
				System.err.format("* Error carga: %s.\n", e.getMessage());
				e.printStackTrace();
			}
		} else {
			System.out.println("Es el invitado, no se le guardan los puntos");
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
    
	// FUNCION PARA ASIGNAR UN LOGRO A UN USUARIO
	public void asignarLogroAUsuario(int codigoUsuario, int codigoLogro) {
		// Primero comprobamos si ya lo tiene para no duplicar
		String sqlChek = "SELECT * FROM TIENE WHERE COD_USU = ? AND COD_LOG = ?";
		String sqlInsert = "INSERT INTO TIENE (COD_USU, COD_LOG) VALUES (?, ?)";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING)){
			//Check
			try(PreparedStatement pstCheck = con.prepareStatement(sqlChek)){
				pstCheck.setInt(1, codigoUsuario);
				pstCheck.setInt(2, codigoLogro);
				ResultSet rs = pstCheck.executeQuery();
				if (rs.next()) return; // Si ya existe, no hacemos nada				
			}
			
			// Insert
			try (PreparedStatement pstInsert = con.prepareStatement(sqlInsert)){
				pstInsert.setInt(1, codigoUsuario);
				pstInsert.setInt(2, codigoLogro);
				pstInsert.executeUpdate();
				System.out.println("¡Logro "+ "desbloqueado para el usuario "+ codigoUsuario+ "!");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int obtenerRecordPersonal(int codUsuario) {
		int max = 0;
		String sql = "SELECT max(PUNTOS1) FROM PUNTAJE WHERE COD_USU= ? AND NOM=JUEGO= '2048'";
		
		try {
			Connection conn = DriverManager.getConnection(CONNECTION_STRING);
			
			PreparedStatement ps = conn.prepareStatement(sql);
			ps.setInt(1,codUsuario);
			ResultSet rs = ps.executeQuery();
			
			if(rs.next()) {
				max= rs.getInt(1);
			}
			
		} catch (SQLException e) {
			System.err.println("Error al obtener récord: " + e.getMessage());
		}
		
		return max;
	}
	
	public int obtenerSiguienteCodigoPuntaje() {
		
	    String sql = "SELECT max(COD_PUN) FROM PUNTAJE";
	    
	    try (Connection conn=DriverManager.getConnection(CONNECTION_STRING)){
			PreparedStatement ps = conn.prepareStatement(sql);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) {
				int maximo = rs.getInt(1);
				return maximo +1;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	    return 1;
	    
	}
	
	// FUNCIÓN QUE COMPRUEBA QUE EXISTE EL USUARIO DEL NOMBRE QUE SE LE DA Y QUE COINCIDE CON SU CONTRASEÑA,
	// SI EL USUARIO EXISTE Y LA CONTRASEÑA COINCIDE, DEVUELVE EL COD_USU, SI NO DEVUELVE -1
	
	public int comprobarUsuario(String nombre, String contra, Component c) {
		int codigoUsuarioCorrecto = -1;
		String sqlSelect = "SELECT * FROM USUARIO WHERE NOM_USU = ?";
		
		try (Connection con = DriverManager.getConnection(CONNECTION_STRING);
				PreparedStatement pstSelect = con.prepareStatement(sqlSelect)){
			
			pstSelect.setString(1, nombre);
			
			ResultSet rsUsuario = pstSelect.executeQuery();
			
			if (rsUsuario.next()) {
				String contraBD = rsUsuario.getString("CONTRA");
				
				if (contra.equals(contraBD)) {
					codigoUsuarioCorrecto = rsUsuario.getInt("COD_USU");
				} else {
					JOptionPane.showMessageDialog(c, "Contraseña incorrecta", "Error", JOptionPane.OK_OPTION);
				}
			} else {
				JOptionPane.showMessageDialog(c, "No hay existe ese usuario", "Error", JOptionPane.OK_OPTION);
			}

			con.close();
		} catch (SQLException e) {
			System.err.format("* Error recuperando el usuario: %s.\n", e.getMessage());
			e.printStackTrace();
		}
				
		return codigoUsuarioCorrecto;
	}
	
	
	
	public void vaciarPuntajesBlackJack() {
	    String sql = "DELETE FROM PUNTAJE WHERE NOM_JUEGO = 'BlackJack'";
	    try (Connection con = DriverManager.getConnection(CONNECTION_STRING); 
	         Statement st = con.createStatement()) {
	        st.executeUpdate(sql);
	        System.out.println("¡Tabla de BlackJack reseteada!");
	    } catch (SQLException e) {
	        e.printStackTrace();
	    }
	}
	
	
	
	// Este main se quita después

	public static void main(String[] args) {
//		GestorBD gestorBD = new GestorBD();
//		gestorBD.vaciarPuntajesBlackJack();
		
//		System.out.println(gestorBD.comprobarUsuario("Usuario2", "Usuario1",null));
		
//    	gestorBD.subirPuntaje(new Puntaje("BuscaMinas", 850, 1003, 462374553));

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
