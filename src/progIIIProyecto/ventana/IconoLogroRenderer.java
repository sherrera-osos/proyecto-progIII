package progIIIProyecto.ventana;
import java.awt.Color;
import java.awt.Component;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.UIManager;
import javax.swing.table.TableCellRenderer;

import progIIIProyecto.domain.Calidad;

public class IconoLogroRenderer extends JLabel implements TableCellRenderer {

	private static final long serialVersionUID = 1L;
	
	// ICONOS SI LO CONSIGUE O NO
	private final ImageIcon ICONO_CONSEGUIDO = cargarIcono("/imagenes/check.png");
	private final ImageIcon ICONO_NO_CONSEGUIDO = cargarIcono("/imagenes/cross.png");
	
	// ICONOS DEPENDIENDO DE LA CALIDAD DEL LOGRO
	private final ImageIcon ICONO_ORO = cargarIcono("/imagenes/oro.png");
	private final ImageIcon ICONO_PLATA = cargarIcono("/imagenes/plata.png");
	private final ImageIcon ICONO_BRONCE = cargarIcono("/imagenes/bron.png");

	public IconoLogroRenderer() {
		setOpaque(true);
		setHorizontalAlignment(CENTER); //CENTRAMOS EL ICONO
	}
	
	// METODO IMPORTANTE (CARGAR Y DIMENSIONAR LOS ICONOS)
	private ImageIcon cargarIcono(String ruta) {
		try {
			//LO ESCALAMOS EN 40x40
			ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta));
			return new ImageIcon(iconoOriginal.getImage().getScaledInstance(60, 60, java.awt.Image.SCALE_SMOOTH));
		} catch (Exception e) {
			System.err.println("Error al cargar icono: " + ruta);
			return (ImageIcon) UIManager.getIcon("OptionPane.warningIcon"); 
		}
	}

	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
			int row, int column) {

		if (isSelected) {
			setBackground(table.getSelectionBackground());
			setForeground(table.getSelectionForeground());
		} else {
			setBackground(table.getBackground());
			setForeground(table.getForeground());
		}
		
		// RESTABLECEMOS EL CONTENIDO POR DEFECTO
		setIcon(null);
		setText("");
		setToolTipText(null);
		
		if (column == 0) { // COLUMNA DE CONSEGUIDO
			boolean conseguido = (boolean) value;
			
			if (conseguido) {
				setIcon(ICONO_CONSEGUIDO);
				setBackground(new Color(200, 255, 200));
				setToolTipText("Obtenido");
			} else {
				setIcon(ICONO_NO_CONSEGUIDO);
				setBackground(new Color(255, 200, 200));
				setToolTipText("No obtenido");
			}

		} else if (column == 1) { // COLUMNA DEL NOMBRE
			setText((String) value);
			setBackground(new Color(250, 250, 250));
			setToolTipText((String) value);

		} else if (column == 2) { // COLUMNA DE LA CALIDAD
			Calidad calidad = (Calidad) value;
			setText(""); // EL ICONO REEMPLAZA EL TEXTO
			setToolTipText(calidad.name());
			
			if (calidad.equals(Calidad.ORO)) {
				setIcon(ICONO_ORO);
				setBackground(new Color(255, 255, 180));
			} else if (calidad.equals(Calidad.PLATA)) {
				setIcon(ICONO_PLATA);
				setBackground(new Color(230, 230, 230));
			} else if (calidad.equals(Calidad.BRONCE)) {
				setIcon(ICONO_BRONCE);
				setBackground(new Color(255, 220, 180));
			}
		}

		return this;
	}
}
