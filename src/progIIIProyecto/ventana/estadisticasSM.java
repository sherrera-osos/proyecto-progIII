package progIIIProyecto.ventana;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.util.Arrays;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

public class estadisticasSM extends JFrame{

	private static final long serialVersionUID = 1L;
	private JTable tabla;
	private estadisticasSMModel modelo;
	private JButton btnVolver = new JButton("VOLVER");
	
	public estadisticasSM(JFrame previo, int tipo) {
		this.setSize(1000, 400);
		this.setTitle("Estadisticas Slot Machine");
		this.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		this.setLocationRelativeTo(previo);
		
		JPanel sur = new JPanel();
		this.add(sur, BorderLayout.SOUTH);
		if(tipo == 0) {
			sur.setBackground(new Color(55, 0, 95));
			btnVolver.setBackground(new Color(55, 0, 95));
			btnVolver.setForeground(Color.WHITE);
		} else if(tipo == 1) { //FUEGO
			sur.setBackground(new Color(78, 26, 4));
			btnVolver.setBackground(new Color(78, 26, 4));
			btnVolver.setForeground(Color.WHITE);
		} else if(tipo == 2) { //AGUA
			sur.setBackground(new Color(1, 58, 99));
			btnVolver.setBackground(new Color(1, 58, 99));
			btnVolver.setForeground(Color.WHITE);
		} else if(tipo == 3) { //ELECTRICO
			sur.setBackground(new Color(45, 45, 45));
			btnVolver.setBackground(new Color(255, 234, 0));
			btnVolver.setForeground(Color.BLACK);
		} else { //GHOST
			sur.setBackground(new Color(28, 0, 51));
			btnVolver.setBackground(new Color(28, 0, 51));
			btnVolver.setForeground(Color.WHITE);
		}
		sur.add(btnVolver);
		
		btnVolver.setFont(new Font("Monospaced", Font.BOLD, 20));
		btnVolver.setBorderPainted(false);
		btnVolver.setFocusPainted(false);
		
		btnVolver.addActionListener((e)->{
			this.setVisible(false);
			previo.setVisible(true);
		});

		//USANDO LA BASE DE DATOS:
		String[] listaUsuarios = {"Usuario1","Usuario2","Usuario3","Usuario4","Usuario5","Usuario6","Usuario7","Usuario8","Usuario9","Usuario10","Usuario11","Usuario12","Usuario13","Usuario14","Usuario15","Usuario16"};
		Integer[] listaPuntos = {120,500,300,700,150,450,250,120,200,220,350,220,400,450,670,550};
		Integer[] listaIntentos = {6,2,4,6,1,7,9,10,15,14,10,8,6,7,5,12};
		
		//SI LAS LISTAS NO COINCIDEN
		int total = Math.min(listaUsuarios.length, 
                Math.min(listaPuntos.length, listaIntentos.length));
		
		
		//CREAMOS EL MODELO
		modelo = new estadisticasSMModel(
				Arrays.copyOf(listaUsuarios, total),
                Arrays.copyOf(listaPuntos, total),
                Arrays.copyOf(listaIntentos, total)
                );
		
		tabla = new JTable(modelo);
		JScrollPane scroll = new JScrollPane(tabla);
        this.add(scroll);

        tabla.setGridColor(new Color(75, 0, 130));
        tabla.setRowHeight(40);
        tabla.getTableHeader().setResizingAllowed(false);
        tabla.getColumnModel().getColumn(0).setMinWidth(180);
        tabla.getColumnModel().getColumn(1).setMinWidth(400);
        tabla.getColumnModel().getColumn(2).setMinWidth(200);
        tabla.getColumnModel().getColumn(3).setMinWidth(200);
        
        //RENDERIZADO GENERAL DE LA TABLA
        tabla.getTableHeader().setDefaultRenderer(new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel resultado = new JLabel(value.toString());
				
				resultado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				resultado.setHorizontalAlignment(JLabel.CENTER);
				resultado.setFont(new Font("Monospaced", Font.BOLD, 30));
				resultado.setForeground(Color.WHITE);
				resultado.setOpaque(true);

				//PARA QUE DEPENDIENDO DEL ESTILO EN EL QUE ESTEMOS
				//LA TABLA APAREZCA DE UN COLOR O DE OTRO
				if(tipo == 0) { //NORMAL
					resultado.setBackground(new Color(120, 0, 200));
				} else if(tipo ==1) { //FUEGO
					resultado.setBackground(new Color(200, 55, 40));
				} else if(tipo ==2) { //AGUA
					resultado.setBackground(new Color(0, 168, 232));
				} else if(tipo ==3) { // ELECTRICO
					resultado.setBackground(new Color(255, 234, 0));
				} else { //GHOST
					resultado.setBackground(new Color(28, 0, 51));
				}
				
				return resultado;
			}
		});
        
        //RENDERIZADO PERSONALIZADO PARA LAS CELDAS
        tabla.setDefaultRenderer(Object.class, new TableCellRenderer() {
			
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
					int row, int column) {
				JLabel resultado = new JLabel(value.toString());
				
				if(row == 0) {
					resultado.setBackground(new Color(212, 175, 55));
					resultado.setFont(new Font("SansSerif", Font.BOLD, 30));
				} else if(row == 1) {
					resultado.setBackground(new Color(192, 192, 192));
					resultado.setFont(new Font("SansSerif", Font.BOLD, 25));
				}else if(row == 2) {
					resultado.setBackground(new Color(205, 127, 50));
					resultado.setFont(new Font("SansSerif", Font.BOLD, 20));
				} else {
					resultado.setFont(new Font("Dialog", Font.PLAIN, 20));
					
					if(row%2 == 0) {
						resultado.setBackground(new Color(255, 255, 255));
					} else {
						resultado.setBackground(new Color(230, 225, 245));
					}
				}
				
				resultado.setBorder(BorderFactory.createLineBorder(Color.BLACK));
				resultado.setHorizontalAlignment(JLabel.CENTER);
				resultado.setOpaque(true);
				
				return resultado;
			}
		});
        
        

        this.setVisible(true);
	}
	
	
	
	
	public class estadisticasSMModel extends AbstractTableModel{

		private static final long serialVersionUID = 1L;
		private String[] nombreColumnas = {"RANKING", "NOMBRE USUARIO", "PUNTUACION", "INTENTOS"};
		private String[] nombre;
		private Integer[] puntos;
		private Integer[] intentos;
		
		public estadisticasSMModel(String[] nombre, Integer[] puntos, Integer[] intentos) {
			this.nombre = nombre;
			this.puntos = puntos;
			this.intentos = intentos;
			
			ordenarPorPuntuacion();
		}
		
		private void ordenarPorPuntuacion(){
			for (int i = 0; i < puntos.length -1; i++) {
				for (int j = i +1; j < puntos.length; j++) {
					if(puntos[j] > puntos[i]) {
						
						//SWAP PUNTOS
						int tmpP = puntos[i];
                        puntos[i] = puntos[j];
                        puntos[j] = tmpP;
                        
                        //SWAP NOMBRES
                        String tmpU = nombre[i];
                        nombre[i] = nombre[j];
                        nombre[j] = tmpU;
						
                        //SWAP INTENTOS
                        int tmpI = intentos[i];
                        intentos[i] = intentos[j];
                        intentos[j] = tmpI;
					}
				}	
			}
		}
		
		
		@Override
		public int getRowCount() {
			// TODO Auto-generated method stub
			return nombre.length;
		}

		@Override
		public int getColumnCount() {
			// TODO Auto-generated method stub
			return nombreColumnas.length;
		}

		@Override
        public String getColumnName(int column) {
            return nombreColumnas[column];
        }
		
		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			switch(columnIndex) {
	            case 0: return rowIndex + 1;           // RANKING
	            case 1: return nombre[rowIndex];      // NOMBRE
	            case 2: return puntos[rowIndex];        // PUNTOS
	            case 3: return intentos[rowIndex];      // INTENTOS
	            default: return null;
			}
		}
	}
}