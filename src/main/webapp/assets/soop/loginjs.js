$(document).ready(function () {   //same as: $(function() { 
    $("#submit_pwd").click(function () {
        //form, success_title_msg, success_msg, ctrl, reload
        submitForm($("#m_form_pwd"), "Richiesta inviata!",
        "Se hai inserito un nome utente valido Riceverai a breve una mail con la nuova password all'indirizzo ad esso associato.",
        !checkObblFieldsContent($("#m_form_pwd")), false);
    });
});