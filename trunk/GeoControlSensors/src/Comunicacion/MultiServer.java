/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.ArrayList;

/**
 *
 * @author Usuario
 */
public class MultiServer {

    private ServerSocket serverSocket = null;
    private boolean listening = true;
    ArrayList<MultiServerThread> listaConexiones = new ArrayList<MultiServerThread>();

    public MultiServer(int puerto) throws IOException {

        serverSocket = new ServerSocket(puerto);

        System.out.println("Servidor Iniciado [" + puerto + "]");

    }

    public void escucharConexiones() throws IOException {
        while (listening) {
            MultiServerThread con = new MultiServerThread(serverSocket.accept());
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

    public static void main(String[] args) throws IOException {
//        ServerSocket serverSocket = null;
//        boolean listening = true;
//
//        try {
//            serverSocket = new ServerSocket(1024);
//        } catch (IOException e) {
//            System.err.println("No se puede escuchar el puerto 1024");
//            System.exit(-1);
//        }
//
//        while (listening) {
//            new MultiServerThread(serverSocket.accept()).start();
//        }
//
//        serverSocket.close();
    }
}
