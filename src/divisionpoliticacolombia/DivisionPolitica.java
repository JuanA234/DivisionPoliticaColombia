package divisionpoliticacolombia;

import java.io.*;
import javax.swing.*;
import javax.swing.tree.*;

public class DivisionPolitica {

    public static void llenarArbol(JTree arbol) {
        //***Obtener el nombre del archivo
        //Obtener la ruta del proyecto
        String ruta = System.getProperty("user.dir");

        //Obtener el nombre completo del archivo
        String nombreArchivo = ruta + "/src/Datos/Colombia.txt";

        //Cargar datos desde un archivo plano
        BufferedReader br = Archivo.abrirArchivo(nombreArchivo);

        //Instanciar el nodo raíz del arbol
        DefaultMutableTreeNode nr = new DefaultMutableTreeNode("Colombia");
        //Instanciar el modelo de datos para el arbol
        DefaultTreeModel dtm = new DefaultTreeModel(nr);

        //Asignar el modelo al arbol
        arbol.setModel(dtm);

        if (br != null) {
            try {
                //Leer la primera línea
                String linea = br.readLine();
                //Variable que indica cambio de departamento
                String anteriorDptmento = "";
                DefaultMutableTreeNode nd = new DefaultMutableTreeNode();
                //Recorrer el archivo
                while (linea != null) {
                    //Partir la línea en sus componentes
                    String[] datos = linea.split(",");

                    if (datos.length >= 2) {
                        if (!anteriorDptmento.equals(datos[0])) {
                            anteriorDptmento = datos[0];
                            //Instanciar nodo de departamento
                            nd = new DefaultMutableTreeNode(datos[0]);

                            //Agregar el nodo de departamento al nodo raíz
                            nr.add(nd);

                        }
                        //Instanciar nodo del municipio
                        DefaultMutableTreeNode nm = new DefaultMutableTreeNode(datos[1].trim());
                        //Agregar el nodo de municipio al nodo dels departamento
                        nd.add(nm);

                    }
                    //Leer la siguiente línea
                    linea = br.readLine();
                }

            } catch (IOException ex) {

            }
        }

    }

    public static void mostrarMapa(JTree arbol, JLabel lbl) {

        //Obtener nodo seleccionado
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();

        //Hay algún nodo seleccionado?
        if (nodo != null) {
            //Es el nodo de un departamento
            if (nodo.getLevel() <= 1) {

                String nombreDptmento = nodo.toString();

                //***Abrir el archivo con los nombres de los mapas
                //Obtener la ruta del proyecto
                String ruta = System.getProperty("user.dir");

                //Obtener el nombre completo del archivo
                String nombreArchivo = ruta + "/src/Datos/mapas.txt";

                //Cargar datos desde un archivo plano
                BufferedReader br = Archivo.abrirArchivo(nombreArchivo);

                if (br != null) {
                    try {
                        String linea = br.readLine();
                        while (linea != null) {
                            String[] datos = linea.split(",");
                            if (datos.length >= 2) {
                                if (datos[0].equalsIgnoreCase(nombreDptmento)) {
                                    String nombreArchivoMapa = ruta + "/src/Mapas/" + datos[1];
                                    Archivo.cargarImagen(lbl, nombreArchivoMapa);
                                    break;
                                }
                            }
                            linea = br.readLine();
                        }
                    } catch (IOException ex) {

                    }
                }

            }

        }
    }

    public static void mostrarFoto(JTree arbol, JLabel lbl) {

        //Obtener nodo seleccionado
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();

        //Hay algún nodo seleccionado?
        if (nodo != null) {
            //Es el nodo de un departamento
            if (nodo.getLevel() == 2) {

                String nombreMpio = nodo.toString();
                //**** Crear la ruta de la imagen del muncipio
                //Obtener la ruta del proyecto
                String ruta = System.getProperty("user.dir");

                //Obtener el nombre completo del archivo
                String nombreArchivo = ruta + "/src/Fotos/" + quitarTildes(nombreMpio.toLowerCase()) + ".jpg";
                Archivo.cargarImagen(lbl, nombreArchivo);

            }
        }
    }

    public static void mostrarDatos(JTree arbol, JTextArea txt) {
        String contenido = "";

        //Obtener nodo seleccionado
        DefaultMutableTreeNode nodo = (DefaultMutableTreeNode) arbol.getLastSelectedPathComponent();

        //Hay algún nodo seleccionado?
        if (nodo != null) {
            //Es el nodo de un departamento
            if (nodo.getLevel() == 2) {
                String nombreMpio = nodo.toString();
                String nombreDptmento = nodo.getParent().toString();
                //**** Crear la ruta del archivo de datos del departamento
                //Obtener la ruta del proyecto
                String ruta = System.getProperty("user.dir");

                //Obtener el nombre completo del archivo
                String nombreArchivo = ruta + "/src/Datos/" + nombreDptmento + ".txt";

                //Cargar datos desde un archivo plano
                BufferedReader br = Archivo.abrirArchivo(nombreArchivo);

                if (br != null) {
                    try {
                        //Leer la prímera línea
                        String linea = br.readLine();
                        String[] encabezados = linea.split(";");

                        //******* Buscar el municipio en las datos del archivo
                        linea = br.readLine();
                        while (linea != null) {

                            String[] datos = linea.split(";");
                            if (datos.length >= encabezados.length) {
                                if (quitarTildes(datos[0].toLowerCase()).contains(quitarTildes((nombreMpio.toLowerCase())))
                                        || quitarTildes(nombreMpio.toLowerCase()).contains(quitarTildes((datos[0].toLowerCase())))) {
                                    for (int i = 0; i < encabezados.length; i++) {
                                        contenido += encabezados[i] + ": " + datos[i] + "\n";
                                    }
                                    break;
                                }
                            }

                            linea = br.readLine();

                        }

                    } catch (IOException ex) {

                    }
                }

            }
        }
        txt.setText(contenido);
    }

    public static String quitarTildes(String texto) {
        return texto.replace('á', 'a').
                replace('é', 'e').
                replace('í', 'i').
                replace('ó', 'o').
                replace('ú', 'u').
                replace('ü', 'u').
                replace("ñ", "n");
    }
}
