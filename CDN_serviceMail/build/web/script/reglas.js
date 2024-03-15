html = '';
id   = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        select();
        selectPais();
    }else{
        window.location.href = "login.html";
    }    
});


function insert() {

    segmentacion = $("#txtSegmentacion").val();
    cantidad     = $("#txtCantidad").val();
    valor_extra  = $("#txtValor_extra").val();
    pais         = $("#selPais").val();
    data         = '{ "segmentacion" : "' + segmentacion + '" , "cantidad" : "' + cantidad + '" , "valor_extra" : "' + valor_extra + '" , "pais" : "' + pais + '" }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Reglas',
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        headers: {
            "token": token,
            "rol": rolUser
        },
        async: false,
        data: data,
        success: function (response) {
            if (response.Description == 'OK') {
                alertify.success('Se añadió correctamente');
                $('#modalNuevo').modal('hide');
                select();
            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function select(valorPais) {

    var baseSelect = null;

    if (valorPais != null) {
        baseSelect = localURL + 'Reglas' + "?pais=" + valorPais;
    } else {
        baseSelect = localURL + "Reglas";
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
                html += '<tr>';
                html += '<td colspan="4">No existen datos para esta combinación</td>';
                html += '<td colspan="1"><button type="button" class="btn btn-secondary" id="boton-recargar">Actualizar</button></td>';
                html += '</tr>';
            } else {
                for (i = 0; i < sizeRespuesta; i++) {
                    html += '<tr id="' + response.Response[i].ID + '">';
                    html += '<td id="segmentacion-' + response.Response[i].ID + '">' + response.Response[i].SEGMENTACION + '</td>';
                    html += '<td id="cantidad-' + response.Response[i].ID + '">' + response.Response[i].CANTIDAD + '</td>';
                    html += '<td id="valor_extra-' + response.Response[i].ID + '">' + response.Response[i].VALOR_EXTRA + '</td>';
                    html += '<td data-pais="' + response.Response[i].PAIS_ID + '"" id="pais-' + response.Response[i].ID + '">' + response.Response[i].PAIS + '</td>';
                    html += '<td style="text-align: center;" >';
                    html += '<button type="button" class="btn btn-warning" data-toggle="modal" id="boton-editar" data-target="#modalNuevo">Editar</button>';
                    html += '<button type="button" class="btn btn-danger" data-toggle="modal" id="boton-eliminar" data-target="#modalEliminar" >Eliminar</button>';
                    html += '</td>';
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

function update(segmentacion, cantidad, valor_extra, pais, id) {
    data = '{ "segmentacion" : "' + segmentacion + '" , "cantidad" : "' + cantidad + '" , "valor_extra" : "' + valor_extra + '" , "pais" : "' + pais + '","id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Reglas',
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        headers: {
            "token": token,
            "rol": rolUser
        },
        async: false,
        data: data,
        success: function (response) {
            if (response.Description == 'OK') {
                alertify.success('Se edito correctamente');
                $('#modalNuevo').modal('hide');
                select();
            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function deleteId(id) {

    data = '{  "id" : "' + id + '"}';
    $.ajax({
        type: 'DELETE',
        url: localURL + 'Reglas',
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        headers: {
            "token": token,
            "rol": rolUser
        },
        async: false,
        data: data,
        success: function (response) {
            if (response.Description == 'OK') {
                alertify.success('Se elimino correctamente');
                $('#modalEliminar').modal('hide');
                select();
                $("#filtroPais").val("");
            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function validarCampo(element){
    valor = $(element).val();
    if(valor === "N" || valor === "n") {
        $("#txtCantidad").attr("readonly",false);
    }else{
        $("#txtCantidad").attr("readonly",true);
    }   
}

$("#insertar").on("click", function () {
    insert();
});


$("table").delegate("#boton-eliminar", "click", function () {
    id = $(this.parentNode.parentNode).attr("id");

});

$("#eliminar").on("click", function () {
    deleteId(id);
    $("#filtroPais").val("");
});

$("table").delegate("#boton-editar", "click", function () {
    id           = $(this.parentNode.parentNode).attr("id");
    segmentacion = $("#txtSegmentacion").val($("#segmentacion-" + id).html());
    cantidad     = $("#txtCantidad").val($("#cantidad-" + id).html());
    valor_extra  = $("#txtValor_extra").val($("#valor_extra-" + id).html());
    pais         = $("#selPais").val($("#pais-" + id).data('pais'));
    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {

    update(segmentacion.val(), cantidad.val(), valor_extra.val(), pais.val(), id);

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtSegmentacion").val('');
    $("#txtCantidad").val('');
    $("#txtValor_extra").val('');
    $("#selPais").val('');

    selectPais();
});


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