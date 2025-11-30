package progIIProyecto.baseDeDatos;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
    
    public ArrayList<Usuario> loadUsuarios(){
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
    			Pais pais = Pais.valueOf(rs.getString("PAIS"));
    			Genero genero = Genero.valueOf(rs.getString("GENERO"));
    			
    			Usuario usuario = new Usuario(codigo, nombre, contrasenya, tlf, correo, pais, genero);
    			
    			listaUsuarios.add(usuario);
    		}
    		
    	} catch (Exception e) {
            System.err.format("* Error recuperando usuarios: %s.\n", e.getMessage());
        }
        
		return listaUsuarios;
    	
    }
    
    // Este main se quita después
    
    public static void main(String[] args) {
    	GestorBD gestorBD = new GestorBD();
    	
    	ArrayList<Usuario> listaUsuarios = gestorBD.loadUsuarios();
		for (Usuario usuario : listaUsuarios) {
			System.out.println(usuario.toString());
		}
	}

}
