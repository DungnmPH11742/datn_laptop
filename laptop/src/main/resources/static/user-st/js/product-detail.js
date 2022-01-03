function addToCart(id) {
    let data = {
        'idProduct': id,
        'quantityProduct': 1,
    };
    $.ajax({
        type: "PUT",
        url: "/addToCart",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "JSON",
        success: function (data) {

            $('#hienThiSoLuong').html('');
            $('#hienThiSoLuong').append(`${data.totalItem}`);

            $("#notification-compare").append(`<div class="alert alert-success alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>Thành công!</strong>
                                                            <p>Thêm vào giỏ hàng thành công, đang có ${data.totalItem} sản phẩm</p>
                                                       </div>`);
            setTimeout(function () {
                    $('.alert').fadeOut(`${data.message}`);
                }, 4000
            );
        }, error: function () {
            alert("Lỗi addToCart");
        }
    });

}

//    Lựa chọn cấu hình
function selectOption(sku) {
    console.log($(location).attr('href'));
    // window.location.replace(url);
    /*$.ajax({
        type: "GET",
        url: `/admin/find-by-sku/${sku}`,
        contentType: "application/json",
        dataType: "JSON",
        success: function (data) {
            console.log(data);
            $('.flex-chk').prop("checked", false);
            $(`#${sku}-chk`).prop("checked", true);
            $('.option-product').removeClass("active-option");
            $(`#${sku}-div`).addClass("active-option");
            $('#img-note').attr("src", data.productsDetail.imgUrl);
            $('.dt-ing').attr("src", data.productsDetail.imgUrl);
            $('.link-add-to-cart').attr('onClick', `addToCart(${sku})`);
            $('.link-add-to-compare').attr('onClick', `addCompareProduct(${sku})`);
            $('#information-short-product').html('');
            $('#information-short-product').append(generateInformationShort(data));
        }, error: function () {
            alert("Lỗi chọn cấu hình");
        }
    });*/
}

const generateInformationShort = (value) => {
    const data = value.productsDetail;
    //Mô tả chung
    const information =
        `<span>
                <span>
                        ${data.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")} đ
                </span>
                <div>
                    <label>Số lượng:</label>
                    <input type="number" value="1" min="1" max="${data.quantity}" />
                </div>
            </span>
            <br>`
    //Mô tả laptop
    const informationLaptop =
        `${data.cpu == '' || data.cpu == null? '' : `<p><b>Vi xử lý: </b>${data.cpu}</p>`}
            ${data.ram == '' || data.ram == null ? '' : `<p><b>RAM:</b>${data.ram}</p>`}
            ${data.hardDrive == '' || data.hardDrive == null ? '' : `<p><b>Ổ cứng: </b>${data.hardDrive}</p>`}
            ${data.vga == '' || data.vga == null ? '' : `<p><b>Card đồ họa: </b> ${data.vga} </p>`}
            ${data.displaySize == '' || data.displaySize == null ? '' : `<p><b>Màn hình: </b> ${data.displaySize} </p>`}
            ${data.os == '' || data.os == null ? '' : `<p><b>Hệ điều hành: </b> ${data.os} </p>`}
            ${data.color == '' || data.color == null ? '' : `<p><b>Màu sắc: </b> ${data.color} </p>`}`;

    //mô tả PC
    const informationPC =
        `${data.cpu == '' || data.cpu == null? '' : `<p><b>Vi xử lý: </b>${data.cpu}</p>`}
            ${data.ram == '' || data.ram == null ? '' : `<p><b>RAM:</b>${data.ram}</p>`}
            ${data.hardDrive == '' || data.hardDrive == null ? '' : `<p><b>Ổ cứng: </b>${data.hardDrive}</p>`}
            ${data.vga == '' || data.vga == null ? '' : `<p><b>Card đồ họa: </b> ${data.vga} </p>`}
            ${data.displaySize == '' || data.displaySize == null ? '' : `<p><b>Màn hình: </b> ${data.displaySize} </p>`}
            ${data.os == '' || data.os == null ? '' : `<p><b>Hệ điều hành: </b> ${data.os} </p>`}
            ${data.color == '' || data.color == null ? '' : `<p><b>Màu sắc: </b> ${data.color} </p>`}
            ${data.power == '' ? '' || data.power == null : `<p><b>Nguồn: </b>${data.power}</p>`}`;

    //mô tả màn hình
    const informationMonitor =
        `${data.displaySize == '' || data.displaySize == null? '' : `<p><b>Kích thước màn hình: </b>${data.displaySize}</p>`}
            ${data.brightness == '' || data.brightness == null ? '' : `<p><b>Độ sáng:</b>${data.brightness}</p>`}
            ${data.contrast == '' || data.contrast == null ? '' : `<p><b>Tỉ lệ tương phản: </b>${data.contrast}</p>`}
            ${data.resolution == '' || data.resolution == null ? '' : `<p><b>Độ phân giải: </b> ${data.resolution} </p>`}
            ${data.responseTime == '' || data.responseTime == null ? '' : `<p><b>Thời gian đáp ứng: </b> ${data.responseTime} </p>`}
            ${data.viewScreenshots == '' || data.viewScreenshots == null ? '' : `<p><b>Góc nhìn: </b> ${data.viewScreenshots} </p>`}`;

    //mô tả trạng thái sản phẩm
    const informationStatus =
        `${data.quantity == '' ? '' || data.quantity == null : `<p><b>Tình trạng: </b>${data.quantity != 0 ? 'Còn hàng' : 'Hết hàng'}</p>`}`;
    return information + ${data.typeOfItem == 'LT01'? informationLaptop : data.typeOfItem == 'PC01'? informationPC : informationMonitor} + informationStatus;
}