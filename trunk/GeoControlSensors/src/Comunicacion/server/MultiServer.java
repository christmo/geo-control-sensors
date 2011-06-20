/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.server;

import BaseDatos.BaseDatos;
import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author kradac
 */
public class MultiServer {

    private ServerSocket serverSocket = null;
    private boolean listening = true;
    private ArrayList<MultiServerThread> listaConexiones = new ArrayList<MultiServerThread>();
    private BaseDatos bd;

    public MultiServer(int puerto, BaseDatos bd) throws IOException {
        this.bd = bd;
        serverSocket = new ServerSocket(puerto);
        System.out.println("Servidor Iniciado [" + puerto + "]");

    }

    public void escucharConexiones() throws IOException {
        while (listening) {
            MultiServerThread con = new MultiServerThread(serverSocket.accept(), bd);
            con.start();
            listaConexiones.add(con);
        }
    }

    public void cerrarConexionServidorMultiple() throws IOException {
        listening = false;
        for (MultiServerThread multiServerThread : listaConexiones) {
            multiServerThread.cerrarConexion();
        }
        serverSocket.close();
    }
}
