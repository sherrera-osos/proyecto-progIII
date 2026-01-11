package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
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
import progIIIProyecto.ventana.estadisticasBuscaMinas.VentanaEjemploEstadisticasModel;

public class estadisticas2048 extends JFrame{
	
	private static final long serialVersionUID = 1L;
    private JTable tablaEstadisticas;
    private ArrayList<Puntaje> listaPuntajes;
	
	public estadisticas2048(JFrame previo, Usuario usuario) {
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setMinimumSize(new Dimension(1200, 400));
        setTitle("Ranking Global 2048");
        setLocationRelativeTo(previo);

        // 1. Obtener datos de la BD para el juego "2048"
        GestorBD gestorBD = new GestorBD();
        listaPuntajes = gestorBD.bajarPuntajesDeJuego("2048");

        if (listaPuntajes != null && !listaPuntajes.isEmpty()) {
            preferencia(listaPuntajes, listaPuntajes.size()-1);
        }

        initTables();

        this.pack();
        setVisible(true);
    }
	
	
	public void initTables() {
        // Modelo de tabla
		ModeloEstadisticas2048 modelo = new ModeloEstadisticas2048 (listaPuntajes);	
        this.tablaEstadisticas = new JTable(modelo);
        
        tablaEstadisticas.setGridColor(Color.BLACK);
        tablaEstadisticas.setRowHeight(45);
        tablaEstadisticas.getTableHeader().setResizingAllowed(false);
        
        // Ajuste de anchos de columna
        tablaEstadisticas.getColumnModel().getColumn(0).setPreferredWidth(100); // Ranking
        tablaEstadisticas.getColumnModel().getColumn(1).setPreferredWidth(400); // Usuario
        tablaEstadisticas.getColumnModel().getColumn(2).setPreferredWidth(200); // Puntos
        tablaEstadisticas.getColumnModel().getColumn(3).setPreferredWidth(200); // Tiempo

        tablaEstadisticas.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                JLabel label = new JLabel(value.toString());
                label.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                label.setHorizontalAlignment(JLabel.CENTER);
                label.setFont(new Font("Arial", Font.BOLD, 18));
                label.setOpaque(true);
                label.setBackground(new Color(180, 120, 80)); // Color acorde al estilo 2048
                label.setForeground(Color.WHITE);
                return label;
            }
        });
        
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
        
        JScrollPane scrollPaneEstadisticas = new JScrollPane(tablaEstadisticas);
		this.add(scrollPaneEstadisticas, BorderLayout.CENTER);
	}
	
	
	public class ModeloEstadisticas2048 extends AbstractTableModel {
        private String[] titulos = {"Posición", "Usuario", "Puntuación", "Tiempo"};
        private ArrayList<Puntaje> puntos;

        public ModeloEstadisticas2048(ArrayList<Puntaje> puntos) {
            this.puntos = puntos;
        }

        @Override public int getRowCount() { 
        	return puntos.size(); 
        	}
        
        @Override public int getColumnCount() { 
        	return titulos.length; 
        	}
        
        @Override public String getColumnName(int col) { 
        	return titulos[col]; 
        	}

        @Override
        public Object getValueAt(int rowIndex, int columnIndex) {
            Puntaje p = puntos.get(rowIndex);
            switch (columnIndex) {
                case 0: return rowIndex + 1;
                
                case 1: 
                    GestorBD bd = new GestorBD();
                    Usuario u = bd.obtenerUsuario(p.getCodigoDelUsuario());
                    if (u==null) {
                    	return "Invitado";
                    }
                    return u.getNombre();
                    
                    
                case 2: return p.getPuntos1(); 
                
                case 3: 
                    long segundos = p.getPuntos2(); 
                    return String.format("%02d:%02d", 
                        TimeUnit.SECONDS.toMinutes(segundos),
                        segundos % 60);
                default: return null;
            }
        }
	}
	
	
	public void preferencia(ArrayList<Puntaje> lista, int indice) {
		int i = indice;
        while (i > 0) {
            for (int j = 0; j < i; j++) {
            	Puntaje pArriba = lista.get(i);
            	Puntaje pAbajo = lista.get(j);
            	
            	boolean cambiar = false;
            	if (pArriba.getPuntos1() > pAbajo.getPuntos1()) {
            		cambiar = true;
            		
            	} else if(pArriba.getPuntos1() == pAbajo.getPuntos1() && pArriba.getPuntos2() < pAbajo.getPuntos2()) {
            		cambiar = true;
            	}
            	
            	if (cambiar) {
            		lista.set(i, pAbajo);
            		lista.set(j, pArriba);
            	}
            }
            i--;
            preferencia(lista, i);
        }
	}
}
