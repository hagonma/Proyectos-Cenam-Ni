html         = '';
id           = null;
valorPais    = null;

$(function () {
    if (token !="null") {
        $("body").show();   
        selectDay();
        selectMonth();
    //selectPais();
    //dataTable();
    }else{
        //window.location.href = "login.html";
    }    
});


function dataTable(){
    $('#tabla').DataTable( {
        dom: 'Bfrtip',
        buttons: [
          'excel'
        ]
    });
}    



function selectDay(valorPais) {
    baseSelect = localURL + "genericDashboard/Day";
    var html_var_day = '<table class="table table-hover display" id="tablaDashboardDia" style="width:100%">';
    html_var_day += '<thead>';
    html_var_day += '<tr>';
    html_var_day += '<th>Fecha</th>';
    html_var_day += '<th>Pais</th>';
    html_var_day += '<th>Proyecto</th>';
    html_var_day += '<th>Cantidad</th>';
    html_var_day += '</tr>';
    html_var_day += '</thead></table>';
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
            var data = response["Response"];
            //console.log(response["Response"])
            $('#table-response-dia').html(html_var_day);
            $('#tablaDashboardDia').DataTable({
                deferRender: true,
                dom: 'Bfrtip',
                buttons: [
                    'copy', 'csv', 'excel'
                ],
                data: data,
                columns: [
                    { "data": "MES" },
                    { "data": "PAIS" },
                    { "data": "TIPO" },
                    { "data": "CANTIDAD" }
                ]
            });
        },
        error: function (response) {
            //window.location.href = "login.html";
        }
    });
}

function selectMonth(valorPais) {
    baseSelect = localURL + "genericDashboard/Month";
    var html_var_month = '<table class="table table-hover display" id="tablaDashboardMes" style="width:100%">';
    html_var_month += '<thead>';
    html_var_month += '<tr>';
    html_var_month += '<th>Mes</th>';
    html_var_month += '<th>Pais</th>';
    html_var_month += '<th>Proyecto</th>';
    html_var_month += '<th>Cantidad</th>';
    html_var_month += '</tr>';
    html_var_month += '</thead></table>';
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
            var data = response["Response"];
            //console.log(response["Response"])
            $('#table-response-mes').html(html_var_month);
            $('#tablaDashboardMes').DataTable({
                deferRender: true,
                dom: 'Bfrtip',
                buttons: [
                    'copy', 'csv', 'excel'
                ],
                data: data,
                columns: [
                    { "data": "MES" },
                    { "data": "PAIS" },
                    { "data": "TIPO" },
                    { "data": "CANTIDAD" }
                ]
            });
        },
        error: function (response) {
            //window.location.href = "login.html";
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
