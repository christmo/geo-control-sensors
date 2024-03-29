/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.server;

import BaseDatos.BaseDatos;
import Utilitarios.Utilitarios;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import monitoreo.sensores.Monitoreo;
import monitoreo.sensores.Sensor;

/**
 *
 * @author kradac
 */
class MultiServerThread extends Thread {

    private Socket socket = null;
    private BaseDatos bd;

    public MultiServerThread(Socket socket, BaseDatos bd) {
        super("MultiServerThread");
        this.socket = socket;
        this.bd = bd;
        System.out.println("Conectado:["
                + socket.getInetAddress().getHostAddress()
                + "]["
                + socket.getPort()
                + "]");
    }

    @Override
    public void run() {
        PrintWriter out = null;
        BufferedReader in = null;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(
                    new InputStreamReader(
                    socket.getInputStream()));

            String inputLine;

            while ((inputLine = in.readLine()) != null) {
                //System.out.println("ENTRA:" + inputLine);
                procesarTrama(inputLine);
            }
            out.close();
            in.close();
        } catch (SocketException sex) {
            try {
                cerrarConexion();
            } catch (IOException ex) {
                //Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
                System.out.println("Cerrada la conexión por problemas en la red...");
            }
        } catch (IOException ex) {
            if (!ex.getMessage().equals("socket closed") && !ex.getMessage().equals("Connection reset")) {
                Logger.getLogger(MultiServerThread.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Cerrar conexion del socket
     * @throws IOException
     */
    public void cerrarConexion() throws IOException {
        System.out.println("Cerrar Conexion: [" + socket.getPort() + "]");
        socket.close();
    }

    /**
     * Procesar trama para leida del modulo de los sensores...
     * @param inputLine
     */
    private void procesarTrama(String inputLine) {
        //M01%T1*14.173%T2*27.370%T3*14.173%T4*26.392##
        String[] lecturas = inputLine.split("%");
        String modulo = lecturas[0];
        ArrayList<Sensor> listaParMaxMinModulo = bd.getListaParametrosMaxMinModulo(modulo);
        String[] parametros;
        String fecha;
        String hora;
        for (int i = 1; i < lecturas.length; i++) {
            try {
                parametros = lecturas[i].split("&");
                //Guardar la temperatura que sea > que -5 y < 100
                if (Double.parseDouble(parametros[1]) >= -5
                        && Double.parseDouble(parametros[1]) <= 100) {
                    fecha = Utilitarios.getFechaAAAAMMddGuion();
                    hora = Utilitarios.getHora();
                    bd.insertarDatosSensor(modulo + parametros[0],
                            Double.parseDouble(parametros[1]),
                            fecha,
                            hora);
                    Monitoreo m = new Monitoreo(modulo,
                            modulo + parametros[0],
                            parametros[1],
                            fecha,
                            hora,
                            listaParMaxMinModulo, bd);
                    m.start();
                }
            } catch (ArrayIndexOutOfBoundsException ex) {
                System.out.println("Trama no valida...");
            } catch (NumberFormatException ex) {
                System.out.println("Trama no valida...");
            }
        }
    }
}
