html      = '';
id        = null;
valorPais = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        select();
        selectPais();
        selectRol();
    }else{
        window.location.href = "login.html";
    }    
});

function selectRol(paisRol) {

    var baseSelect = null;
    html = "";
    if (paisRol != null) {
        baseSelect = localURL + 'Rol' + "?pais=" + paisRol;
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
            html += '<option value="" disabled selected hidden>Seleccione un Rol</option>';
            for (i = 0; i < sizeRespuesta; i++) {
                html += '<option value=' + response.Response[i].ID + '>' + response.Response[i].NOMBRE + '</option>';
            }
            if (paisRol == null) {
                $("#filtroRol").html(html);
                $("#selRol").html(html);
            } else {
                $("#selRol").html(html);
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function insert() {

    nombre   = $("#txtNombre").val();
    username = $("#txtUsername").val();
    pais     = $("#selPais").val();
    rol      = $("#selRol").val();
    data     = '{ "nombre" : "' + nombre + '" , "username" : "' + username + '" , "pais" : "' + pais + '","rol" : "' + rol + '"  }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Usuario',
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

function select(valorPais, valorRol) {

    var baseSelect = null;

    if (valorRol == null && valorPais != null) {
        baseSelect = localURL + 'Usuario' + "?pais=" + valorPais;
    }

    if (valorRol != null && valorPais == null) {
        baseSelect = localURL + 'Usuario' + "?rol=" + valorRol;
    }

    if (valorRol != null && valorPais != null) {
        baseSelect = localURL + 'Usuario' + "?rol=" + valorRol + "&pais=" + valorPais;
    }

    if (valorRol == null && valorPais == null) {
        baseSelect = localURL + "Usuario";
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
                    html += '<td id="username-' + response.Response[i].ID + '">' + response.Response[i].USERNAME + '</td>';
                    html += '<td data-pais="' + response.Response[i].PAIS_ID + '"" id="pais-' + response.Response[i].ID + '">' + response.Response[i].PAIS + '</td>';
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

function update(nombre, username, pais, rol, id) {
    data = '{ "nombre" : "' + nombre + '" , "username" : "' + username + '" , "pais" : "' + pais + '","rol" : "' + rol + '", "id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Usuario',
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
        url: localURL + 'Usuario',
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
    id       = $(this.parentNode.parentNode).attr("id");
    nombre   = $("#txtNombre").val($("#nombre-" + id).html());
    username = $("#txtUsername").val($("#username-" + id).html());
    pais     = $("#selPais").val($("#pais-" + id).data('pais'));
    rol      = $("#selRol").val($("#rol-" + id).data('rol'));

    $("#insertar").hide();
    $("#editar").show();
  
});

$("#editar").on("click", function () {

    update(nombre.val(), username.val(), pais.val(), rol.val(), id);

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtNombre").val('');
    $("#txtUsername").val('');
    $("#selPais").val('');
    $("#selRol").val('');

    $("#selRol").prop("disabled", true);

    selectPais();
    selectRol();
});


$("#filtrar").on("click", function () {
    valorPais = $("#filtroPais").val();
    valorRol = $("#filtroRol").val();

    select(valorPais, valorRol);

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPais").val("");
    $("#filtroRol").val("");
    select();
});

$("#selPais").on("change", function () {
    paisRol = $("#selPais").val();
    $("#selRol").prop("disabled", false);
    selectRol(paisRol);
});

$("#refrescar").on("click", function () {
    $("#filtroPais").val("");
    $("#filtroRol").val("");
    select();
});