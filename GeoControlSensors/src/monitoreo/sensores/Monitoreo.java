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
            ArrayList<Sensor> listaParMaxMinModulo,
            BaseDatos bd) {
        this.modulo = modulo;
        this.id_sen = id_sen;
        this.param_sen = Double.parseDouble(param_sen);
        this.listaParMaxMinModulo = listaParMaxMinModulo;
        this.bd = bd;
    }

    @Override
    public void run() {
        for (Sensor s : listaParMaxMinModulo) {
            if (s.getId_sen().equals(id_sen)) {
                if (param_sen < s.getParam_min() || param_sen > s.getParam_max()
                        && bd.esTiempoNuevaAlerta(id_sen)) {
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
    private void lanzarAlerta(double param_min, double param_max, String tipoSensor) {
        ArrayList<Contactos> listaContactos = bd.getListaContactosReportar(id_sen);
        int id_dato = 0;
        for (Contactos contacto : listaContactos) {
            AlertaMail alerta = new AlertaMail(
                    id_sen,
                    modulo,
                    tipoSensor,
                    param_sen,
                    param_min,
                    param_max,
                    contacto.getStrMail(),
                    contacto.getStrNombre());
            alerta.start();
            id_dato = bd.getIDDatoInsertado(id_sen,Utilitarios.getHora(),param_sen);
            bd.insertarNotificacion(contacto.getId_con(),id_dato);
        }
    }
}
