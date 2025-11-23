package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.util.Collections;
import java.util.TreeMap;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class VentanaEjemploEstadisticas extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTable tablaEstadisticas;
	private TreeMap<Integer, String> mapa;
	
	public VentanaEjemploEstadisticas(JFrame previo) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setMinimumSize(new Dimension(880, 460));
		setTitle("Ventana Ejemplo estadisticas");
		setLocationRelativeTo(previo);
		
		// Esta parte del mapa habra que sacarla de la base de datos
		
		String[] listaUsuarios = {"Usuario1","Usuario2","Usuario3","Usuario4","Usuario5","Usuario6","Usuario7","Usuario8","Usuario9","Usuario10","Usuario11","Usuario12","Usuario13","Usuario14","Usuario15"};
		Integer[] listaPuntos = {12,50,30,70,15,45,28,12,20,22,37,23,4,45,67,54};
		
		TreeMap<Integer, String> mapaUsuariosYPuntos = new TreeMap<Integer, String>(Collections.reverseOrder());
		for (int i = 0;i<listaUsuarios.length;i++) {
			mapaUsuariosYPuntos.put(listaPuntos[i], listaUsuarios[i]);
		}
		
		this.mapa = mapaUsuariosYPuntos;
		
		// ---
		
		initTables();
		
		this.pack();
		setVisible(true);
	}
	
	// función que inicializa la tabla
	
	public void initTables() {
		
		// Se crea la tabla con el modelo
		VentanaEjemploEstadisticasModel modelo = new VentanaEjemploEstadisticasModel(this.mapa);	
		this.tablaEstadisticas = new JTable(modelo);
		tablaEstadisticas.setGridColor(Color.BLACK);
		tablaEstadisticas.setRowHeight(40);
		tablaEstadisticas.getTableHeader().setResizingAllowed(false);
		tablaEstadisticas.getColumnModel().getColumn(0).setMinWidth(180);
		tablaEstadisticas.getColumnModel().getColumn(1).setMinWidth(480);
		tablaEstadisticas.getColumnModel().getColumn(2).setMinWidth(200);
		
		// Renderizado personalizado para la cabecera
		tablaEstadisticas.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel result = new JLabel(value.toString());
				
				result.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				result.setHorizontalAlignment(JLabel.CENTER);
				result.setFont(new Font("Arial", Font.BOLD, 28));
				
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
	
	// El modelo de la tabla (Dependiendo del juego se pueden añadir más columnas)
	
	public class VentanaEjemploEstadisticasModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private String[] nombresColumnas = {"Ranking","Nombre usuario","Puntuación"};
		private TreeMap<Integer, String> mapa;
		
		public VentanaEjemploEstadisticasModel(TreeMap<Integer, String> mapa) {
			this.mapa = mapa;
		}

		@Override
		public int getRowCount() {
			return mapa.keySet().size();
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
				return mapa.get(mapa.keySet().toArray()[rowIndex]); 
			} else if (columnIndex == 2) {
				return mapa.keySet().toArray()[rowIndex];
			}
			return null;
		}
		
	}

}
