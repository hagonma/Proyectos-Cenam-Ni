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

    nombre      = $("#txtNombre").val();
    descripcion = $("#txtDescripcion").val();
    valor       = $("#txtValor").val();
    pais        = $("#selPais").val();
    data        = '{ "nombre" : "' + nombre + '" , "descripcion" : "' + descripcion + '" , "valor" : "' + valor + '" , "pais" : "' + pais + '" }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Parametro',
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        async: false,
        headers: {
            "token": token,
            "rol": rolUser
        },
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
        baseSelect = localURL + 'Parametro' + "?pais=" + valorPais;
    } else {
        baseSelect = localURL + "Parametro";
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
                    html += '<td id="nombre-' + response.Response[i].ID + '">' + response.Response[i].NOMBRE + '</td>';
                    html += '<td id="descripcion-' + response.Response[i].ID + '">' + response.Response[i].DESCRIPCION + '</td>';
                    html += '<td id="valor-' + response.Response[i].ID + '">' + response.Response[i].VALOR + '</td>';
                    html += '<td data-pais="' + response.Response[i].PAIS_ID + '"" id="pais-' + response.Response[i].ID + '">' + response.Response[i].PAIS + '</td>';
                    html += '<td style="text-align: center;" >';
                    html += '<button type="button" class="btn btn-warning" data-toggle="modal" id="boton-editar" data-target="#modalNuevo">Editar</button>';
                    html += '<button type="button" class="btn btn-danger" data-toggle="modal" id="boton-eliminar" data-target="#modalEliminar" >Eliminar</button>';
                    html += '</td>';
                    html += '</tr>';
                }
            }
            $("#body-crud").html(html);
        }
    });
}

function update(nombre, descripcion, valor, pais, id) {
    data = '{ "nombre" : "' + nombre + '" , "descripcion" : "' + descripcion + '" , "valor" : "' + valor + '" , "pais" : "' + pais + '", "id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Parametro',
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        async: false,
        headers: {
            "token": token,
            "rol": rolUser
        },
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
        url: localURL + 'Parametro',
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
    id          = $(this.parentNode.parentNode).attr("id");
    nombre      = $("#txtNombre").val($("#nombre-" + id).html());
    descripcion = $("#txtDescripcion").val($("#descripcion-" + id).html());
    valor       = $("#txtValor").val($("#valor-" + id).html());
    pais        = $("#selPais").val($("#pais-" + id).data('pais'));
    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {

    update(nombre.val(), descripcion.val(), valor.val(), pais.val(), id);

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtNombre").val('');
    $("#txtDescripcion").val('');
    $("#txtValor").val('');
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