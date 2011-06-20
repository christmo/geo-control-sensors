<?php
require_once('../dll/conect.php');
extract($_GET);

$salida = "";

$consultaSql = "SELECT PARAMETRO_DAT
    FROM DATOS
    WHERE CONCAT(FECHA_DAT,' ',HORA_DAT) =
    (SELECT MAX(CONCAT(FECHA_DAT,' ',HORA_DAT))
    FROM DATOS
    WHERE ID_SEN = '$id_sen')
    AND ID_SEN = '$id_sen'";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"label\": \"$id_sen\",\"data\": [";
for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "".$fila["PARAMETRO_DAT"]."";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;


?>
