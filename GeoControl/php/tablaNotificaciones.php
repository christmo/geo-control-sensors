<?php

require_once('../dll/conect.php');
extract($_GET);

$sql = "SELECT N.FECHA_NOT AS FECHA, N.HORA_NOT AS HORA, C.NOMBRE_CON AS CONTACT,
        D.PARAMETRO_DAT AS PARAM, S.NOMBRE_SEN AS SEN, S.MODULO_SEN AS MODULO
        FROM NOTIFICACIONES N, DATOS D, CONTACTOS C, SENSORES S
        WHERE N.ID_DAT_INC = D.ID_DAT_INC
        AND N.ID_CON = C.ID_CON
        AND D.ID_SEN = S.ID_SEN
        AND FECHA_NOT = '$fecha'
        AND HORA_NOT BETWEEN '$hora_ini' AND '$hora_fin'
        AND MODULO_SEN='$modulo'";

$result = consultarVariasFilas($sql);

echo "<div class=\"ui-widget\">";

echo "<table id=\"tablaNotif\" RULES=ROWS FRAME=HSIDES>";
echo "<tr class=\"trTitulo\">
    <td>Fecha</td>
    <td>Hora</td>
    <td>Contacto</td>
    <td>Temperatura</td>
    <td>Sensor</td>
    <td>M&oacute;dulo</td>
    </tr>";
for ($i = 0; $i < count($result); $i++) {
    $fila = $result[$i];
    echo "<tr>";
    echo "<td class=\"tdFecha\">";
    echo $fila["FECHA"];
    echo "</td>";
    echo "<td class=\"tdHora\">";
    echo $fila["HORA"];
    echo "</td>";
    echo "<td class=\"tdNombre\">";
    echo utf8_encode($fila["CONTACT"]);
    echo "</td>";
    echo "<td class=\"tdPar\">";
    echo $fila["PARAM"];
    echo "</td>";
    echo "<td class=\"tdSen\">";
    echo $fila["SEN"];
    echo "</td>";
    echo "<td class=\"tdMod\">";
    echo $fila["MODULO"];
    echo "</td>";
    echo "</tr>";
}
echo "</table>";
echo "</div>";
?>