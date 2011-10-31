/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package Comunicacion.comm;

/**
 *
 * @author Usuario
 */
public class SMS {

    private String mensaje;
    private String cmd;
    private String numero;
    private char fin = 26;

    public SMS(String mensaje, String numero) {
        this.mensaje = mensaje + fin;
        this.numero = numero;
        try {
            Integer.parseInt(numero);
            this.cmd = "AT+CMGS=\"" + numero + "\";\r\n";
        } catch (NumberFormatException ex) {
            this.cmd = "AT\r\n";
        }
    }

    /**
     * @return the mensaje
     */
    public String getMensaje() {
        return mensaje;
    }

    /**
     * @param mensaje the mensaje to set
     */
    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    /**
     * @return the cmd
     */
    public String getCmd() {
        return cmd;
    }

    /**
     * @param cmd the cmd to set
     */
    public void setCmd(String cmd) {
        this.cmd = cmd;
    }

    /**
     * @return the numero
     */
    public String getNumero() {
        return numero;
    }

    /**
     * @param numero the numero to set
     */
    public void setNumero(String numero) {
        this.numero = numero;
    }
}
