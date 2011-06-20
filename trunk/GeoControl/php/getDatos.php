<?php

require_once('../dll/conect.php');
extract($_GET);

$salida = "";

//$consultaSql = "SELECT UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000 as hora,PARAMETRO_DAT
$consultaSql = "SELECT (UNIX_TIMESTAMP(CONCAT(FECHA_DAT,' ',HORA_DAT))*1000)-(5*60*60*1000) as hora,PARAMETRO_DAT
FROM datos
where ID_SEN = '$id_sen'
order by hora desc limit 100";

consulta($consultaSql);
$resulset = variasFilas();

$salida = "{\"label\": \"$id_sen\",\"data\": [";
for ($i = 0; $i < count($resulset); $i++) {
    $fila = $resulset[$i];
    $salida .= "[" . $fila["hora"] . "," . $fila["PARAMETRO_DAT"] . "]";
    if ($i != count($resulset) - 1) {
        $salida .= ",";
    }
}

$salida .="]}";

echo $salida;
?>
