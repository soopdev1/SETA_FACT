/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function checkEmail(email) {
    var re = /^(([^<>()\[\]\\.,;:\s@"]+(\.[^<>()\[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/;
    if (email.val() == '' || !re.test(email.val().toLowerCase())) {
        email.removeClass("is-valid").addClass("is-invalid");
        return true;
    } else {
        email.removeClass("is-invalid").addClass("is-valid");
        return false;
    }
}

function fastSwalElementResponsive(HTMLmessage, buttonMessage) {
    swal.fire({
        html: HTMLmessage,
        buttonsStyling: false,
        scrollbarPadding: true,
        confirmButtonText: buttonMessage,
//        confirmButtonClass: "btn btn-brand",
    });
}

function showLoad() {
    swal.fire({
        title: '',
        text: '',
        onOpen: function () {
            swal.showLoading()
        },
        customClass: {
            container: 'my-swal',
        }
    });
}
function showLoad(title) {
    swal.fire({
        title: title,
        text: '',
        onOpen: function () {
            swal.showLoading()
        },
        customClass: {
            container: 'my-swal',
        }
    });
}

function fastSwalShow(HTMLmessage, animation) {
    swal.fire({
        html: HTMLmessage,
        showCloseButton: true,
        showCancelButton: false,
        showConfirmButton: false,
        width: '50%',
        buttonsStyling: false,
        animation: false,
        customClass: {
            popup: 'animated ' + animation,
            container: 'my-swal',
        }
    });
}

function swalSuccessReload(title, HTMLmessage) {
    swal.fire({
        "title": '<h2 class="kt-font-io-n"><b>' + title + '</b></h2><br>',
        "html": "<h4>" + HTMLmessage + "</h4><br>",
        "type": "success",
        "confirmButtonColor": '#363a90',
        "confirmButtonClass": "btn btn-brand",
        customClass: {
            container: 'my-swal',
        }
    }).then(function () {
        showLoad();
        location.reload();
    });
}

function swalSuccess(title, HTMLmessage) {
    swal.fire({
        "title": '<h2 class="kt-font-io-n"><b>' + title + '</b></h2><br>',
        "html": "<h4>" + HTMLmessage + "</h4><br>",
        "type": "success",
        "confirmButtonColor": '#363a90',
        "confirmButtonClass": "btn btn-brand",
        customClass: {
            container: 'my-swal',
        }
    });
}
function swalError(title, message) {
    swal.fire({
        "title": title,
        "html": message,
        "type": "error",
        cancelButtonClass: "btn btn-secondary",
        customClass: {
            container: 'my-swal',
        }
    });
}
function swalWarning(title, message) {
    swal.fire({
        "title": title,
        "html": message,
        "type": "warning",
        cancelButtonClass: "btn btn-secondary",
        customClass: {
            container: 'my-swal',
        }
    });
}

function swalConfirm(title, HTMLmessage, func) {
    swal.fire({
        "title": '<h2 class="kt-font-io-n"><b>' + title + '</b></h2><br>',
        "html": "<h4>" + HTMLmessage + "</h4><br>",
        animation: false,
        showCancelButton: true,
        confirmButtonText: '&nbsp;<i class="la la-check"></i>',
        cancelButtonText: '&nbsp;<i class="la la-close"></i>',
        cancelButtonClass: "btn btn-secondary",
        confirmButtonClass: "btn btn-brand",
        customClass: {
            popup: 'large-swal animated bounceInUp',
        },
    }).then((result) => {
        if (result.value) {
            func();
        } else {
            swal.close();
        }
    });
}


function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function closeSwal() {
    swal.close();
}

function checkObblFieldsContent(content) {
    var err = false;
    content.find('input.obbligatory').each(function () {
        if ($(this).val() == '') {
            err = true;
            $(this).removeClass("is-valid").addClass("is-invalid");
        } else {
            $(this).removeClass("is-invalid").addClass("is-valid");
        }
    });
    content.find('textarea.obbligatory').each(function () {
        if ($(this).val() == '') {
            err = true;
            $(this).removeClass("is-valid").addClass("is-invalid");
        } else {
            $(this).removeClass("is-invalid").addClass("is-valid");
        }
    });
    content.find('select.obbligatory').each(function () {
        if ($(this).val() == '' || $(this).val() == '-') {
            err = true;
            $('#' + this.id + '_div').removeClass("is-valid-select").addClass("is-invalid-select");
        } else {
            $('#' + this.id + '_div').removeClass("is-invalid-select").addClass("is-valid-select");
        }
    });
    return err;
}

function resetInput() {
    $('.form-control').val('');
    $('.form-control').removeClass('is-valid');
    $('.custom-file-input').val('');
    $('.custom-file-label').html('');
    $('.custom-file-input').removeClass('is-valid');
    $('select').val('-');
    $('select').trigger('change');
    $('div.dropdown.bootstrap-select.form-control.kt-').removeClass('is-valid is-valid-select');
}

function submitForm(form, success_title_msg, success_msg, ctrl, reload) {
    if (ctrl) {
        showLoad();
        form.ajaxSubmit({
            error: function () {
                closeSwal();
                swalError("Errore", "Riprovare, se l'errore persiste contattare l'assistenza");
            },
            success: function (json) {
                closeSwal();
                if (json.result) {
                    var message = json.message !== null ? ".<br>" + json.message : "";
                    if (reload) {
                        swalSuccessReload(success_title_msg, success_msg);
                    } else {
                        swalSuccess(success_title_msg, success_msg + message);
                    }
                } else {
                    swalError("Errore!", json.message);
                }
            }
        });
    }
}

function getFormData($form) {
    var unindexed_array = $form.serializeArray();
    var indexed_array = {};

    $.map(unindexed_array, function (n, i) {
        indexed_array[n['name']] = n['value'];
    });

    return indexed_array;
}

function formattedDate(d) {
    return [d.getDate(), d.getMonth() + 1, d.getFullYear()]
            .map(n => n < 10 ? `0${n}` : `${n}`).join('/');
}

function formattedDateTime(d) {
    var day = [d.getDate(), d.getMonth() + 1, d.getFullYear()]
            .map(n => n < 10 ? `0${n}` : `${n}`).join('/');
    var time = [d.getHours(), d.getMinutes(), d.getSeconds()]
            .map(n => n < 10 ? `0${n}` : `${n}`).join(':');
    return day + " " + time;
}