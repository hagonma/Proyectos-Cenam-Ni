html = '';

function selectMenu() {

    $.ajax({
        type: 'GET',
        url: localURL + 'Acceso?rol=' + rolUser,
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
            html += '<nav class="navbar navbar-expand-lg navbar-dark bg-dark"  style="font-size: 18px">';
            html += '<a class="" href="inicio.html"><img src="./img/logoclaro.png" width="50" height="50"></a>';
            html += '<ul class="navbar-nav mr-auto">';
            for (i = 0; i < sizeRespuesta; i++) {

                if (response.Response[i].PANTALLA === 'Inicio') {
                    html += '<li class="nav-item" style="display: none;"> <a class="nav-link" href="' + response.Response[i].URL + '">' + response.Response[i].PANTALLA + '</a> </li>';    
                }else{
                    html += '<li class="nav-item"> <a class="nav-link" href="' + response.Response[i].URL + '">' + response.Response[i].PANTALLA + '</a> </li>';
                }
            }
            html += '</ul>';
            html += ' <a class="navbar-brand"><img width="35" height="35"  onmouseout="this.src=\'./img/out.png\';" onmouseover="this.src=\'./img/outred.png\';" src="./img/out.png" id="logout" ></a>';
            html += '</nav>';

            $(".navbarText").html(html);
            $(".menuContainer").html(html);

        },
        error: function (response) {
            //window.location.href = "login.html";
        }
    });
}


$(document).ready(function () {

    selectMenu();

    if (token === "") {

        window.location.href = "login.html";
    }

});


$("body").delegate("#logout", "click", function () {
    setCookie('api-token', 'null', 1);
    setCookie('api-rol', 'null', 1);
    // console.log(getCookie('api-token'))
    window.location.href = "login.html";
});