package divisionpoliticacolombia;;

import java.io.*;
import javax.swing.*;

public class Archivo {

    // Método estático para abrir archivos planos
    public static BufferedReader abrirArchivo(String nombreArchivo) {
        File f = new File(nombreArchivo);
        // Existe el archivo?
        if (f.exists()) {
            /* captura de error estructurada
             * Intentar realizar la instrucción de apertura del archivo.
             * Es susceptible de no poder realizarse */
            try {
                /* Apertura del archivo plano
                 * La clase StreamReader permite manipular secuencia de caracteres.
                 * La clase abstracta File ofrece funcionalidad para operar con archivos */
                FileReader fr = new FileReader(f);
                return new BufferedReader(fr);
            } catch (IOException e) {
                /* Sucedió un error que se captura mediante la clase IOException
                 * encargada de manipular errores de entrada y salida */
                JOptionPane.showMessageDialog(new JFrame(), e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                return null;
            }
        } else {
            JOptionPane.showMessageDialog(new JFrame(), "El archivo " + nombreArchivo + " no existe", "Error", JOptionPane.ERROR_MESSAGE);
            return null;
        }

    }

    //Metodo para cargar una imagen en un elemento de ventana
    public static void cargarImagen(JLabel lbl, String nombreArchivo) {
        try {
            File f = new File(nombreArchivo);
            // Existe el archivo?
            if (f.exists()) {
                //Cargar la imagen
                ImageIcon imagen = new ImageIcon(nombreArchivo);
                //Llevarla al elemento de despliegue (JLabel)
                lbl.setIcon(imagen);
            } else {
                JOptionPane.showMessageDialog(new JFrame(), "El archivo " + nombreArchivo + " no existe", "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
        }
    }
}
