/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    $('.m-select2').select2();

    function ctrlForm() {
        var err = false;
        err = checkObblFieldsContent($("#form")) ? true : err;
        err = checkEmail($("#email")) ? true : err;
        err = checkUsername() ? true : err;
        return err;
    }

    function checkUsername() {
        var check = true;
        if ($("#username").val() === "") {
            return check;
        }
        $.ajax({
            async: false,
            type: 'POST',
            url: "Query",
            data: {type: "checkUsername", username: $("#username").val()},
            success: function (data) {
                if (data === true || data === 'true') {
                    fastSwalShow("<h5>Attenzione! Username gi&agrave; presente</h5>", "");
                    $("#username").removeClass("is-valid").addClass("is-invalid");
                } else {
                    check = false;
                }
            }
        });
        return check;
    }

    $("#submit").click(function () {
        submitForm($("#form"), "Creato!", "Utente creato con successo", !ctrlForm(), false);
    });
});