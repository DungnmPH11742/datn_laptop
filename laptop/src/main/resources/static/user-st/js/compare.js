let type_of_item = 0;

let list_compare = [];

//Thêm vào so sánh
function addCompareProduct(sku) {
    $.ajax({
        type: "Get",
        url: "/add-compare",
        data: {'sku': sku},
        success: function (data) {
            console.log(data);
            // if(data.key !== '003'){
                getCompareProduct();
            // }
            $("#notification-compare").append(`<div class="alert ${data.key === '000'? 'alert-success':'alert-warning'} alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>${data.key === '000'? 'Thành công':'Thất bại' }!</strong>
                                                            <p>${data.message}</p>
                                                       </div>`);
            setTimeout(function() {
                $('.alert').fadeOut('slow');}, 3000
            );
        },
        error: function (e) {
            console.log(e);
        }
    });
}


//Gọi danh sách so sánh
function getCompareProduct() {
    $.ajax({
        type: "Get",
        url: "/get-compare",
        success: function (data) {
            list_compare = data;
            console.log(data);
            type_of_item = data[0]?.typeOfItem != null ? data[0]?.typeOfItem : 0;
            let list_product = '';
            for (let i = 0; i < data.length; i++) {
                if (data[i] != null) {
                    list_product = list_product + `
                        <div class="col-1-compare col-sm-5">
                            <div class="col-sm-6 d-flex">
                            <img class="col-sm-10 align-self-center p" id="p${i + 1}"
                                 src="${data[i]?.productsDetail.imgUrl}"
                                 alt="">
                            <i class="fa fa-times-circle" onclick="remove('p${i + 1}')"></i>
                        </div>
                        <div class="col-sm-6 p" id="p${i + 1}_dt">
                            <h5>${data[i]?.name}</h5>
                            ${data[i]?.productsDetail?.saleProduct != null ? `
                            <p style="color: red;">${(data[i]?.productsDetail?.price - data[i]?.productsDetail?.price * data[i]?.productsDetail?.saleProduct?.promotion / 100).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")} đ</p>` : 
                                '<span>${data[i]?.productsDetail?.price.toString().replace(/\\B(?=(\\d{3})+(?!\\d))/g, ".")} đ</span>'}
                            ${ data[i]?.productsDetail?.saleProduct != null ? `
                            <s>${data[i]?.productsDetail?.price.toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")} đ</s>` : '' }
                            </div>
                        </div>`;
                } else {
                    list_product = list_product +
                        `<div class="col-sm-5" id="p2_compare">
                                <div class="col-sm-12 d-flex">
                                    <button class="btn" onclick="showBoxSearch()">Thêm so sánh</button>
                                </div>
                            </div>`
                }
            }

            //
            let html =
                `<nav role="navigation" id="nav-compare" class="navbar navbar-white navbar-embossed navbar-lg navbar-fixed-top d-compera">
                <i onclick="closeCompare()" class="fa fa-times col-sm-1 float-right"></i>
                <div class="col-sm-11 float-right">
                    <div class="d-content">
                        ${list_product}
                        <div class="col-sm-2">
                            <a class="btn btn-primary" href="/compare">So sánh ngay</a>
                            <br>
                            <a onclick="removeAllCompare()" style="cursor: pointer;">Xóa tất cả sản phẩm</a>
                        </div>
                    </div>
                </div>
            </nav>`;
            $('#compare').html('');
            $('#compare').append(html);
        },
        error: function (e) {
            console.log(e);
        }
    })
}


const compareNow = () => {
    let img, td_pducer, td_name, td_color, td_cpu, td_hard, td_cam, td_vga, td_mass,
        td_redate, td_hdh, td_pow, td_scanf, td_bpanel, td_contrast, td_retime,
        td_vsreen, td_dsize,td_sratio, td_bright, td_bh, td_pk;
    for (let i = 0; i < list_compare.length; i++) {
        img += `
                    <div class="col-sm-6 d-flex">
                        <img class="col-sm-11 align-self-center" src="${data[i].imgUrl}" alt="">
                        <i class="fa fa-times-circle"></i>
                    </div>`;
        td_pducer += `<td>${list_compare[i]?.productsDetail.producer}</td>`;
    }
    let body = `
            <div class=" category-tab shop-details-tab">
                <h4>So sánh</h4>
                <div class="col-sm-11 float-right">
                    ${img}
                </div>
                <table class="table">
                    <tbody class="compare-tab">

                    </tbody>
                </table>
            </div>`
    $('#body_modal_compare').append(body);
}

const remove = (id) => {
    $.ajax({
        type: "Get",
        url: "/remove-compare",
        data: {'key': id},
        success: function (data) {
            console.log(data);
            getCompareProduct();
            $('#compare_button').attr("disabled", true);
        },
        error: function () {
            alert("Lỗi rồi")
        }
    })
}

//Thêm vào so sánh
function findByNameAndType(name, type) {
    $.ajax({
        type: "Get",
        url: "/find-by",
        data: {'name': name, 'type': type},
        success: function (data) {
            console.log(data);
            let tcontent = '';
            for (let i = 0; i < data.length; i++) {
                tcontent += `
                            <tr>
                                <th class="col-sm-3">
                                    <img class="col-sm-12 align-self-center p" id="p1"
                                        src="${data[i].imgUrl}"
                                        alt="">
                                </th>
                                <th class="col-sm-6">
                                    <h5>${data[i]?.name}</h5>
                                    <p style="color: red;">${(data[i]?.outputPrice - data[i]?.outputPrice * data[i]?.saleProduct?.promotion / 100).toString().replace(/\B(?=(\d{3})+(?!\d))/g, ".")} đ</p>
                                </th>
                                <th class="col-sm-3">
                                    <a style="cursor: pointer" onclick="addCompareProduct('${data[i]?.id}')">Thêm so sánh</a>
                                </th>
                            </tr>`;
                console.log(``)
            }
            $('#searchProducts').html('');
            $('#searchProducts').append(tcontent);
        },
        error: function () {
            alert("Lỗi rồi")
        }
    });
}

const removeAllCompare = () => {
    remove("p1");
    remove("p2");
    type_of_item = 0;
}

const closeCompare = () => {
    document.getElementById('nav-compare').style.animation = 'change_height_default ease .3s forwards';
}

const showBoxSearch = () => {
    $('#p2_compare').html('');
    $('#p2_compare').append(`
				<div class="col-sm-12 d-flex">
					<input class="col-sm-2 form-control" id="search-p-compare" oninput="onchangSearch()" type="text" placeholder="Search">
					<button class="col-sm-2 btn" onclick="cancelBoxSearch()">Hủy</button>
				</div>
                <table class="table">
                    <thead class="thead-light" id="searchProducts">

                    </thead>
                    <tbody id="searchProducts">

                    </tbody>
                </table>`);

    document.getElementById('nav-compare').style.animation = 'change_height_max ease .3s forwards';
    findByNameAndType('', type_of_item);
}

const cancelBoxSearch = () => {
    $('#p2_compare').html('');
    document.getElementById('nav-compare').style.animation = 'change_height_min ease .3s forwards';
    $('#p2_compare').append(`<button class="btn" onclick="showBoxSearch()">Thêm so sánh</button>`);
}


const onchangSearch = () => {
    let name = $('#search-p-compare').val();
    findByNameAndType(name, type_of_item);
}