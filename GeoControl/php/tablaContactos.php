<?php

require_once('../dll/conect.php');
extract($_GET);

if (isset($id)) {
    $sql = "DELETE FROM CONTACTOS WHERE ID_CON=" . $id;
    consulta($sql);
} else {
    if (isset($nombre) && isset($mail) && isset($tel)) {
        $sql = "INSERT INTO CONTACTOS(NOMBRE_CON, MAIL_CON, TELEFONO)
                VALUES('$nombre','$mail','$tel')";
        consulta($sql);
    }
}

$sql = "SELECT ID_CON,NOMBRE_CON, MAIL_CON, TELEFONO
        FROM CONTACTOS";

$result = consultarVariasFilas($sql);

//echo "<div id=\"mod\">";
//echo "<div class=\"ui-widget\">";
echo "<table id=\"tablaContactos\" RULES=ROWS FRAME=HSIDES>";
echo "<tr class=\"trTitulo\">
    <td>Nombre</td>
    <td>Correo</td>
    <td>Tel&eacute;fono</td>
    <td>Eliminar</td>
    </tr>";
for ($i = 0; $i < count($result); $i++) {
    $fila = $result[$i];
    echo "<tr>";
    echo "<td class=\"tdNombre\">";
    echo utf8_encode($fila["NOMBRE_CON"]);
    echo "</td>";
    echo "<td class=\"tdCorreo\">";
    echo $fila["MAIL_CON"];
    echo "</td>";
    echo "<td class=\"tdTelefono\">";
    echo $fila["TELEFONO"];
    echo "</td>";
    echo "<td>";
    echo "<img class='delete' id='" . $fila["ID_CON"] . "' alt='eliminar' src='img/eliminar.png'>";
    echo "</td>";
    echo "</tr>";
}
echo "</table>";
//echo "</div>";
//echo "</div>";

echo "<script type=\"text/javascript\">

            $(document).ready(function(){
                $('#tablaContactos td img.delete').click(function(){
                    var id = $(this).attr('id');
                    $('#tabla').load(\"php/tablaContactos.php?id=\"+id);
                });
            });
        </script>";
?>