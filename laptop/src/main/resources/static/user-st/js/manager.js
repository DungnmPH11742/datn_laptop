
$('#city').on('change',function (){
    let code = $(this).val();
    let nameStr = $(this).find(":selected").text();
    $('#name-city').val(nameStr);
    jQuery.ajax({
        url:"/ajax/districts-address?code="+code, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            $('#wards option').remove();
            $('#wards').append("<option value disabled selected>Phường/Xã</option>");
            $('#district option').remove();
            $('#district').append("<option value disabled selected>Quận/Huyện</option>");
            for (let i = 0; i < jsonResult.length; i++) {
                $('#district').append("<option value="+jsonResult[i]['code']+">"+jsonResult[i]['name']+"</option>");
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
})

$('#district').on('change',function (){
    let code = $(this).val();
    let codeCity = $('#city').val();
    let nameStr = $(this).find(":selected").text();
    $('#name-district').val(nameStr);
    jQuery.ajax({
        url:"/ajax/wards-address?code="+code+"&city="+codeCity, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            $('#wards option').remove();
            $('#wards').append("<option value disabled selected>Phường/Xã</option>");
            for (let i = 0; i < jsonResult.length; i++) {
                $('#wards').append("<option value="+jsonResult[i]['code']+">"+jsonResult[i]['name']+"</option>");
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
});
$('#wards').on('change',function (){
    let nameStr = $(this).find(":selected").text();
    $('#name-wards').val(nameStr);
})
$().ready(function (){
    $.validator.addMethod("validatePhone", function (value, element) {
        return this.optional(element) || /(84|0[3|5|7|8|9])+([0-9]{8})\b/i.test(value);
    },"Sai định dạng số điện thoại !");
    $.validator.addMethod("validateEmail", function (value, element) {
        return this.optional(element) ||/^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/i.test(value);
    },"Sai định dạng Email !");
    $('#form-address').validate({
        rules: {
            username: "required",
            numberhome:"required",
            userphone: {
                required:true,
                minlength:10,
                validatePhone:true,
            },
            city:"required",
            district:"required",
            wards:"required",
        },

        messages: {
            username: "Vui lòng nhập tên !",
            numberhome:"Vui lòng nhật số nhà/ tên đường !",
            userphone: {
                required: "Vui lòng nhập số điện thoại !",
                minlength: "Số điện thoại ít nhất 10 số !"
            },
            wards:"Vui lòng chọn xã/phường",
            city: "Vui lòng chọn tỉnh/thành phố",
            district: "Vui lòng chọn quận/huyện",
        },
        errorElement : 'div',

        submitHandler: function(form) {
            form.submit();
        },
    });
    $('#form-manager-user').validate({
        rules: {
            fullName: "required",
            dayBird:"required",
            monthBird:"required",
            yearBird:"required",
            sex:"required",
            phone: {
                required:true,
                minlength:10,
                validatePhone:true,
            },
            email:{
                required:true,
                validateEmail:true,
            }
        },

        messages: {
            fullName: "Vui lòng nhập tên !",
            dayBird: "Vui lòng chọn ngày sinh",
            monthBird: "Vui lòng chọn tháng sinh",
            yearBird: "Vui lòng chọn năm sinh",
            sex: "Vui lòng chọn giới tính",
            phone: {
                required: "Vui lòng nhập số điện thoại !",
                minlength: "Số điện thoại ít nhất 10 số !"
            },
            email: {
                required: "Vui lòng nhập email !",
            }
        },
        errorElement : 'div',
        errorPlacement: function(error, element) {
            if (element.attr('name') == 'sex') {
                error.insertAfter('#sex-error');
            } else{
                error.insertAfter(element);
            }
        },
        submitHandler: function(form) {
            form.submit();
        },
    });
    $('#form-change-password').validate({
        rules: {
            oldpassword: "required",
            newpassword:{
                required:true,
                minlength:6,
            },
            renewpassword: {
                required:true,
            },
        },

        messages: {
            oldpassword: "Hãy nhập mật khẩu cũ",
            newpassword:{
                required: "Hãy nhập mật khẩu mới",
                minlength: "Mật khẩu phải ít nhất 6 kí tự"
            },
            renewpassword: {
                required:"Hãy nhập lại mật khẩu mới",
            },
        },
        errorElement : 'div',

        submitHandler: function(form) {
            form.submit();
        },
    });
})


$(".address-delete").on('click',function(){
    $(".popup-delate-address-cart").addClass("active");
    $('.bg-h-pop').addClass('active');
    var idDelete = $(this).data('delete');
    $('#js-delete-address-id').val(idDelete);
})
$('.h-pop-close').on('click',function(){
    $(".popup-delate-address-cart").removeClass("active");
    $(".bg-h-pop").removeClass("active");
})

$(".h-pop-not").on("click", function () {
    $(".popup-delate-address-cart").removeClass("active");
    $(".bg-h-pop").removeClass("active");
});

$(".bg-h-pop").on("click", function () {
    $(".popup-delate-address-cart").removeClass("active");
    $(this).removeClass("active");
});
$('.h-pop-ok').on('click',function (){
    var idAddressDelete = $('#js-delete-address-id').val();
    jQuery.ajax({
        url:"/ajax/delete-address?idAddress="+idAddressDelete, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            $('.info-address[data-address='+jsonResult.data.id+']').remove();
            $(".popup-delate-address-cart").removeClass("active");
            $(".bg-h-pop").removeClass("active");
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
});


//Order jQuery
$(".btn-cancel-order").on('click',function(){
    $(".popup-cancel-order").addClass("active");
    $('.bg-h-pop').addClass('active');
    var idOrder = $(this).data('cancel');
    $('#js-delete-order-id').val(idOrder);
})
$('.h-pop-close').on('click',function(){
    $(".popup-cancel-order").removeClass("active");
    $(".bg-h-pop").removeClass("active");
})

$(".h-pop-not-order").on("click", function () {
    $(".popup-cancel-order").removeClass("active");
    $(".bg-h-pop").removeClass("active");
});
$('#myModal .close').on("click",function (){
    $('#myModal').removeClass("active");
})
$(".bg-h-pop").on("click", function () {
    $(".popup-cancel-order").removeClass("active");
    $(this).removeClass("active");
});
$('.h-pop-ok-order').on('click',function (){
    var idOrder = $('#js-delete-order-id').val();
    jQuery.ajax({
        url:"/cancel-order/"+idOrder, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            if (!$.trim(jsonResult.data)){
                $(".popup-cancel-order").removeClass("active");
                $(".bg-h-pop").removeClass("active");
                $('#myModal').addClass("active");
            }else {
                console.log(jsonResult.data.id);
                $('.btn-cancel-order').each(function (){
                    if ($(this).data('cancel') == jsonResult.data.id){
                        $(this).remove();
                    }
                })
                $(".popup-cancel-order").removeClass("active");
                $(".bg-h-pop").removeClass("active");

            }


        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
});


//Manager user


