<?php
session_start();
require_once('dll/conect.php');
require_once('php/login/isLogin.php');
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Monitoreo de Sensores - GeoControl</title>
        <link href="css/layout.css" rel="stylesheet" type="text/css">
        <script language="javascript" type="text/javascript" src="js/jquery.js"></script>
        <script language="javascript" type="text/javascript" src="js/jquery.flot.js"></script>
        <script language="javascript" type="text/javascript" src="js/funciones.js"></script>

        <script type="text/javascript" src="js/menu.js"></script>

        <link type="text/css" href="css/ui-lightness/jquery-ui-1.8.13.custom.css" rel="stylesheet" />
        <script type="text/javascript" src="js/jquery-ui-1.8.13.custom.min.js"></script>
        <script type="text/javascript" src="js/jquery-ui-timepicker-addon.js"></script>
        <style type="text/css">
            #ui-datepicker-div{ font-size: 80%; }

            /* css for timepicker */
            .ui-timepicker-div .ui-widget-header{ margin-bottom: 8px; }
            .ui-timepicker-div dl{ text-align: left; }
            .ui-timepicker-div dl dt{ height: 25px; }
            .ui-timepicker-div dl dd{ margin: -25px 10px 10px 65px; }
            .ui-timepicker-div td { font-size: 90%; }

        </style>

    </head>
    <body>

        <div id="encabezado">
            <img src="img/logo.jpg" alt="logo"/>
        </div>

        <?php include('php/menu.php'); ?>

        <div id="panel"></div>

        <?php

        $sql = "SELECT MODULO_SEN
                FROM SENSORES
                GROUP BY MODULO_SEN";

        $result = consultarVariasFilas($sql);

        echo "<div id=\"modulo\">";

        echo "<div class=\"ui-widget\">";
        echo "Fecha a consultar: <input type=\"text\" id=\"datepicker\" class=\"fecha\"/> ";
        echo "Hora Inicio:  <input id=\"horaini\" type=\"text\" value=\"00:00\" class=\"tiempo\"> ";
        echo "Hora Fin:  <input id=\"horafin\" type=\"text\" value=\"00:00\" class=\"tiempo\"><br/>";
        echo "<label>Modulo a consultar: </label>";
        echo "<select id=\"combobox\">";
        for ($i = 0; $i < count($result); $i++) {
            $fila = $result[$i];
            echo "<option value=\"" . $fila["MODULO_SEN"] . "\">" . $fila["MODULO_SEN"] . "</option>";
        }
        echo "</select><br/>";
        echo "<button onclick=\"update()\">Buscar</button>";
        echo "</div>";

        echo "<div id=\"grafconsulta\" class=\"grafico\"></div>";
        echo "</div>";
        echo "<script type=\"text/javascript\">
              var previousPoint = null;
              $(\"#grafconsulta\").bind(\"plothover\", function (event, pos, item) {
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
                  }else {
                          $(\"#tooltip\").remove();
                          previousPoint = null;
                  }
              });

            var placeholder = $('#grafconsulta');

            function onDataReceived(series) {
                $.plot(placeholder, series, options);
            }

            function update() {
                var mod = $('#combobox').val();
                var fec = $('#datepicker').val();
                var ini = $('#horaini').val();
                var fin = $('#horafin').val();
                //alert(compararHoras(ini,fin));
                if(compararHoras(ini,fin)){
                    $.ajax({
                        url: 'php/getDatosConsulta.php',
                        method: 'GET',
                        data: 'mod='+mod+'&'+'fecha='+fec+'&'+'hora_ini='+ini+'&'+'hora_fin='+fin,
                        dataType: 'json',
                        success: onDataReceived
                    });
                }else{
                    alert('La hora de inicio debe ser menor a la de fin...');
                }
            }

        </script>";

        echo "<script type=\"text/javascript\">
            $(function() {
            $('#horafin').timepicker('setDate', (new Date()));
          });</script>";

        ?>

    </body>
</html>