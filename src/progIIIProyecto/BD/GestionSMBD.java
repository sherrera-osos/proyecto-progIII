package progIIIProyecto.BD;

import java.awt.Color;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;

public class GestionSMBD {
	
	//LA RUTA HACIA MI BASE DE DATOS
	private static final String SQLITE_FILE = "resources/db/SlotMachine.db";
	private static final String CONNECTION_STRING = "jdbc:sqlite:";
	
	
	public GestionSMBD() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			System.err.format("* Error al cargar el driver de la BBDD: %s\n\"", e.getMessage());
		}
	}
	
	//#########################################################################################################################//
	//ESTABLECEMOS LA CONEXION
	public static Connection conectar() throws SQLException{
		return DriverManager.getConnection(CONNECTION_STRING + SQLITE_FILE);
	}
	
	
	//#########################################################################################################################//

	public void crearTablas() {
		String sqlThemes = "CREATE TABLE IF NOT EXISTS themes "
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "name TEXT NOT NULL UNIQUE, "
				+ "desc TEXT); ";
		
		String sqlThemeColors = "CREATE TABLE IF NOT EXISTS theme_colors "
				+ "(id INTEGER PRIMARY KEY AUTOINCREMENT, "
				+ "theme_id INTEGER NOT NULL, "
				+ "element TEXT NOT NULL, "
				+ "r INTEGER NOT NULL, "
				+ "g INTEGER NOT NULL, "
				+ "b INTEGER NOT NULL, "
				+ "FOREIGN KEY(theme_id) REFERENCES themes(id)); ";
	
		try (Connection con = conectar();
				Statement stmt = con.createStatement()){
			
			stmt.execute(sqlThemes);
			stmt.execute(sqlThemeColors);
			
			System.out.println("TABLAS CREADAS CORRECTAMENTE");
			
		} catch (SQLException e) {
			System.err.println("ERROR CREANDO LAS TABLAS");
			e.printStackTrace();
		}
	
	
	}
	//#########################################################################################################################//
	
	public void insertarTheme(String name, String desc) {
		
		String sql = "INSERT OR IGNORE INTO themes(name, desc) VALUES(?,?) ";
		
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, name);
			pstmt.setString(2, desc);
			
			pstmt.executeUpdate();
			
			System.out.println("Theme insertado");
		} catch (SQLException e) {
			System.err.println("Error insertando theme");
	        e.printStackTrace();
		}
		
	}
	
	//#########################################################################################################################//
	public int obtenerThemeID(String themeName) {
		
		String sql = "SELECT id FROM themes WHERE name = ? ";
		int id = -1;
		
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, themeName);
			ResultSet rs = pstmt.executeQuery();
			
			if(rs.next()) {
				id = rs.getInt("id");
			}
			
		} catch (SQLException e) {
			System.err.println("Error obteniendo theme_id");
	        e.printStackTrace();
		}
		
		return id;
	}
	//#########################################################################################################################//

	
	public void insertarColorTheme(int themeId, String element, int r, int g, int b) {
		
		String sql = "INSERT INTO theme_colors (theme_id, element, r, g, b) "
				+ "VALUES (?, ?, ?, ?, ?) ";
		
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setInt(1, themeId);
			pstmt.setString(2, element);
			pstmt.setInt(3, r);
			pstmt.setInt(4, g);
			pstmt.setInt(5, b);

			pstmt.executeUpdate();
			System.out.println("Color añadido: "+element);
			
		} catch (SQLException e) {
			System.err.println("Error insertando color");
	        e.printStackTrace();
		}
	}
	//#########################################################################################################################//

	public void insertarThemeCompleto(String themeName, String desc, Map<String, Color>colores) {
		
		insertarTheme(themeName, desc);
		int themeId= obtenerThemeID(themeName);
		
		if(themeId == -1) {
			System.err.println("No se pudo obtener el ID del theme: "+ themeName);
			return;
		}
		
		for(String elemento : colores.keySet()) {
			Color c = colores.get(elemento);
			insertarColorTheme(themeId, elemento, c.getRed(), c.getGreen(), c.getBlue());
		}
		
		System.out.println("Theme completo insertado: "+themeName+"\n");
	}
	
	
	//#########################################################################################################################//
	
	public void mostrarTodosLosThemes() {
	    String sql = "SELECT * FROM themes ";

	    try (Connection con = conectar();
	         Statement stmt = con.createStatement();
	         ResultSet rs = stmt.executeQuery(sql)) {

	        System.out.println("\n---- LISTA DE TEMAS DISPONIBLES ----");
	        System.out.println("ID, NOMBRE, DESCRIPCIÓN");
	        System.out.println("------------------------------------------------------------");

	        while (rs.next()) {
	            int id = rs.getInt("id");
	            String name = rs.getString("name");
	            String desc = rs.getString("desc");

	            System.out.println(id+", "+name+", "+ desc);
	        }
	        System.out.println("------------------------------------------------------------\n");

	    } catch (SQLException e) {
	        System.err.println("Error al recuperar los themes");
	        e.printStackTrace();
	    }
	}
	//#########################################################################################################################//

	public Map<String, Color> cargarTheme(String themeName){
		
		Map<String, Color> colores = new HashMap<>();
		
		String sql ="SELECT tc.element, tc.r, tc.g, tc.b "
				+ "FROM theme_colors tc "
				+ "JOIN themes t ON tc.theme_id = t.id "
				+ "WHERE t.name = ? ";
		
		try (Connection con = conectar();
				PreparedStatement pstmt = con.prepareStatement(sql)){
			
			pstmt.setString(1, themeName);
			ResultSet rs = pstmt.executeQuery();
			
			while(rs.next()) {
				String elemento = rs.getString("element");
				int r = rs.getInt("r");
				int g = rs.getInt("g");
				int b = rs.getInt("b");
				
				colores.put(elemento, new Color(r,g,b));
			}
			
		} catch (SQLException e) {
			System.err.println("Error cargando theme: " + themeName);
	        e.printStackTrace();
		}
		
		return colores;
	}
	
	//#########################################################################################################################//

	
	
	//#########################################################################################################################//

	
	public void borrarTabla(String nombreTabla) {
	    String sql = "DROP TABLE IF EXISTS " + nombreTabla;

	    try (Connection conn = DriverManager.getConnection(CONNECTION_STRING + SQLITE_FILE);
	         Statement stmt = conn.createStatement()) {

	        stmt.execute(sql);
	        System.out.println("Tabla eliminada correctamente: " + nombreTabla);

	    } catch (SQLException e) {
	        System.err.println("Error al borrar la tabla " + nombreTabla);
	        e.printStackTrace();
	    }
	}
	
	
	public static void main(String[] args) {
		
		GestionSMBD gestionSMBD = new GestionSMBD(); //CARGAR DRIVER
			
		gestionSMBD.crearTablas();
		
		
		Map<String, Color> ghost = new HashMap<>();

		ghost.put("background_main", new Color(28, 0, 51));
		ghost.put("menu_bar", new Color(28, 0, 51));
		ghost.put("panel_principal", new Color(28, 0, 51));
		ghost.put("panel_botones", new Color(28, 0, 51));
		ghost.put("button_primary", new Color(28, 0, 51));
		ghost.put("button_secondary", new Color(28, 0, 51));
		ghost.put("button_text", Color.WHITE);
		
		Map<String, Color> fuego = new HashMap<>();
		
		fuego.put("background_main", new Color(200, 55, 40));
		fuego.put("menu_bar", new Color(200, 55, 40));
		fuego.put("panel_principal", new Color(200, 55, 40));
		fuego.put("panel_botones", new Color(78, 26, 4));
		fuego.put("button_primary", new Color(255, 106, 0));
		fuego.put("button_secondary", new Color(255, 143, 51));
		fuego.put("button_text", Color.WHITE);
		
		Map<String, Color> agua = new HashMap<>();
		
		agua.put("background_main", new Color(0, 168, 232));
		agua.put("menu_bar", new Color(0, 168, 232));
		agua.put("panel_principal", new Color(0, 168, 232));
		agua.put("panel_botones", new Color(1, 58, 99));
		agua.put("button_primary", new Color(0, 168, 232));
		agua.put("button_secondary", new Color(0, 168, 232));
		agua.put("button_text", Color.WHITE);
		
		Map<String, Color> electrico = new HashMap<>();
		
		electrico.put("background_main", new Color(255, 234, 0));
		electrico.put("menu_bar", new Color(45, 45, 45));
		electrico.put("panel_principal", new Color(255, 234, 0));
		electrico.put("panel_botones", new Color(45, 45, 45));
		electrico.put("button_primary", new Color(255, 234, 0));
		electrico.put("button_secondary", new Color(255, 234, 0));
		electrico.put("button_text", Color.BLACK);
		
		Map<String, Color> normal = new HashMap<>();
		
		normal.put("background_main", Color.LIGHT_GRAY);
		normal.put("menu_bar", Color.LIGHT_GRAY);
		normal.put("panel_principal", Color.LIGHT_GRAY);
		normal.put("panel_botones", Color.LIGHT_GRAY);
		normal.put("button_primary", Color.LIGHT_GRAY);
		normal.put("button_secondary", Color.LIGHT_GRAY);
		normal.put("button_text", Color.BLACK);
		
		Map<String, Color> bicho = new HashMap<>();
		
		bicho.put("background_main", new Color(121, 163, 29));
		bicho.put("menu_bar", new Color(121, 163, 29));
		bicho.put("panel_principal", new Color(121, 163, 29));
		bicho.put("panel_botones", new Color(121, 163, 29));
		bicho.put("button_primary", new Color(121, 163, 29));
		bicho.put("button_secondary", new Color(121, 163, 29));
		bicho.put("button_text", Color.BLACK);
		
		Map<String, Color> planta = new HashMap<>();
		
		planta.put("background_main", new Color(31, 156, 84));
		planta.put("menu_bar", new Color(31, 156, 84));
		planta.put("panel_principal", new Color(31, 156, 84));
		planta.put("panel_botones", new Color(31, 156, 84));
		planta.put("button_primary", new Color(31, 156, 84));
		planta.put("button_secondary", new Color(31, 156, 84));
		planta.put("button_text", Color.WHITE);
		
		
		gestionSMBD.insertarThemeCompleto("ghost", "Tema fantasma por defecto", ghost);
		gestionSMBD.insertarThemeCompleto("fuego", "Tema fuego", fuego);
		gestionSMBD.insertarThemeCompleto("agua", "Tema agua", agua);
		gestionSMBD.insertarThemeCompleto("electrico", "Tema electrico", electrico);
		gestionSMBD.insertarThemeCompleto("normal", "Tema normal", normal);
		gestionSMBD.insertarThemeCompleto("bicho", "Tema bicho", bicho);
		gestionSMBD.insertarThemeCompleto("planta", "Tema planta", planta);

		gestionSMBD.mostrarTodosLosThemes();
		
		
//		gestionSMBD.borrarTabla("theme_colors");
//		gestionSMBD.borrarTabla("themes");
//		gestionSMBD.borrarTabla("colors");
//		
	}

}
