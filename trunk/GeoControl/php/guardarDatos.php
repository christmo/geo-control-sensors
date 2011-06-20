<?php
/**
 * Guardar los datos de las temperatudas que se envien desde el panel de config
 */
require_once('../dll/conect.php');
extract($_GET);

$sql = "UPDATE SENSORES
        SET PAR_MAX_SEN = $max, PAR_MIN_SEN = $min
        WHERE MODULO_SEN = '$mod'";

consulta($sql);

echo true;
?>
