<?php

/**
 * Se utiliza para sacar los ultimos puntos de un modulo, dentro de la hora en
 * la que se encuentra la maquina
 */
require_once('../dll/conect.php');
extract($_GET);

/**
 * Hora minima para graficar el punto de las lineas de los limites
 */
$hora_min = "SELECT ((UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000)-(5*60*60*1000)) AS HORA
            /*SELECT MIN((UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000)-(5*60*60*1000)) AS HORA*/
            FROM DATOS
            /*WHERE ID_SEN LIKE '$mod%'*/
            WHERE ID_SEN = '" . $mod . "T1'
            AND ID_DAT_PAR=DATE_FORMAT(CURDATE(),'%Y%m%d')
            /*AND HOUR(HORA_DAT) = HOUR(CURTIME())*/
            ORDER BY HORA DESC LIMIT 60";
$dato = consultarVariasFilas($hora_min);
if (count($dato) <= 59) {
    $hora_min = $dato[count($dato) - 1]["HORA"];
} else {
    $hora_min = $dato[59]["HORA"];
}

/**
 * Hora maxima para graficar el punto de las lineas de los limites
 */
$hora_max = "SELECT MAX((UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000)-(5*60*60*1000)) AS HORA
            FROM DATOS
            WHERE ID_SEN LIKE '$mod%'
            AND ID_DAT_PAR=DATE_FORMAT(CURDATE(),'%Y%m%d')";
consulta($hora_max);
$dato = unicaFila();
$hora_max = $dato["HORA"];

/**
 * Temperatura minima para graficar las lineas de los limites
 */
$temp_min = "SELECT AVG(PAR_MIN_SEN) AS MIN FROM sensores
            WHERE MODULO_SEN='$mod'";
consulta($temp_min);
$dato = unicaFila();
$temp_min = $dato["MIN"];

/**
 * Temperatura maxima para graficar las lineas de los limites
 */
$temp_max = "SELECT AVG(PAR_MAX_SEN) AS MAX FROM sensores
            WHERE MODULO_SEN='$mod'";
consulta($temp_max);
$dato = unicaFila();
$temp_max = $dato["MAX"];

$salida = "[";

$lim = "{\"label\": \"TMIN\",\"data\":[[$hora_max,$temp_min],[$hora_min,$temp_min],[$hora_min," . ($temp_min - 5) . "]]},";
$salida .= $lim;

/**
 * Permite obtener que sensores de ese modulo estan reportando ahora para hacer
 * la grafica solo de ellos.
 */
$sql = "SELECT D.ID_SEN, S.NOMBRE_SEN
        FROM DATOS D, SENSORES S
        WHERE ID_DAT_PAR=DATE_FORMAT(CURDATE(),'%Y%m%d')
        AND HOUR(HORA_DAT) = HOUR(CURTIME())
        AND D.ID_SEN = S.ID_SEN AND S.MODULO_SEN = '$mod'
        GROUP BY ID_SEN";

$sensores = consultarVariasFilas($sql);

if (count($sensores) != 0) {
    for ($i = 0; $i < count($sensores); $i++) {
        $sensor = $sensores[$i];
        $consultaSql = "SELECT (UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000)-(5*60*60*1000) AS HORA,PARAMETRO_DAT
            FROM DATOS
            WHERE ID_SEN = '" . $sensor["ID_SEN"] . "'
                AND ID_DAT_PAR=DATE_FORMAT(CURDATE(),'%Y%m%d')
                /*AND HOUR(HORA_DAT) = HOUR(CURTIME())*/
            ORDER BY HORA DESC LIMIT 60"; //LIMIT 60

        $resulset = consultarVariasFilas($consultaSql);
        if (count($resulset) > 0) {
            $salida .= "{\"label\": \"" . $sensor["NOMBRE_SEN"] . "\",\"data\": [";
            for ($j = 0; $j < count($resulset); $j++) {
                $fila = $resulset[$j];
                $salida .= "[" . $fila["HORA"] . "," . $fila["PARAMETRO_DAT"] . "]";
                if ($j != count($resulset) - 1) {
                    $salida .= ",";
                }
            }

            $salida .="]}";

            if ($i == count($sensores) - 1) {
                $lim = ",{\"label\": \"TMAX\",\"data\":[[$hora_max,$temp_max],[$hora_min,$temp_max],[$hora_min," . ($temp_max + 5) . "]]}";
                $salida .= $lim;
                $salida .="]";
            } else {
                $salida .=",";
            }
        }
    }
} else {
    $salida = "{\"data\":\"no hay datos...\"}";
}
echo $salida;
?>
