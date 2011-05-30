/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Usuario
 */
class MultiServerThread extends Thread {

    private Socket socket = null;

    public MultiServerThread(Socket socket) {
        super("MultiServerThread");
        this.socket = socket;
        System.out.println("Conectado:["
                + socket.getInetAddress().getHostAddress()
                + "]["
                + socket.getPort()
                + "]");
    }

    @Override
    public void run() {

        try {
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));

            String inputLine;

            //String outputLine = "hola";
            //out.println(outputLine);

            while ((inputLine = in.readLine()) != null) {
                System.out.println("ENTRA:" + inputLine);
                //outputLine = inputLine;
                //out.println(outputLine);
                //if (outputLine.equals("Bye")) {
                //    break;
                //}
                procesarTrama(inputLine);
            }
            out.close();
            in.close();
        } catch (IOException ex) {
            //System.out.println(""+ex.getMessage());
            if (!ex.getMessage().equals("socket closed")) {
                Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void cerrarConexion() throws IOException {
        System.out.println("Cerrar Conexion: [" + socket.getPort() + "]");
        socket.close();
    }

    private void procesarTrama(String inputLine) {
        //M01%T1*14.173%T2*27.370%T3*14.173%T4*26.392##
        String[] lecturas = inputLine.split("%");
        String modulo = lecturas[0];
        System.out.println("Mod:" + modulo);
        String[] parametros;
        for (int i = 1; i < lecturas.length; i++) {
            try {
                parametros = lecturas[i].split("&");
                System.out.println(modulo + parametros[0] + " T:" + parametros[1]);
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Trama no valida...");
            }
        }
    }
}
