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

    nombre = $("#txtNombre").val();
    pais   = $("#selPais").val();
    data   = '{ "nombre" : "' + nombre + '" , "pais" : "' + pais + '" }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Rol',
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
        baseSelect = localURL + 'Rol' + "?pais=" + valorPais;
    } else {
        baseSelect = localURL + "Rol";
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
                html += '<td colspan="2">No existen datos para esta combinación</td>';
                html += '<td colspan="1"><button type="button" class="btn btn-secondary" id="boton-recargar">Actualizar</button></td>';
                html += '</tr>';
            } else {
                for (i = 0; i < sizeRespuesta; i++) {
                    html += '<tr id="' + response.Response[i].ID + '">';
                    html += '<td id="nombre-' + response.Response[i].ID + '">' + response.Response[i].NOMBRE + '</td>';
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

function update(nombre, pais, id) {
    data = '{ "nombre" : "' + nombre + '" , "pais" : "' + pais + '" , "id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Rol',
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
        url: localURL + 'Rol',
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
    id     = $(this.parentNode.parentNode).attr("id");
    nombre = $("#txtNombre").val($("#nombre-" + id).html());
    pais   = $("#selPais").val($("#pais-" + id).data('pais'));
    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {

    update(nombre.val(), pais.val(), id);

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtNombre").val('');
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