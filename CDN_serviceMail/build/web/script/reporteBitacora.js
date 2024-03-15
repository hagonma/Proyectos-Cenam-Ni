html         = '';
id           = null;
valorPais    = null;

$(function () {
    if (token !="null") {
        $("body").show();   
    select();
    selectPais();
    dataTable();
    }else{
        window.location.href = "login.html";
    }    
});


function dataTable(){

    $('#tabla').DataTable( {
        dom: 'Bfrtip',
        buttons: [
          'excel'
        ]
    } );    
}    



function select(valorPais) {

    var baseSelect = null;

    if (valorPais != null) {
        baseSelect = localURL + 'ReporteBitacora' + "?pais=" + valorPais;
    } else {
        baseSelect = localURL + "ReporteBitacora";
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
                html += '<td colspan="3">No existen datos para esta combinación</td>';
                html += '<td colspan="1"><button type="button" class="btn btn-secondary" id="boton-recargar">Actualizar</button></td>';
                html += '</tr>';
            } else {
                for (i = 0; i < sizeRespuesta; i++) {
                    html += '<tr id="' + response.Response[i].NUMERO + '">';
                    html += '<td id="numero-' + response.Response[i].NUMERO + '">' + response.Response[i].NUMERO + '</td>';
                    html += '<td id="descripcion-' + response.Response[i].NUMERO + '">' + response.Response[i].DESCRIPCION + '</td>';
                    html += '<td id="fecha_hora-' + response.Response[i].NUMERO + '">' + response.Response[i].FECHA_HORA + '</td>';                                        
                    html += '<td id="pais-' + response.Response[i].NUMERO + '">' + response.Response[i].NOMBRE + '</td>';
                    html += '</tr>';
                }
            }
            $("#body-crud").html(html);
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}



$("#filtrar").on("click", function () {
    valorPais = $("#filtroPais").val();
    select(valorPais);

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPais").val("");
    select();
});


$("#refrescar").on("click", function () {
    $("#filtroPais").val("");
    select();
});
