/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    $('.m-select2').select2();
    var DatatablesBasicBasic = {init: function () {
            var e;
            (e = $("#m_table_1")).DataTable({
                responsive: true,
                dom: "<'row'<'col-sm-6'B><'col-sm-6'f><'col-sm-12'tr>>\n\t\t\t<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",
                lengthMenu: [25, 50, 100, "Tutti"],
                pageLength: 25,
                searchDelay: 500,
                processing: true,
                //serverSide: true,
                ajax: {
                    url: 'Query?type=queryUser',
                    type: 'POST',
                    data: getFormData($('#formsearch'))
                },
                columns: [
                    {defaultContent: ''},
                    {data: 'username'},
                    {data: 'nome'},
                    {data: 'cognome'},
                    {data: 'email'},
                    {data: 'descrizionetipo'}
                ],
                columnDefs: [
                    {
                        targets: 0,
                        className: 'text-center',
                        orderable: false,
                        render: function (data, type, row, meta) {

                            var option = '<a class="btn btn-sm btn-outline-success m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air" href="javascript:void(0);" onclick="deleteUser(\'' + row.username + '\');" data-toggle="popover" data-placement="right" title="Elimina" data-content="Elimina Utente"><i class="fa fa-trash-alt"></i></a>';
                            return option;
                        }
                    }],
                buttons: [],
                language: {
                    lengthMenu: "Mostra _MENU_",
                    emptyTable: "Nessun Risultato Trovato",
                    info: "Mostrati da _START_ a _END_ di _TOTAL_ risultati",
                    infoEmpty: "Nessun risultato",
                    infoFiltered: "(filtrati su _MAX_ risultati totali)",
                    infoPostFix: "",
                    thousands: ".",
                    loadingRecords: "Caricamento...",
                    processing: "Caricamento...",
                    search: "Ricerca:",
                    zeroRecords: "Nessun Risultato Corrispondente",
                    paginate: {
                        first: "Inizio",
                        last: "Fine",
                        next: "Avanti",
                        previous: "Indietro"
                    }
                },
                order: [],
                drawCallback: function () {
                    $('[data-toggle="tooltip"]').tooltip();
                    $('[data-toggle="popover"]').popover({trigger: "hover"});
                }
            });
        }};
    jQuery(document).ready(function () {
        DatatablesBasicBasic.init();
    });
    $("#search").click(function () {
        refresh($("#m_table_1"), "Query?type=queryUser", $('#formsearch').serialize());
    });
    $("#formsearch").keypress(function (e) {
        if (e.which === 13) {
            refresh($("#m_table_1"), "Query?type=queryUser", $('#formsearch').serialize());
        }
    });

    function deleteUser(username) {
        swalConfirm("Eliminare", "Eliminare utente?", function () {
            showLoad();
            $.post("Operations", {type: "deleteUser", "username": username})
                    .done(function (resp) {
                        if (resp.result) {
                            swalSuccess('Successo', 'Utente eliminato con successo');
                            reload($("#m_table_1"));
                        } else {
                            swalError('Errore', resp.message);
                        }
                    });
        });
    }
});