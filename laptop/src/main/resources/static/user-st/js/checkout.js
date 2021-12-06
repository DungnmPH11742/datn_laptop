$("#action-add-new").on("click", function () {
    if ($("#add-address").hasClass("hide")) {
        $("#add-address").removeClass("hide").addClass("show");
    }
    $('.title-header-form').text("Thêm mới địa chỉ");
    $('.button-add-new-address').text("Lưu Địa Chỉ");
    $('#full_name').val('');
    $('#phone_number').val('');
    $('#id-address').val('');
    $('#js-bandc-province').val('');
    $('#js-bandc-distric').val('');
    $('#js-bandc-wards').val('');
    $('#addressDetail').val('');

});

$(".close-modal").on("click", function () {
    if ($("#add-address").hasClass("show")) {
        $("#add-address").removeClass("show").addClass("hide");
    }
});
$(".select-address").on("click", function () {
    if ($(".item-add").hasClass("active")) {
        $(".item-add").removeClass("active");
        $(this).closest('div.item-add').addClass("active");
        var idAdress = $(this).data('id-address');
        $('#id-address-select').val(idAdress);
    }
    if (!$(this).hasClass("active")){
        $(this).closest('div.item-add').addClass("active");
        var idAdress = $(this).data('id-address');
        $('#id-address-select').val(idAdress);
    }
});
$("#js-bandc-province").on("click", function () {
    if ($("#js-bandc-distric-list").css("display") == "block" || $("#js-bandc-wards-list").css("display") == "block") {
        $("#js-bandc-distric-list").css("display", "none");
        $("#js-bandc-wards-list").css("display", "none");
    }
    $("#js-bandc-province-list").css("display", "block");
});
$("#js-bandc-distric").on("click", function () {
    if (
        $("#js-bandc-province-list").css("display") == "block" ||
        $("#js-bandc-wards-list").css("display") == "block"
    ) {
        $("#js-bandc-province-list").css("display", "none");
        $("#js-bandc-wards-list").css("display", "none");
    }
    $("#js-bandc-distric-list").css("display", "block");
});
$("#js-bandc-wards").on("click", function () {
    if (
        $("#js-bandc-province-list").css("display") == "block" ||
        $("#js-bandc-distric-list").css("display") == "block"
    ) {
        $("#js-bandc-province-list").css("display", "none");
        $("#js-bandc-distric-list").css("display", "none");
    }
    $("#js-bandc-wards-list").css("display", "block");
});

$('#js-bandc-province-list>span').on('click',function (){
    var value = $(this).text();
    var code = $(this).data('key');
    $('#js-bandc-province').val(value);
    $('#js-bandc-province-list').css("display","none");
    $('#js-bandc-province').attr('data-id',code);
    $('#js-bandc-province').data('id',code);
    jQuery.ajax({
        url:"/ajax/city?code="+code, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            $('#js-bandc-distric-list').find('span').remove();
            for (let i = 0; i < jsonResult.length; i++) {
                $('#js-bandc-distric-list').append("<span data-key="+jsonResult[i]['code']+">"+jsonResult[i]['name']+"</span>");
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
});
$('#js-bandc-distric-list').on('click','span',function (){
    var value = $(this).text();
    var code = $(this).data('key');
    var keyCity = $('#js-bandc-province').data('id');
    $('#js-bandc-distric').val(value);
    $('#js-bandc-distric-list').css("display","none");
    jQuery.ajax({
        url:"/ajax/wards?code="+code+"&city="+keyCity, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            $('#js-bandc-wards-list').find('span').remove();
            for (let i = 0; i < jsonResult.length; i++) {
                $('#js-bandc-wards-list').append("<span data-key="+jsonResult[i]['code']+">"+jsonResult[i]['name']+"</span>");
            }
            $('#js-bandc-wards-list>span').on('click',function (){
                var value = $(this).text();
                $('#js-bandc-wards').val(value);
                $('#js-bandc-wards-list').css("display","none");
            })
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
})

$('.edit-address').on('click',function (){
    if ($("#add-address").hasClass("hide")) {
        $("#add-address").removeClass("hide").addClass("show");
    }
    var idAddress = $(this).data('id');
    jQuery.ajax({
        url:"/ajax/getAddress?idAddress="+idAddress, //->action
        type: "post",
        contentType: "application/json",
        dataType: "json", // kieu du lieu tra ve tu controller la json
        success: function(jsonResult) {
            console.log(jsonResult.address)
            var addressStr = jsonResult.address.address;
            var addressDetail = addressStr.split(', ')[0];
            var  province = addressStr.split(', ')[3];
            var  distric = addressStr.split(', ')[2];
            var  ward = addressStr.split(', ')[1];
            $('#full_name').val(jsonResult.address.name);
            $('#phone_number').val(jsonResult.address.phone);
            $('#id-address').val(jsonResult.address.id);
            console.log(jsonResult.address.id)
            $('#js-bandc-province').val(province);
            $('#js-bandc-distric').val(distric);
            $('#js-bandc-wards').val(ward);
            $('#addressDetail').val(addressDetail);
            if (jsonResult.address.setAsDefault == true){
                $('#checkBox-default').attr('checked','checked');
            }
            $('.title-header-form').text("Sửa thổng tin địa chỉ");
            $('.button-add-new-address').text("Sửa Địa Chỉ");
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
})

$(".delete-address").on('click',function(){
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
            $('.item-add[data-address='+jsonResult.data.id+']').remove();
            $(".popup-delate-address-cart").removeClass("active");
            $(".bg-h-pop").removeClass("active");
            if ($('.add-new-address-event').hasClass('remove')){
                $('.add-new-address-event').removeClass('remove');
            }
        },
        error: function(jqXhr, textStatus, errorMessage) {
            alert(errorMessage);
        }
    });
});

$('#form-checkout-submit').one('submit',function (e){
    e.preventDefault();
    if ($('#id-address-select').val() == null || $('#id-address-select').val().length ==0 || $('#id-address-select').val()==''){
        alert("Hãy chọn địa chỉ");
    }else {
        $(this).submit();
    }
})
$('.btn-voucher').on('click',function (){
    if ($('#voucher').val() != null && $('#voucher').val() != ''){
        var voucherValue = $('#voucher').val();
        jQuery.ajax({
            url:"/ajax/voucher?voucher="+voucherValue, //->action
            type: "post",
            contentType: "application/json",
            dataType: "json", // kieu du lieu tra ve tu controller la json
            success: function(jsonResult) {

            },
            error: function(jqXhr, textStatus, errorMessage) {
                alert(errorMessage);
            }
        });
    }
})