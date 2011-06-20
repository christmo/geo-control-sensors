<?php
session_start();
//require_once('dll/conect.php');
require_once('php/login/isLogin.php');
?>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=utf-8">
        <title>Monitoreo de Sensores - GeoControl</title>
        <link href="css/layout.css" rel="stylesheet" type="text/css"/>
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

        <script type="text/javascript">
            $(function() {
                $.get("php/graficas.php",
                {
                    nbRandom:Math.random()
                },
                function(data){
                    $("#panel").html(data);
                }
            );
            });
        </script>

    </head>
    <body>

        <div id="encabezado">
            <img src="img/logo.jpg" alt="logo"/>
        </div>

        <?php include('php/menu.php'); ?>

        <div id="panel"></div>

    </body>
</html>