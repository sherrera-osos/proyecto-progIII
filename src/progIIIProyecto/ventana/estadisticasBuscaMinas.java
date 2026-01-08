package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.Time;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import progIIIProyecto.BD.GestorBD;
import progIIIProyecto.domain.Puntaje;
import progIIIProyecto.domain.Usuario;

public class estadisticasBuscaMinas extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable tablaEstadisticas;
	private ArrayList<Puntaje> listaPuntajes;
	
	public estadisticasBuscaMinas(JFrame previo, Usuario usuario) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(1200, 300));
		setTitle("Estadísticas Buscaminas");
		setLocationRelativeTo(previo);
		
		// Sacamos todos los puntajes del juego del la bd y los ponemos en una lista
		
		GestorBD gestorBD = new GestorBD();
		listaPuntajes = gestorBD.bajarPuntajesDeJuego("BuscaMinas");
		
		// Ordenamos esa lista segun los valores de los puntajes principales y secundarios
		
		ordenar(listaPuntajes,listaPuntajes.size()-1);
		
		initTables();
		
		this.pack();
		setVisible(true);
	}
	
	// función que inicializa la tabla
	
	public void initTables() {
		
		// Se crea la tabla con el modelo
		VentanaEjemploEstadisticasModel modelo = new VentanaEjemploEstadisticasModel(listaPuntajes);	
		this.tablaEstadisticas = new JTable(modelo);
		tablaEstadisticas.setGridColor(Color.BLACK);
		tablaEstadisticas.setRowHeight(40);
		tablaEstadisticas.getTableHeader().setResizingAllowed(false);
		tablaEstadisticas.getColumnModel().getColumn(0).setMinWidth(180);
		tablaEstadisticas.getColumnModel().getColumn(1).setMinWidth(480);
		tablaEstadisticas.getColumnModel().getColumn(2).setMinWidth(200);
		tablaEstadisticas.getColumnModel().getColumn(3).setMinWidth(200);
		
		// Renderizado personalizado para la cabecera
		tablaEstadisticas.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel result = new JLabel(value.toString());
				
				result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				result.setHorizontalAlignment(JLabel.CENTER);
				result.setFont(new Font("Arial", Font.BOLD, 20));
				
				result.setOpaque(true);
				return result;
			}
		});
		
		// Renderizado personalizado para las celdas de la tabla
		tablaEstadisticas.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel result = new JLabel(value.toString());
				
				if (row == 0) {
					result.setFont(new Font("Arial", Font.BOLD, 36));
					result.setBackground(Color.YELLOW);
				} else if (row == 1) {
					result.setFont(new Font("Arial", Font.BOLD, 32));
					result.setBackground(Color.LIGHT_GRAY);
				} else if (row == 2) {
					result.setFont(new Font("Arial", Font.BOLD, 28));
					result.setBackground(Color.ORANGE);
				} else {
					result.setFont(new Font("Arial", Font.PLAIN, 24));
					if (row%2 == 0) {
						result.setBackground(new Color(210, 227, 219));
					} else {
						result.setBackground(new Color(250, 250, 250));
					}
				}
				
				result.setHorizontalAlignment(JLabel.CENTER);
				
				result.setOpaque(true);
				return result;
			}
		});
		
		// Añadidos los renderers a un JscrollPane que se añadira a la tabla
		JScrollPane scrollPaneEstadisticas = new JScrollPane(tablaEstadisticas);
		this.add(scrollPaneEstadisticas, BorderLayout.CENTER);
	}
	
	// El modelo de la tabla
	
	public class VentanaEjemploEstadisticasModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] nombresColumnas = {"Ranking","Nombre usuario","Puntos", "Tiempo"};
		private ArrayList<Puntaje> listaPuntajes;
		
		public VentanaEjemploEstadisticasModel(ArrayList<Puntaje> listaPuntajes) {
			this.listaPuntajes = listaPuntajes;
		}

		@Override
		public int getRowCount() {
			return listaPuntajes.size();
		}

		@Override
		public int getColumnCount() {
			return nombresColumnas.length;
		}
		
		@Override
	    public String getColumnName(int column) {
	        return nombresColumnas[column];
	    }

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return rowIndex+1;
			} else if (columnIndex == 1) {
				GestorBD gestorBD = new GestorBD();
				String nombreUsuario = gestorBD.obtenerUsuario(listaPuntajes.get(rowIndex).getCodigoDelUsuario()).getNombre();
				return nombreUsuario; 
			} else if (columnIndex == 2) {
				int puntuacionPrincipal = listaPuntajes.get(rowIndex).getPuntos1();
				return puntuacionPrincipal;
			} else if (columnIndex == 3) {
				
				long segundos = listaPuntajes.get(rowIndex).getPuntos2();
				long minutos = TimeUnit.SECONDS.toMinutes(segundos);
				segundos -= TimeUnit.MINUTES.toSeconds(minutos);
				
				String stringMinutos = minutos+"";
				if ((stringMinutos).length()==1) stringMinutos = "0"+ minutos;
				String stringSegundos = segundos+"";
				if ((stringSegundos).length()==1) stringSegundos = "0"+ segundos;
				
				String puntuacionSecundaria = stringMinutos+":"+stringSegundos;
				
				return puntuacionSecundaria;
				
			}
			return null;
		}
		
	}
	
	// Función para ordenar los puntajes de cada usuario, primero por el puntaje principal y después por el secundario
	// En un buscaminas por ejemplo, el puntaje principal sería las casillas resuletas, y el secundario el tiempo conseguido
	// así, todos los jugadores que hayan encontrado todas las minas estaran arriba del todo, pero, para desempatar, el que
	// menoa tiempo haya hecho sera el primero (en este caso habría que cambiar la función para que los tiempos los ordene de
	// forma descendente, dandole la vuelta al >)
	
	
	public void ordenar(ArrayList<Puntaje> listaPuntajes, int indice) {
		int i = indice;
		while (i>0) {
			for (int j=0;j<i;j++)
				if (listaPuntajes.get(i).getPuntos1() > listaPuntajes.get(j).getPuntos1()) {
					Puntaje puntajeTemporal = listaPuntajes.get(i);
					listaPuntajes.set(i, listaPuntajes.get(j));
					listaPuntajes.set(j, puntajeTemporal);
				} else if (listaPuntajes.get(i).getPuntos1() == listaPuntajes.get(j).getPuntos1() && listaPuntajes.get(i).getPuntos2() < listaPuntajes.get(j).getPuntos2()){
					Puntaje puntajeTemporal = listaPuntajes.get(i);
					listaPuntajes.set(i, listaPuntajes.get(j));
					listaPuntajes.set(j, puntajeTemporal);  // En caso de empate en el puntaje principal se desempata con el puntaje secundario
				}
			i--;
			ordenar(listaPuntajes, i);
		}
	}
	
	public static void main(String[] args) {
		new estadisticasBuscaMinas(null, null);
	}

}
