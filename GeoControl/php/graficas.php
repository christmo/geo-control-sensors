<?php

require_once('../dll/conect.php');
$sql = "SELECT MODULO_SEN
                FROM SENSORES S, DATOS D
                WHERE S.ID_SEN = D.ID_SEN
                AND ID_DAT_PAR=DATE_FORMAT(CURDATE(),'%Y%m%d')
                AND HOUR(HORA_DAT) = HOUR(CURTIME())
                GROUP BY MODULO_SEN
                HAVING COUNT(*)>0";

$result = consultarVariasFilas($sql);

if ($result > 0) {
    for ($i = 0; $i < count($result); $i++) {
        $fila = $result[$i];

        echo "<div id=\"modulo\">";
        echo "<b>Modulo: </b>" . $fila["MODULO_SEN"] . "<br/>";
        echo "<div id=\"placeholder$i\" class=\"grafico\"></div>";
        echo "</div>";

        echo "<script type=\"text/javascript\">
                    $(function () {
                        var placeholder = $(\"#placeholder$i\");

                        function onDataReceived(series) {
                            $.plot(placeholder, series, options);
                        }

                        var previousPoint = null;
                        $(\"#placeholder$i\").bind(\"plothover\", function (event, pos, item) {
                            if (item) {
                                if (previousPoint != item.dataIndex) {
                                    previousPoint = item.dataIndex;
                                    $(\"#tooltip\").remove();
                                    var horaTimeStamp = item.datapoint[0].toFixed(2),
                                    temp = item.datapoint[1].toFixed(2);
                                    convertirHora(
                                        item.pageX,
                                        item.pageY,
                                        item.series.label,
                                        horaTimeStamp,
                                        temp
                                    );
                                }
                            }
                            else {
                                $(\"#tooltip\").remove();
                                previousPoint = null;
                            }
                        });

                        function update() {
                            $.ajax({
                                url: 'php/getDatosMul.php',
                                method: 'GET',
                                data: 'mod='+'" . $fila["MODULO_SEN"] . "',
                                dataType: 'json',
                                success: onDataReceived
                            });

                            setTimeout(update, updateInterval);
                        }

                        update();
                    });
                </script>";
    }
} else {
    echo "<div id=\"modulo\">";
    echo "<b>No se estan recuperando datos de los sensores ahora,
                revisar el sistema de monitoreo y actualizar esta p&aacute;gina con F5... :-)</b><br/>";
    echo "</div>";
}
?>