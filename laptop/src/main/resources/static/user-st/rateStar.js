$(document).ready(function () {
    autoRate();
    var checkErrors = null;
    var star = null;

    function checkRate() {

        let product = $("#idProduct").text();
        let data = {
            product: product
        }
        $.ajax({
            type: "GET",
            data: data,
            async: false,
            url: "http://localhost:8080/api/checkRateStar",
            dataType: 'json',
        }).done(function (response) {
            checkErrors = response.errors;
            // if (response.errors !== null) {
            //
            //     if (checkErrors !== null) {
            //         if ($('.ajs-message').length < 3) {
            //             alertify.notify(checkErrors, 'custom', 3);
            //
            //         } else {
            //             if ($('.ajs-warning').length < 1) {
            //                 alertify.notify("Vui lòng không spam", 'warning', 3);
            //             }
            //         }
            //
            //     }
            //
            // }
            //alert(response.errors)

        });
        return checkErrors;
    }

    function rate(rate, average, text) {
        $("#" + rate).prop("checked", true);
        let starAverage = '<h6>Trung bình: ' + text + '</h6>';
        if (average < 3) {
            $(".myratings").css('color', 'red');
            $(".myratings").html(starAverage);
        } else if (average < 5) {
            $(".myratings").css('color', 'green');
            $(".myratings").html(starAverage);
        } else {
            $(".myratings").css("color", "#007bff");
            $(".myratings").html(starAverage);
        }
    }

    function confirmStar(average, text) {


        if (average === 5) {
            rate("star5", average, text)
        } else if (average === 4.5) {
            rate("star4half", average, text)
        } else if (average === 4) {
            rate("star4", average, text)
        } else if (average === 3.5) {
            rate("star3half", average, text)
        } else if (average === 3) {
            rate("star3", average, text)
        } else if (average === 2.5) {
            rate("star2half", average, text)
        } else if (average === 2) {
            rate("star2", average, text)
        } else if (average === 1.5) {
            rate("star1half", average, text)
        } else if (average === 1) {
            rate("star1", average, text)
        } else {
        }

    }

    function autoRate() {

        let product = $("#idProduct").text();
        let data = {
            product: product
        }
        $.ajax({
            type: "GET",
            data: data,
            url: "http://localhost:8080/api/rateStar",
            dataType: 'json',
            timeout: 2000,
        }).done(function (response) {
            let starAverage = response.star;
            let starAccount = response.starTextAccount;
            let text = response.starText;
            if (starAccount !== null) {

                if (starAccount % 1 !== 0) {
                    let target = $('.halfUser').filter('[data-value="' + starAccount + '"]');
                    setHalfStarState(target);
                    $(target).closest('.ratingUser').find('.js-score').text($(target).data('value'));
                } else {
                    let target = $('.fullUser').filter('[data-value="' + starAccount + '"]');
                    setFullStarState(target)
                    $(target).closest('.ratingUser').find('.js-score').text($(target).data('value'));
                }

            }

            confirmStar(starAverage, text)
        });
    }


    $("input[type='radio']").on('click', function (e) {
        e.preventDefault();
        return false;
    });


    function setRateStar(starValue) {

        let product = $("#idProduct").text();
        var data = {
            rate: starValue,
            product: product
        }
        $.ajax({
            type: "POST",
            data: data,
            url: "http://localhost:8080/api/rateStar",
        }).done(function (response) {
            let message = response.message;
            if (message !== null) {
                if ($('.ajs-message').length < 3) {
                    alertify.notify(message, 'success', 3);
                    let starAverage = response.star;
                    let starAccount = response.starTextAccount;
                    let text = response.starText;
                    confirmStar(starAverage, text)
                } else {
                    if ($('.ajs-warning').length < 1) {
                        alertify.notify("Vui lòng không spam", 'warning', 3);
                    }
                }

            }
        });
    }

    var starClicked = false;

    $(function () {

        $('.starUser').click(function () {

            $(this).children('.selectedUser').addClass('is-animated');
            $(this).children('.selectedUser').addClass('pulse');

            let target = this;

            setTimeout(function () {
                $(target).children('.selectedUser').removeClass('is-animated');
                $(target).children('.selectedUser').removeClass('pulse');
            }, 1000);

            starClicked = true;
        })

        $('.halfUser').click(function (e) {
            e.preventDefault();
            //  alert(checkErrors)
            var check = checkRate();
            if (check !== null) {
                if ($('.ajs-message').length < 3) {
                    alertify.notify(check, 'custom', 3);

                } else {
                    if ($('.ajs-warning').length < 1) {
                        alertify.notify("Vui lòng không spam", 'warning', 3);
                    }
                }

                return false
            }
            let rate = $(this).data('value');
            star = rate
            setRateStar(rate)
            setHalfStarState(this)
            $(this).closest('.ratingUser').find('.js-score').text(rate);
            $(this).closest('.ratingUser').data('vote', rate);
            if ($('.ajs-message').length < 3) {
                alertify.notify("Đừng quên còn bình luận nữa thì đánh giá sao mới có hiệu lực nha bạn", 'custom', 5);

            } else {
                if ($('.ajs-warning').length < 1) {
                    alertify.notify("Vui lòng không spam", 'warning', 3);
                }
            }


        })

        $('.fullUser').click(function (e) {
            e.preventDefault();
            var check = checkRate();
            if (check !== null) {
                if ($('.ajs-message').length < 3) {
                    alertify.notify(check, 'custom', 3);

                } else {
                    if ($('.ajs-warning').length < 1) {
                        alertify.notify("Vui lòng không spam", 'warning', 3);
                    }
                }
                return false
            }
            let rate = $(this).data('value');
            setRateStar(rate)
            star = rate;
            setFullStarState(this)
            $(this).closest('.ratingUser').find('.js-score').text(rate);
            $(this).closest('.ratingUser').data('vote', rate);
            if ($('.ajs-message').length < 3) {
                alertify.notify("Đừng quên còn bình luận nữa thì đánh giá sao mới có hiệu lực nha bạn", 'custom', 5);

            } else {
                if ($('.ajs-warning').length < 1) {
                    alertify.notify("Vui lòng không spam", 'warning', 3);
                }
            }
        })


    })

    function updateStarState(target) {
        $(target).parent().prevAll().addClass('animate');
        $(target).parent().prevAll().children().addClass('star-colourUser');
        $(target).parent().nextAll().removeClass('animate');
        $(target).parent().nextAll().children().removeClass('star-colourUser');


    }

    function setHalfStarState(target) {
        $(target).addClass('star-colourUser');
        $(target).siblings('.fullUser').removeClass('star-colourUser');
        updateStarState(target)
    }

    function setFullStarState(target) {
        $(target).addClass('star-colourUser');
        $(target).parent().addClass('animate');
        $(target).siblings('.halfUser').addClass('star-colourUser');

        updateStarState(target)
    }

    function setStarOutPut(rate) {
        let rateStar = rate;
        let product = $("#idProduct").text();
        let data = {
            rate: rateStar,
            product: product
        }
        $.ajax({
            type: "POST",
            data: data,
            url: "http://localhost:8080/api/rateStar",
            dataType: 'json',
        }).done(function (response) {
            let starAverage = response.star;
            let starAccount = response.starTextAccount;

            if (response.errors != null) {
                if ($('.ajs-message').length < 3) {
                    alertify.notify(response.errors, 'error', 3);

                } else {
                    if ($('.ajs-warning').length < 1) {
                        alertify.notify("Vui lòng không spam", 'warning', 3);
                    }
                }
                // confirmStar(starAverage, starAccount);
            } else {
                if ($('.ajs-message').length < 3) {
                    //autoRate();
                    alertify.notify(response.message, 'success', 3);
                } else {
                    if ($('.ajs-warning').length < 1) {
                        alertify.notify("Vui lòng không spam", 'warning', 3);
                    }
                }

                //  confirmStar(starAverage, starAccount);
            }

        });
    }
    // if (parseInt(result.totalElements) === 0) {
    //     $('#alert').remove();
    // }

    var is_busy = false;
    var page = 1;

    loadComment(1);
    $("#comment_form").on('submit', function (event) {
        event.preventDefault();
        var values = {};
        $.each($("form").serializeArray(), function (i, field) {
            values[field.name] = field.value;
        });
        var getValue = function (valueName) {
            return values[valueName];
        };

        let comment_content = getValue("comment_content");
        let product = $("#idProduct").text();
        let rate = $(".js-score").text();
        if (parseFloat(rate) === 0) {
            if ($('.ajs-message').length < 3) {
                alertify.notify("Bạn cần đánh giá sao để được bình luận", 'custom', 3);

            } else {
                if ($('.ajs-warning').length < 1) {
                    alertify.notify("Vui lòng không spam", 'warning', 3);
                }
            }
            return false;
        } else {
            let data = {
                rate: rate,
                product: product,
                comment_content: comment_content
            }
            $.ajax({
                type: "POST",
                data: data,
                url: "http://localhost:8080/api/commentProduct",
            }).done(function (response) {
                loadToltalComment()
                let message = response.message;
                let errors = response.errors;
                console.log(response)
                if (message !== null) {

                    setRateStar(rate);
                    if ($('.ajs-message').length < 3) {
                        alertify.notify(message, 'success', 3);
                        autoRate();
                        let html = '   <div class="media">\n' +
                            '                    <a class="pull-left" href="#"><img class="media-object" style="width: 60px; height: 60px" src="https://bootdey.com/img/Content/avatar/avatar1.png" alt=""></a>\n' +
                            '                    <div class="media-body">\n' +
                            '                        <h4 class="media-heading" style="margin-bottom: 7px;color: #669698">' + response.data.account.fullName + ' </h4>\n' +
                            '                        <p>' + response.data.comment + '</p>\n' +
                            '                        <ul class="list-unstyled list-inline media-detail pull-left" style="margin-top: -7px">\n' +
                            '                            <li style="color: #7d7d7d;font-size: 12px" ><svg xmlns="http://www.w3.org/2000/svg" width="11" height="11"  fill="currentColor" class="bi bi-calendar" viewBox="0 0 16 16">\n' +
                            '  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>\n' +
                            '</svg><span style="margin-left: 5px">' + moment(response.data.startComment).format('YYYY-MM-DD HH:m:s') + '</span></li>\n' +
                            '                        </ul>\n' +
                            '                        <ul class="list-unstyled list-inline media-detail pull-right">\n' +

                            '                        </ul>\n' +
                            '                    </div>\n' +
                            '                </div>';

                        $('#showComment').prepend(html);
                    } else {
                        if ($('.ajs-warning').length < 1) {
                            alertify.notify("Vui lòng không spam", 'warning', 3);
                            return false;
                        }
                    }
                    $('#comment_form')[0].reset();
                }
                if (errors !== null) {
                    if ($('.ajs-message').length < 3) {
                        alertify.notify(errors, 'custom', 3);

                    } else {
                        if ($('.ajs-warning').length < 1) {
                            alertify.notify("Vui lòng không spam", 'warning', 3);
                        }
                    }
                    return false;
                }
            });
        }
    })
    // function loadComment() {

    $('#load_more').click(function (event) {
        event.preventDefault();
        if (is_busy == true) {
            return false;
        }
        page++;
        $('#load_more').html('Đang tải ...');
        loadComment(page)

    })

    function loadToltalComment() {
        let product = $("#idProduct").text();
        let data = {
            product: product
        };
        $.ajax(
            {
                type: 'get',
                data: data,
                dataType: 'json',
                url: "http://localhost:8080/api/showComment/" + 1,
                success: function (result) {
                    if (parseInt(result.totalElements) === 0) {
                        $('#load_more').html(' ');
                    }
                    $('#totalComment').html('<h3>Bình luận(' + result.totalElements + ')</h3>')
                }
            }
        )
    }

    function loadComment(pageNumber) {
        let product = $("#idProduct").text();
        let data = {
            product: product
        };
        $.ajax(
            {
                type: 'get',
                data: data,
                dataType: 'json',
                url: "http://localhost:8080/api/showComment/" + pageNumber,
                success: function (result) {
                    $('#totalComment').html('<h3>Bình luận(' + result.totalElements + ')</h3>')
                    var htmll = '';
                    if (parseInt(result.totalElements) === 0) {
                        $('#alert').css("display","none");
                    }
                    if (result.pageable.pageNumber === result.totalPages - 1) {
                        console.log(result)
                        $.each(result.content, function (key, obj) {
                            htmll += '   <div class="media">\n' +
                                '                    <a class="pull-left" href="#"><img class="media-object" style="width: 50px; height: 50px;border-radius: 50%" src="'+obj.account.imgUrl+'" alt=""></a>\n' +
                                '                    <div class="media-body">\n' +
                                '                        <h4 class="media-heading" style="margin-bottom: 7px;color: #669698">' + obj.account.fullName + ' </h4>\n' +
                                '                        <p>' + obj.comment + '</p>\n' +
                                '                        <ul class="list-unstyled list-inline media-detail pull-left" style="margin-top: -7px">\n' +
                                '                            <li style="color: #7d7d7d;font-size: 12px" ><svg xmlns="http://www.w3.org/2000/svg" width="11" height="11"  fill="currentColor" class="bi bi-calendar" viewBox="0 0 16 16">\n' +
                                '  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>\n' +
                                '</svg><span style="margin-left: 5px">' + moment(obj.startComment).format('YYYY-MM-DD HH:m:s') + '</span></li>\n' +
                                '                        </ul>\n' +
                                '                        <ul class="list-unstyled list-inline media-detail pull-right">\n' +

                                '                        </ul>\n' +
                                '                    </div>\n' +
                                '                </div>';

                        });
                        $('#showComment').append(htmll);

                        $('#alert').css("display","none");
                    } else {

                        console.log(result)
                        $.each(result.content, function (key, obj) {
                            console.log(key + '--' + result.content.length)
                            htmll += '   <div class="media">\n' +
                                '                    <a class="pull-left" href="#"><img class="media-object" style="width: 50px; height: 50px;border-radius: 50%" src="'+obj.account.imgUrl+'" alt=""></a>\n' +
                                '                    <div class="media-body">\n' +
                                '                        <h4 class="media-heading" style="margin-bottom: 7px;color: #669698">' + obj.account.fullName + ' </h4>\n' +
                                '                        <p>' + obj.comment + '</p>\n' +
                                '                        <ul class="list-unstyled list-inline media-detail pull-left" style="margin-top: -7px">\n' +
                                '                            <li style="color: #7d7d7d;font-size: 12px"><svg xmlns="http://www.w3.org/2000/svg" width="11" height="11" fill="currentColor" class="bi bi-calendar" viewBox="0 0 16 16">\n' +
                                '  <path d="M3.5 0a.5.5 0 0 1 .5.5V1h8V.5a.5.5 0 0 1 1 0V1h1a2 2 0 0 1 2 2v11a2 2 0 0 1-2 2H2a2 2 0 0 1-2-2V3a2 2 0 0 1 2-2h1V.5a.5.5 0 0 1 .5-.5zM1 4v10a1 1 0 0 0 1 1h12a1 1 0 0 0 1-1V4H1z"/>\n' +
                                '</svg><span style="margin-left: 5px">' + moment(obj.startComment).format('YYYY-MM-DD HH:m:s') + '</span></li>\n' +

                                '                        </ul>\n' +
                                '                        <ul class="list-unstyled list-inline media-detail pull-right">\n' +

                                '                        </ul>\n' +
                                '                    </div>\n' +
                                '                </div>';


                        });
                        $('#showComment').append(htmll);
                        $('#alert').css("display","block");
                    }

                }
            })
            .always(function () {
                $('#load_more').html('Tải thêm');
                is_busy = false;
            });
    }
});