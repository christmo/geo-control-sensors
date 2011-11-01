/**
 * Se utiliza para definir las opciones a realizar por los botones del menu
 */
$(function() {
    $( ".menu button:first" ).button({
        icons: {
            primary: "ui-icon-locked"
        }
    }).next().button({
        icons: {
            primary: "ui-icon-locked"
        }
    }).next().button({
        icons: {
            primary: "ui-icon-locked"
        }
    }).next().button({
        icons: {
            primary: "ui-icon-gear"
        }
    }).next().button({
        icons: {
            primary: "ui-icon-gear"
        }
    });
});

function salir(){
    window.location='php/login/logout.php';
}

function graficas(){
    window.location='indexg.php';
    //$("#panel").load("php/graficas.php");
}

function historico(){
    window.location='historico.php';
//$("#panel").load("php/consultas.php");
}

function configuracion(){
    window.location='config.php';
}

function notificaciones(){
    window.location='notificaciones.php';
}