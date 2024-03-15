
function login() {
    username = $("#username").val();
    password = $("#password").val();
    country  = $("#country").val();
    data     = ' {"user": "' + username + '", "pass": "' + password + '", "codigoPais": "' + country + '"}';
    
    $.ajax({
        type: 'POST',
        url: localURL + "LDAP",
        contentType: "application/json",
        dataType: 'json',
        crossDomain: true,
        async: false,
        data: data,
        success: function (data) {
            console.log(data);
            json = eval(data);
            var prueba = data.Response.Acceso;
            var token = data.Response.apiToken;
            var usuario = data.Response.Usuario;
            var rolUsuario = data.Response.Rol;
            var idUser = data.Response.ID;


            if (prueba === "Autorizado") {

                if (usuario === "Existe") {

                    window.location.href = "inicio.html";
                    setCookie('api-token', token, 1);
                    setCookie('api-rol', rolUsuario, 1);
                    setCookie('api-idUser', idUser, 1);
                } else {
                    alert("Usuario no existe en Base de datos");
                }
            }
            if (prueba === "No Autorizado") {
                alert("Credenciales incorrectas");
            }

        },
        error: function (err) {}
    });

}

$("#log").on('click', function () {

    if ($("#username").val() === "" && $("#password").val() === "") {
        alert("Usuario y Contraseña Vacios");
    } else if ($("#username").val() === "") {
        alert("Usuario Vacio");
    } else if ($("#password").val() === "") {
        alert("Contaseña Vacia");
    } else {
        login();
    }

});

$('body').keyup(function (e) {
    if (e.keyCode == 13) {
        login();
    }
});