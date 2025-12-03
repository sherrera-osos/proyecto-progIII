package progIIIProyecto.ventana;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.LineBorder;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;

import progIIIProyecto.domain.Calidad;
import progIIIProyecto.domain.Genero;
import progIIIProyecto.domain.Logro;
import progIIIProyecto.domain.Pais;
import progIIIProyecto.domain.Usuario;

public class VentanaLogros extends JFrame{

	private static final long serialVersionUID = 1L;
	private int numeroOros = 0;
	private int numeroPlatas = 0;
	private int numeroBronces = 0;
	
	
	public VentanaLogros(JFrame previo) {
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		setSize(new Dimension(1200, 460));
		setLocationRelativeTo(previo);
	
		JPanel panelPrincipal = new JPanel();
		panelPrincipal.setLayout(new GridLayout(2, 2));
		
		// Esto habría que sacarlo de la base de datos
		
		ArrayList<Logro> listaLogrosJuego1 = new ArrayList<Logro>();
		listaLogrosJuego1.add(new Logro("Conseguir más de 10 puntos", true, 10, "Juego1", Calidad.BRONCE));
		listaLogrosJuego1.add(new Logro("Conseguir más de 50 puntos", false, 50, "Juego1", Calidad.PLATA));
		listaLogrosJuego1.add(new Logro("Conseguir más de 100 puntos", false, 100, "Juego1", Calidad.ORO));
		
		ArrayList<Logro> listaLogrosJuego2 = new ArrayList<Logro>();
		listaLogrosJuego2.add(new Logro("Conseguir más de 10 puntos", true, 10, "Juego2", Calidad.BRONCE));
		listaLogrosJuego2.add(new Logro("Conseguir más de 50 puntos", true, 50, "Juego2", Calidad.PLATA));
		listaLogrosJuego2.add(new Logro("Conseguir más de 100 puntos", false, 100, "Juego2", Calidad.ORO));
		
		ArrayList<Logro> listaLogrosJuego3 = new ArrayList<Logro>();
		listaLogrosJuego3.add(new Logro("Conseguir más de 10 puntos", false, 10, "Juego3", Calidad.BRONCE));
		listaLogrosJuego3.add(new Logro("Conseguir más de 50 puntos", false, 50, "Juego3", Calidad.PLATA));
		listaLogrosJuego3.add(new Logro("Conseguir más de 100 puntos", false, 100, "Juego3", Calidad.ORO));
		
		ArrayList<Logro> listaLogrosJuego4 = new ArrayList<Logro>();
		listaLogrosJuego4.add(new Logro("Conseguir más de 10 puntos", true, 10, "Juego4", Calidad.BRONCE));
		listaLogrosJuego4.add(new Logro("Conseguir más de 50 puntos", true, 50, "Juego4", Calidad.PLATA));
		listaLogrosJuego4.add(new Logro("Conseguir más de 100 puntos", true, 100, "Juego4", Calidad.ORO));
		
		ArrayList<ArrayList<Logro>> listaListaLogros = new ArrayList<ArrayList<Logro>>();
		listaListaLogros.add(listaLogrosJuego1);
		listaListaLogros.add(listaLogrosJuego2);
		listaListaLogros.add(listaLogrosJuego3);
		listaListaLogros.add(listaLogrosJuego4);
		
		Usuario usuarioRelacionado = new Usuario("Usuario1", "Contraseña1234", 666666666, "usuario1@gmail.com", Pais.Colombia, Genero.Otro);
		
		// -------------------------------------------
		
		setTitle("Logros de " + usuarioRelacionado.getNombre());
		
		//#########################################################################################################################//
		//INSTANCIAMOS EL RENDERER QUE CREAMOS EN LA OTRA CLASE
		IconoLogroRenderer renderer = new IconoLogroRenderer();
		//#########################################################################################################################//

		
		for (ArrayList<Logro> listaLogros : listaListaLogros) {
			
			JPanel panelLogrosJuego = new JPanel();
			panelLogrosJuego.setBorder(BorderFactory.createTitledBorder(listaLogros.getFirst().getNombreJuego()));
			
			TablaLogrosModel modelo = new TablaLogrosModel(listaLogros);
			JTable tablaLogrosJuego = new JTable(modelo);
			
			//#########################################################################################################################//
			tablaLogrosJuego.getColumnModel().getColumn(0).setCellRenderer(renderer);
			tablaLogrosJuego.getColumnModel().getColumn(2).setCellRenderer(renderer);
			//#########################################################################################################################//

			tablaLogrosJuego.setDefaultRenderer(Object.class, new TableCellRenderer() {
				
				@Override
				public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
						int row, int column) {
					JLabel result = new JLabel();
					result.setToolTipText(value.toString());
					
					if (column == 0) {
						if ((boolean) value) {
							result.setBackground(Color.GREEN);
							result.setToolTipText("Obtenido");
						} else {
							result.setBackground(Color.RED);
							result.setToolTipText("No obtenido");
						}
						
					} else if (column == 1) {
						result.setText((String) value);
						result.setFont(new Font("Arial", Font.PLAIN, 16));
						result.setBackground(new Color(250, 250, 250));
						
					} else if (column == 2) {
						if (value.equals(Calidad.ORO)) {
							result.setBackground(Color.YELLOW);
						} else if (value.equals(Calidad.PLATA)) {
							result.setBackground(Color.LIGHT_GRAY);
						} else {
							result.setBackground(Color.ORANGE);
						}
					}
					
					result.setHorizontalAlignment(JLabel.CENTER);
					
					result.setOpaque(true);
					return result;
				}
			});
			
			panelLogrosJuego.add(tablaLogrosJuego);
			tablaLogrosJuego.setFillsViewportHeight(true);
			tablaLogrosJuego.setRowHeight(80);
			tablaLogrosJuego.getColumnModel().getColumn(0).setMinWidth(80);
			tablaLogrosJuego.getColumnModel().getColumn(1).setMinWidth(320);
			tablaLogrosJuego.getColumnModel().getColumn(2).setMinWidth(80);
			tablaLogrosJuego.setGridColor(Color.BLACK);
			tablaLogrosJuego.setBorder(new LineBorder(Color.BLACK));
			
			panelPrincipal.add(panelLogrosJuego);
			
			// Aprovechamos este for para meter el for del contador dentro			
			for (Logro logro : listaLogros) {
				if (logro.isConseguido()) {
					if (logro.getCalidad().equals(Calidad.ORO)) {
						numeroOros += 1;
					} else if (logro.getCalidad().equals(Calidad.PLATA)) {
						numeroPlatas += 1;
					} else if (logro.getCalidad().equals(Calidad.BRONCE)) {
						numeroBronces += 1;
					}
				}
			}
			
		}
		
		JScrollPane jScrollPane = new JScrollPane(panelPrincipal);
		add(jScrollPane, BorderLayout.CENTER);
		
		//Añadimos el contador de trofeos que ira arriba
		
		//#########################################################################################################################//
		//ESTABLECEMOS LAS RUTAS DE CADA ICONO
		ImageIcon iconoOro = new ImageIcon(getClass().getResource("/imagenes/oro.png"));
		ImageIcon iconoPlata = new ImageIcon(getClass().getResource("/imagenes/plata.png"));
		ImageIcon iconoBronce = new ImageIcon(getClass().getResource("/imagenes/bron.png"));
		//#########################################################################################################################//

		
		JPanel panelContador = new JPanel(new FlowLayout(FlowLayout.RIGHT));
		
		JLabel jLabelIconoBronce = new JLabel("");
		//ESCALAMOS EL ICONO EN 30x30
		jLabelIconoBronce.setIcon(new ImageIcon(iconoBronce.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		//
		jLabelIconoBronce.setFont(new Font("Arial", Font.PLAIN, 16));
		jLabelIconoBronce.setBorder(new LineBorder(Color.BLACK));
		panelContador.add(jLabelIconoBronce);
		
		JLabel jLabelBronce = new JLabel("Bronce: " + numeroBronces + "   ");
		jLabelBronce.setFont(new Font("Arial", Font.PLAIN, 16));
		panelContador.add(jLabelBronce);
		
		
		
		JLabel jLabelIconoPlata = new JLabel("");
		//ESCALAMOS EL ICONO EN 30x30
		jLabelIconoPlata.setIcon(new ImageIcon(iconoPlata.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		//
		jLabelIconoPlata.setFont(new Font("Arial", Font.PLAIN, 16));
		jLabelIconoPlata.setBorder(new LineBorder(Color.BLACK));
		panelContador.add(jLabelIconoPlata);
		
		JLabel jLabelPlata = new JLabel("Plata: " + numeroPlatas + "   ");
		jLabelPlata.setFont(new Font("Arial", Font.PLAIN, 16));
		panelContador.add(jLabelPlata);
		
		
		
		
		JLabel jLabelIconoOro = new JLabel("");
		//ESCALAMOS EL ICONO EN 30x30
		jLabelIconoOro.setIcon(new ImageIcon(iconoOro.getImage().getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH)));
		//
		jLabelIconoOro.setFont(new Font("Arial", Font.PLAIN, 16));
		jLabelIconoOro.setBorder(new LineBorder(Color.BLACK));
		panelContador.add(jLabelIconoOro);
		
		JLabel jLabelOro = new JLabel("Oro: " + numeroOros + "         ");
		jLabelOro.setFont(new Font("Arial", Font.PLAIN, 16));
		panelContador.add(jLabelOro);
		
		add(panelContador, BorderLayout.NORTH);
	
		setVisible(true);
	}
	
	// El modelo de las tablas
	
	public class TablaLogrosModel extends AbstractTableModel {

		private static final long serialVersionUID = 1L;
		private ArrayList<Logro> listaLogros;
		private int COLUMNAS = 3;
		
		public TablaLogrosModel(ArrayList<Logro> listaLogros) {
			this.listaLogros = listaLogros;
		}

		@Override
		public int getRowCount() {
			return listaLogros.size();
		}

		@Override
		public int getColumnCount() {
			return COLUMNAS;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			if (columnIndex == 0) {
				return listaLogros.get(rowIndex).isConseguido();
				
			} else if (columnIndex == 1) {
				return listaLogros.get(rowIndex).getNombre(); 
				
			} else if (columnIndex == 2) {
				return listaLogros.get(rowIndex).getCalidad();
			}
			return null;
		}
		
	}

}
