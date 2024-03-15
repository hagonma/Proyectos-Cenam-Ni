html = '';
id   = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        select();
        selectPais();
        selectDetalleCatalogo();  
        selectCodigoCatalogo();
    }else{
        window.location.href = "login.html";  
    }    
});

function insert() {

    codigo      = $("#txtCodigo").val();
    descripcion = $("#txtDescripcion").val();
    detalle     = $("#selDetalle").val();
    pais        = $("#selPais").val();
    data        = '{"codigo": "'+codigo+'", "idDetalle":"'+detalle+'", "descripcion":"'+descripcion+'","pais":"'+pais+'"}';


    $.ajax({
        type: 'POST',
        url: localURL + 'Catalogo',
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
                selectDetalleCatalogo();
                selectCodigoCatalogo();
                selectPais();              

            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function select(valorPais,valorDetalle) {

    var baseSelect = null;

    if (valorDetalle == null && valorPais != null) {
        baseSelect = localURL + 'Catalogo' + "?pais=" + valorPais;
    }

    if (valorDetalle != null && valorPais == null) {
        baseSelect = localURL + 'Catalogo' + "?codigo="+ "'"+valorDetalle+"'";
    }

    if (valorDetalle != null && valorPais != null) {
        baseSelect = localURL + 'Catalogo' + "?codigo=" + "'"+valorDetalle+"'" + "&pais=" + valorPais;
    }

    if (valorDetalle == null && valorPais == null) {
        baseSelect = localURL + "Catalogo";
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
                    html += '<tr id="' + response.Response[i].ID_CATALOGO+ '">';
                    html += '<td id="codigo-' + response.Response[i].ID_CATALOGO + '">' + response.Response[i].CODIGO + '</td>';
                    html += '<td id="descripcion-' + response.Response[i].ID_CATALOGO + '">' + response.Response[i].DESCRIPCION + '</td>';
                    html += '<td data-detalle="' + response.Response[i].ID_DETALLE + '"" id="detalle-' + response.Response[i].ID_CATALOGO + '">' + response.Response[i].DETALLE + '</td>';
                    html += '<td data-pais="' + response.Response[i].ID_PAIS + '"" id="pais-' + response.Response[i].ID_CATALOGO + '">' + response.Response[i].NOMBRE + '</td>';
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

function update(id,codigo,detalle,descripcion,pais) {
    data = '{"idCatalogo": "'+id+'", "codigo": "'+codigo+'", "idDetalle":"'+detalle+'", "descripcion":"'+descripcion+'","pais":"'+pais+'"}';

    $.ajax({
        type: 'PUT',
        url: localURL + 'Catalogo',
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
                selectDetalleCatalogo();
                selectCodigoCatalogo();
                selectPais();                
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

    data = '{  "idCatalogo" : "' + id + '"}';
    $.ajax({
        type: 'DELETE',
        url: localURL + 'Catalogo',
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
                selectDetalleCatalogo();
                selectCodigoCatalogo();
                selectPais();
            } else {
                alertify.error('Error en la operación');
            }
        },
        error: function (response) {
            window.location.href = "login.html";
        }
    });
}

function selectDetalleCatalogo() {
  html = null;
  $.ajax({
    type: 'GET',
    url: localURL + 'DetalleCatalogo',
    contentType: "application/json",
    dataType: 'json',
    crossDomain: true,
    async: false,
    success: function (response) {
      sizeRespuesta = response.Response.length;
      html += '<option value="" disabled selected hidden>Seleccione un Detalle</option>';
      for (i = 0; i < sizeRespuesta; i++) {
        html += '<option value=' + response.Response[i].ID_DETALLE + '>' + response.Response[i].CODIGO + '</option>';
      }
      $("#selDetalle").html(html);
      $("#filtroDetalle").html(html);
    }
  });
}

function selectCodigoCatalogo() {
  html = null;
  $.ajax({
    type: 'GET',
    url: localURL + 'CodigoCatalogo',
    contentType: "application/json",
    dataType: 'json',
    crossDomain: true,
    async: false,
    success: function (response) {
      sizeRespuesta = response.Response.length;
      html += '<option value="" disabled selected hidden>Seleccione un Código</option>';
      for (i = 0; i < sizeRespuesta; i++) {
        html += '<option value=' + response.Response[i].CODIGO + '>' + response.Response[i].CODIGO + '</option>';
      }
      $("#filtroCodigoDetalle").html(html);
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
    codigo      = $("#txtCodigo").val($("#codigo-" + id).html());
    descripcion = $("#txtDescripcion").val($("#descripcion-" + id).html());
    detalle        = $("#selDetalle").val($("#detalle-" + id).data('detalle'));
    pais        = $("#selPais").val($("#pais-" + id).data('pais')); 
    $("#insertar").hide();
    $("#editar").show();

});

$("#editar").on("click", function () {

    update(id,codigo.val(),detalle.val(),descripcion.val(),pais.val())

});

$("#boton-nuevo").on("click", function () {
    $("#editar").hide();
    $("#insertar").show();
    $("#txtCodigo").val('');
    $("#txtDescripcion").val('');
    $("#selDetalle").val('');
    $("#selPais").val('');

    selectPais();
    selectDetalleCatalogo();
    selectCodigoCatalogo();
});


$("#filtrar").on("click", function () {
    valorPais = $("#filtroPais").val();
    valorDetalle = $("#filtroDetalle").val();
    valorCodigoDetalle = $("#filtroCodigoDetalle").val();
    select(valorPais,valorCodigoDetalle);

});

$("table").delegate("#boton-recargar", "click", function () {
    $("#filtroPais").val("");
    $("#filtroDetalle").val("");
    $("#filtroCodigoDetalle").val("");
    select();
});

$("#refrescar").on("click", function () {
    $("#filtroPais").val("");
    $("#filtroDetalle").val("");
    $("#filtroCodigoDetalle").val("");
    select();
});