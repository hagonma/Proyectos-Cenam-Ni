html = '';
id   = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        select();
        selectPais();
        selTipoMensaje();
    }else{
        window.location.href = "login.html";
    }    
});

function insert() {
    
    tipoMensaje        = $("#selTipoMensaje").val();
    nombre             = $("#txtDescripcion").val();
    pais               = $("#selPais").val();
    data               = '{ "nombre" : "' + nombre + '" ,  "pais" : "' + pais + '" ,  "tipoMensaje" : "' + tipoMensaje + '" }';

    $.ajax({
        type: 'POST',
        url: localURL + 'Mensaje',
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

function select(valorPais, valorTipoMensaje) {

    var baseSelect = null;

    if (valorTipoMensaje == null && valorPais != null) {
        baseSelect = localURL + 'Mensaje' + "?pais=" + valorPais;
    }

    if (valorTipoMensaje != null && valorPais == null) {
        baseSelect = localURL + 'Mensaje' + "?tipoMensaje=" + valorTipoMensaje;
    }

    if (valorTipoMensaje != null && valorPais != null) {
        baseSelect = localURL + 'Mensaje' + "?tipsoMensaje=" + valorTipoMensaje + "&pais=" + valorPais;
    }

    if (valorTipoMensaje == null && valorPais == null) {
        baseSelect = localURL + "Mensaje";
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
                    html += '<td>' + response.Response[i].ID + '</td>';
                    html += '<td data-tipoMensaje="' + response.Response[i].ID_TIPO_MENSAJE + '"" id="tipoMensaje-' + response.Response[i].ID + '">' + response.Response[i].TIPO_MENSAJE + '</td>';
                    html += '<td id="descripcion-' + response.Response[i].ID + '">' + response.Response[i].DESCRIPCION + '</td>';
                    html += '<td data-pais="' + response.Response[i].ID_PAIS + '"" id="pais-' + response.Response[i].ID + '">' + response.Response[i].PAIS + '</td>';
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

function update(descripcion, tipoMensaje, pais, id) {
    
    data = '{ "nombre" : "' + descripcion + '" , "tipoMensaje" : "' + tipoMensaje + '" , "pais" : "' + pais + '", "id" : "' + id + '" }';
    $.ajax({
        type: 'PUT',
        url: localURL + 'Mensaje',
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
        url: localURL + 'Mensaje',
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
                $("#filtroTipoMensaje").val("");
            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}
function selTipoMensaje() {
  html = null;
  $.ajax({
    type: 'GET',
    url: localURL + 'TipoMensaje',
    contentType: "application/json",
    dataType: 'json',
    crossDomain: true,
    async: false,
    success: function (response) {
      sizeRespuesta = response.Response.length;
      html += '<option value="" disabled selected hidden>Seleccione tipo mensaje</option>';
      for (i = 0; i < sizeRespuesta; i++) {
        html += '<option value=' + response.Response[i].ID + '>' + response.Response[i].DESCRIPCION + '</option>';
      }
      $("#filtroTipoMensaje").html(html);
      $("#selTipoMensajeDestino").html(html);
      $("#selTipoMensaje").html(html);
    }
  });
}

function enviarMensaje() {
  numero             = $("#numeroDestino").val();
  id                 = $("#codigoMensaje").val();
  pais               = $("#selPais2").val();
  descripcionEnvio   = $("#descripcion").val();
  html = null;
  $.ajax({
    type: 'POST',
    url: localURL + 'EnviarSMSGenerico',
    contentType: "application/json",
    dataType: 'json',
    crossDomain: true,
    async: false,
    data:'{"id": "'+id+'", "pais":'+pais+', "numero":'+numero+', "descripcion":"'+descripcionEnvio+'"}' ,
    success: function (response) {
        if (response.Description == 'OK') {
            alertify.success('Se envio correctamente');
        } else {
            alertify.error('Error en la operación');
        }
        $("#numeroDestino").val("");
        $("#codigoMensaje").val("");
        $("#selPais2").val("");
        $("#descripcion").val("");
    },
    error: function (response) {
       alertify.error('Error en la operación');
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
    $("#filtroTipoMensaje").val("");
});

$("table").delegate("#boton-editar", "click", function () {
    id          = $(this.parentNode.parentNode).attr("id");
    descripcion = $("#txtDescripcion").val($("#descripcion-" + id).html());
    tipoMensaje = $("#selTipoMensaje").val($("#tipoMensaje-" + id).data('tipomensaje'));
    pais        = $("#selPais").val($("#pais-" + id).data('pais'));
    
    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {

    update(descripcion.val(), tipoMensaje.val(), pais.val(), id);

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtNombre").val('');
    $("#txtDescripcion").val('');
    $("#txtValor").val('');
    $("#selPais").val('');
    $("#selTipoMensaje").val('');

    selectPais();
    selTipoMensaje();
});


$("#filtrar").on("click", function () {
    valorPais = $("#filtroPais").val();
    valorTipoMensaje = $("#filtroTipoMensaje").val();
    select(valorPais, valorTipoMensaje);
    

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPais").val("");
    $("#filtroTipoMensaje").val("");
    select();
});

$("#refrescar").on("click", function () {
    $("#filtroPais").val("");
    $("#filtroTipoMensaje").val("");
    select();
});


$("#btnPerso").on("click", function () {
    $("#thCodigo").hide("");
    $("#tdCodigo").hide("");
    $("#codigoMensaje").hide("");

    $("#containerEnviar").show("");
    $("#thDescripcion").show("");
    $("#tdDescripcion").show("");
    $("#descripcion").show("");

});

$("#btnPrede").on("click", function () {
    $("#containerEnviar").show("");
    $("#thCodigo").show("");
    $("#tdCodigo").show("");
    $("#codigoMensaje").show("");

    $("#thDescripcion").hide("");
    $("#tdDescripcion").hide("");
    $("#descripcion").hide("");

});