$(document).ready(function () {
    $(".content label").on('click', function (event) {
       // event.stopPropagation(); //<-- has no effect to the described behavior
        console.log($(this).find('span').text())
        window.location.href = $(this).find('span').text();
     //alert($(this).find("span.value"))
    })
})