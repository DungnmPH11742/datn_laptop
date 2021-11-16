//}


$(document).ready(function () {


    updatelisting(1);
    $("#delFilter").on('click', function (event) {
        event.preventDefault();
        $('#formFilter').html(' ');
        $('.pagination li').remove();
        $("input:checkbox").attr("checked", false);
        updatelisting(1);

    })
    $(".sortAsc").on('click', function (event) {
        event.preventDefault();
        $('#formFilter').html(' ');
        $('.pagination li').remove();
        $('.desc').prop("checked", false);
        $('.asc').prop("checked", true);
        updatelisting(1);

    })

    $(".sortDesc").on('click', function (event) {
        event.preventDefault();
        $('#formFilter').html(' ').fadeIn();
        $('.pagination li').remove().fadeIn();
        ;
        $('.asc').prop("checked", false);
        $('.desc').prop("checked", true);
        updatelisting(1).fadeOut();
        ;

    })
    const checkbox = $("input:checkbox");

    checkbox.on('change', function (event) {
        event.preventDefault();
        $('#formFilter').html(' ');
        $('.pagination li').remove();
        updatelisting(1);
    });

    function updatelisting(currentPage) {
        this.event.preventDefault()
        var url = "http://localhost:8080/api/filter?numberPage=" + currentPage;
        var c = new Array();
        var d = new Array();
        var a = new Array();
        var f = new Array();
        var min = new Array();
        var max = new Array();

        var size = new Array();

        var raito = new Array();

        var scan = new Array();

        var reso = new Array();

        var minM = new Array();
        var maxM = new Array();
        var vgaCheck = new Array();
        var sort0 = new Array();
        $('[id="checkbox0"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                sort0.push(e.value);
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
        $('[id="checkbox1"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                c.push(e.value);
            }
        })

        $('[id="checkbox2"]').each(function (i, e) {


            if ($(e).is(':checked')) {

                d.push(e.value);
                console.log(e.value)

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


        $('[id="checkbox3"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                a.push(e.value);
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
        $('[id="checkbox4"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                f.push(e.value);
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
        $('[id="checkbox5"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                size.push(e.value);
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
        $('[id="checkbox6"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                raito.push(e.value);
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
        $('[id="checkbox7"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                scan.push(e.value);
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
        $('[id="checkbox8"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                reso.push(e.value);
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
        $('[id="checkbox9"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                vgaCheck.push(e.value);
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
                d.push(e.value);
                console.log(e.value)
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

        var sale = ($('#checkboxSale').is(":checked")) ? "true" : "false";

        var category = c.toString();
        var cateParent = $("#cate_item").val();
        var minPrice = min.toString();
        var maxPrice = max.toString();
        var cpu = a.toString();
        var ram = f.toString();
        var displaySize = size.toString();
        var screenRatio = raito.toString();
        var scanFrequency = scan.toString();
        var resolution = reso.toString()

        var minMass = minM.toString();
        var maxMass = maxM.toString();
        var vga = vgaCheck.toString();
        var sort1 = sort0.toString();

        var data = {
            sort: sort1,
            currentPage: currentPage,
            category: category,
            minPrice: (minPrice != null || !minPrice === ' ') ? minPrice : 0,
            maxPrice: (maxPrice != null || !maxPrice === ' ') ? maxPrice : 10000000000,
            cpu: cpu,
            ram: ram,
            cateParent: cateParent,
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
            url,
            data,
            function (result) {

                var totalElements = result.totalElements;
                $('#totalElements').html('Đã thấy ' + totalElements + ' sản phẩm')
                if (result.content.length === 0) {
                    $('#emty').html("<h1 style='text-align: center'>Không có sản phẩm nào vui lòng chọn lại</h1>")
                } else {
                    $('#emty').html('')

                    $('.mainSca').remove()
                    $.each(result.content, function (i, product) {

                        var content = '<div class="col-sm-3">\n' +
                            '    <div class="product-image-wrapper">\n' +
                            '        <a   class="single-products">\n' +
                            '            <div class="productinfo">\n' +
                            '                <img class="anhSP" src="/user-st/images/product/' + product.id + '.jpg" alt="">\n' +
                            '                <div class="content--product">\n' +
                            '                    <b >' + product.name + '</b>\n' +
                            '                    <h4 class="price">' +
                            (product.outputPrice).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                            '</h4>\n' +
                            '                    <s>56.750.000 đ</s>\n' +
                            '                </div>\n' +
                            '                <div>\n' +
                            '                    <i class="fa fa-gift"></i><span style="margin-left: 5px;">Giảm giá</span>\n' +
                            '                </div>\n' +
                            '                <a href="#" class="btn btn-default add-to-cart">\n' +
                            '                    <i class="fa fa-shopping-cart"></i>\n' +
                            '                </a>\n' +
                            '                <a href="#" class="btn add-to-cart">\n' +
                            '                    <i class="fa fa-plus"></i>\n' +
                            '                </a>\n' +
                            '            </div>\n' +
                            '\n' +
                            '        </a>\n' +
                            '    </div>\n' +
                            '</div>';

                        $('#formFilter').append(content);
                        $('#pagination-2').pagination({
                            total: result.totalPages,
                            current: currentPage,
                            length: 12,
                            size: 2,
                            click: function (options, $target) {
                                $('#formFilter').html(' ').fadeIn();
                                $('.pagination li').remove().fadeIn();
                                ;
                                updatelisting(options.current).fadeOut();
                            }
                        });
                    })

                }

            })


    }


    findName(1)
    var timeout = null;

    // Sự kiện keyup
    function findName(numberPage) {
        $(document).on('keyup', '#search', function (event) {
            event.preventDefault();
            clearTimeout(timeout);
            timeout = setTimeout(function () {
                var data = {
                    name: $('#search').val(),
                    cateParent: $("#cate_item").val(),
                    numberPage: numberPage
                };
                var href = "http://localhost:8080/api/findId";
                $.get(href, data, function (result) {
                    $('#formFilter').html(' ').fadeIn();
                    $('.pagination li').remove().fadeIn();
                    var totalElements = result.totalElements;
                    $('#totalElements').html('Đã thấy ' + totalElements + ' sản phẩm')
                    if (result.content.length === 0) {
                        $('#emty').html("<h1 style='text-align: center'>Không có sản phẩm nào vui lòng chọn lại</h1>")
                    } else {
                        $('#emty').html('')

                        $('.mainSca').remove()
                        $.each(result.content, function (i, product) {
                            var totalElements = result.totalElements;
                            var content = '<div class="col-sm-3">\n' +
                                '    <div class="product-image-wrapper">\n' +
                                '        <a   class="single-products">\n' +
                                '            <div class="productinfo">\n' +
                                '                <img class="anhSP" src="/user-st/images/product/' + product.id + '.jpg" alt="">\n' +
                                '                <div class="content--product">\n' +
                                '                    <b >' + product.name + '</b>\n' +
                                '                    <h4 class="price">' +
                                (product.outputPrice).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                                '</h4>\n' +
                                '                    <s>56.750.000 đ</s>\n' +
                                '                </div>\n' +
                                '                <div>\n' +
                                '                    <i class="fa fa-gift"></i><span style="margin-left: 5px;">Giảm giá</span>\n' +
                                '                </div>\n' +
                                '                <a href="#" class="btn btn-default add-to-cart">\n' +
                                '                    <i class="fa fa-shopping-cart"></i>\n' +
                                '                </a>\n' +
                                '                <a href="#" class="btn add-to-cart">\n' +
                                '                    <i class="fa fa-plus"></i>\n' +
                                '                </a>\n' +
                                '            </div>\n' +
                                '\n' +
                                '        </a>\n' +
                                '    </div>\n' +
                                '</div>';

                            $('#formFilter').append(content);
                            $('#example-2').pagination({
                                total: result.totalPages,
                                current: numberPage,
                                length: 12,
                                size: 2,
                                click: function (options, $target) {
                                    $('#formFilter').html(' ').fadeIn();
                                    $('.pagination li').remove().fadeIn();
                                    ;
                                    updatelisting(options.current).fadeOut();
                                }
                            });

                        })
                    }


                })


            }, 500);
        })
    }
})