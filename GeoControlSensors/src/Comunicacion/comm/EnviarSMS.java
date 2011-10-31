/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.comm;

import PrincipalGUI.GUI_Server;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
/**
 *
 * @author Usuario
 */
public class EnviarSMS extends Thread {

    public static ArrayList<SMS> listaMensajes = new ArrayList<SMS>();
    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(EnviarSMS.class);
    public EnviarSMS() {
    }

    @Override
    public void run() {
        while (true) {
            if (listaMensajes.size() > 0) {
                for (SMS sms : listaMensajes) {
//                    GUI_Server.comm.enviarDatos(sms.getCmd());
                    log.trace(sms.getCmd());
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        log.trace("Hilo interrumpido...");
                    }
                    //GUI_Server.comm.enviarDatos(sms.getMensaje());
                    log.trace(sms.getMensaje());
                    try {
                        Thread.sleep(2000);
                    } catch (InterruptedException ex) {
                       log.trace("Hilo interrumpido...");
                    }
                    //GUI_Server.comm.enviarDatos("AT\r\n");
                    log.trace("AT\r\n");
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException ex) {
                        log.trace("Hilo interrumpido...");
                    }
                }
                listaMensajes.clear();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException ex) {
                log.trace("Hilo interrumpido...");
            }
        }

    }
}
