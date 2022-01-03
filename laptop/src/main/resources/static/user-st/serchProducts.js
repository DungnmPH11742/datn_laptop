$(document).ready(function () {

    updatelisting(1);
    getCate();

    function updatelisting(currentPage) {
        this.event.preventDefault();
        var parameter = window.location.search;
        var urlHostName = window.location.pathname;
        var url = "http://localhost:8080/api/page/" + currentPage + urlHostName + parameter;
        var sale = ($('#checkboxSale').is(":checked")) ? "true" : "false";
        if (sale === "true") {
            $("input:checkbox").attr("checked", false);
            $("#checkboxSale").prop("checked", true);
        }

        var min = new Array();
        var max = new Array();

        var minM = new Array();
        var maxM = new Array();

        $('[id="checkbox2"]').each(function (i, e) {
            if ($(e).is(':checked')) {

                if (e.value === 'duoi_10_trieu') {
                    min.push(0)
                    max.push(10000000)
                }
                if (e.value === ('10_20_trieu')) {
                    min.push(10000000)
                    max.push(20000000)
                }
                if (e.value === ('20_30_trieu')) {
                    min.push(20000000)
                    max.push(30000000)
                }
                if (e.value === ('30_40_trieu')) {
                    min.push(30000000)
                    max.push(40000000)
                }
                if (e.value === ('40_50_trieu')) {
                    min.push(40000000)
                    max.push(50000000)
                }
                if (e.value === ('tren_50_trieu')) {
                    min.push(50000000)
                    max.push(1000000000)
                }

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
        })

        $('[id="checkbox10"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                if (e.value === 'duoi_1kg') {
                    minM.push(0)
                    maxM.push(1)
                }
                if (e.value === ('1_1.5kg')) {
                    minM.push(1)
                    maxM.push(1.5)
                }
                if (e.value === ('1.5_2kg')) {
                    minM.push(1.5)
                    maxM.push(2)
                }
                if (e.value === ('2_2.5kg')) {
                    minM.push(2)
                    maxM.push(2.5)
                }
                if (e.value === ('2.5_3.5kg')) {
                    minM.push(2.5)
                    maxM.push(3.5)
                }
                if (e.value === ('tren_3.5kg')) {
                    minM.push(3.5)
                    maxM.push(99)
                }
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
        })

        var category = get_filter('checkbox1').toString();
        var minPrice = min.toString();
        var maxPrice = max.toString();
        var cpu = get_filterOne('checkbox3').toString();
        var ram = get_filterOne('checkbox4').toString();
        var displaySize = get_filterOne('checkbox5').toString();
        var screenRatio = get_filterOne('checkbox6').toString();
        var scanFrequency = get_filterOne('checkbox7').toString();
        var resolution = get_filterOne('checkbox8').toString();
        var vga = get_filterOne('checkbox9').toString();
        var minMass = minM.toString();
        var maxMass = maxM.toString();
        var sort1 = get_filterOne('checkbox0').toString();
        var data = {
            sort: sort1,
            category: category,
            minPrice: (minPrice != null || !minPrice === ' ') ? minPrice : 0,
            maxPrice: (maxPrice != null || !maxPrice === ' ') ? maxPrice : 10000000000,
            cpu: cpu,
            ram: ram,
            sale: sale,
            displaySize: displaySize,
            screenRatio: screenRatio,
            scanFrequency: scanFrequency,
            resolution: resolution,
            vga: vga,
            minMass: (minMass != null || !minMass === ' ') ? minMass : 0,
            maxMass: (maxMass != null || !maxMass === ' ') ? maxMass : 99,

        };
        $.get(
            url, data,
            function (result) {
                reset();

                var totalElements = result.totalElements;
                var id = result.id;
                if (id === 'MN01') {
                    $('#cpu').remove();
                    $('#cdh').remove();
                    $('#ram').remove();
                }
                if (id === 'PC01') {
                    $('#dpg').remove();
                    $('#tsq').remove();
                    $('#tlmh').remove();
                }
                $('#totalElements').html('Đã thấy ' + totalElements + ' sản phẩm');
                if (result.data.length === 0) {
                    $('#emty').html("<h1 style='text-align: center'>Không có sản phẩm nào vui lòng chọn lại</h1>");
                } else {
                    $('#emty').html('');
                    $.each(result.data, function (i, p) {
                        // if(dateCheck(p.saleProduct.dateOn,p.saleProduct.dateOff, new Date().toISOString().slice(0, 10))){
                        //     alert(p.name);}
                        // else{
                        //     alert("Not Availed");
                        // }
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


                        content += '                <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + p.id + '\')">\n' +
                            '                    <i class="fa fa-shopping-cart"></i>\n' +
                            '                </a>\n' +
                            '                <a href="#" class="btn add-to-cart" onClick="addCompareProduct(\''+p.id+ '\')">\n' +
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
                            current: currentPage,
                            length: 24,
                            size: 2,
                            click: function (options, $target) {
                                $('#formFilter').html(' ');
                                $('.pagination li').remove();
                                updatelisting(options.current);
                            }
                        }).hide().fadeIn(50);
                        ;
                    })

                }
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

    function reset() {
        $('#formFilter').html(' ').fadeOut(0);
        $('.pagination li').remove().fadeOut(0);
    }

    function seeMore(id) {
        this.event.preventDefault()
        var href = "http://localhost:8080/api/findTrademark/" + id;
        $.get(href, function (result) {

            $.each(result, function (i, category) {
                if (i < 5) {
                    var sp = '  <label class="check-list">' +
                        ' <span class="text">' + category.name + '</span>\n' +
                        '                                        <input class="br" type="checkbox" id="checkbox1" value="' + category.id + '">\n' +
                        '                                        <span class="checkmark"></span>' +
                        '</label>';
                    $("#trademark").append(sp);

                }


            })

            var seeMore = '  <a class="view-more" id="seeMore">\n' +
                '                                        Xem thêm thương hiệu\n' +
                '                                    </a>';
            if (result.length > 5) {
                $("#trademark").append(seeMore);
            }
            $("#seeMore").on('click', function () {
                var href = "http://localhost:8080/api/findTrademark/" + id;
                $.get(href, function (result) {
                    $.each(result, function (i, category) {
                        if (i >= 5) {
                            var sp = '  <label class="check-list">' +
                                ' <span class="text">' + category.name + '</span>\n' +
                                '                                        <input class="br" type="checkbox" id="checkbox1" value="' + category.id + '">\n' +
                                '                                        <span class="checkmark"></span>' +
                                '</label>';

                            $("#trademark").append(sp);
                            $("#seeMore").hide();
                            $("#seeMore").removeAttr('id')
                        }

                    })
                    const checkbox = $("input:checkbox");

                    checkbox.on('change', function (event) {
                        event.preventDefault();
                        updatelisting(1);
                    });
                })
            })

            const checkbox = $("input:checkbox");
            checkbox.on('change', function (event) {
                event.preventDefault();
                updatelisting(1);
            });

        })
    }

    function getCate() {
        this.event.preventDefault();
        var hostName = window.location.pathname;
        var href = "http://localhost:8080/api" + hostName;
        $.get(href, function (result) {
            seeMore(result);
        })
    }

    filterPriceHome();

    function filterPriceHome() {
        let price = parseInt(window.location.search.slice(1).split("&")[0].split("=")[1]);
        reset();
        if (price === 6) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="tren_50_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);

        }
        if (price === 5) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="40_50_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);
        }
        if (price === 4) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="30_40_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);

        }
        if (price === 3) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="20_30_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);
        }
        if (price === 2) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="10_20_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);
        }
        if (price === 1) {
            $('#khoang').trigger('click');
            $('input[type=checkbox][value="duoi_10_trieu"]').trigger('click');
            reset();
            updatelisting(1);
            updatelisting(1);
        }

        //  let target = $('#checkbox2').filter('[value="' + '20_30_trieu' + '"]');

    }


    $("#delFilter").on('click', function (event) {
        event.preventDefault();
        $("input:checkbox").attr("checked", false);
        updatelisting(1);

    })
    $(".sortAsc").on('click', function (event) {
        event.preventDefault();
        $('.desc').prop("checked", false);
        $('.asc').prop("checked", true);
        updatelisting(1);

    })

    $(".sortDesc").on('click', function (event) {
        event.preventDefault();
        $('.asc').prop("checked", false);
        $('.desc').prop("checked", true);
        updatelisting(1);


    })


    function get_filter(id_name) {
        var filter = new Array();

        $('[id="' + id_name + '"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                filter.push($(this).val());
            }

        });
        return filter;
    }

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
    }
})

function addToCart(id) {
    let data = {
        'idProduct': id,
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

