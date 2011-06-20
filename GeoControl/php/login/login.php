<?php

include ('../../dll/conect.php');
extract($_POST);

//$salt = "KOMPRESORKR@D@C";
//$encriptClave = md5(md5(md5($txtClave) . md5($salt)));
$consultaSql = " SELECT USUARIO_USU,CLAVE_USU FROM USUARIOS WHERE USUARIO_USU = '" . $txtUsuario . "' AND CLAVE_USU = '" . $txtClave . "'";


consulta($consultaSql);
$registro = unicaFila();

if ($registro["CLAVE_USU"] == $txtClave && $registro["USUARIO_USU"] == $txtUsuario) {
    session_start();
    //$_SESSION["empresa"] = $registro["ID_EMPRESA"];
    $_SESSION["usuario"] = $registro["USUARIO_USU"];
    $_SESSION["sesion"] = true;


    // Deteccion de la ip y del proxy
//    if (isset($HTTP_SERVER_VARS["HTTP_X_FORWARDED_FOR"])) {
//        $ip = $HTTP_SERVER_VARS["HTTP_X_FORWARDED_FOR"];
//        $array = split(",", $ip);
//        $ip_proxy = $array[0];
//        $host = @gethostbyaddr($ip_proxy);
//        $ip_proxy = $HTTP_SERVER_VARS["REMOTE_ADDR"];
//    } else {
//        $ip = $_SERVER['REMOTE_ADDR'];
//        $host = @gethostbyaddr($ip);
//    }

//    $us = $registro["USUARIO_USU"];
//    $fecha = @date("Y-m-d");
//    $hora = @date("H:i:s");


    //$consultaSql = "INSERT INTO ACCESOS VALUES ('$ip','$host','$us','$fecha','$hora')";

    //$sal = consulta($consultaSql);

    echo '<script type="text/javascript">';

    //redireccionar a dentro de la aplicación
    echo "window.location='../../indexg.php';";
    echo '</script>';
} else {
    echo '<script type="text/javascript">';
    echo 'alert ("LO SENTIMOS NO ESTA REGISTRADO");';
    echo "window.location='../../index.php';";
    echo '</script>';
}
?>
