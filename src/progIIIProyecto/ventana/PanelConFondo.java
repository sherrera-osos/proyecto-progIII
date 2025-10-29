package progIIIProyecto.ventana;

import java.awt.Graphics;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PanelConFondo extends JPanel{

	private static final long serialVersionUID = 1L;
	
	private Image imagenFondo;
	
	public PanelConFondo(String rutaImagen) {
		//REALIZAMOS UN TRY-CATCH EN CASO DE QUE NO SE CARGUE BIEN LA IMAGEN
		
		System.out.println("Intentando cargar: " + rutaImagen);
		//PARA SABER LA RUTA REAL QUE ESTAMOS USANDO
		System.out.println("Ruta real utilizada: " + getClass().getResource(rutaImagen));
		
		try {
			
			imagenFondo = new ImageIcon(getClass().getResource(rutaImagen)).getImage();
			
		} catch (Exception e) {
			
			System.err.println("Error al cargar la imagen de fondo: "+rutaImagen);
			e.printStackTrace();
			imagenFondo=null;
			
		}
		
	}
		
	/**
     * DIBUJA LA IMAGEN Y LA PONE A ESCALA.
     */
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // LLAMA AL METODO DE LA CLASE PADRE
        
        if (imagenFondo != null) {
            // PINTA LA IMAGEN EN LAS COORDS 0,0 Y LA ESCALA AL ANCHO Y ALTO DEL PANEL
            g.drawImage(imagenFondo, 0, 0, getWidth(), getHeight(), this);
        }
    }


}
