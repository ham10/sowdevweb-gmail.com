"use strict";
jQuery(document).on('ready', function() {
    var loader_html 	= '<div class="dc-preloader-section"><div class="dc-preloader-holder"><div class="dc-loader"></div></div></div>';
    var is_loggedin     = scripts_vars.is_loggedin;
    var calendar_locale  = scripts_vars.calendar_locale;
    var is_rtl  		= scripts_vars.is_rtl;
    if( calendar_locale  && calendar_locale != null){
        jQuery.datetimepicker.setLocale(calendar_locale);
        moment.locale(calendar_locale);
    }

    //Google Connect
    jQuery(document).on('click', '.dc-loginfor-offer', function (event) {
        event.preventDefault();
        var _this = jQuery(this);
        jQuery('html, body').animate({scrollTop:0}, 'slow');
    });

    /* MOBILE MENU	*/
    function collapseMenu(){
        jQuery('.dc-navigation ul li.menu-item-has-children,.dc-navigation ul li.page_item_has_children, .dc-navdashboard ul li.menu-item-has-children, .dc-navigation ul li.menu-item-has-mega-menu').prepend('<span class="dc-dropdowarrow"><i class="lnr lnr-chevron-right"></i></span>');
        jQuery('.dc-navigation ul li.menu-item-has-children span, .dc-navigation ul li.page_item_has_children span, .dc-navigation ul li.menu-item-has-mega-menu span').on('click', function() {
            jQuery(this).parent('li').toggleClass('dc-open');
            jQuery(this).next().next().slideToggle(300);
        });
        jQuery('.dc-navdashboard ul li.menu-item-has-children').on('click', function(){
            jQuery(this).toggleClass('dc-open');
            jQuery(this).find('.sub-menu').slideToggle(300);
        });
    }

    collapseMenu();
    // Show - Hide Tipso on Click

    jQuery('.show-hide-tipso').on('click', function(e){
        if(jQuery(this).hasClass('clicked')){
            jQuery(this).removeClass('clicked');
            jQuery(this).parent('.dc-tipso').tipso('hide');
        } else {
            jQuery(this).addClass('clicked');
            jQuery(this).parent('.dc-tipso').tipso('show');
        }

    });

    //Toolip init
    function tipso_init(){
        if(jQuery('.dc-tipso').length > 0){
            jQuery('.dc-tipso').tipso({
                tooltipHover	  : true,
                useTitle		  : false,
                background        : scripts_vars.tip_content_bg,
                titleBackground   : scripts_vars.tip_title_bg,
                color             : scripts_vars.tip_content_color,
                titleColor        : scripts_vars.tip_title_color,
            });
        }
    }

    tipso_init();

    //Booking modal
    jQuery('.dc-booking-model').on('click', function(){
        if (scripts_vars.is_loggedin === 'false') {
            jQuery('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.booking_message, {classList: 'important', speed: 200, autoclose: 7000});
            return false;
        }

        if (scripts_vars.user_type !== 'regular_users') {
            jQuery('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.allow_booking, {classList: 'important', speed: 200, autoclose: 7000});
            return false;
        } else {
            jQuery('#appointment').modal('show');
        }
    });

    //on call bookings details
    jQuery('.dc-booking-contacts').on('click', function(){

        var _this 	= jQuery(this);
        var _id		= _this.data('id');
        jQuery('body').append(loader_html);
        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: {
                action	: 'doctreat_bookings_details',
                id		: _id
            },
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery('.dc-modelbooking-contacts').append(response.html);
                    jQuery('#dc-bookingcontactsmodal').modal('show');
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    // post likes
    jQuery('.dcget-likes').on('click', function(){
        event.preventDefault();
        var _this 	= jQuery(this);
        var _id		= _this.data('key');

        jQuery('body').append(loader_html);
        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: {
                action	: 'doctreat_post_likes',
                id		: _id
            },
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    _this.removeClass('dcget-likes');
                    _this.find('a').html(response.html);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    jQuery('.dc-recommend-click').on('click', function(e){
        var _this	= jQuery(this);
        jQuery('.dc-recommend-click').removeClass('dc-active-recommend');
        _this.addClass('dc-active-recommend');
    });

    //Add feedback
    jQuery('.dc-add-feedback').on('click', function(event){
        event.preventDefault();
        var _this 	= jQuery(this);
        var _id		= _this.data('id');
        if (scripts_vars.is_loggedin === 'false') {
            jQuery('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.feedback_message, {classList: 'important', speed: 200, autoclose: 7000});
            return false;
        }

        if (scripts_vars.user_type !== 'regular_users') {
            jQuery('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.allow_feedback, {classList: 'important', speed: 200, autoclose: 7000});
            return false;
        } else {
            jQuery('body').append(loader_html);
            jQuery.ajax({
                type: 'POST',
                url: scripts_vars.ajaxurl,
                data: {
                    action	: 'doctreat_check_feedback',
                    id		: _id
                },
                dataType: "json",
                success: function (response) {
                    jQuery('.dc-preloader-section').remove();
                    if (response.type === 'success') {
                        jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                        jQuery('#feedbackmodal').modal('show');
                    } else {
                        jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                    }
                }
            });
        }
    });

    /*get app link*/
    jQuery(document).on('click', '.dc-get-app', function (event) {
        event.preventDefault();
        var _this 		= jQuery(this);
        var _app_eamil	= _this.prev('input').val();
        if( _app_eamil === '' ){
            jQuery.sticky(scripts_vars.email_required, {classList: 'important', speed: 200, autoclose: 5000});
            return false;
        } else {
            jQuery.ajax({
                type: 'POST',
                url:  scripts_vars.ajaxurl,
                data: {
                    action		: 'doctreat_get_app_link',
                    app_eamil	: _app_eamil
                },
                dataType: "json",
                success: function (response) {
                    jQuery('body').find('.dc-preloader-section').remove();
                    if (response.type === 'success') {
                        jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                    } else {
                        jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});

                    }
                }
            });
        }
    });

    //feedback step 3 form submit
    jQuery(document).on('click', '.dc-formfeedback-btn', function (event) {
        jQuery('body').append(loader_html);
        jQuery.ajax({
            type: 'POST',
            url:  scripts_vars.ajaxurl,
            data: jQuery('.dc-formfeedback').serialize() + '&action=doctreat_add_feedback',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                    window.location.reload();
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //THEME VERTICAL SCROLLBAR
    if(jQuery('.dc-verticalscrollbar').length > 0){
        var _dc_verticalscrollbar = jQuery('.dc-verticalscrollbar');
        _dc_verticalscrollbar.mCustomScrollbar({
            axis:"y",
        });
    }

    //THEME HORIZANTAL SCROLLBAR
    if(jQuery('.dc-horizontalthemescrollbar').length > 0){
        var _dc_horizontalthemescrollbar = jQuery('.dc-horizontalthemescrollbar');
        _dc_horizontalthemescrollbar.mCustomScrollbar({
            axis:"x",
            advanced:{autoExpandHorizontalScroll:true},
        });
    }

    //Question form submit
    jQuery(document).on('change', '.dc-booking-hospitals', function (event) {
        event.preventDefault();
        var _this 	= jQuery(this);
        var _id		= _this.val();
        jQuery('body').append(loader_html);
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: {
                action	: 'doctreat_get_booking_data',
                id		: _id
            },
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery('#booking_service_select').html(response.booking_services);
                    jQuery('.dc-update-timeslots').html(response.time_slots);
                    jQuery('#booking_fee').html(response.consultant_fee);
                    jQuery('.dc-checkbox-service').on('change', function() {
                        var _this 			= jQuery(this);
                        var _id				= _this.val();

                        if(_this.is(":checked") ) {
                            var _text			= _this.data('title');
                            var _price_formate	= _this.data('price-formate');
                            var _price			= _this.data('price');
                            var _fee			= jQuery('.dc-consultant-fee').data('fee');
                            var _service_price	= '<li id="dc-service-'+_id+'"><span>'+_text+'<em>'+_price_formate+' <i class="far fa-question-circle toltip-content dc-service-price" data-price="'+_price+'"></i></em></span></li>';

                            jQuery('#consultant_fee').after(_service_price);
                        } else {
                            jQuery('#dc-service-'+_id).remove();
                        }

                        var total_amount	= 0;
                        jQuery(".dc-service-price").each(function(price,item){
                            total_amount = total_amount + jQuery(item).data('price');
                        });
                        jQuery('#dc-total-price').attr('data-price',total_amount);
                        jQuery('#dc-total-price').html(scripts_vars.currency_symbols+total_amount);

                    });

                    themeAccordion();

                } else {
                    jQuery('#booking_service_select').html('');
                    jQuery('.dc-update-timeslots').html('');
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //render model js
    dcModal();

    //Booking step 1 form submit
    jQuery(document).on('click', '.dc-booking-step1-btn', function (event) {
        'use strict';
        event.preventDefault();

        var dcModalBody = jQuery("#dcModalBody");
        var modalBody1 	= jQuery("#dcModalBody1");
        var dcModalBody = jQuery("#dcModalBody");
        var dcBody2 	= jQuery("#dcbody2");
        var modalBody2 	= jQuery("#dcModalBody2");
        var btn 		= jQuery("#dcbtn");
        var btn2 		= jQuery("#dcbtn2");
        var _this 	= jQuery(this);
        var _id     = parseInt(_this.data('id'));

        jQuery('body').append(loader_html);

        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: 'id=' + _id +'&'+jQuery('.dc-booking-step1').serialize() + '&action=doctreat_booking_step1',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                    jQuery(modalBody2).css('display', 'block');
                    jQuery(btn2).css('display', 'block');
                    jQuery(modalBody1).css('overflow-y', 'hidden')
                    jQuery(modalBody1).css('display', 'none');
                    jQuery(btn).css('display', 'none');
                    jQuery(dcModalBody).css('display', 'none');

                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Booking step 2 form submit
    jQuery(document).on('click', '.dc-booking-step2-btn', function (event) {
        'use strict';
        event.preventDefault();

        var dcBody2 = jQuery("#dcbody2");
        var modalBody2 = jQuery("#dcModalBody2");
        var modalBody3 = jQuery("#dcModalBody3");
        var btn3 = jQuery("#dcbtn3");
        var btn2 = jQuery("#dcbtn2");

        var _this 	= jQuery(this);
        var _id     = parseInt(_this.data('id'));
        jQuery('body').append(loader_html);

        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: 'id=' + _id +'&'+jQuery('.dc-booking-step2').serialize() + '&action=doctreat_booking_step2',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                    jQuery(modalBody2).css('display', 'none');
                    jQuery(btn3).css('display', 'block');
                    jQuery(btn2).css('display', 'none');
                    jQuery(dcBody2).css('display', 'block');
                    jQuery(modalBody3).css('display', 'block');
                    jQuery('.email_address').text(response.email);

                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Booking step 3 form submit
    jQuery(document).on('click', '.dc-booking-step3-btn', function (event) {
        'use strict';
        event.preventDefault();
        var modalBody3 = jQuery("#dcModalBody3");
        var modalBody4 = jQuery("#dcModalBody4");
        var btn3 = jQuery("#dcbtn3");
        var btn4 = jQuery("#dcbtn4");
        var _this 	= jQuery(this);
        var _id     = parseInt(_this.data('id'));
        jQuery('body').append(loader_html);

        jQuery.ajax({
            type: 'POST',
            url:  scripts_vars.ajaxurl,
            data: jQuery('.dc-booking-step3').serialize() + '&action=doctreat_booking_step3',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});

                    jQuery(btn4).css('display', 'block');
                    jQuery(modalBody4).css('display', 'block');
                    jQuery(btn3).css('display', 'none');
                    jQuery(modalBody3).css('display', 'none');
                    window.location = response.checkout_url;

                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Newsletter form submit
    jQuery(document).on('click', '.subscribe_me', function (event) {
        'use strict';
        event.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);

        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: _this.parents('form').serialize() + '&action=doctreat_subscribe_mailchimp',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Question form submit
    jQuery(document).on('click', '.submit-question', function (event) {
        'use strict';
        event.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        jQuery.ajax({
            type: 'POST',
            url: scripts_vars.ajaxurl,
            data: _this.parents('form').serialize() + '&action=doctreat_question_submit',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000});
                    window.location.reload();
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    // Show all services
    jQuery(document).on('click', '.dc-viewall-services', function (e) {
        e.preventDefault();
        var _this 	= jQuery(this);
        _this.hide();
        _this.nextAll().css( "display", "block" );
    });

    //Add to saved doctors
    jQuery(document).on('click', '.dc-add-wishlist', function (e) {
        e.preventDefault();
        jQuery('body').append(loader_html);

        if (scripts_vars.is_loggedin == 'false') {
            jQuery('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.wishlist_message, {classList: 'important', speed: 200, autoclose: 7000});
            return false;
        }

        var _this 		= jQuery(this);
        var id 			= _this.data('id') ;
        var dataString 	= 'id=' + id + '&action=doctreat_follow_doctors';

        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    _this.removeClass('dc-add-wishlist');
                    _this.addClass('dc-liked');
                    //_this.find('em').html( response.text );
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });


    /*OPEN CLOSE */
    jQuery('.dc-loginbtn, .dc-loginheader a').on('click', function(event){
        event.preventDefault();
        jQuery('.dc-loginarea .dc-loginformhold').slideToggle();
    });

    jQuery('.dc-forgot-password').on('click', function (e) {
        jQuery('.do-login-form').addClass('dc-hide-form');
        jQuery('.dc-loginheader span').html('Reset Password');
        jQuery('.do-forgot-password-form').removeClass('dc-hide-form');
    });

    jQuery('.dc-show-login').on('click', function (e) {
        jQuery('.do-login-form').removeClass('dc-hide-form');
        jQuery('.dc-loginheader span').text('Login');
        jQuery('.do-forgot-password-form').addClass('dc-hide-form');
    });

    //DASHBOARD MENU
    if(jQuery('#dc-btnmenutoggle').length > 0){
        jQuery("#dc-btnmenutoggle").on('click', function(event) {
            event.preventDefault();
            jQuery('#dc-wrapper').toggleClass('dc-closemenu');
            jQuery('body').toggleClass('dc-noscroll');
            jQuery('.dc-navdashboard ul.sub-menu').hide();
        });
    }

    //ADD AND REMOVE CLASS
    if(jQuery('.dc-docsearch').length > 0){
        var _dc_docsearch = jQuery('.dc-docsearch');
        _dc_docsearch.on('click',function () {
            jQuery(this).parents('.dc-innerbanner-holder').addClass('dc-open');
            jQuery(this).parent().parents('.dc-innerbanner-holder').addClass('dc-opensearchs');
        });
        var _dc_home = jQuery('.dc-home');
        _dc_home.on('click',function () {
            jQuery('.dc-home').parents('.dc-innerbanner-holder').removeClass('dc-opensearchs');
        });
    }


    //OPEN CLOSE
    jQuery('input[name=myself]').on('click', function(event){
        var _this 		= jQuery(this);
        var selected	= _this.val();
        if( selected === 'myself') {
            jQuery('.dc-docinfoform').hide();
        } else {
            jQuery('.dc-docinfoform').show();
        }
    });

    //ADD Class
    jQuery('#someelse').on('click', function() {
        var _this = jQuery(this);
        _this.parents('.dc-tabbtns').toggleClass('dc-tabbtnsactive');
    });

    //SEARCH CHOSEN
    var config = {
        '.chosen-select'           : {rtl:is_rtl},
        '.chosen-select-deselect'  : {allow_single_deselect:true},
        '.chosen-select-no-single' : {disable_search_threshold:10},
        '.chosen-select-no-results': {no_results_text:'Oops, nothing found!'},
        '.chosen-select-width'     : {width:"95%"}
    }

    for (var selector in config) {
        jQuery(selector).chosen(config[selector]);
    }

    //SEARCH CHOSEN
    jQuery(document).on('click', '.dc-docsearch', function(e){
        e.preventDefault();
        var _this = jQuery(this);
        _this.parents('.dc-opensearchs').find('.dc-advancedsearch-holder').slideToggle(400);
    });

    jQuery(document).on('change','.search_specialities',function () {
        var _this = jQuery(this);
        var _sp_id = this.value;

        if (DT_Editor.elements[_sp_id]) {
            var _options = DT_Editor.elements[_sp_id];
        } else {
            var _options = [];
        }

        var load_repeater = wp.template('load-services-options');
        var data = {options: _options};
        load_repeater = load_repeater(data);

        var _fields	= jQuery('.search_services').empty().append(load_repeater);
        jQuery('.search_services').trigger("chosen:updated");

    });

    //APPOINTMENTS <strong>CALENDER</strong>
    jQuery('#dc-calendar').fullCalendar({
        height: 'auto',
        viewRender: function(currentView){
            var minDate = moment();
            if (minDate >= currentView.start && minDate <= currentView.end) {
                jQuery('.fc-prev-button').prop('disabled', true);
                jQuery('.fc-prev-button').addClass('fc-state-disabled');
            }
            else {
                jQuery('.fc-prev-button').removeClass('fc-state-disabled');
                jQuery('.fc-prev-button').prop('disabled', false);
            }

        },
        dayClick: function(date, jsEvent, view) {
            if (moment().format('YYYY-MM-DD') === date.format('YYYY-MM-DD') || date.isAfter(moment())) {
                var _date			= date.format();
                var _hospital_id	= jQuery('.dc-booking-hospitals').val();
                jQuery(".fc-state-highlight").removeClass("fc-state-highlight");
                jQuery("td[data-date="+_date+"]").addClass("fc-state-highlight");

                if( _hospital_id	== '' ){
                    jQuery.sticky(scripts_vars.location_required, {classList: 'important',position:'top-right', speed: 200, autoclose: 5000});
                    return false;
                }

                jQuery('body').append(loader_html);
                var dataString 	  = '_date='+_date+'&_hospital_id='+_hospital_id+'&action=doctreat_get_slots';
                jQuery.ajax({
                    type: "POST",
                    url: scripts_vars.ajaxurl,
                    data: dataString,
                    dataType: "json",
                    success: function (response) {
                        jQuery('body').find('.dc-preloader-section').remove();
                        if (response.type === 'success') {
                            jQuery('.dc-update-timeslots').html(response.time_slots);
                            jQuery('#appointment_date').val(_date);
                        } else {
                            jQuery('.dc-update-timeslots').html('');
                            jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                        }
                    }
                });
            }
        }
    });

    //Order form submit
    jQuery(document).on('change','.order',function () {
        var _this 	= jQuery(this);
        var _val	= _this.val();
        jQuery('.search_order').val(_val);
        jQuery('#search_form').submit();
    });

    //Orderby form submit
    jQuery(document).on('change','.orderby',function () {
        var _this 	= jQuery(this);
        var _val	= _this.val();
        jQuery('.search_orderby').val(_val);
        jQuery('#search_form').submit();
    });

    //Health form order by
    jQuery(document).on('change','.orderby_healthforum',function () {
        var _this 	= jQuery(this);
        var _val	= _this.val();
        jQuery('#search_orderby_healthforum').val(_val);
        jQuery('#search_form_healthforum').submit();
    });

    //Reset Button
    jQuery(document).on('click','.dc-resetbtn',function () {
        jQuery('#search_form')[0].reset();
    });

});


//Preloader
jQuery(window).load(function () {
    var loading_duration = scripts_vars.loading_duration;
    jQuery(".preloader-outer").delay(loading_duration).fadeOut();
    jQuery(".pins").delay(loading_duration).fadeOut("slow");
});


//Sticky Note
!function(e){e.sticky=e.fn.sticky=function(t,s,i){"function"==typeof s&&(i=s);var a=function(e){var t=0,s=0,i=e.length;if(0===i)return t;for(s=0;s<i;s++)t=(t<<5)-t+e.charCodeAt(s),t&=t;return"s"+Math.abs(t)},c={position:"top-right",speed:"fast",allowdupes:!0,autoclose:5e3,classList:""},r=a(t),n=!0,o=!1;if(s&&e.extend(c,s),e(".sticky").each(function(){e(this).attr("id")===a(t)&&(o=!0,c.allowdupes||(n=!1)),e(this).attr("id")===r&&(r=a(t))}),scripts_vars.sm_success)var l=scripts_vars.sm_success;else l=c.position;e(".sticky-queue").length?e(".sticky-queue").removeClass(["top-right","top-center","top-left","bottom-right","bottom-center","bottom-left","middle-left","middle-right","middle-center"].join(" ")).addClass(l):e("body").append('<div class="sticky-queue '+l+'">'),n&&e(".sticky-queue").prepend('<div id="ID" class="dc-alert-loader alert-dismissible border-POS CLASSLIST" role="alert"><button type="button" class="dc-close" data-dismiss="alert" aria-label="Close"><span aria-hidden="true"><i class="lnr lnr-cross"></i></span></button><div class="dc-description"><p><i class="lnr lnr-bullhorn"></i>NOTE</p></div></div>'.replace("POS",l).replace("ID",r).replace("NOTE",t).replace("CLASSLIST",c.classList)).find("#"+r).slideDown(c.speed,function(){n=!0,i&&"function"==typeof i&&i({id:r,duplicate:o,displayed:n})}),e(".sticky").ready(function(){c.autoclose&&e("#"+r).delay(c.autoclose).fadeOut(c.speed,function(){e(this).remove()})}),e(".jf-close").on("click",function(){var t=e(this);t.parents(".jf-alert").hasClass("sp-cacheit")?e.confirm({title:scripts_vars.cache_title,message:scripts_vars.cache_message,buttons:{Yes:{class:"blue",action:function(){t.parents(".jf-alert").hasClass("cache-verification")?e.cookie("sp_cache_verification_"+scripts_vars.current_user_id,"true",{expires:365}):t.parents(".jf-alert").hasClass("cache-deactivation")&&e.cookie("sp_cache_deactivation_"+scripts_vars.current_user_id,"true",{expires:365}),e("#"+t.parents(".jf-alert").attr("id")).dequeue().fadeOut(c.speed,function(){t.remove()})}},No:{class:"gray",action:function(){return!1}}}}):e("#"+t.parents(".jf-alert").attr("id")).dequeue().fadeOut(c.speed,function(){t.remove()})})}}(jQuery);


// Confirm Box
!function(n){n.confirm=function(i){if(n("#confirmOverlay").length)return!1;var o="";n.each(i.buttons,function(n,i){n="Yes"==n?scripts_vars.yes:"No"==n?scripts_vars.no:n,o+='<a href="#" class="button '+i.class+'">'+n+"<span></span></a>",i.action||(i.action=function(){})});var t=['<div id="confirmOverlay">','<div id="confirmBox">',"<h1>",i.title,"</h1>","<p>",i.message,"</p>",'<div id="confirmButtons">',o,"</div></div></div>"].join("");n(t).hide().appendTo("body").fadeIn();var c=n("#confirmBox .button"),r=0;n.each(i.buttons,function(i,o){c.eq(r++).on("click",function(){return o.action(),n.confirm.hide(),!1})})},n.confirm.hide=function(){n("#confirmOverlay").fadeOut(function(){n(this).remove()})}}(jQuery);

//get distance
function _get_distance(lat1, lon1, lat2, lon2, unit) {
    var radlat1 = Math.PI * lat1 / 180
    var radlat2 = Math.PI * lat2 / 180
    var theta = lon1 - lon2
    var radtheta = Math.PI * theta / 180
    var dist = Math.sin(radlat1) * Math.sin(radlat2) + Math.cos(radlat1) * Math.cos(radlat2) * Math.cos(radtheta);
    dist = Math.acos(dist)
    dist = dist * 180 / Math.PI
    dist = dist * 60 * 1.1515
    if (unit == "K") {
        dist = dist * 1.609344
    }
    if (unit == "N") {
        dist = dist * 0.8684
    }
    return dist
}

// get rounded value
function _get_round(value, decimals) {
    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
}

// string replace URL
function _string_replace_url(url) {
    var _url = url.replace("#038;", "&");
    _url = _url.replace("&", "&");
    return _url;
}

//Map styles
function doctreat_get_map_styles(style) {

    var styles = '';
    if (style == 'view_1') {
        var styles = [{"featureType": "administrative.country", "elementType": "geometry", "stylers": [{"visibility": "simplified"}, {"hue": "#ff0000"}]}];
    } else if (style == 'view_2') {
        var styles = [{"featureType": "water", "elementType": "all", "stylers": [{"hue": "#7fc8ed"}, {"saturation": 55}, {"lightness": -6}, {"visibility": "on"}]}, {"featureType": "water", "elementType": "labels", "stylers": [{"hue": "#7fc8ed"}, {"saturation": 55}, {"lightness": -6}, {"visibility": "off"}]}, {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"hue": "#83cead"}, {"saturation": 1}, {"lightness": -15}, {"visibility": "on"}]}, {"featureType": "landscape", "elementType": "geometry", "stylers": [{"hue": "#f3f4f4"}, {"saturation": -84}, {"lightness": 59}, {"visibility": "on"}]}, {"featureType": "landscape", "elementType": "labels", "stylers": [{"hue": "#ffffff"}, {"saturation": -100}, {"lightness": 100}, {"visibility": "off"}]}, {"featureType": "road", "elementType": "geometry", "stylers": [{"hue": "#ffffff"}, {"saturation": -100}, {"lightness": 100}, {"visibility": "on"}]}, {"featureType": "road", "elementType": "labels", "stylers": [{"hue": "#bbbbbb"}, {"saturation": -100}, {"lightness": 26}, {"visibility": "on"}]}, {"featureType": "road.arterial", "elementType": "geometry", "stylers": [{"hue": "#ffcc00"}, {"saturation": 100}, {"lightness": -35}, {"visibility": "simplified"}]}, {"featureType": "road.highway", "elementType": "geometry", "stylers": [{"hue": "#ffcc00"}, {"saturation": 100}, {"lightness": -22}, {"visibility": "on"}]}, {"featureType": "poi.school", "elementType": "all", "stylers": [{"hue": "#d7e4e4"}, {"saturation": -60}, {"lightness": 23}, {"visibility": "on"}]}];
    } else if (style == 'view_3') {
        var styles = [{"featureType": "water", "stylers": [{"saturation": 43}, {"lightness": -11}, {"hue": "#0088ff"}]}, {"featureType": "road", "elementType": "geometry.fill", "stylers": [{"hue": "#ff0000"}, {"saturation": -100}, {"lightness": 99}]}, {"featureType": "road", "elementType": "geometry.stroke", "stylers": [{"color": "#808080"}, {"lightness": 54}]}, {"featureType": "landscape.man_made", "elementType": "geometry.fill", "stylers": [{"color": "#ece2d9"}]}, {"featureType": "poi.park", "elementType": "geometry.fill", "stylers": [{"color": "#ccdca1"}]}, {"featureType": "road", "elementType": "labels.text.fill", "stylers": [{"color": "#767676"}]}, {"featureType": "road", "elementType": "labels.text.stroke", "stylers": [{"color": "#ffffff"}]}, {"featureType": "poi", "stylers": [{"visibility": "off"}]}, {"featureType": "landscape.natural", "elementType": "geometry.fill", "stylers": [{"visibility": "on"}, {"color": "#b8cb93"}]}, {"featureType": "poi.park", "stylers": [{"visibility": "on"}]}, {"featureType": "poi.sports_complex", "stylers": [{"visibility": "on"}]}, {"featureType": "poi.medical", "stylers": [{"visibility": "on"}]}, {"featureType": "poi.business", "stylers": [{"visibility": "simplified"}]}];
    } else if (style == 'view_4') {
        var styles = [{"elementType": "geometry", "stylers": [{"hue": "#ff4400"}, {"saturation": -68}, {"lightness": -4}, {"gamma": 0.72}]}, {"featureType": "road", "elementType": "labels.icon"}, {"featureType": "landscape.man_made", "elementType": "geometry", "stylers": [{"hue": "#0077ff"}, {"gamma": 3.1}]}, {"featureType": "water", "stylers": [{"hue": "#00ccff"}, {"gamma": 0.44}, {"saturation": -33}]}, {"featureType": "poi.park", "stylers": [{"hue": "#44ff00"}, {"saturation": -23}]}, {"featureType": "water", "elementType": "labels.text.fill", "stylers": [{"hue": "#007fff"}, {"gamma": 0.77}, {"saturation": 65}, {"lightness": 99}]}, {"featureType": "water", "elementType": "labels.text.stroke", "stylers": [{"gamma": 0.11}, {"weight": 5.6}, {"saturation": 99}, {"hue": "#0091ff"}, {"lightness": -86}]}, {"featureType": "transit.line", "elementType": "geometry", "stylers": [{"lightness": -48}, {"hue": "#ff5e00"}, {"gamma": 1.2}, {"saturation": -23}]}, {"featureType": "transit", "elementType": "labels.text.stroke", "stylers": [{"saturation": -64}, {"hue": "#ff9100"}, {"lightness": 16}, {"gamma": 0.47}, {"weight": 2.7}]}];
    } else if (style == 'view_5') {
        var styles = [{"featureType": "water", "elementType": "geometry", "stylers": [{"color": "#e9e9e9"}, {"lightness": 17}]}, {"featureType": "landscape", "elementType": "geometry", "stylers": [{"color": "#f5f5f5"}, {"lightness": 20}]}, {"featureType": "road.highway", "elementType": "geometry.fill", "stylers": [{"color": "#ffffff"}, {"lightness": 17}]}, {"featureType": "road.highway", "elementType": "geometry.stroke", "stylers": [{"color": "#ffffff"}, {"lightness": 29}, {"weight": 0.2}]}, {"featureType": "road.arterial", "elementType": "geometry", "stylers": [{"color": "#ffffff"}, {"lightness": 18}]}, {"featureType": "road.local", "elementType": "geometry", "stylers": [{"color": "#ffffff"}, {"lightness": 16}]}, {"featureType": "poi", "elementType": "geometry", "stylers": [{"color": "#f5f5f5"}, {"lightness": 21}]}, {"featureType": "poi.park", "elementType": "geometry", "stylers": [{"color": "#dedede"}, {"lightness": 21}]}, {"elementType": "labels.text.stroke", "stylers": [{"visibility": "on"}, {"color": "#ffffff"}, {"lightness": 16}]}, {"elementType": "labels.text.fill", "stylers": [{"saturation": 36}, {"color": "#333333"}, {"lightness": 40}]}, {"elementType": "labels.icon", "stylers": [{"visibility": "off"}]}, {"featureType": "transit", "elementType": "geometry", "stylers": [{"color": "#f2f2f2"}, {"lightness": 19}]}, {"featureType": "administrative", "elementType": "geometry.fill", "stylers": [{"color": "#fefefe"}, {"lightness": 20}]}, {"featureType": "administrative", "elementType": "geometry.stroke", "stylers": [{"color": "#fefefe"}, {"lightness": 17}, {"weight": 1.2}]}];
    } else if (style == 'view_6') {
        var styles = [{"featureType": "landscape", "stylers": [{"hue": "#FFBB00"}, {"saturation": 43.400000000000006}, {"lightness": 37.599999999999994}, {"gamma": 1}]}, {"featureType": "road.highway", "stylers": [{"hue": "#FFC200"}, {"saturation": -61.8}, {"lightness": 45.599999999999994}, {"gamma": 1}]}, {"featureType": "road.arterial", "stylers": [{"hue": "#FF0300"}, {"saturation": -100}, {"lightness": 51.19999999999999}, {"gamma": 1}]}, {"featureType": "road.local", "stylers": [{"hue": "#FF0300"}, {"saturation": -100}, {"lightness": 52}, {"gamma": 1}]}, {"featureType": "water", "stylers": [{"hue": "#0078FF"}, {"saturation": -13.200000000000003}, {"lightness": 2.4000000000000057}, {"gamma": 1}]}, {"featureType": "poi", "stylers": [{"hue": "#00FF6A"}, {"saturation": -1.0989010989011234}, {"lightness": 11.200000000000017}, {"gamma": 1}]}];
    } else {
        var styles = [{"featureType": "administrative.country", "elementType": "geometry", "stylers": [{"visibility": "simplified"}, {"hue": "#ff0000"}]}];
    }
    return styles;
}

//convert bytes to KB< MB,GB,TB
function bytesToSize(bytes) {
    var sizes = ['Bytes', 'KB', 'MB', 'GB', 'TB'];
    if (bytes == 0) return '0 Byte';
    var i = parseInt(Math.floor(Math.log(bytes) / Math.log(1024)));
    return Math.round(bytes / Math.pow(1024, i), 2) + ' ' + sizes[i];
};

//validate amount
function validateAmount(_this) {
    if (isNaN(jQuery.trim(jQuery(_this).val()))) {
        jQuery(_this).val("");
    } else {
        var amt = jQuery(_this).val();
        if (amt != '') {
            if (amt.length > 16) {
                amt = amt.substr(0, 16);
                jQuery(_this).val(amt);
            }
            //amount = amt;
            return true;
        } else {
            //amount = gloAmount;
            return true;
        }
    }
}

//get random ID
function get_random_number() {
    function s4() {
        return Math.floor((1 + Math.random()) * 0x10000)
            .toString(16)
            .substring(1);
    }
    return s4();
}

//Cookie
!function(e){"function"==typeof define&&define.amd?define(["jquery"],e):"object"==typeof exports?module.exports=e(require("jquery")):e(jQuery)}(function(e){var n=/\+/g;function o(e){return t.raw?e:encodeURIComponent(e)}function i(e){return o(t.json?JSON.stringify(e):String(e))}function r(o,i){var r=t.raw?o:function(e){0===e.indexOf('"')&&(e=e.slice(1,-1).replace(/\\"/g,'"').replace(/\\\\/g,"\\"));try{return e=decodeURIComponent(e.replace(n," ")),t.json?JSON.parse(e):e}catch(e){}}(o);return e.isFunction(i)?i(r):r}var t=e.cookie=function(n,c,u){if(arguments.length>1&&!e.isFunction(c)){if("number"==typeof(u=e.extend({},t.defaults,u)).expires){var s=u.expires,a=u.expires=new Date;a.setMilliseconds(a.getMilliseconds()+864e5*s)}return document.cookie=[o(n),"=",i(c),u.expires?"; expires="+u.expires.toUTCString():"",u.path?"; path="+u.path:"",u.domain?"; domain="+u.domain:"",u.secure?"; secure":""].join("")}for(var d,f=n?void 0:{},p=document.cookie?document.cookie.split("; "):[],l=0,m=p.length;l<m;l++){var x=p[l].split("="),g=(d=x.shift(),t.raw?d:decodeURIComponent(d)),v=x.join("=");if(n===g){f=r(v,c);break}n||void 0===(v=r(v))||(f[g]=v)}return f};t.defaults={},e.removeCookie=function(n,o){return e.cookie(n,"",e.extend({},o,{expires:-1})),!e.cookie(n)}});

/* THEME ACCORDION */
function themeAccordion() {
    jQuery('.dc-panelcontent').hide();
    jQuery('.dc-accordion .dc-paneltitle:first').addClass('active').next().slideDown('slow');
    jQuery('.dc-accordion .dc-paneltitle').on('click',function() {
        if(jQuery(this).next().is(':hidden')) {
            jQuery('.dc-accordion .dc-paneltitle').removeClass('active').next().slideUp('slow');
            jQuery(this).toggleClass('active').next().slideDown('slow');
        }
    });
}

function childAccordion() {
    jQuery('.dc-subpanelcontent').hide();
    jQuery('.dc-childaccordion .dc-subpaneltitle:first').addClass('active').next().slideDown('slow');
    jQuery('.dc-childaccordion .dc-subpaneltitle').on('click',function() {
        if(jQuery(this).next().is(':hidden')) {
            jQuery('.dc-childaccordion .dc-subpaneltitle').removeClass('active').next().slideUp('slow');
            jQuery(this).toggleClass('active').next().slideDown('slow');
        }
    });
}

//DC Modal
function dcModal(){
    var dcmodal = jQuery("#appointment");
    jQuery(dcmodal).on('shown.bs.modal', function () {
        jQuery("#dc-calendar").fullCalendar('render');
    });
}

function dc_doctor_booking_model(){
    var dcmodal = jQuery("#booking-appointment");
    jQuery(dcmodal).on('shown.bs.modal', function () {
        jQuery("#dc-calendar").fullCalendar('render');
    });
}
// Email validtion
function doctreat_validate_email(email) {
    var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    return regex.test(email);
}
//SVG Render
jQuery("img.amsvglogo").each(function(){var t=jQuery(this),r=t.attr("id"),a=t.attr("class"),e=t.attr("src");jQuery.get(e,function(e){var i=jQuery(e).find("svg");void 0!==r&&(i=i.attr("id",r)),void 0!==a&&(i=i.attr("class",a+" replaced-svg")),i=i.removeAttr("xmlns:a"),t.replaceWith(i)},"xml")});
