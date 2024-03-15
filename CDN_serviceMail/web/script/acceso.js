html      = '';
id        = null;
valorPais = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        select();
        selectPantalla();
        selectRol();
        selectPais();
    }else{
        window.location.href = "login.html";
    }    
});


function selectRol() {
    html = null;
    $.ajax({
        type: 'GET',
        url: localURL + 'Rol',
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
            html += '<option value="" disabled selected hidden>Seleccione un Rol</option>';
            for (i = 0; i < sizeRespuesta; i++) {
                html += '<option value=' + response.Response[i].ID + '>' + response.Response[i].NOMBRE + '</option>';
            }
            $("#selRol").html(html);
            $("#filtroRol").html(html);
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function selectPantalla() {
    html = null;
    $.ajax({
        type: 'GET',
        url: localURL + 'Pantalla',
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
            html += '<option value="" disabled selected hidden>Seleccione una Pantalla</option>';
            for (i = 0; i < sizeRespuesta; i++) {
                html += '<option value=' + response.Response[i].ID + '>' + response.Response[i].NOMBRE + '</option>';
            }
            $("#selPantalla").html(html);
            $("#filtroPantalla").html(html);
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function insert() {

    pantalla = $("#selPantalla").val();
    rol      = $("#selRol").val();
    data     = '{ "pantalla" : "' + pantalla + '" , "rol" : "' + rol + '" }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Acceso',
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

function select(valorPantalla, valorRol) {

    var baseSelect = null;

    if (valorRol == null && valorPantalla != null) {
        baseSelect = localURL + 'Acceso' + "?pantalla=" + valorPantalla;
    }

    if (valorRol != null && valorPantalla == null) {
        baseSelect = localURL + 'Acceso' + "?rol=" + valorRol;
    }

    if (valorRol != null && valorPantalla != null) {
        baseSelect = localURL + 'Acceso' + "?rol=" + valorRol + "&pantalla=" + valorPantalla;
    }

    if (valorRol == null && valorPantalla == null) {
        baseSelect = localURL + "Acceso";
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
                    html += '<td data-pantalla="' + response.Response[i].PANTALLA_ID + '"" id="pantalla-' + response.Response[i].ID + '">' + response.Response[i].PANTALLA + '</td>';
                    html += '<td data-rol="' + response.Response[i].ROL_ID + '"" id="rol-' + response.Response[i].ID + '">' + response.Response[i].ROL + '</td>';
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

function update(pantalla, rol, id) {
    data = '{ "pantalla" : "' + pantalla + '" ,"rol" : "' + rol + '", "id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Acceso',
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
        url: localURL + 'Acceso',
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
    $("#filtroPantalla").val("");
    $("#filtroRol").val("");
});

$("table").delegate("#boton-editar", "click", function () {
    id       = $(this.parentNode.parentNode).attr("id");
    pantalla = $("#selPantalla").val($("#pantalla-" + id).data('pantalla'));
    rol      = $("#selRol").val($("#rol-" + id).data('rol'));

    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {
    update(pantalla.val(), rol.val(), id);
});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#selPantalla").val('');
    $("#selRol").val('');

    selectPantalla();
    selectRol();
});


$("#filtrar").on("click", function () {
    valorPantalla = $("#filtroPantalla").val();
    valorRol = $("#filtroRol").val();
    select(valorPantalla, valorRol);

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPantalla").val("");
    $("#filtroRol").val("");
    select();
});


$("#refrescar").on("click", function () {
    $("#filtroPantalla").val("");
    $("#filtroRol").val("");
    select();
});