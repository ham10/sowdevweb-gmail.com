jQuery(document).ready(function(){
    var loader_html 	= '<div class="dc-preloader-section"><div class="dc-preloader-holder"><div class="dc-loader"></div></div></div>';
    //Registration Step One
    jQuery(document).on('click', '.rg-step-one', function (e) {
        e.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        var dataString = jQuery('.dc-formregister').serialize() + '&action=doctreat_process_registration_step_one';
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                    window.location.replace(response.retrun_url);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Process Registration
    jQuery(document).on('click', '.dc-step-two', function (e) {
        e.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        var dataString = jQuery('.dc-formregister-step-two').serialize() + '&action=doctreat_process_registration_step_two';
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                    window.location.replace(response.retrun_url);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Submit Validation Form
    jQuery(document).on('click', '.dc-step-three', function(e){
        e.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        var dataString = jQuery('.dc-verifyform').serialize() + '&action=doctreat_process_registration_step_three';
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                    window.location.replace(response.retrun_url);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Registration Complete
    jQuery(document).on('click', '.dc-go-to-dashboard', function (e) {
        e.preventDefault();
        var _this = jQuery(this);
        var id = _this.data('id');
        jQuery('body').append(loader_html);
        var dataString = 'id='+ id+ '&action=doctreat_process_registration_complete';
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                    window.location.replace(response.retrun_url);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });


    //Resend Verification Code
    jQuery(document).on('click', '.dc-resend-code', function (e) {
        e.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        var dataString = 'action=doctreat_resend_verification_code';
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: dataString,
            dataType: "json",
            success: function (response) {
                jQuery('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });


    //Reset password Ajax
    jQuery(document).on('click', '.dc-change-password', function (event) {
        event.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);

        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: jQuery('.dc-reset_password_form').serialize() + '&action=doctreat_ajax_reset_password',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type == 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                    jQuery('.dc-reset_password_form').get(0).reset();
                    window.location.replace(response.redirect_url);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Login Ajax
    jQuery(document).on('click', '.do-login-button', function (event) {
        event.preventDefault();
        var _this = jQuery(this);
        jQuery('body').append(loader_html);
        var _serialize = _this.parents('form.do-login-form').serialize();
        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: _serialize + '&action=doctreat_ajax_login',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type === 'success') {
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 500000, position: 'top-right'});
                    window.location.replace(response.redirect);
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Lost passowrd Box
    jQuery('.dc-forgot-password').on('click', function (e) {
        jQuery('.do-login-form').addClass('dc-hide-form');
        jQuery('.dc-loginheader span').html(scripts_vars.forgot_password);
        jQuery('.do-forgot-password-form').removeClass('dc-hide-form');
    });
    jQuery('.dc-show-login').on('click', function (e) {
        jQuery('.do-login-form').removeClass('dc-hide-form');
        jQuery('.dc-loginheader span').text(scripts_vars.login);
        jQuery('.do-forgot-password-form').addClass('dc-hide-form');
    });

    //Lost password Ajax
    jQuery(document).on('click', '.do-get-password', function (event) {
        event.preventDefault();
        var _this = jQuery(this);
        var _email = jQuery('.get_password').val();
        jQuery('body').append(loader_html);

        if (!(doctreat_isValidEmailAddress(_email))) {
            jQuery('body').find('.dc-preloader-section').remove();
            jQuery.sticky(scripts_vars.valid_email, {classList: 'important', speed: 200, autoclose: 5000});
            return false;
        }

        jQuery.ajax({
            type: "POST",
            url: scripts_vars.ajaxurl,
            data: jQuery('.do-forgot-password-form').serialize() + '&action=doctreat_ajax_lp',
            dataType: "json",
            success: function (response) {
                jQuery('body').find('.dc-preloader-section').remove();
                if (response.type == 'success') {
                    jQuery('.do-forgot-password-form').get(0).reset();
                    jQuery.sticky(response.message, {classList: 'success', speed: 200, autoclose: 5000 });
                } else {
                    jQuery.sticky(response.message, {classList: 'important', speed: 200, autoclose: 5000});
                }
            }
        });
    });

    //Email Validation
    function doctreat_isValidEmailAddress(emailAddress) {
        var pattern = /^([a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+(\.[a-z\d!#$%&'*+\-\/=?^_`{|}~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]+)*|"((([ \t]*\r\n)?[ \t]+)?([\x01-\x08\x0b\x0c\x0e-\x1f\x7f\x21\x23-\x5b\x5d-\x7e\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|\\[\x01-\x09\x0b\x0c\x0d-\x7f\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))*(([ \t]*\r\n)?[ \t]+)?")@(([a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\d\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.)+([a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]|[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF][a-z\d\-._~\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]*[a-z\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])\.?$/i;
        return pattern.test(emailAddress);
    }

});