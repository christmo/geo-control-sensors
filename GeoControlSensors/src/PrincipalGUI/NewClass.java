/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package PrincipalGUI;

import BaseDatos.BaseDatos;
import Utilitarios.Utilitarios;
import java.io.FileNotFoundException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kradac
 */
public class NewClass {
    public static void main(String[] args) {
        try {
            Properties p = Utilitarios.obtenerArchivoPropiedades("configuracion.properties");
            BaseDatos bd = new BaseDatos(p);

            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(NewClass.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
