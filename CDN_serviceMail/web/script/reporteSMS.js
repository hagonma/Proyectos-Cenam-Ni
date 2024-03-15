html         = '';
id           = null;
valorPais    = null;

$(function () { 
    if (token != "null") {
    $("body").show();   
    select();
    selectPais();
    }else{
        window.location.href = "login.html";  
    }    
});
  
function select(valorPais,fechaInicio,fechaFin) {  

    var baseSelect = null;

    if (valorPais != null && fechaInicio == null && fechaFin == null) {
        baseSelect = localURL + 'ReporteSMS' + "?pais=" + valorPais;
    } 
    if (fechaInicio != null && fechaFin != null && valorPais == null) {
        baseSelect = localURL + 'ReporteSMS' + "?fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin;
    }
    if (fechaInicio != null && fechaFin != null && valorPais != null) {
        baseSelect = localURL + 'ReporteSMS' + "?fechaInicio=" + fechaInicio + "&fechaFin=" + fechaFin + "&pais=" + valorPais;
    }                  
    if (fechaInicio == null && fechaFin == null && valorPais == null) {
        baseSelect = localURL + 'ReporteSMS';
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
    success: function (response) {
            sizeRespuesta = response.Response.length;
            html = "";
            if (sizeRespuesta == 0) {
                html += '<tr style="text-align: center;">';
                html += '<td colspan="4">No existen datos para esta combinación</td>';
                html += '<td colspan="1"><button type="button" class="btn btn-secondary" id="boton-recargar">Actualizar</button></td>';
                html += '</tr>';
            } else {
                for (i = 0; i < sizeRespuesta; i++) {
                    html += '<tr>';
                    html += '<td>' + response.Response[i].PAIS + '</td>';
                    html += '<td>' + response.Response[i].NUMERO_ORIGEN + '</td>';
                    html += '<td>' + response.Response[i].MARCACION + '</td>';                                        
                    html += '<td>' + response.Response[i].FECHA_ENVIO + '</td>';
                    html += '<td>' + response.Response[i].TEXTO_MSG + '</td>';
                    html += '</tr>';
                }
            }
            $("#body-crud").html(html);
        },
        error: function (response) {
            console.log(response.Codigo);
            window.location.href = "login.html";
        }
    });
}

$("#filtrar").on("click", function () {
    var fechaI = $("#dateMin").val();
    var fechaF = $("#dateMax").val();
    valorPais  = $("#filtroPais").val();

   if (fechaI == '' || fechaF == '') {
        select(valorPais);
    }
    else{
        select(valorPais,fechaI,fechaF);
    } 

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPais").val("");
    $("#dateMin").val("");
    $("#dateMax").val("");
    select();
});


$("#refrescar").on("click", function () {
    $("#filtroPais").val("");
    $("#dateMin").val("");
    $("#dateMax").val("");
    select();
});

$("#btnExport").click(function (e) {
        $("#tablaSMS").table2excel({
            filename: "SMS.xls"
        });
});
