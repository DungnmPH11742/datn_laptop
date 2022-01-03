function sendContact() {
    var name = document.getElementById("name").value;
    var email = document.getElementById("email").value;
    var phoneNumber = document.getElementById("phoneNumber").value;
    var message = document.getElementById("message").value;
    var phone_regex = /((09|03|07|08|05)+([0-9]{8})\b)/g;
    var emailValid = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    var check = 0;

    if (name.length == 0) {
        document.getElementById("nameError").innerHTML = "Tên không được để trống";
        check = 1;
    }


    if (emailValid.test(email) == false) {
        document.getElementById("emailError").innerHTML = "Email sai định dạng";
        check = 1;
    }

    if (phone_regex.test(phoneNumber) == false) {
        document.getElementById("phoneNumberError").innerHTML = "Số điện thoại sai định dạng";
        check = 1;
    }

    if (message.length == 0) {
        document.getElementById("messageError").innerHTML = "Nội dung không được để trống";
        check = 1;
    }

    if (check == 1) {
        return;
    }
    var contact = new Object();
    contact.name = name;
    contact.email = email;
    contact.phoneNumber = phoneNumber;
    contact.message = message;
    contact.status = "Chưa trả lời"
    var data = JSON.stringify(contact)
    $.ajax({
        type: "POST",
        data: data,
        contentType: "application/json",
        url: "http://localhost:8080/api/contact",
        success: function (result) {
            alert("Cảm ơn quý khách đã liên hệ với chúng tôi. KingdomOfComputer sẽ phản hồi sớm cho bạn");
            window.location.href = "/contact";
        },
        error: function (e) {
            alert("Error: ", e);
            console.log("Error", e);
        }
    });
}