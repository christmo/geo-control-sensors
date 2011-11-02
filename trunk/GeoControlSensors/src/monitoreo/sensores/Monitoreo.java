/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo.sensores;

import BaseDatos.BaseDatos;
import Comunicacion.comm.EnviarSMS;
import Comunicacion.comm.SMS;
import Comunicacion.mail.AlertaMail;
import Utilitarios.Utilitarios;
import java.text.DecimalFormat;
import java.util.ArrayList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kradac
 */
public class Monitoreo extends Thread {

    private String modulo;
    private String id_sen;
    private double param_sen;
    //private String fecha;
    private String hora;
    private BaseDatos bd;
    private ArrayList<Sensor> listaParMaxMinModulo = new ArrayList<Sensor>();
    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(Monitoreo.class);

    /**
     * Comprobación de los parametros medidos del sensor para saber si hay que 
     * generar una alerta.
     * @param modulo
     * @param id_sen
     * @param param_sen
     * @param bd
     */
    public Monitoreo(String modulo,
            String id_sen,
            String param_sen,
            String fecha,
            String hora,
            ArrayList<Sensor> listaParMaxMinModulo,
            BaseDatos bd) {
        this.modulo = modulo;
        this.id_sen = id_sen;
        this.param_sen = Double.parseDouble(param_sen);
        //this.fecha = fecha;
        this.hora = hora;
        this.listaParMaxMinModulo = listaParMaxMinModulo;
        this.bd = bd;
    }

    @Override
    public void run() {
        boolean isLanzarAlerta = false;
        double parametro_min, parametro_max;
        for (Sensor s : listaParMaxMinModulo) {
            if (s.getId_sen().equals(id_sen)) {
                isLanzarAlerta = bd.esTiempoNuevaAlerta(id_sen);
                parametro_min = s.getParam_min();
                parametro_max = s.getParam_max();
                System.out.println(isLanzarAlerta + " " + parametro_min + " " + param_sen + " " + parametro_max + " " + id_sen + " " + Utilitarios.getHora());
                if ((param_sen < parametro_min || param_sen > parametro_max) && isLanzarAlerta) {
                    System.err.println("Enviar: " + id_sen + " " + param_sen + " " + isLanzarAlerta + " " + Utilitarios.getHora());
                    lanzarAlerta(s.getParam_min(), s.getParam_max(), s.getTipoSensor());
                }
                break;
            }
        }
    }

    /**
     * Envia las alertas a todos los que esten el la tabla de contactos
     * @param param_min
     * @param param_max
     * @param tipoSensor
     */
    private synchronized void lanzarAlerta(double param_min, double param_max, String tipoSensor) {
        ArrayList<Contactos> listaContactos = bd.getListaContactosReportar(id_sen);
        int id_dato = 0;
        DecimalFormat format = new DecimalFormat("##.##");

        String mod = bd.getNombreModulo(modulo);
        String sensor = bd.getNombreSensor(id_sen);

        for (Contactos contacto : listaContactos) {
            id_dato = bd.getIDDatoInsertado(id_sen, hora, param_sen);
            bd.insertarNotificacion(contacto.getId_con(), id_dato);

            AlertaMail alerta = new AlertaMail(
                    sensor,
                    mod,
                    tipoSensor,
                    format.format(param_sen),
                    param_min,
                    param_max,
                    contacto.getStrMail(),
                    contacto.getStrNombre(), bd);
            alerta.start();
            try {
                String sendSMS = bd.getValorConfiguracion("sms");
                if (sendSMS.equals("si") || sendSMS.equals("SI")
                        || sendSMS.equals("true")) {
                    String mensaje = "ALARMA DE TEMPERATURA\n" + mod
                            + "\n" + sensor + " : " + format.format(param_sen)
                            + "\nFECHA:" + Utilitarios.getFechaAAAAMMddSlash()
                            + "\nHORA:" + Utilitarios.getHora();

                    if (!contacto.getStrNumero().equals("") || contacto.getStrNumero() != null) {
                        EnviarSMS.listaMensajes.add(new SMS(mensaje, contacto.getStrNumero()));
                    }
                }
            } catch (NullPointerException ex) {
                log.trace("No está la directiva para enviar SMS en la base de datos...");
            }

        }
    }
}
