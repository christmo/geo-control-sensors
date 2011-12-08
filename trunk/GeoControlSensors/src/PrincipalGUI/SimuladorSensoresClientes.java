/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package PrincipalGUI;

/**
 *
 * @author kradac
 */
import java.io.*;
import java.net.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SimuladorSensoresClientes {

    public static void main(String[] args) {
        try {
            int c;
            Socket s = null;
            InputStream sIn;
            OutputStream ou;
            // Abrimos una conexión con breogan en el puerto 4321
            try {
                s = new Socket("localhost", 444);
            } catch (IOException e) {
                System.out.println(e);
            } // leemos esa entrada
            //sIn = s.getInputStream();
            ou = s.getOutputStream();

            String demo = "";

//            while ((c = sIn.read()) != -1) {
//                System.out.print((char) c);
//            }
            while (true) {
                //demo = "M01%T1&" + (int)(Math.random() * 30) + "%T2&" + (int)(Math.random() * 30) + "%T3&" + (int)(Math.random() * 30) + "%T4&" + (int)(Math.random() * 30)+"\n";
                //demo = "M01%T1&" + (int) (Math.random() * 30) + "%T2&" + (int) (Math.random() * 30) + "\n";
                demo = "M01%T2&" + (Math.random() * 30)+ "\n";
                System.out.print("" + demo);
                ou.write(demo.getBytes());
                try {
                    Thread.sleep(1000 * 1);
                } catch (InterruptedException ex) {
                    Logger.getLogger(SimuladorSensoresClientes.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            // Cuando se alcance el fin de fichero, cerramos la conexión y
            // abandonamos
            //s.close();
        } catch (IOException ex) {
            Logger.getLogger(SimuladorSensoresClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
