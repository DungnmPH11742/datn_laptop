$(document).ready(function () {
    var timeout = null;
    $('.fa-search').on('click', function () {
        var key = $('#search').val();
        window.location.href = "/categorySearch?search=" + key + "&type=";
    })

    $(document).on('input', '#search', function (event) {
        event.preventDefault();
        var search = $('#search').val();
        var url;
        if(search.length === 0){
            url =  "http://localhost:8080/api-product/findName?name=";
        }
        else{
            url = "http://localhost:8080/api-product/findName?name=" + search;
        }

        timeout = setTimeout(function () {
            $.get(url, function (result) {

                var lstLaptop = '';
                var lstPC = ''
                var lstManHinh = ''
                var countLapTop = 0;
                var countPC = 0;
                var countMH = 0;
                for (const dataResult of result) {

                    if (String(dataResult.typeOfItem) === 'LT01') {
                        console.log(dataResult)
                        countLapTop++;
                        if (countLapTop <= 2) {
                            lstLaptop += '  <div class="items"><a href="/view?id=' + dataResult.id + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a href="/view?id=' + dataResult.id + '" class="">\n' +
                                '                                                ' + dataResult.name + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.outputPrice) + '&nbsp;\n' +
                                '          </span></div>'
                            $('#wathAllLaptop').html('')
                        }



                    }

                    if (String(dataResult.typeOfItem) === 'PC01') {
                        countPC++;
                        if (countPC <= 2) {
                            lstPC += '  <div class="items"><a href="/view?id=' + dataResult.id + '" class="ava"><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a href="/view?id=' + dataResult.id + '" class="">\n' +
                                '                                                ' + dataResult.name + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.outputPrice) + '&nbsp;\n' +
                                '          </span></div>'

                        }

                    }
                    if (String(dataResult.typeOfItem) === 'MN01') {
                        countMH++;
                        if (countMH <= 2) {
                            lstManHinh += '  <div class="items"><a href="/view?id=' + dataResult.id + '" class="ava "><img\n' +
                                '                                                src="' + dataResult.imgUrl + '"></a>\n' +
                                '                                            <h3 class="name"><a href="/view?id=' + dataResult.id + '" class="">\n' +
                                '                                                ' + dataResult.name + '\n' +
                                '                                            </a></h3> <span class="prie">\n' +
                                '            ' + new Intl.NumberFormat('vi-VN', {
                                    style: 'currency',
                                    currency: 'VND'
                                }).format(dataResult.outputPrice) + '&nbsp;\n' +
                                '          </span></div>'
                        }


                    }
                }


                if(countLapTop === 0){
                    $('.productLT').css("display","none");
                }
                else if (countLapTop > 2) {
                    $('#wathAllLaptop').html('<a href="/categorySearch?search=' + search + '&type=LT01">Xem tất cả</a>')
                }
                else{
                    $('.productLT').css("display","block");
                }


                if(countPC === 0){
                    $('.productPC').css("display","none");
                }
                else if (countPC > 2) {
                    $('#wathAllPC').html('<a href="/categorySearch?search=' + search + '&type=PC01">Xem tất cả</a>')
                }
                else{
                    $('.productPC').css("display","block");
                }

                if(countMH === 0){
                    $('.productMH').css("display","none");
                }
                else if (countMH > 2) {
                    $('#wathAllMH').html('<a href="/categorySearch?search=' + search + '&type=MN01">Xem tất cả</a>')
                }
                else{
                    $('.productMH').css("display","block");
                }

                if (search.length !== 0) {
                    $('.suggest-search').css("display", "block");
                    $('#laptop').html(lstLaptop).fadeIn();
                    $('#PC').html(lstPC).fadeIn();
                    $('#manhinh').html(lstManHinh).fadeIn();
                }
            })


            //  $('.table tbody').html(html);
        }, 250);
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
