var updateInterval = 5000;

/**
 * Opciones de configuración generales del grafico de FLOT para la presentacion
 * de los puntos
 */
var options = {
    series: {
        lines: {
            show: true
        },
        points: {
            show: true,
            radius: 3
        }
    },
    legend: {
        noColumns: 6,
        position: 'nw'
    },
    grid: {
        hoverable: true
    },
    yaxis: {
        /*min: 0,
        max: 50*/
        autoscaleMargin: null
    },
    xaxis: {
        mode: "time",
        timeformat: "%H:%M"
    }
};

/**
 * Convierte cada una de las hora en el formato que se necesita para que se
 * presente en el tooltip de flot
 */
function convertirHora(x,y,sensor,numHora,temp){
    $.ajax({
        url: 'php/timestamp.php',
        method: 'GET',
        data: 'hora='+numHora,
        success: function(hora){
            showTooltip(x, y,"Sensor: "+sensor + "<br/> Hora: " + hora + "<br/> Temp: " + temp+"º");
        }
    });
}

/**
 * Muestra el ToolTip dentro de la grafica al poner el mouse en cada punto
 * medido por los sensores
 */
function showTooltip(x, y, contents) {
    $('<div id="tooltip">' + contents + '</div>').css( {
        position: 'absolute',
        display: 'none',
        top: y + 5,
        left: x + 5,
        border: '2px solid #fdd',
        padding: '3px',
        'background-color': '#10496e',
        opacity: 0.80,
        color: '#ffffff'
    }).appendTo("body").fadeIn(200);
}

/**
 * Calendario de la interfaz para escoger una fecha
 */
$(function() {
    $('#datepicker').datepicker();
    $('#datepicker').datepicker("option", "dateFormat", 'yy-mm-dd');
    $('#datepicker').datepicker('setDate', (new Date()));

    $('#horaini').timepicker({
        hourGrid: 3,
        minuteGrid: 10
    });
    $('#horafin').timepicker({
        hourGrid: 3,
        minuteGrid: 10
    });
});

/**
 * Comprueba si el formato es valido de la hora que se seleccione
 */
function compararHoras(sHora1, sHora2) {

    var arHora1 = sHora1.split(":");
    var arHora2 = sHora2.split(":");

    // Obtener horas y minutos (hora 1)
    var hh1 = parseInt(arHora1[0],10);
    var mm1 = parseInt(arHora1[1],10);

    // Obtener horas y minutos (hora 2)
    var hh2 = parseInt(arHora2[0],10);
    var mm2 = parseInt(arHora2[1],10);

    // Comparar
    if (hh1<hh2 || (hh1==hh2 && mm1<mm2)){
        return true;
    }else if (hh1>hh2 || (hh1==hh2 && mm1>mm2)){
        return false;
    }else{
        return false;
    }
}

/**
 * Guardar los datos de temperatura max y min de todo un modulo para a partir
 * de estos valores generar las alertas
 */
function guardar(mod){
    var id_max = "#"+mod+"MAX";
    var id_min = "#"+mod+"MIN";

    var max = $(id_max).val();
    var min = $(id_min).val();

    $.ajax({
        url: 'php/guardarDatos.php',
        method: 'GET',
        data: 'mod='+mod+"&max="+max+"&min="+min,
        success: function(){
            alert('Datos guardados correctamente...');
        }
    });
}
