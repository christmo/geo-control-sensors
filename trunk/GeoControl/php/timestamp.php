<?php
/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

extract($_GET);

//echo $hora."<br/>";
$ar = ($hora/1000);
echo date('H:i:s',$ar);

?>
