//var localURL = 'http://10.218.41.90:7045/CDN/webresources/';
var localURL = 'http://172.21.200.83:7502/CDN/webresources/';
//var localURL = 'http://172.16.169.132:8503/CDN/webresources/'; 
//var localURL = 'http://192.168.0.102:7001/CDN/webresources/'; 

var rolUser  = getCookie('api-rol');
var idUser   = getCookie('api-idUser');
var token    = getCookie('api-token')+"-"+idUser;

 
var arr ;
var arrPantalla = [];
var size;

alertify.set('notifier', 'position', 'top-right');

function get(url, param) {
  var url_string = url;
  var url = new URL(url_string);
  var param = url.searchParams.get(param);
  return param;
}


function selectPais() {
  html = null;
  $.ajax({
    type: 'GET',
    url: localURL + 'Pais',
    contentType: "application/json",
    dataType: 'json',
    crossDomain: true,
    async: false,
    success: function (response) {
      sizeRespuesta = response.Response.length;
      html += '<option value="" disabled selected hidden>Seleccione un País</option>';
      for (i = 0; i < sizeRespuesta; i++) {
        html += '<option value=' + response.Response[i].ID + '>' + response.Response[i].NOMBRE + '</option>';
      }
      $("#selPais").html(html);
      $("#selPais2").html(html);
      $("#filtroPais").html(html);
    }
  });
}


function fecha(dias) {
    if(dias!=0 ){
        var now = new Date((new Date()).valueOf() - (dias*1000)*3600*24);
    }else{
        var now = new Date();
    }
    var y = now.getFullYear();
    var m = now.getMonth() + 1;
    var d = now.getDate();
    var mm = m < 10 ? '0' + m : m;
    var dd = d < 10 ? '0' + d : d;
    return y+'-'+mm+'-'+dd;
}
