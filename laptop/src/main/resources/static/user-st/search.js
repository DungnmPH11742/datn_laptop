$(document).ready(function () {
    var hash = window.location.search.substring(1);
    findProduct();

    function findProduct() {
        if (hash !== null || hash !== "") {
            console.log(hash)
            findName(1, hash);
        }
    }

    function reset() {
        $('#formFilter').html(' ').fadeOut(0);
        $('.pagination li').remove().fadeOut(0);
    }

    $(".sortAsc").on('click', function (event) {
        event.preventDefault();
        $('.desc').prop("checked", false);
        $('.asc').prop("checked", true);
        findName(1, hash);

    })

    $(".sortDesc").on('click', function (event) {
        event.preventDefault();
        $('.asc').prop("checked", false);
        $('.desc').prop("checked", true);

        findName(1, hash);


    });


    function get_filterOne(id_name) {
        var filter = new Array();

        $('[id="' + id_name + '"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                filter.push($(this).val());
            }
            $("input:checkbox").on('click', function () {
                var $box = $(this);
                if ($box.is(":checked")) {
                    var group = "input:checkbox[name='" + $box.attr("name") + "']";
                    $(group).prop("checked", false);
                    $box.prop("checked", true);
                } else {
                    $box.prop("checked", false);
                }
            });
        });
        return filter;
    };
    function findName(numberPage, search) {
        var sort1 = get_filterOne('checkbox0').toString();
        var search = search;
        var href = "http://localhost:8080/api/findId/" + numberPage + "?" + search;
        var data = {
            sort: sort1
        }
        $.get(href, data, function (result) {
            reset();
            $('#emty').html('')
            $.each(result.content, function (i, p) {
                var content = ' ';
                content += '<div class="col-sm-3">\n' +
                    '    <div class="product-image-wrapper">\n' +
                    '        <a href="/view?id=' + p.id + '"   class="single-products">\n' +
                    '            <div class="productinfo">\n' +
                    '                <img class="anhSP" src="' + p.imgUrl + '" alt="">\n';

                if (p.saleProduct !== null) {
                    if (dateCheck(p.saleProduct.dateOn, p.saleProduct.dateOff, new Date().toISOString().slice(0, 10))) {
                        if (p.saleProduct.status.toString() === 'true') {
                            content += '                <div class="content--product">\n' +
                                '                    <b >' + p.name + '</b>\n' +
                                '                    <h4 class="price">' +
                                (p.outputPrice * (100 - p.saleProduct.promotion) / 100).toLocaleString('vi', {
                                    style: 'currency',
                                    currency: 'VND'
                                }) +
                                '</h4>\n' +
                                '                    <s>' + (p.outputPrice).toLocaleString('vi', {
                                    style: 'currency',
                                    currency: 'VND'
                                }) + '</s>\n' +
                                '                </div>\n' +
                                '                <div>\n' +
                                '                    <i class="fa fa-gift"></i><span style="margin-left: 5px;">Giảm giá</span>\n' +
                                '                </div>\n';
                        }
                        else {
                            content += '                <div class="content--product">\n' +
                                '                    <b >' + p.name + '</b>\n' +
                                '                    <h4 class="price">' +
                                (p.outputPrice).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                                '</h4>\n' +
                                '                </div>\n';
                        }


                    } else {
                        content += '                <div class="content--product">\n' +
                            '                    <b >' + p.name + '</b>\n' +
                            '                    <h4 class="price">' +
                            (p.outputPrice).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                            '</h4>\n' +
                            '                </div>\n';
                    }


                } else {
                    {
                        content += '                <div class="content--product">\n' +
                            '                    <b >' + p.name + '</b>\n' +
                            '                    <h4 class="price">' +
                            (p.outputPrice).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                            '</h4>\n' +

                            '                </div>\n';
                    }

                }


                content += '                <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + p.sku + '\')">\n' +
                    '                    <i class="fa fa-shopping-cart"></i>\n' +
                    '                </a>\n' +
                    '                <a href="#" class="btn add-to-cart"  onClick="addCompareProduct(\'' + p.id + '\')">\n' +
                    '                    <i class="fa fa-plus"></i>\n' +
                    '                </a>\n' +
                    '            </div>\n' +
                    '\n' +
                    '        </a>\n' +
                    '    </div>\n' +
                    '</div>';

                $('#formFilter').append(content).fadeIn(50);
                $('#pagination-2').pagination({
                    total: result.totalPages,
                    current: numberPage,
                    length: 24,
                    size: 2,
                    click: function (options, $target) {
                        reset();
                        findName(options.current, search);
                    }
                }).fadeIn(50);
            });


        })

    }
    function dateCheck(from, to, check) {

        var fDate, lDate, cDate;
        fDate = Date.parse(from);
        lDate = Date.parse(to);
        cDate = Date.parse(check);

        if ((cDate <= lDate && cDate >= fDate)) {
            return true;
        }
        return false;
    }

})

function addToCart(sku) {
    let data = {
        'sku': sku,
        'quantityProduct': 1,
    };
    $.ajax({
        type: "POST",
        url: "/addToCart",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "JSON",
        success: function (data) {
            $("#notification-compare").append(`<div class="alert alert-success alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>Thành công!</strong>
                                                            <p>Thêm vào giỏ hàng thành công, đang có ${data.totalItem} sản phẩm</p>
                                                       </div>`);
            setTimeout(function () {
                    $('.alert').fadeOut('slow');
                }, 3000
            );
        }, error: function () {
            alert("Lỗi addToCart");
        }
    });

}