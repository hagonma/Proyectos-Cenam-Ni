html = '';
id = null;
valorPais = null;
$(function() {
    if (token != "null") {
        $("body").show();
        select();
        selectPais();
    } else {
        window.location.href = "login.html";
    }
});

/*
    var tablaMail2 = $('#tablaMail').DataTable({
        lengthChange: false,
        "ordering": false,
        buttons: [
            'excel'
        ]
    });



var tablaMail = $('#tablaMail').DataTable({
    lengthChange: false,
    "ordering": false,
    buttons: ['excel', {
        extend: 'excel'
    }]
});
*/
function select(valorPais, fechaInicio, fechaFin) {

    var baseSelect = null;
    if (valorPais != null && fechaInicio == null && fechaFin == null) {
        baseSelect = localURL + 'ReporteMail' + "?pais=" + valorPais;
    }
    if (fechaInicio != null && fechaFin != null && valorPais == null) {
        baseSelect = localURL + 'ReporteMail' + "?fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;
    }
    if (fechaInicio != null && fechaFin != null && valorPais != null) {
        baseSelect = localURL + 'ReporteMail' + "?fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin + "&pais=" + valorPais;
    }
    if (fechaInicio == null && fechaFin == null && valorPais == null) {
        baseSelect = localURL + 'ReporteMail';
    }


    $.ajax({
        type: 'GET',
        url: baseSelect,
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        headers: {
            "token": token,
            "rol": rolUser
        },
        async: false,
        success: function(response) {
            sizeRespuesta = response.Response.length;
            html = "";
            if (sizeRespuesta == 0) {
                html += '<tr style="text-align: center;">';
                html += '<td colspan="7">No existen datos para esta combinación</td>';
                html += '<td colspan="3"><button type="button" class="btn btn-secondary" id="boton-recargar">Actualizar</button></td>';
                html += '</tr>';
            } else {
                for (i = 0; i < sizeRespuesta; i++) {
                    html += '<tr id="' + response.Response[i].NUMERO + '">';
                    html += '<td id="nombre-' + response.Response[i].NUMERO + '">' + response.Response[i].ID + '</td>';
                    html += '<td id="nombre-' + response.Response[i].NUMERO + '">' + response.Response[i].NOMBRE_CLIENTE + '</td>';
                    html += '<td id="nombre-' + response.Response[i].NUMERO + '">' + response.Response[i].TMCODE + '</td>';
                    html += '<td id="username-' + response.Response[i].NUMERO + '">' + response.Response[i].VIGENCIA_CONTRATO + '</td>';
                    html += '<td id="username-' + response.Response[i].NUMERO + '">' + response.Response[i].CUOTA + '</td>';
                    html += '<td id="username-' + response.Response[i].NUMERO + '">' + response.Response[i].NOMBRE + '</td>';
                    html += '<td id="username-' + response.Response[i].NUMERO + '">' + response.Response[i].FECHA_CONTRATACION + '</td>';
                    html += '<td id="username-' + response.Response[i].NUMERO + '">' + response.Response[i].FECHA_PAGO + '</td>';
                    html += '<td id="nombre-' + response.Response[i].NUMERO + '">' + response.Response[i].FECHAENVIOMAIL + '</td>';
                    html += '</tr>';
                }
            }
            $("#body-crud").html(html);
        },
        error: function(response) {
            console.log(response.Codigo);
            window.location.href = "login.html";
        }
    });
}



$("#filtrar").on("click", function() {
    var fechaI = $("#dateMin").val();
    var fechaF = $("#dateMax").val();
    valorPais = $("#filtroPais").val();
    if (fechaI == '' || fechaF == '') {
        select(valorPais);
    } else {
        select(valorPais, fechaI, fechaF);
    }


});
$("table").delegate("#boton-recargar", "click", function() {
    $("#filtroPais").val("");
    $("#dateMin").val("");
    $("#dateMax").val("");
    select();
});
$("#refrescar").on("click", function() {
    $("#filtroPais").val("");
    $("#dateMin").val("");
    $("#dateMax").val("");
    select();
});

$("#btnExport").click(function (e) {
        $("#tablaMail").table2excel({
            filename: "Mail.xls"
        });
});