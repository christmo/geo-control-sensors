var updateInterval = 10000;

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
        min: 0,
        max: 50
    },
    xaxis: {
        mode: "time",
        timeformat: "%H:%M"
    }
};

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