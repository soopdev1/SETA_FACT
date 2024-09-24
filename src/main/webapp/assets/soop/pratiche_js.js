/* 
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/JavaScript.js to edit this template
 */

$(document).ready(function () {
    $('.m-select2').select2({
        placeholder: 'Seleziona',
        allowClear: !0
    });
    var DatatablesBasicBasic = {init: function () {
            var e;
            (e = $("#m_table_1")).DataTable({
                responsive: true,
                dom: "<'row'<'col-sm-6'B><'col-sm-6'f><'col-sm-12'tr>>\n\t\t\t<'row'<'col-sm-12 col-md-5'i><'col-sm-12 col-md-7 dataTables_pager'lp>>",
                lengthMenu: [25, 50, 100],
                pageLength: 25,
                searchDelay: 500,
                processing: true,
                ajax: {
                    url: 'Query?type=queryPratiche',
                    type: 'POST',
                    data: $('#formsearch').serializeArray()
                },
                columns: [
                    {data: 'azioni', orderable: false, className: 'text-nowrap'},
                    {data: 'tipodoc'},
                    {data: 'lavorazione'},
                    {data: 'pratica'},
                    {data: 'cir'},
                    {data: 'cliente'},
                    {data: 'tvei'},
                    {data: 'ldv'},
                    {data: 'lotto'},
                    {data: 'data', type: 'date-euro'},
                    {data: 'ws'},
                    {data: 'invii'}
                ],
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
                buttons: [
                    {
                        extend: 'excelHtml5',
                        title: 'Pratiche export',
                        text: "<i class='far fa-file-excel'></i> Excel",
                        exportOptions: {
                            columns: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11]
                        }
                    }
                ],
                order: [],
                initComplete: function (settings, json) {
                    $('[data-toggle="tooltip"]').tooltip();
                    $('[data-toggle="popover"]').popover({trigger: "hover"});
                }
            });
        }};
    jQuery(document).ready(function () {
        DatatablesBasicBasic.init();
    });
});