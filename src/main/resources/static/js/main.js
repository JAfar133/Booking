/*  ---------------------------------------------------
    Template Name: Sona
    Description: Sona Hotel Html Template
    Author: Colorlib
    Author URI: https://colorlib.com
    Version: 1.0
    Created: Colorlib
---------------------------------------------------------  */

'use strict';
jQuery(function ($) {
    $.datepicker.regional['ru'] = {
        closeText: 'Закрыть',
        prevText: '&#x3c;Пред',
        nextText: 'След&#x3e;',
        currentText: 'Сегодня',
        monthNames: ['Января', 'Февраля', 'Марта', 'Апреля', 'Мая', 'Июня',
            'Июля', 'Августа', 'Сентября', 'Октября', 'Ноября', 'Декабря'],
        monthNamesShort: ['Янв', 'Фев', 'Мар', 'Апр', 'Май', 'Июня',
            'Июля', 'Авг', 'Сен', 'Октября', 'Ноября', 'Декабря'],
        dayNames: ['воскресенье', 'понедельник', 'вторник', 'среда', 'четверг', 'пятница', 'суббота'],
        dayNamesShort: ['вск', 'пнд', 'втр', 'срд', 'чтв', 'птн', 'сбт'],
        dayNamesMin: ['Вс', 'Пн', 'Вт', 'Ср', 'Чт', 'Пт', 'Сб'],
        weekHeader: 'Нед',
        dateFormat: 'dd.mm.yy',
        firstDay: 1,
        isRTL: false,
        showMonthAfterYear: false,
        yearSuffix: ''
    };
    $.datepicker.setDefaults($.datepicker.regional['ru']);
});
(function ($) {

    /*------------------
        Preloader
    --------------------*/
    $(window).on('load', function () {
        $(".loader").fadeOut();
        $("#preloder").delay(50).fadeOut("slow");
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
        });
    });

    $(function(){
        $(".time-start").timepicker({
            minTime: '8:30',
            maxTime: '22:00',
            hourMin: 8,
	        hourMax: 22
        })
    });
    $(function(){
        $(".time-end").timepicker({
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
    if($(window).width() < 1300 && $(window).width() > 991) {
        $(".nice-select span").css({"font-size": "10pt"})
    };
    if($(window).width() < 991) {
        $(".nice-select span").css({"font-size": "14pt"})
    };
    if($(window).width() < 767) {
        $(".nice-select span").css({"font-size": "12pt"})
    };
    if($(window).width() < 479) {
        $(".nice-select span").css({"font-size": "9pt"})
    };
    if($(window).width() < 390) {
        $(".nice-select span").css({"font-size": "7.5pt"})
    };

})(jQuery);
function datetimepick(date_id,timeStart_id,timeEnd_id,altDate_id) {
    $(function() {
        $(date_id).datepicker({
            minDate: 0,
            beforeShowDay: disableSunday,
            dateFormat: 'dd MM, yy',
            altFormat: 'yy-mm-dd',
            altField: altDate_id
        })
    });
    function disableSunday(sunday){
        var calendarday = sunday.getDay();
        return [(calendarday!=0),''];
    };

    $(function(){
        $(timeStart_id).timepicker({
            minTime: '8:30',
            maxTime: '22:00',
            hourMin: 8,
            hourMax: 22
        })
    });
    $(function(){
        $(timeEnd_id).timepicker({
            minTime: '8:30',
            maxTime: '22:00',
            hourMin: 8,
            hourMax: 22
        });
    });
};

// Free date block
$('.myFreeDate .block-date').each(function (){
    var hundred = 22-8.30;
    var timeStart = $(this).find('div').eq(0).text().replaceAll(':','.');
    var timeEnd = $(this).find('div').eq(1).text().replaceAll(':','.');
    var dx = timeEnd - timeStart;
    var width = $('.myFreeDate').css("width").replaceAll('px','');
    var pixels = 100*dx/hundred*10+"px";
    var percent = dx/hundred*width*0.95+"px";
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

// AJAX FORMS
$(document).ready(function () {
    $("#place-not-free-alert").css({"display": "none"});
    $("#booking-form-index").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        setDisplayNoneForErrorsAndClear();
        booking_ajax_submit();
    });
    $("#personForm").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        setDisplayNoneForErrorsAndClear();
        person_ajax_submit();
    });
    $("#booking-form-room-details").submit(function (event) {
        //stop submit the form, we will post it manually.
        event.preventDefault();
        setDisplayNoneForErrorsAndClear();
        booking_ajax_submit();
    });

    $(".date-change").click(function(e) {
        var button_id = $(this).attr("id");
        var booking_id = button_id.split("date-change")[1];
        var modal_id = "#change-date-modal" + booking_id;
        var form_id = "#admin-change-date-form" + booking_id;
        var date_id = "#date-in"+booking_id;
        var alt_date_id = "#alt-date"+booking_id;
        var time_start_id = "#time-start"+booking_id;
        var time_end_id = "#time-end"+booking_id;
        datetimepick(date_id,time_start_id,time_end_id,alt_date_id);
        $(modal_id).modal('show');
        $(form_id).submit(function (event) {
            event.preventDefault();
            admin_change_date_ajax_submit(booking_id, modal_id);
        });
    });
    $(".confirm-btn").click(function(e) {
        var button_id = $(this).attr("id");
        var booking_id = button_id.split("confirm-btn")[1];
        var form_id = "#admin-confirm-form" + booking_id;
        $(form_id).submit(function (event) {
            event.preventDefault();
            admin_confirm_ajax_submit(booking_id, button_id);
        });
    });
    $(".delete-btn").click(function(e) {
        var button_id = $(this).attr("id");
        var booking_id = button_id.split("delete-btn")[1];
        var form_id = "#admin-delete-form" + booking_id;
        $(form_id).submit(function (event) {
            event.preventDefault();
            admin_delete_ajax_submit(booking_id, button_id);
        });
    });
});

//AJAX SUBMIT
function booking_ajax_submit() {


    var bookingDTO = {}
    bookingDTO["date"] = $("#alt-date").val();
    bookingDTO["timeStart"] = $("#time-start").val();
    bookingDTO["timeEnd"] = $("#time-end").val();
    bookingDTO["placeId"] = $("#room").val();

    $("#booking-submit-btn").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api",
        data: JSON.stringify(bookingDTO),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#booking-submit-btn").prop("disabled", false);
            $('#exampleModal').modal('show');
        },
        error: function (e) {
            $.each(e.responseJSON.errors, function (index, value) {
                // Помещение занято
                if(value.field=="place"){
                    placeIsNotFree(bookingDTO.placeId, bookingDTO.date,
                                    bookingDTO.timeStart, bookingDTO.timeEnd);
                }
                // Неправильное время
                if(value.field=="timeEnd"){
                    wrongTimeEnd(value.msg);
                }
            })
            $("#booking-submit-btn").prop("disabled", false);
        }
    });
}

function person_ajax_submit() {
    var bookingDTO = {}
    var personDTO = {}
    personDTO["lastName"] = $("#last_name").val();
    personDTO["firstName"] = $("#first_name").val();
    personDTO["middleName"] = $("#middle_name").val();
    personDTO["phoneNumber"] = $("#phone").val();
    personDTO["post"] = $("#post").val();
    personDTO["institute"] = $("#institute").val();
    personDTO["course"] = $("#course").val();
    personDTO["structure"] = $("#structure").val();

    bookingDTO["date"] = $("#alt-date").val();
    bookingDTO["timeStart"] = $("#time-start").val();
    bookingDTO["timeEnd"] = $("#time-end").val();
    bookingDTO["placeId"] = $("#room").val();
    bookingDTO["comment"] = $("#comment").val();

    $("#person-details-submit-btn").prop("disabled", true);
    $.ajax({
        type: "POST",
        contentType: "application/json",
        url: "/api/booking",
        data: JSON.stringify({personDTO, bookingDTO}),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $("#person-details-submit-btn").prop("disabled", false);
            $('#exampleModal').modal('toggle'); // Закрываем попап для регистр.
            $('#alert-success').modal('show'); // Открываем уведомление об успешной операции

        },
        error: function (e) {
            
            $.each(e.responseJSON.errors, function (index, value) {
                // Неправильный номер телефона
                if(value.field=="phoneNumber"){
                    $('#phone-error-alert').css({"display":"block"});
                    $('#phone-error-alert').append(value.msg);
                }
                if(value.field=="place"){
                    placeIsNotFree(bookingDTO.placeId, bookingDTO.date,
                        bookingDTO.timeStart, bookingDTO.timeEnd);
                }

            })

            $("#person-details-submit-btn").prop("disabled", false);
        }
    });
}

function admin_change_date_ajax_submit(booking_id, modal_id) {
    // id div'ов для ошибок
    let pnfa_id = "#place-not-free-alert"+booking_id;
    let wtaa_id = "#wrong-time-end-alert"+booking_id;

    // Другие нужные id
    var btn_id = "#admin-change-date-submit-btn"+booking_id;
    var alt_date_id = "#alt-date"+booking_id;
    var time_start_id = "#time-start"+booking_id;
    var time_end_id = "#time-end"+booking_id;
    var booking_card_id = "#booking-card"+booking_id;

    // json body
    var booking = {}
    booking["date"] = $(alt_date_id).val();
    booking["timeStart"] = $(time_start_id).val();
    booking["timeEnd"] = $(time_end_id).val();

    //Очищаем ошибки
    $(pnfa_id).empty();
    $(wtaa_id).empty();

    $(btn_id).prop("disabled", true);
    $.ajax({
        type: "PATCH",
        contentType: "application/json",
        url: "/admin/api/change-date/"+booking_id,
        data: JSON.stringify(booking),
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $(btn_id).prop("disabled", false);
            $(modal_id).modal('toggle'); // Закрываем попап
            $(booking_card_id).addClass("alert-success");
            $(booking_card_id).fadeOut(500, function(){ $(this).remove();}); // Убираем заказ со страницы
        },
        error: function (e) {
            
            $.each(e.responseJSON.errors, function (index, value) {
                // Помещение занято
                if(value.code=="424"){
                    $(pnfa_id).css({"display": "block"});
                    $(pnfa_id).append("Помещение занято в это время");
                }
                // Неправильное время
                if(value.code=="423"){
                    $(wtaa_id).css({"display":"block"});
                    $(wtaa_id).append(msg);
                }
            })
            $(btn_id).prop("disabled", false);
        }
    });
}
function admin_confirm_ajax_submit(booking_id, btn_id) {
    var booking_card_id = "#booking-card"+booking_id;
    $(btn_id).prop("disabled", true);
    $.ajax({
        type: "PATCH",
        contentType: "application/json",
        url: "/admin/api/booking/"+booking_id,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $(btn_id).prop("disabled", false);
            $(booking_card_id).addClass("alert-success");
            $(booking_card_id).fadeOut(500, function(){ $(this).remove();});
        },
        error: function (e) {
            
            $(btn_id).prop("disabled", false);
        }
    });
}
function admin_delete_ajax_submit(booking_id, btn_id) {
    var booking_card_id = "#booking-card"+booking_id;
    $(btn_id).prop("disabled", true);
    $.ajax({
        type: "DELETE",
        contentType: "application/json",
        url: "/admin/api/booking/"+booking_id,
        dataType: 'json',
        cache: false,
        timeout: 600000,
        success: function (data) {
            $(btn_id).prop("disabled", false);
            $(booking_card_id).addClass("alert-danger");
            $(booking_card_id).fadeOut(500, function(){ $(this).remove();});
        },
        error: function (e) {
            
            $(btn_id).prop("disabled", false);
        }
    });
}

// Функции DRY
function placeIsNotFree(place_id,date,timeStart,timeEnd) {
    $(".place-not-free-alert").css({"display": "block"});
    $(".place-not-free-alert").append(
        "К сожалению данное помещение занято в это время" +
        "<a href='/object/free-date?roomHall=" + place_id+ "&date=" + date+"'> посмотреть свободные даты </a>" +
        "или <a href='/rooms?date="+date+"&timeStart="+timeStart+"&timeEnd="+timeEnd+"'> посмотреть свободные помещения в это время </a>"
    );
}
function wrongTimeEnd(msg){
    $("#wrong-time-end-alert").css({"display":"block"});
    $("#wrong-time-end-alert").append(msg);
}
function setDisplayNoneForErrorsAndClear(){
    $("#wrong-time-end-alert").css({"display":"none"});
    $(".place-not-free-alert").css({"display":"none"});
    $("#phone-error-alert").css({"display":"none"});

    $("#wrong-time-end-alert").empty();
    $(".place-not-free-alert").empty();
    $("#phone-error-alert").empty();
}
/*Errors code (field): {
        Пустые поля:[
            420 - time-start, (timeStart)
            421 - time-end, (timeEnd)
            422 - date, (date)
            431 - institute, (institute)
            432 - structure (structure)
            ]
        },
        423 - time-end <= time-start, (timeEnd)
        424 - Помещение занято (place)
*/




