/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package monitoreo.sensores;

/**
 *
 * @author kradac
 */
public class Sensor {

    private String id_sen;
    private double param_min;
    private double param_max;
    private String tipoSensor;

    public Sensor(String id_sen, double param_min, double param_max, String tipoSnesor) {
        this.id_sen = id_sen;
        this.param_min = param_min;
        this.param_max = param_max;
        this.tipoSensor = tipoSnesor;
    }

    public Sensor(String id_sen, double param_min, double param_max) {
        this.id_sen = id_sen;
        this.param_min = param_min;
        this.param_max = param_max;
    }

    /**
     * @return the id_sen
     */
    public String getId_sen() {
        return id_sen;
    }

    /**
     * @param id_sen the id_sen to set
     */
    public void setId_sen(String id_sen) {
        this.id_sen = id_sen;
    }

    /**
     * @return the param_min
     */
    public double getParam_min() {
        return param_min;
    }

    /**
     * @param param_min the param_min to set
     */
    public void setParam_min(double param_min) {
        this.param_min = param_min;
    }

    /**
     * @return the param_max
     */
    public double getParam_max() {
        return param_max;
    }

    /**
     * @param param_max the param_max to set
     */
    public void setParam_max(double param_max) {
        this.param_max = param_max;
    }

    /**
     * @return the tipoSnesor
     */
    public String getTipoSensor() {
        return tipoSensor;
    }

    /**
     * @param tipoSnesor the tipoSnesor to set
     */
    public void setTipoSensor(String tipoSnesor) {
        this.tipoSensor = tipoSnesor;
    }
}
