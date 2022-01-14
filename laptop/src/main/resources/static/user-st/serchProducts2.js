$(document).ready(function () {
    if (!!window.performance && window.performance.navigation.type === 2) {
        window.location.reload();
    }
    var origin = window.location.origin;
    var filterPO = new Array();
    var distinct = new Array();
    updatelisting(1);
    seeMore();

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
        var url = origin + "/api/page/" + currentPage + "/search";
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

        var sort1 = get_filterOne('checkbox0').toString();
        var search = getSearchParams("search").toString();
        var data = {
            sort: sort1,
            category: category,
            minPrice: (minPrice != null || !minPrice === ' ') ? minPrice : 0,
            maxPrice: (maxPrice != null || !maxPrice === ' ') ? maxPrice : 10000000000,
            sale: sale,
            name: search,
        };
        $.get(
            url, data,
            function (result) {
                reset();
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
                                '      <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + products.idProduct + '\')">\n' +
                                '                    <i class="fa fa-shopping-cart"></i>\n' +
                                '                </a>\n' +
                                '                <a href="#" class="btn add-to-cart" onClick="addCompareProduct(\'' + products.idProduct + '\')">\n' +
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
                                '      <a href="#" class="btn btn-default add-to-cart" onClick="addToCart(\'' + products.idProduct + '\')">\n' +
                                '                    <i class="fa fa-shopping-cart"></i>\n' +
                                '                </a>\n' +
                                '                <a href="#" class="btn add-to-cart" onClick="addCompareProduct(\'' + products.idProduct + '\')">\n' +
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

    function seeMore() {
        this.event.preventDefault();

        var href = origin + "/api/get-category";
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
                var href = origin + "/api/get-category";
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

    $("#delFilter").on('click', function (event) {
        event.preventDefault();
        $("input:checkbox").attr("checked", false);
        distinct.forEach(function (i, idx, array) {
            $('#' + escapeRegExp(i.replace(/\s/g, ''))).remove();
        })
        distinct = [];
        filterPO = [];
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


