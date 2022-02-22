$(document).ready(function () {
    var timeout = null;
    var origin = window.location.origin;
    var pathname = new Array();
    var resultname = new Array();

    function addToHistory(value) {
        var history = getHistory();
        history.unshift(value);
        localStorage.setItem('search', JSON.stringify(history.slice(0, 5)));
    }

    var getHistory = function () {
        return JSON.parse(localStorage.getItem("search")) || [];
    }

    function keySearch(array) {

        var unique = {};
        var suggetkey = '';
        var distinct = new Array();
        for (var i in array) {

            if (typeof (unique[array[i]]) == "undefined") {
                if (distinct.indexOf(array[i]) === -1) {
                    suggetkey += '<li id="suggetkey-' + i + '"><a style="color: #171c27" href="'+origin+'/search?search='+array[i]+'"> ' + array[i] + '</a></li>';
                }
            }
            unique[array[i]] = 0;
        }
        $('.list-key').prepend(suggetkey);
    }


    $(document).on('input', '#search', function (event) {
        event.preventDefault();

        pathname = [];
        resultname = [];
        var search = $('#search').val();
        var url = '';

        $('.list-key').html('');

        if (search.length <= 0) {
            url = origin + "/api-product/findName?name=";
        } else {
            url = origin + "/api-product/findName?name=" + search;
        }
        timeout = setTimeout(function () {


            $.get(url, function (result) {

                var lstLaptop = '';
                var lstPC = ''
                var lstManHinh = ''
                var countLapTop = 0;
                var countPC = 0;
                var countMH = 0;
                pathname = [];
                resultname = [];

                if (getHistory()[0] == null) {
                    $('.list-key').html('');
                    $('.suggest-keyword').css("display", "none");
                } else {
                    $('.suggest-keyword').css("display", "block");
                    $('.list-key').html('');
                    keySearch(getHistory());

                }

                for (const dataResult of result) {
                    if (String(dataResult.typeOfItem) === 'LT01') {
                        countLapTop++;
                        if (countLapTop <= 2) {
                            lstLaptop += '  <div class="items"><a href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'
                            // $('#wathAllLaptop').html('')
                        }


                    }

                    if (String(dataResult.typeOfItem) === 'PC01') {
                        countPC++;
                        if (countPC <= 2) {
                            lstPC += '  <div class="items"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'

                        }

                    }
                    if (String(dataResult.typeOfItem) === 'MN01') {
                        countMH++;
                        if (countMH <= 2) {
                            lstManHinh += '  <div class="items"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava "><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'
                        }


                    }
                }


                if (countLapTop <= 0) {
                    $('.productLT').css("display", "none");
                } else if (countLapTop <= 2) {
                    resultname.push("/laptop");
                    $('#wathAllLaptop').html('')
                    $('.productLT').css("display", "block");
                } else if (countLapTop > 2) {
                    $('.productLT').css("display", "block");
                    $('#wathAllLaptop').html('<a href="/category/laptop?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/laptop");
                }


                if (countPC <= 0) {
                    $('.productPC').css("display", "none");
                } else if (countPC <= 2) {
                    $('#wathAllPC').html('')
                    $('.productPC').css("display", "block");
                    resultname.push("/pc%20văn%20phòng");
                } else if (countPC > 2) {
                    $('.productPC').css("display", "block");
                    $('#wathAllPC').html('<a href="/category/pc%20văn%20phòng?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/pc%20văn%20phòng");
                }

                // alert("countMH ")
                if (countMH <= 0) {
                    $('.productMH').css("display", "none");
                } else if (countMH <= 2) {
                    $('#wathAllMH').html('')
                    $('.productMH').css("display", "block");
                    resultname.push("/màn%20hình?search");
                } else if (countMH > 2) {
                    $('.productMH').css("display", "block");
                    $('#wathAllMH').html('<a href="/category/màn%20hình?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/màn%20hình?search");
                }

                //     if (search.length === 0) {
                $('.suggest-search').css("display", "block");
                $('#laptop').html(lstLaptop).fadeIn();
                $('#PC').html(lstPC).fadeIn();
                $('#manhinh').html(lstManHinh).fadeIn();
                // } else if (search.length > 0) {
                //     $('.suggest-search').css("display", "block");
                //     $('#laptop').html(lstLaptop).fadeIn();
                //     $('#PC').html(lstPC).fadeIn();
                //     $('#manhinh').html(lstManHinh).fadeIn();
                // }



            })

        }, 250);
    })
    $(document).on('click', '#search', function (event){
        event.preventDefault();

        pathname = [];
        resultname = [];
        var search = $('#search').val();
        var url = '';

        $('.list-key').html('');

        if (search.length <= 0) {
            url = origin + "/api-product/findName?name=";
        } else {
            url = origin + "/api-product/findName?name=" + search;
        }
        timeout = setTimeout(function () {


            $.get(url, function (result) {

                var lstLaptop = '';
                var lstPC = ''
                var lstManHinh = ''
                var countLapTop = 0;
                var countPC = 0;
                var countMH = 0;
                pathname = [];
                resultname = [];

                if (getHistory()[0] == null) {
                    $('.list-key').html('');
                    $('.suggest-keyword').css("display", "none");
                } else {
                    $('.suggest-keyword').css("display", "block");
                    $('.list-key').html('');
                    keySearch(getHistory());

                }

                for (const dataResult of result) {
                    if (String(dataResult.typeOfItem) === 'LT01') {
                        countLapTop++;
                        if (countLapTop <= 2) {
                            lstLaptop += '  <div class="items"><a href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'
                            // $('#wathAllLaptop').html('')
                        }


                    }

                    if (String(dataResult.typeOfItem) === 'PC01') {
                        countPC++;
                        if (countPC <= 2) {
                            lstPC += '  <div class="items"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'

                        }

                    }
                    if (String(dataResult.typeOfItem) === 'MN01') {
                        countMH++;
                        if (countMH <= 2) {
                            lstManHinh += '  <div class="items"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="ava "><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a  href="/view/' + dataResult.idProduct + '?sku=' + dataResult.sku + '" class="">\n' +
                                '                                                ' + dataResult.nameProduct + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.price) + '&nbsp;\n' +
                                '          </span></div>'
                        }


                    }
                }


                if (countLapTop <= 0) {
                    $('.productLT').css("display", "none");
                } else if (countLapTop <= 2) {
                    resultname.push("/laptop");
                    $('#wathAllLaptop').html('')
                    $('.productLT').css("display", "block");
                } else if (countLapTop > 2) {
                    $('.productLT').css("display", "block");
                    $('#wathAllLaptop').html('<a href="/category/laptop?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/laptop");
                }


                if (countPC <= 0) {
                    $('.productPC').css("display", "none");
                } else if (countPC <= 2) {
                    $('#wathAllPC').html('')
                    $('.productPC').css("display", "block");
                    resultname.push("/pc%20văn%20phòng");
                } else if (countPC > 2) {
                    $('.productPC').css("display", "block");
                    $('#wathAllPC').html('<a href="/category/pc%20văn%20phòng?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/pc%20văn%20phòng");
                }

                // alert("countMH ")
                if (countMH <= 0) {
                    $('.productMH').css("display", "none");
                } else if (countMH <= 2) {
                    $('#wathAllMH').html('')
                    $('.productMH').css("display", "block");
                    resultname.push("/màn%20hình?search");
                } else if (countMH > 2) {
                    $('.productMH').css("display", "block");
                    $('#wathAllMH').html('<a href="/category/màn%20hình?search=' + search + '">Xem tất cả</a>')
                    pathname.push("/màn%20hình?search");
                }

                //     if (search.length === 0) {
                $('.suggest-search').css("display", "block");
                $('#laptop').html(lstLaptop).fadeIn();
                $('#PC').html(lstPC).fadeIn();
                $('#manhinh').html(lstManHinh).fadeIn();
                // } else if (search.length > 0) {
                //     $('.suggest-search').css("display", "block");
                //     $('#laptop').html(lstLaptop).fadeIn();
                //     $('#PC').html(lstPC).fadeIn();
                //     $('#manhinh').html(lstManHinh).fadeIn();
                // }



            })

        }, 250);
    })
    $('.fa-search').on('click', function () {
        var key = $('#search').val();
        if (key.length <= 0) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Vui lòng nhập tên sản phẩm", 'custom', 3);

            } else {
                if ($('.ajs-warning').length < 1) {
                    alertify.notify("Vui lòng không spam", 'warning', 3);
                }
            }
            return;
        }
        addToHistory(key);
        return window.location.href = "/search?search=" + key;

    })

    var input = document.getElementById("search");
    input.addEventListener("keyup", function (event) {
        if (event.keyCode === 13) {
            event.preventDefault();
            $('.fa-search').click();
        }

    });

    $(document).on('click', '#remove-search', function (event){
        event.preventDefault();
        localStorage.removeItem('search');
    })

    $('html').click(function (e) {
        if (e.target.id !== 'suggest-search' && e.target.id !== 'search') {
            $('.suggest-search').css("display", "none")
            $('#laptop').html("");
            $('#PC').html("");
            $('#manhinh').html("");
        }
    });

})
