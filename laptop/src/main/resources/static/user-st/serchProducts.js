$(document).ready(function () {
    if (!!window.performance && window.performance.navigation.type === 2) {
        window.location.reload();
    }
    var origin = window.location.origin;
    var filterPO = new Array();
    var distinct = new Array();
    filterPriceHome();
    updatelisting(1);
    getCate();

    function removeItem(array, item) {
        for (var i in array) {
            if (array[i] == item) {
                array.splice(i, 1);
                break;
            }
        }
    }

    // function getSearchParams(k){
    //     var p={};
    //     location.search.replace(/[?&]+([^=&]+)=([^&]*)/gi,function(s,k,v){p[k]=v})
    //     return k?p[k]:"";
    // }

    function getSearchParams(name) {
        return (location.search.split(name + '=')[1] || '').split('&')[0];
    }

    function updatelisting(currentPage) {
        this.event.preventDefault();
        var parameter = window.location.search;
        var urlHostName = window.location.pathname;
        var url = origin + "/api/page/" + currentPage + urlHostName + parameter;
        var sale = ($('#checkboxSale').is(":checked")) ? "true" : "false";
        var min = new Array();
        var max = new Array();

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
        })

        var category = get_filter('checkbox1').toString();
        var minPrice = min.toString();
        var maxPrice = max.toString();
        var cpu = get_filter('checkbox3').toString();
        var ram = get_filter('checkbox4').toString();
        var displaySize = get_filter('checkbox5').toString();
        var screenRatio = get_filter('checkbox6').toString();
        var scanFrequency = get_filter('checkbox7').toString();
        var resolution = get_filter('checkbox8').toString();
        var vga = get_filter('checkbox9').toString();
        var sort1 = get_filterOne('checkbox0').toString();
        var search = getSearchParams("search").toString();
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
            name: search,
        };
        $.get(
            url, data,
            function (result) {
                reset();
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
                let sumP = result.data.length;
                $('#totalElements').html('Đã thấy ' + sumP + ' sản phẩm');
                if (parseInt(sumP) === 0 || result.data[0] === null) {
                    $('#emty').html("<h1 style='text-align: center'>Không có sản phẩm nào vui lòng chọn lại</h1>");
                } else {
                    $('#emty').html('');
                    $.each(result.data, function (i, products) {


                        var content = ' ';
                        if (products.saleProduct === null) {
                            content = ' ';
                            content += '<div class="col-sm-3">\n' +
                                '    <div class="product-image-wrapper">\n' +
                                '        <a href="/view/' + products.idProduct + '?sku=' + products.sku + '"   class="single-products">\n' +
                                '            <div class="productinfo">\n' +
                                '                <img class="anhSP" src="' + products.imgUrl + '" alt="">\n';


                            content += '                <div class="content--product">\n' +
                                '                    <b >' + products.nameProduct + '</b>\n' +
                                '                    <h4 class="price">' +
                                (products.price).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                                '</h4>\n' +
                                '                </div>\n';


                            content += '          ' +
                                '      <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + products.sku + '\')">\n' +
                                '                    <i class="fa fa-shopping-cart"></i>\n' +
                                '                </a>\n' +
                                '                <a href="#" class="btn add-to-cart" onClick="addCompareProduct(\'' + products.sku + '\')">\n' +
                                '                    <i class="fa fa-plus"></i>\n' +
                                '                </a>\n' +
                                '            </div>\n' +
                                '\n' +
                                '        </a>\n' +
                                '    </div>\n' +
                                '</div>';

                            $('#formFilter').append(content).fadeIn(50);

                        } else if (products.saleProduct != null) {
                            content = ' ';
                            content += '<div class="col-sm-3">\n' +
                                '    <div class="product-image-wrapper">\n' +
                                '        <a href="/view/' + products.idProduct + '?sku=' + products.sku + '"   class="single-products">\n' +
                                '            <div class="productinfo">\n' +
                                '                <img class="anhSP" src="' + products.imgUrl + '" alt="">\n';

                            if (dateCheck(products.saleProduct.dateOn, products.saleProduct.dateOff, new Date().toISOString().slice(0, 10))) {
                                if (products.saleProduct.status.toString() === 'true') {

                                    content += '                <div class="content--product">\n' +
                                        '                    <b >' + products.nameProduct + '</b>\n' +
                                        '                    <h4 class="price">' +
                                        (products.price * (100 - products.saleProduct.promotion) / 100).toLocaleString('vi', {
                                            style: 'currency',
                                            currency: 'VND'
                                        }) +
                                        '</h4>\n' +
                                        '                    <s>' + (products.price).toLocaleString('vi', {
                                            style: 'currency',
                                            currency: 'VND'
                                        }) + '</s>\n' +
                                        '                </div>\n' +
                                        '                <div>\n' +
                                        '                    <i class="fa fa-gift"></i><span style="margin-left: 5px;">Giảm giá</span>\n' +
                                        '                </div>\n';
                                } else {
                                    content += '                <div class="content--product">\n' +
                                        '                    <b >' + products.price + '</b>\n' +
                                        '                    <h4 class="price">' +
                                        (products.price).toLocaleString('vi', {
                                            style: 'currency',
                                            currency: 'VND'
                                        }) +
                                        '</h4>\n' +
                                        '                </div>\n';
                                }


                            } else {
                                content += '                <div class="content--product">\n' +
                                    '                    <b >' + products.nameProduct + '</b>\n' +
                                    '                    <h4 class="price">' +
                                    (products.price).toLocaleString('vi', {style: 'currency', currency: 'VND'}) +
                                    '</h4>\n' +
                                    '                </div>\n';
                            }

                            content += '          ' +
                                '      <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + products.sku + '\')">\n' +
                                '                    <i class="fa fa-shopping-cart"></i>\n' +
                                '                </a>\n' +
                                '                <a href="#" class="btn add-to-cart" onClick="addCompareProduct(\'' + products.sku + '\')">\n' +
                                '                    <i class="fa fa-plus"></i>\n' +
                                '                </a>\n' +
                                '            </div>\n' +
                                '\n' +
                                '        </a>\n' +
                                '    </div>\n' +
                                '</div>';

                            $('#formFilter').append(content).fadeIn(50);
                        }


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

    function get_unique_values_from_array_object(array) {

        var unique = {};
        var pd;
        var name;
        for (var i in array) {

            if (typeof (unique[array[i]]) == "undefined") {
                if (distinct.indexOf(array[i]) === -1) {
                    pd = '      <li class="flex items-center" id="' + array[i].replace(/\s/g, '') + '"><span id="unique">\n' +
                        '              ' + array[i] + '\n' +
                        '            </span><span id="removeSVG_' + array[i].replace(/\s/g, '') + '" class="cursor-pointer"  ><a   >\n' +
                        '                   <svg xmlns="http://www.w3.org/2000/svg" width="16" height="16" fill="currentColor" \n' +
                        '                        class="bi bi-x"  viewBox="0 0 16 16">\n' +
                        '                         <path d="M4.646 4.646a.5.5 0 0 1 .708 0L8 7.293l2.646-2.647a.5.5 0 0 1 .708.708L8.707 8l2.647 2.646a.5.5 0 0 1-.708.708L8 8.707l-2.646 2.647a.5.5 0 0 1-.708-.708L7.293 8 4.646 5.354a.5.5 0 0 1 0-.708z"/>\n' +
                        '                   </svg></a>\n' +
                        '             </span>\n' +
                        '        </li>';

                    distinct.push(array[i]);
                    name = array[i].replace(/\s/g, '');
                }


            }
            unique[array[i]] = 0;

        }

        $('#filterPD').prepend(pd);
        if (/^\s+$/.test(name))
        {
            name = name.replace(/^\s+|\s+$/g, "");
        }
        $("#removeSVG_" + escapeRegExp(name)).on('click', function (event) {
            event.preventDefault();
            var selectors = Array.from(document.querySelectorAll('span.text'))
                .find(el => el.textContent === $(this).prev().text().toString().trim()).nextElementSibling;
            $(selectors).prop("checked", false);
            console.log(distinct);
            if ($(selectors).is(':checked')) {
                filterPO.push($(this).prev().text())
                get_unique_values_from_array_object(filterPO);

            } else {
                removeItem(distinct, $(selectors).prev().text());
                removeItem(filterPO, $(selectors).prev().text());
                $('#' + escapeRegExp($(selectors).prev().text().replace(/\s/g, ''))).remove();
            }
            updatelisting(1);

        })


    }

    function escapeRegExp(str) {
        var format = /[!@#$%^&*()_+\-=\[\]{};':"\\|,.<>\/?]+/;

        if(format.test(str)){
            return str.replace(/[.:*+?^${}()|[\]\\]/g, "\\$&");
        } else {
            return str;
        }

    }

    function reset() {
        $('#formFilter').html(' ').fadeOut(0);

        $('.pagination li').remove().fadeOut(0);

    }

    function seeMore(id) {
        this.event.preventDefault();

        var href = origin + "/api/findTrademark/" + id;
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
                    const checkbox = $('input:checkbox[id^="checkbox1"]');

                    checkbox.on('change', function (event) {
                        event.preventDefault();
                        updatelisting(1);
                        if ($(this).is(':checked')) {
                            filterPO.push($(this).prev().text())
                            get_unique_values_from_array_object(filterPO);

                        } else {
                            removeItem(distinct, $(this).prev().text());
                            removeItem(filterPO, $(this).prev().text());
                            $('#' + $(this).prev().text().replace(/\s/g, '')).remove();
                        }


                    });
                })
            })

            const checkbox = $('input:checkbox[id^="checkbox1"]');
            checkbox.on('change', function (event) {
                event.preventDefault();
                if ($(this).is(':checked')) {
                    filterPO.push($(this).prev().text())
                    get_unique_values_from_array_object(filterPO);
                } else {
                    removeItem(distinct, $(this).prev().text());
                    removeItem(filterPO, $(this).prev().text());
                    $('#' + $(this).prev().text().replace(/\s/g, '')).remove();
                }
                updatelisting(1);

            });

        })
    }

    function getCate() {
        this.event.preventDefault();
        var hostName = window.location.pathname
        var href = origin + "/api" + hostName;
        $.get(href, function (result) {
            seeMore(result);
        })
    }



    function filterPriceHome() {
        let price = parseInt(window.location.search.slice(1).split("&")[0].split("=")[1]);
        reset();
        if (price === 6) {
            $('#khoang').trigger('click');
            let selectors6 = $('input[type=checkbox][value="tren_50_trieu"]');
            selectors6.trigger('click');
            reset();
            if (selectors6.is(':checked')) {
                filterPO.push(selectors6.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);

            } else {
                removeItem(distinct, selectors6.prev().text());
                removeItem(filterPO, selectors6.prev().text());
                $('#' + selectors6.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }


        }
        if (price === 5) {
            $('#khoang').trigger('click');
            let selectors5 = $('input[type=checkbox][value="40_50_trieu"]');
            selectors5.trigger('click');
            reset();
            if (selectors5.is(':checked')) {
                filterPO.push(selectors5.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);
            } else {
                removeItem(distinct, selectors5.prev().text());
                removeItem(filterPO, selectors5.prev().text());
                $('#' + selectors5.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }
        }

        if (price === 4) {
            $('#khoang').trigger('click');
            let selectors4 = $('input[type=checkbox][value="30_40_trieu"]');
            selectors4.trigger('click');
            reset();

            if (selectors4.is(':checked')) {
                filterPO.push(selectors4.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);
            } else {
                removeItem(filterPO, selectors4.prev().text());
                removeItem(distinct, selectors4.prev().text());
                $('#' + selectors4.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }

        }

        if (price === 3) {
            $('#khoang').trigger('click');
            let selectors3 = $('input[type=checkbox][value="20_30_trieu"]');
            selectors3.trigger('click');
            reset();
            if (selectors3.is(':checked')) {
                filterPO.push(selectors3.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);
            } else {
                removeItem(distinct, selectors3.prev().text());
                removeItem(filterPO, selectors3.prev().text());
                $('#' + selectors3.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }
        }

        if (price === 2) {
            $('#khoang').trigger('click');
            let selectors2 = $('input[type=checkbox][value="10_20_trieu"]');
            selectors2.trigger('click');
            reset();
            if ($(selectors2).is(':checked')) {
                filterPO.push(selectors2.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);
            } else {
                removeItem(distinct, selectors2.prev().text());
                removeItem(filterPO, selectors2.prev().text());
                $('#' + selectors2.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }

        }
        if (price === 1) {
            $('#khoang').trigger('click');
            let selectors1 = $('input[type=checkbox][value="duoi_10_trieu"]');
            selectors1.trigger('click');
            reset();
            if (selectors1.is(':checked')) {
                filterPO.push(selectors1.prev().text())
                get_unique_values_from_array_object(filterPO);
                updatelisting(1);
            } else {
                removeItem(distinct, selectors1.prev().text());
                removeItem(filterPO, selectors1.prev().text());
                $('#' + selectors1.prev().text().replace(/\s/g, '')).remove();
                updatelisting(1);
            }

        }
    }


    $("#delFilter").on('click', function (event) {
        event.preventDefault();
        $("input:checkbox").attr("checked", false);
        distinct.forEach(function (i, idx, array) {
            $('#' + escapeRegExp(i.replace(/\s/g, ''))).remove();
        })
        distinct =  new  Array();
        filterPO =  new  Array();
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

    $(function () {
        $('input:checkbox:not("#checkbox1")').change(function (event) {
            event.preventDefault();
            updatelisting(1);
            if ($(this).is(':checked')) {
                filterPO.push($(this).prev().text())
                get_unique_values_from_array_object(filterPO);
            } else {
                removeItem(filterPO, $(this).prev().text())
                removeItem(distinct, $(this).prev().text())
                $('#' + $(this).prev().text().replace(/\s/g, '')).remove();
            }
        });
    });

    function get_filter(id_name) {
        var filter = new Array();

        $('[id="' + id_name + '"]').each(function (i, e) {
            if ($(e).is(':checked')) {
                filter.push($(this).val());
                filterPO.push($(this).prev().text())
            } else {
                removeItem(filterPO, $(this).prev().text())
                removeItem(filterPO, $(this).prev().text())
                removeItem(distinct, $(this).prev().text())
                $('#' + escapeRegExp($(this).prev().text().replace(/\s/g, ''))).remove();
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

function addToCart(sku) {
    let data = {
        'sku': sku,
        'quantityProduct': 1,
    };
    $.ajax({
        type: "PUT",
        url: "/addToCart",
        contentType: "application/json",
        data: JSON.stringify(data),
        dataType: "JSON",
        success: function (data) {

            if(`${data.message}` == 'maxPrice'){
                $("#notification-compare").append(`<div class="alert alert-warning alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>Thất bại</strong>
                                                            <p>Tổng tiền không vượt quá 150.000.000đ</p>
                                                       </div>`);
            }else if(`${data.message}` == 'maxQuantity'){
                $("#notification-compare").append(`<div class="alert alert-warning alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>Thất bại</strong>
                                                            <p>Đã đạt só lượng tối đa</p>
                                                       </div>`);
            }else {
                $("#notification-compare").append(`<div class="alert alert-success alert-dismissible">
                                                            <a href="#" class="close" data-dismiss="alert" aria-label="close">&times;</a>
                                                            <strong>Thành công!</strong>
                                                            <p>Thêm vào giỏ hàng thành công, đang có ${data.totalItem} sản phẩm</p>
                                                       </div>`);

                $("#hienThiSoLuong").html('');
                $("#hienThiSoLuong").append(`${data.totalItem}`);
            }
            setTimeout(function() {
                $('.alert').fadeOut('slow');}, 5000
            );
        }, error: function () {
            alert("Lỗi addToCart");
        }
    });

}


