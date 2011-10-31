/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo.sensores;

/**
 *
 * @author kradac
 */
public class Contactos {

    private int id_con;
    private String strMail;
    private String strNombre;
    private String strNumero;

    /**
     * Información basica de un contacto...
     * @param id_con
     * @param strMail
     * @param strNombre
     */
    public Contactos(int id_con, String strMail, String strNombre,String numero) {
        this.id_con = id_con;
        this.strMail = strMail;
        this.strNombre = strNombre;
        this.strNumero = numero;
    }

    /**
     * @return the id_con
     */
    public int getId_con() {
        return id_con;
    }

    /**
     * @param id_con the id_con to set
     */
    public void setId_con(int id_con) {
        this.id_con = id_con;
    }

    /**
     * @return the strMail
     */
    public String getStrMail() {
        return strMail;
    }

    /**
     * @param strMail the strMail to set
     */
    public void setStrMail(String strMail) {
        this.strMail = strMail;
    }

    /**
     * @return the strNombre
     */
    public String getStrNombre() {
        return strNombre;
    }

    /**
     * @param strNombre the strNombre to set
     */
    public void setStrNombre(String strNombre) {
        this.strNombre = strNombre;
    }

    /**
     * @return the strNumero
     */
    public String getStrNumero() {
        return strNumero;
    }

    /**
     * @param strNumero the strNumero to set
     */
    public void setStrNumero(String strNumero) {
        this.strNumero = strNumero;
    }
}
