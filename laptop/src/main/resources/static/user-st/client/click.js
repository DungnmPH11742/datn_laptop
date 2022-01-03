$(document).ready(function () {
    $(".btn-submitPassword").on('click', function () {
        var password = $("#password").val();
        var password2 = $("#confirmPassword").val();
        var specialCharacters = /[ `!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?~]/;
        var reWhiteSpace = new RegExp("\\s+");
        if (specialCharacters.test(password) || specialCharacters.test(password2)) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Mật khẩu không chứa kí tự đặc biệt!", 'error', 3);
            }
            return false;
        }

        if (reWhiteSpace.test(password) || reWhiteSpace.test(password2)) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Mật khẩu không chứa khoảng trắng!", 'error', 3);

            }
            return false;
        }

        if (password2.length < 6 || password.length < 6) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Mật khẩu tối thiểu 6 kí tự!", 'error', 3);

            }
            return false;
        }
        if (password2 !== password) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Mật khẩu không khớp vui lòng nhập lại!", 'error', 3);

            }
            return false;
        }


        return true
    });
})