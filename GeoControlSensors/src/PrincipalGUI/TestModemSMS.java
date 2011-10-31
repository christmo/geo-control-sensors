/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalGUI;

import Comunicacion.comm.CommMensajes;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author KRADAC
 */
public class TestModemSMS {
    public static void main(String[] args) {
        char fin = 13;
        String mensaje = "hola mundo!!!";
        CommMensajes mens = new CommMensajes("COM2");
        mens.start();
        //mens.enviarDatos("AT+CMGS=\"095978067\";\r\n");
        
        mens.enviarDatos("AT"+fin+fin+fin+fin+fin+fin+fin+fin+fin);
//        try {
//            Thread.sleep(1000);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(TestModemSMS.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        mens.enviarDatos(mensaje + fin);
    }
}
