/*  ---------------------------------------------------
    Template Name: Sona
    Description: Sona Hotel Html Template
    Author: Colorlib
    Author URI: https://colorlib.com
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';

(function ($) {

    /*------------------
        Preloader
    --------------------*/
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(200).fadeOut("slow");
    });

    /*------------------
        Background Set
    --------------------*/
    $('.set-bg').each(function () {
        var bg = $(this).data('setbg');
        $(this).css('background-image', 'url(' + bg + ')');
    });

    //Offcanvas Menu
    $(".canvas-open").on('click', function () {
        $(".offcanvas-menu-wrapper").addClass("show-offcanvas-menu-wrapper");
        $(".offcanvas-menu-overlay").addClass("active");
    });

    $(".canvas-close, .offcanvas-menu-overlay").on('click', function () {
        $(".offcanvas-menu-wrapper").removeClass("show-offcanvas-menu-wrapper");
        $(".offcanvas-menu-overlay").removeClass("active");
    });

    // Search model
    $('.search-switch').on('click', function () {
        $('.search-model').fadeIn(400);
    });

    $('.search-close-switch').on('click', function () {
        $('.search-model').fadeOut(400, function () {
            $('#search-input').val('');
        });
    });

    /*------------------
		Navigation
	--------------------*/
    $(".mobile-menu").slicknav({
        prependTo: '#mobile-menu-wrap',
        allowParentLinks: true
    });

    /*------------------
        Hero Slider
    --------------------*/
   $(".hero-slider").owlCarousel({
        loop: true,
        margin: 0,
        items: 1,
        dots: true,
        animateOut: 'fadeOut',
        animateIn: 'fadeIn',
        smartSpeed: 1200,
        autoHeight: false,
        autoplay: true,
        mouseDrag: false
    });

    /*------------------------
		Testimonial Slider
    ----------------------- */
    $(".testimonial-slider").owlCarousel({
        items: 1,
        dots: false,
        autoplay: true,
        loop: true,
        smartSpeed: 1200,
        nav: true,
        navText: ["<i class='arrow_left'></i>", "<i class='arrow_right'></i>"]
    });

    /*------------------
        Magnific Popup
    --------------------*/
    $('.video-popup').magnificPopup({
        type: 'iframe'
    });

    /*------------------
		Date Picker
	--------------------*/
    function disableSunday(sunday){
        var calendarday = sunday.getDay();
        return [(calendarday!=0),''];
    };
    $(function() {
        $("#date-in").datepicker({
            minDate: 0,
            beforeShowDay: disableSunday,
            dateFormat: 'dd MM, yy',
            altFormat: 'yy-mm-dd',
            altField: '#alt-date',
            required: true
        })
    });

    $(function(){
        $("#time-start").timepicker({
            minTime: '8:30',
            maxTime: '22:00',
            hourMin: 8,
	        hourMax: 22
        })
    });
    $(function(){
        $("#time-end").timepicker({
            minTime: '8:30',
            maxTime: '22:00',
            hourMin: 8,
	        hourMax: 22
        });
    });
    /*------------------
		Nice Select
	--------------------*/
    $("select").niceSelect();

})(jQuery);
$('.myFreeDate .block-date').each(function (){
    var hundred = 22-8.30;
    var timeStart = $(this).find('div').eq(0).text().replaceAll(':','.');
    var timeEnd = $(this).find('div').eq(1).text().replaceAll(':','.');
    var dx = timeEnd - timeStart;
    console.log(timeStart+" "+ timeEnd);
    console.log(dx);
    var width = $('.myFreeDate').css("width").replaceAll('px','');
    var pixels = 100*dx/hundred*10+"px";
    var percent = dx/hundred*width*0.95+"px";
    console.log(pixels);
    console.log(percent);
    console.log(width);
    if(dx==hundred){

        width = Number(width);
        width-=10;
        $(this).css({'min-width': '300px', 'width': width+"px"});
    }
    else{
    $(this).css({'width': percent,
                       'min-width':'95px'});
    }
    if ($(this).css('color') == 'rgb(255, 0, 0)') {
        $(this).addClass('alert-danger');
    }
    else $(this).addClass('alert-primary');
});



