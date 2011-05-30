/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package BaseDatos;

import Utilitarios.Utilitarios;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author kradac
 */
public class BaseDatos {

    private Connection conexion;
    /**
     * Nombre de la Base de datos
     */
    private String bd = "";
    private String ip = "";
    private String usr = "";
    private String pass = "";
    private String puerto = "";
    /**
     * Logger para guardar los log en un archivo y enviar por mail los de error
     */
    private static final Logger log = LoggerFactory.getLogger(BaseDatos.class);
    private Properties arcConfig;
    private Utilitarios funciones = new Utilitarios();

    public BaseDatos() {
        ConexionBase();
    }

    public BaseDatos(Properties arc) {
        ConexionBase(arc);
    }

    /**
     * Crea la conexion directamente a la base de datos de rastreosatelital
     * de kradac, parametros de la conexion quemados por defecto para la
     * maquina local
     */
    private void ConexionBase(Properties conf) {
        try {
            this.arcConfig = conf;
            String driver = "com.mysql.jdbc.Driver";
            this.ip = arcConfig.getProperty("ip_base");
            this.bd = arcConfig.getProperty("base");
            this.usr = arcConfig.getProperty("user");
            this.pass = arcConfig.getProperty("pass");
            this.puerto = arcConfig.getProperty("puerto_base");

            String url = "jdbc:mysql://" + ip + ":" + puerto + "/" + bd;

            try {
                try {
                    Class.forName(driver).newInstance();
                } catch (InstantiationException ex) {
                    log.trace("No se puede cargar el driver de la base de datos...", ex);
                } catch (IllegalAccessException ex) {
                    log.trace("No se puede cargar el driver de la base de datos acceso ilegal...", ex);
                }
            } catch (ClassNotFoundException ex) {
                log.trace("No se puede cargar el driver de la base de datos...", ex);
            }
            try {
                conexion = DriverManager.getConnection(url, usr, pass);
            } catch (SQLException ex) {
                if (ex.getMessage().equals("Communications link failure")) {
                    log.trace("Enlace de conexión con la base de datos falló, falta el archivo de configuración...");
                }
            }

            log.info("Iniciar conexion a la base de datos [" + this.bd + "]");
        } catch (NullPointerException ex) {
            throw new UnsupportedOperationException("null configuracion base");
        }
    }

    /**
     * Crea una conexion a cualquier base de datos mysql, con parametros
     * de conexion indepenientes
     * @param ip - IP del servidor
     * @param bd - Nombre de la Base de datos
     * @param usr -  Nombre de Usuario
     * @param pass - Clave de la Base de datos
     */
    private void ConexionBase() {
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://" + ip + "/" + bd;
        try {
            try {
                Class.forName(driver).newInstance();
            } catch (InstantiationException ex) {
                log.trace("No se puede cargar el driver de la base de datos...", ex);
            } catch (IllegalAccessException ex) {
                log.trace("No se puede cargar el driver de la base de datos acceso ilegal...", ex);
            }
        } catch (ClassNotFoundException ex) {
            log.trace("No se puede cargar el driver de la base de datos...", ex);
        }
        try {
            conexion = DriverManager.getConnection(url, usr, pass);
        } catch (SQLException ex) {
            if (ex.getMessage().equals("Communications link failure")) {
                log.trace("Enlace de conexión con la base de datos falló, falta el archivo de configuración...");
            }
        }

        log.info("Iniciar conexion a la base de datos [" + this.bd + "]");
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsulta(String sql) {
        ResultSet r = null;
        try {
            Statement stat = (Statement) conexion.createStatement();
            r = stat.executeQuery(sql);
            log.trace("Consultar:" + sql);
        } catch (SQLException ex) {
            int intCode = ex.getErrorCode();
            log.trace("Error al consultar codigo[" + intCode + "]", ex);
        } catch (NullPointerException ex) {
        }
        return r;
    }

    /**
     * Ejecuta una consulta en la base de datos, que devuelve valores
     * no es necesario recorrer el resultset
     * @param sql - debe ser Select
     * @return ResultSet
     */
    public ResultSet ejecutarConsultaUnDato(String sql) {
        ResultSet rsCUD = null;
        try {
            Statement sta = (Statement) conexion.createStatement();
            rsCUD = sta.executeQuery(sql);
            log.trace("Consultar:" + sql);
            rsCUD.next();
        } catch (SQLException ex) {
            int intCode = ex.getErrorCode();
            log.trace("Error al consultar codigo[" + intCode + "]", ex);
        }
        return rsCUD;
    }

    /**
     * Ejecuta una sentencia en la base esta puede ser de INSERT, UPDATE O
     * DELETE
     * @param sql - Sentencias INSERT, UPDATE, DELETE
     * @return int - confirmacion del resultado 1 valido || 0 invalido
     */
    public boolean ejecutarSentencia(String sql) {
        try {
            Statement st = (Statement) conexion.createStatement();

            log.trace("Ejecutar:" + sql);

            int rta = st.executeUpdate(sql);
            if (rta >= 0) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException ex) {
            int intCode = ex.getErrorCode();
            log.trace("Error al ejecutar sentecia codigo[" + intCode + "]", ex);
            return false;
        } catch (NullPointerException ex) {
            return false;
        }
    }

    /**
     * Cierra la conexion con la base de datos
     * @return Connection
     * @throws SQLException
     */
    public void CerrarConexion() {
        try {
            conexion.close();
        } catch (SQLException ex) {
            System.out.println("No se pudo completar el cierre de la BD...");
        } catch (NullPointerException ex) {
            System.out.println("NO está abierta la base de datos...");
        }
    }
}
