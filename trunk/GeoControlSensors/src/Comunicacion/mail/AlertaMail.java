/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.mail;

import Utilitarios.Utilitarios;

/**
 *
 * @author kradac
 */
public class AlertaMail extends Thread {

    private String mailPara;
    private String titulo;
    private StringBuilder mensaje;

    /**
     * Envia un email con la informacion del sensor al contacto que se especifica
     * @param sensor
     * @param modulo
     * @param tipoSen
     * @param paramMedido
     * @param parMin
     * @param parMax
     * @param mailPara
     */
    public AlertaMail(String sensor,
            String modulo,
            String tipoSen,
            double paramMedido,
            double parMin,
            double parMax,
            String mailPara,
            String nombre) {
        this.mailPara = mailPara;
        this.titulo = "Alerta!!! Sensor [" + sensor + "]";

        mensaje = new StringBuilder();
        mensaje.append(nombre).append(",<br/><br/>");
        mensaje.append("Le informamos que hay un sensor fuera de los limites permitidos:<br/>");
        mensaje.append("<br/>Sensor: ").append(sensor);
        mensaje.append("<br/>Modulo: ").append(modulo);

        mensaje.append("<br/><br/>Mensaje:<br/><br/>");
        mensaje.append("Este sensor de ").
                append(tipoSen).
                append(" ha registrado la siguiente lectura: <h1>").
                append(+paramMedido).
                append("</h1> ").
                append("Los limites para este sensor son: MIN[ <b>").
                append(parMin).
                append("</b> ] y MAX[ <b>").
                append(parMax).
                append("</b> ]");
        mensaje.append("<br/><br/>Fecha: ").append(Utilitarios.getFechaAAAAMMddSlash());
        mensaje.append("<br/>Hora: ").append(Utilitarios.getHora());
        mensaje.append("<br/><br/>-------------<br/>KRADAC<br/>");
    }

    @Override
    public void run() {
        boolean envio = SMTPConfig.sendMail(titulo, mensaje.toString(), mailPara);
    }
}
