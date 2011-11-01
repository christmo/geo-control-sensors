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

        <script type="text/javascript" src="js/jquery_validate.js"></script>

        <script type="text/javascript">
            function addTableRow(){
                var nombre = $('#txtNombre').val();
                nombre = nombre.replace(/\s/g,"%20");
                var mail = $('#txtMail').val();
                var tel = $('#txtTelefono').val();

                $("#tabla").load("php/tablaContactos.php?nombre="+nombre+"&mail="+mail+"&tel="+tel);

                $('#txtNombre').val('');
                $('#txtMail').val('');
                $('#txtTelefono').val('');
            }

            $(document).ready(function(){
                $("#tabla").load("php/tablaContactos.php");
            });
            
            
            $(function(){
                $('#formInscripcion').validate({
                    rules: {
                        'txtNombre': 'required',
                        'txtTelefono': { required: true, number: true },
                        'txtMail': { required: true, email: true }
                    },
                    messages: {
                        'txtNombre': '<br/>Debe ingresar el nombre',
                        'txtTelefono': { required: '<br/>Debe ingresar el número de teléfono', number: '<br/>Debe ingresar un número' },
                        'txtMail': { required: '<br/>Debe ingresar un correo electrónico', email: '<br/>Debe ingresar el correo electrónico con el formato correcto. <br/>Por ejemplo: soporte@kradac.com' }
                    },
                    debug: true,
                    submitHandler: function(form){
                        addTableRow();
                    }
                });
            });
        </script>

    </head>
    <body>

        <div id="encabezado">
            <img src="img/logo.jpg" alt="logo"/>
        </div>

        <?php include('php/menu.php'); ?>

        <div id="panel">
            <?php
            $sql = "SELECT MODULO_SEN, PAR_MAX_SEN, PAR_MIN_SEN FROM SENSORES
                    GROUP BY MODULO_SEN";

            $result = consultarVariasFilas($sql);

            echo "<div id=\"modulo\">";
            echo "<div class=\"ui-widget\">";
            echo "<div id='mod'>
                    <h1>Rangos de Notificaci&oacute;n</h1><br/>";
            for ($i = 0; $i < count($result); $i++) {
                $fila = $result[$i];
                echo "<div id='txtLabel'>M&oacute;dulo: " . $fila["MODULO_SEN"] . "</div><br/>
                    <div id='izq'>L&iacute;mite Superior:</div><div id='der'><input id=\"" . $fila["MODULO_SEN"] . "MAX\" type='text' class='param' value='" . $fila["PAR_MAX_SEN"] . "'/></div>
                    <div id='izq'>L&iacute;mite Inferior:</div><div id='der'><input id=\"" . $fila["MODULO_SEN"] . "MIN\" type='text' class='param' value='" . $fila["PAR_MIN_SEN"] . "'/></div>
                    <div id='bot'><button id='" . $fila["MODULO_SEN"] . "' onclick=\"guardar('" . $fila["MODULO_SEN"] . "')\">Guardar</button></div>";
            }
            echo "</div><br/>";
            echo "</div>";
            echo "</div>";
            ?>

            <form id="formInscripcion" method="post" action="">
                <div class="ui-widget">
                    <div id="mod">
                        <h1>Ingreso de Contactos</h1><br/>
                        <div id='txtIngreso'><div id="ingLabel">Nombre: </div><input type="text" id="txtNombre" name="txtNombre" /></div>
                        <div id='txtIngreso'><div id="ingLabel">Correo: </div><input type="text" id="txtMail" name="txtMail"/></div>
                        <div id='txtIngreso'><div id="ingLabel">Tel&eacute;fono: </div><input type="text" id="txtTelefono" name="txtTelefono"/></div><br/>
                        <button  type="submit">Guardar</button><br/>
                    </div>
                </div>
            </form>
            <br/>

            <div id="modulo">
                <div id="tabla"></div>
            </div>
        </div>

    </body>
</html>
