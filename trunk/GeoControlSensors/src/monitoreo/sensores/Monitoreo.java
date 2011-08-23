/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo.sensores;

import BaseDatos.BaseDatos;
import Comunicacion.mail.AlertaMail;
import Utilitarios.Utilitarios;
import java.util.ArrayList;

/**
 *
 * @author kradac
 */
public class Monitoreo extends Thread {

    private String modulo;
    private String id_sen;
    private double param_sen;
    private String fecha;
    private String hora;
    private BaseDatos bd;
    ArrayList<Sensor> listaParMaxMinModulo = new ArrayList<Sensor>();

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
        this.fecha = fecha;
        this.hora = hora;
        this.listaParMaxMinModulo = listaParMaxMinModulo;
        this.bd = bd;
    }

    @Override
    public void run() {
        boolean r = false;
        double a, b;
        for (Sensor s : listaParMaxMinModulo) {
            if (s.getId_sen().equals(id_sen)) {
                r = bd.esTiempoNuevaAlerta(id_sen);
                a = s.getParam_min();
                b = s.getParam_max();
                System.out.println(r + " " + a + " " + param_sen + " " + b + " " + id_sen + " " + Utilitarios.getHora());
                if ((param_sen < a || param_sen > b) && r) {
                    System.err.println("Enviar: " + id_sen + " " + param_sen + " " + r + " " + Utilitarios.getHora());
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
        for (Contactos contacto : listaContactos) {
            id_dato = bd.getIDDatoInsertado(id_sen, hora, param_sen);
            bd.insertarNotificacion(contacto.getId_con(), id_dato);
            AlertaMail alerta = new AlertaMail(
                    bd.getNombreSensor(id_sen),
                    bd.getNombreModulo(modulo),
                    tipoSensor,
                    param_sen,
                    param_min,
                    param_max,
                    contacto.getStrMail(),
                    contacto.getStrNombre());
            alerta.start();
        }
    }
}
