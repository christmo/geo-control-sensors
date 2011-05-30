/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilitarios;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.security.CodeSource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.GregorianCalendar;
import java.util.Properties;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JToggleButton;

/**
 *
 * @author kradac
 */
public class Utilitarios {

    public static Properties arcConfig;

    /**
     * Permite deseleccionar los botones de las fuentes para que solo este
     * seleccionado uno de todo el conjunto de 6
     * @param btn
     * @param botones
     */
    public static void botonesFuentes(int btn, ArrayList<JToggleButton> botones) {
        for (int i = 0; i < botones.size(); i++) {
            if (btn == (i + 1)) {
                botones.get(i).setSelected(true);
            } else {
                botones.get(i).setSelected(false);
            }
        }
    }

    /**
     * Permite formatear la hora para que no deje pasar horas no validad
     * @param hora
     * @return String
     */
    public static String formatearHora(String hora) {
        String[] arrHora = hora.split(":");
        String nuevaHora = "";
        int dato;

        if (arrHora.length == 3) {
            for (int i = 0; i < arrHora.length; i++) {
                try {
                    dato = Integer.parseInt(arrHora[i]);
                    if (i == 0) {
                        if (dato >= 0 && dato <= 23) {
                            if (arrHora[i].length() == 2) {
                                nuevaHora += "" + arrHora[i];
                            } else {
                                nuevaHora += "0" + arrHora[i];
                            }
                        }
                    } else {
                        if (dato >= 0 && dato <= 59) {
                            if (arrHora[i].length() == 2) {
                                nuevaHora += "" + arrHora[i];
                            } else {
                                nuevaHora += "0" + arrHora[i];
                            }
                        }
                    }
                } catch (NumberFormatException ex) {
                    nuevaHora += "00";
                }
                nuevaHora += ":";
            }
            try {
                return nuevaHora.substring(0, 8);
            } catch (StringIndexOutOfBoundsException ex) {
                return "";
            }
        } else {
            if (arrHora.length == 1 && hora.length() == 6) {
                try {
                    if (Integer.parseInt(hora.substring(0, 2)) >= 0 && Integer.parseInt(hora.substring(0, 2)) <= 23) {
                        nuevaHora += hora.substring(0, 2) + ":";
                    } else {
                        nuevaHora += "00:";
                    }
                    if (Integer.parseInt(hora.substring(2, 4)) >= 0 && Integer.parseInt(hora.substring(2, 4)) <= 59) {
                        nuevaHora += hora.substring(2, 4) + ":";
                    } else {
                        nuevaHora += "00:";
                    }
                    if (Integer.parseInt(hora.substring(4, 6)) >= 0 && Integer.parseInt(hora.substring(4, 6)) <= 59) {
                        nuevaHora += hora.substring(4, 6);
                    } else {
                        nuevaHora += "00";
                    }
                    return nuevaHora;
                } catch (NumberFormatException ex) {
                    return "";
                }
            } else {
                return "";
            }
        }
    }

    /**
     * Reemplaza todos los enter que pueda tener el texto por un espacio
     * @param mensaje
     * @return String
     */
    public static String quitarEnterTexto(String mensaje) {
        return mensaje.replace("\n", " ");
    }

    /**
     * Trae el archivo properties que se encuentre en el direcctorio del jar
     * @param arc -> "configsystem.properties" nombre del archivo properties
     * @return Properties
     */
    public static File obtenerArchivo(String arc) {

        try {
            CodeSource codeSource = Utilitarios.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();

            if (jarDir != null && jarDir.isDirectory()) {
                File propFile = new File(jarDir, arc);
                return propFile;
//                arcConfig = new Properties();
//                arcConfig.load(new BufferedReader(new FileReader(propFile.getAbsoluteFile())));
            }
//        } catch (IOException ex) {
//            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static File obtenerArchivoDirectorio(String arc, String dir) {

        try {
            CodeSource codeSource = Utilitarios.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();

            File dirArchivos = new File(jarDir.getAbsolutePath() + File.separator + dir);

            if (dirArchivos != null && dirArchivos.isDirectory()) {
                File propFile = new File(dirArchivos, arc);
                return propFile;
//                arcConfig = new Properties();
//                arcConfig.load(new BufferedReader(new FileReader(propFile.getAbsoluteFile())));
            }
//        } catch (IOException ex) {
//            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Obtiene la fecha actual del equipo, este metodo se puede consumir desde
     * todo el proyecto.
     * @return String
     */
    public static String getFechaAAMMdd() {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("yy/MM/dd");
        System.out.println("f:" + sdf.format(c.getTime()));
        return sdf.format(c.getTime());
    }

    public static String getHora() {
        Calendar c = new GregorianCalendar();
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        System.out.println("H:" + sdf.format(c.getTime()));
        return sdf.format(c.getTime());
    }

    public void escribirProperties(String fichero, String key, String value) {
        // Crear el objeto archivo
        File archivo = obtenerArchivo(fichero);
        //File archivo = new File(this.getClass().getResource(fichero).getFile().replace("%20", " "));
        //Crear el objeto properties

        System.out.println(archivo);

        Properties properties = new Properties();
        try {
            // Cargar las propiedades del archivo
            properties.load(new FileInputStream(archivo));
            properties.setProperty(key, value);
            // Escribier en el archivo los cambios
            FileOutputStream fos = new FileOutputStream(archivo.toString().replace("\\", "/"));

            properties.store(fos, null);

        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Trae el archivo properties que se encuentre en el direcctorio del jar
     * @param arc -> "configsystem.properties" nombre del archivo properties
     * @return Properties
     */
    public static Properties obtenerArchivoPropiedades(String arc) throws FileNotFoundException {
        Properties prop = null;
        try {
            CodeSource codeSource = Utilitarios.class.getProtectionDomain().getCodeSource();
            File jarFile = new File(codeSource.getLocation().toURI().getPath());
            File jarDir = jarFile.getParentFile();

            if (jarDir != null && jarDir.isDirectory()) {
                File propFile = new File(jarDir, arc);
                prop = new Properties();
                prop.load(new BufferedReader(new FileReader(propFile.getAbsoluteFile())));
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            //System.err.println("No se encuentra el archivo: " + ex.getMessage());
            throw new FileNotFoundException("No se econtró el archivo de propiedades...");
        } catch (IOException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
        return prop;
    }

    public void crearArchivoPropiedades(String nombre) {
        CodeSource codeSource = Utilitarios.class.getProtectionDomain().getCodeSource();
        File jarFile;
        String url;
        FileWriter w;
        ResourceBundle rb;

        try {
            jarFile = new File(codeSource.getLocation().toURI().getPath());
            url = jarFile.getParentFile() + File.separator + nombre;
            File archivo = new File(url);

            BufferedWriter bw;
            try {
                w = new FileWriter(archivo);
                bw = new BufferedWriter(w);

                rb = ResourceBundle.getBundle("propiedades.configuracion");

                arcConfig = convertResourceBundleToProperties(rb);
                bw.write("#\n# Archivo generado cuando no encuentra la configuración inicial\n#\n");
                bw.write("mensaje = " + arcConfig.getProperty("mensaje") + "\n");
                bw.write("fuente = " + arcConfig.getProperty("fuente") + "\n");
                bw.write("tamano = " + arcConfig.getProperty("tamano") + "\n");
                bw.write("tipo = " + arcConfig.getProperty("tipo") + "\n");

                bw.close();

                System.out.println("Archivo escrito en: " + url);

            } catch (IOException ex) {
                Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (URISyntaxException ex) {
            Logger.getLogger(Utilitarios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Convert ResourceBundle into a Properties object.
     *
     * @param resource a resource bundle to convert.
     * @return Properties a properties version of the resource bundle.
     */
    private static Properties convertResourceBundleToProperties(ResourceBundle resource) {
        Properties properties = new Properties();

        Enumeration<String> keys = resource.getKeys();
        while (keys.hasMoreElements()) {
            String key = keys.nextElement();
            properties.put(key, resource.getString(key));
        }

        return properties;
    }

    /**
     * Pone espacios antes y despues de la frace para que no se vea todo unido
     * @param texto
     * @return String
     */
    public static String espaciosFrace(String texto) {
        int factorM = 80;
        String txt = "";
        for (int i = 0; i < factorM; i++) {
            txt += " ";
            if (i == (int) factorM / 2) {
                txt += texto;
            }
        }
        return txt;
    }
}
