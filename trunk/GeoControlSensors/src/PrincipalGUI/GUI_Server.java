/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

/*
 * GUI_Server.java
 *
 * Created on 30/05/2011, 10:13:46 AM
 */
package PrincipalGUI;

import BaseDatos.BaseDatos;
import Comunicacion.comm.CommMensajes;
import Comunicacion.comm.EnviarSMS;
import Comunicacion.server.MultiServer;
import Utilitarios.Utilitarios;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Properties;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kradac
 */
public class GUI_Server extends javax.swing.JFrame {

    private MultiServer servidor;
    public static Properties arcConfig;
    private BaseDatos bd;
    public static CommMensajes comm;
    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(BaseDatos.class);

    /** Creates new form GUI_Server */
    public GUI_Server() {
        super.setIconImage(new ImageIcon(getClass().getResource("/iconos/kradac_icono.png")).getImage());
        initComponents();
        addWindowListener(new WindowAdapter() {

            @Override
            public void windowClosing(WindowEvent e) {
                pararServidorConexiones(true);
            }
        });
        try {
            arcConfig = Utilitarios.obtenerArchivoPropiedades("configuracion.properties");
            bd = new BaseDatos(arcConfig);
        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null,
                    "No se encontró el archivo de configuración...",
                    "Error...", 0);
            System.exit(0);
        }
        enviarSMS();
    }

    /**
     * Ejecuta un hilo que revisa si hay mensajes en la cola para enviar, este
     * trabaja cada segundo revisando los mensajes.
     */
    public final void enviarSMS() {
        try {
            String sendSMS = bd.getValorConfiguracion("sms");
            if (sendSMS.equals("si") || sendSMS.equals("SI")
                    || sendSMS.equals("true")) {
                abrirPuertoSerial();
                EnviarSMS sms = new EnviarSMS();
                sms.start();
            }
        } catch (NullPointerException ex) {
            log.trace("No está la directiva para enviar SMS en la base de datos...");
        }
    }

    /**
     * Abrir Puerto Serial para el envio de SMS con el modem
     */
    public final void abrirPuertoSerial() {
        try {
            String strPuertoCom = bd.getValorConfiguracion("comm");
            if (!strPuertoCom.equals("0")) {
                comm = new CommMensajes(strPuertoCom);
                comm.start();

                comm.enviarDatos("AT\r\n");
            }
        } catch (NullPointerException ex) {
            JOptionPane.showMessageDialog(null, "No esta configurado el COMM en la BD", "Error...", 0);
            log.trace("No esta configurado el COMM en la BD.");
            System.exit(0);
        }
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btnIniciar = new javax.swing.JToggleButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setTitle("Geo Control Sensors");
        setResizable(false);

        btnIniciar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/iconos/velocidad.png"))); // NOI18N
        btnIniciar.setToolTipText("Iniciar servidor...");
        btnIniciar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnIniciarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btnIniciar, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        java.awt.Dimension screenSize = java.awt.Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((screenSize.width-164)/2, (screenSize.height-188)/2, 164, 188);
    }// </editor-fold>//GEN-END:initComponents

    private void btnIniciarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnIniciarActionPerformed
        if (btnIniciar.isSelected()) {

            Thread server = new Thread(new Runnable() {

                public void run() {
                    try {
                        int puerto = bd.getPuertoEscucharServidor();
                        servidor = new MultiServer(puerto, bd);
                        servidor.escucharConexiones();
                    } catch (NullPointerException ex) {
                        JOptionPane.showMessageDialog(null,
                                "No se puede obtener el puerto del servidor de la base de datos,\n"
                                + "Revisar la base de datos...", "Error...", 0);
                        System.exit(0);
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(null,
                                "No se puede obtener el puerto del servidor de la base de datos,\n"
                                + "Revisar la base de datos...", "Error...", 0);
                        System.exit(0);
                    } catch (IOException ex) {
                        if (!ex.getMessage().equals("socket closed")) {
                            System.out.println(ex.getMessage());
                            JOptionPane.showMessageDialog(null,
                                    "No se puede escuchar en ese puerto...",
                                    "Error...", 0);
                        }
                        btnIniciar.setSelected(false);
                    }
                }
            });
            server.start();

        } else {

            try {
                /**
                 * Solo para la ejecución del servidor mostrando el mensaje no cierra
                 * la ventana
                 */
                pararServidorConexiones(false);
            } catch (NullPointerException ex) {
                System.out.println("No hay conexiones que cerrar...");
            }
        }
    }//GEN-LAST:event_btnIniciarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        java.awt.EventQueue.invokeLater(new Runnable() {

            public void run() {
                new GUI_Server().setVisible(true);
            }
        });
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btnIniciar;
    // End of variables declaration//GEN-END:variables

    /**
     * Muestra el mensaje de advertencia de que si esta seguro de parar el servidor
     * si el parametro es true cierra la ventana de la aplicación caso contrario
     * solo cierra la conexion del servidor para no aceptar mas datos
     * @param salir
     */
    private void pararServidorConexiones(boolean salir) {
        if (salir && !btnIniciar.isSelected()) {
            try {
                bd.CerrarConexion();
            } catch (NullPointerException ex) {
            }
            System.exit(0);
        } else {
            int op = JOptionPane.showConfirmDialog(null,
                    "Esta seguro que quiere detener el monitor de sensores...",
                    "Alerta...",
                    2,
                    JOptionPane.YES_NO_OPTION);
            if (op == 0) {
                //si es true significa que pulso la x hay que salir
                if (salir) {
                    //System.out.println("Salir");
                    try {
                        bd.CerrarConexion();
                    } catch (NullPointerException ex) {
                    }
                    System.exit(0);
                }
                cerrarConexiones();
            } else {
                //salir es false significa que se dio clic en el boton
                //si es true es la x de la ventana
                if (salir) {
                    btnIniciar.setSelected(btnIniciar.isSelected());
                } else {
                    btnIniciar.setSelected(true);
                }
            }
        }
    }

    /**
     * Cierra todas las conexiones abiertas con el servidor
     */
    private void cerrarConexiones() {
        try {
            servidor.cerrarConexionServidorMultiple();
            log.trace("Parar servidor no se aceptan mas conexiones...");
        } catch (IOException ex) {
            log.trace("Error al cerrar la conexión...", ex);
        }
    }
}
