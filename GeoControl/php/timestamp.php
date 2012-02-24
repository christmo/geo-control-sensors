<?php

/*
 * Este archivo hace la conversion de la hora en timestamp a horas, minutos y segundos
 * para que sea presentada en el popup de la grafica
 */

extract($_GET);

/**
 * Se incrementa 5 horas a la hora de visualización para igualar con la hora 
 * timestamp del servidor cuando php en la directiva de DATE no tiene la hora UTC,
 * esto se hace porque FLOT el componente grafico trabaja todo en hora UTC las
 * series de tiempo se generan automáticamente con la consulta timestamp de la base
 * 
 * [Date]
 * date.timezone = UTC
 * 
 * Y esta configurado de manera local
 * 
 * [Date]
 * date.timezone = "America/Guayaquil"
 */
/**
 * Poner a 0 esta variable si el archivo php.ini tiene el date.timezone = UTC
 */
$incrementoUTC = (5 * 60 * 60 * 1000);

$ar = (($hora + $incrementoUTC) / 1000);
echo date('H:i:s', $ar);
?>
