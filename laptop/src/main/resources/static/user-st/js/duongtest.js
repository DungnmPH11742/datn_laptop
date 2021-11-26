$('.btn-plus-category').click(function(){
    
    if(!$(this).parent().parent().find('.catalogue__list').hasClass('show')){
        $(this).parent().parent().find('.catalogue__list').addClass('show');
        $(this).find('.fa-plus').removeClass('fa-plus');
        $(this).find('i').addClass('fa-minus');
    }else{
        $(this).parent().parent().find('.catalogue__list').removeClass('show');
        $(this).find('.fa-minus').removeClass('fa-minus');
        $(this).find('i').addClass('fa-plus');
    }
    
});