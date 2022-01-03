$(document).ready(function () {

    $("#forgotPassword").on('click', function (event) {
        event.preventDefault();
        $('.forgotPasswordModal').modal('show');
    })

    $("#btnSubmit").on('click', function (event) {
        event.preventDefault();
        var email = $("#emailForgot").val();
        console.log(email)
        $.ajax({
            type: "POST",
            data: email,
            url: "http://localhost:8080/api/forgotPassword/"+email,
            dataType: 'json',
            //timeout: 800000,
        }).done(function (response) {
            if (response.message != null) {
                $("#message").html(response.message)
                $("#error").html("")
            }
            if (response.error != null) {
                $("#error").html(response.error)
                $("#message").html("")
            }
        });
    })

})