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
                    url: 'Query?type=queryEccessi',
                    type: 'POST',
                    data: getFormData($('#formsearch'))
                },
                columns: [
                    {defaultContent: ''},
                    {data: 'endorse'},
                    {data: 'pratica'},
                    {data: 'codice_cir'},
                    {data: 'data'},
                    {data: 'ldv'},
                    {data: 'tvei'},
                    {data: 'data_accettazione', type: 'date-euro'}
                ],
                columnDefs: [
                    {
                        targets: 0,
                        className: 'text-center',
                        orderable: false,
                        render: function (data, type, row, meta) {
                            var option = '<div class="dropdown dropdown-inline">'
                                    + '<button type="button" class="btn btn-icon btn-sm btn-icon-md btn-circle" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">'
                                    + '<i class="flaticon-more-1"></i>'
                                    + '</button>'
                                    + '<div class="dropdown-menu dropdown-menu-left">';
                            /*Pubblicare la pratica in eccesso tramite sweetalert (25/02/21)*/
                            // return option;
                            if (row.pubblica === 1) {
                                var pubblicata = '<a class="btn btn-sm btn-outline-success m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air" href="javascript:void(0);" onclick="return false;" data-toggle="popover" data-placement="right" title="Pubblica" data-content="Pratica gi&#224; pubblicata."><i class="fa fa-check"></i></a>';
                                return pubblicata;
                            } else {
                                var pubblicare = '<a class="btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air" href="javascript:void(0);" onclick="pubblica(\'' + row.endorse + '\');" data-toggle="popover" data-placement="right" title="Pubblica" data-content="Pubblica Pratica"><i class="fa fa-share-square"></i></a>';
                                return pubblicare;
                            }


                        }
                    }, {
                        targets: 4,
                        type: 'date-euro',
                        render: function (data, type, row, meta) {
                            return formattedDate(new Date(data));
                        }
                    }, {
                        targets: 7,
                        type: 'date-euro',
                        render: function (data, type, row, meta) {
                            return formattedDateTime(new Date(data));
                        }
                    }
                ],
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
        }
    };
    jQuery(document).ready(function () {
        DatatablesBasicBasic.init();
    });

    $("#search").click(function () {
        refresh($("#m_table_1"), "Query?type=queryEccessi", $('#formsearch').serialize());
    });
    $(".datepicker").change(function () {
        var from = $("#from");
        var to = $("#to");
        if (from.val() !== "" && to.val() !== "") {
            var d_from = moment(from.val(), "DD/MM/YYYY").toDate();
            var d_to = moment(to.val(), "DD/MM/YYYY").toDate();
            if (this.id === "from") {
                if (d_from > d_to) {
                    to.val(from.val());
                }
            } else {
                if (d_from > d_to) {
                    from.val(to.val());
                }
            }
        }
    });

    $("#formsearch").keypress(function (e) {
        if (e.which === 13) {
            refresh($("#m_table_1"), "Query?type=queryEccessi", $('#formsearch').serialize());
        }
    });

    /*Pubblicare la pratica in eccesso tramite sweetalert (25/02/21)*/
    function pubblica(endorse) {
        Swal.fire({
            title: "Pubblica Pratica",
            text: "Sei sicuro di voler pubblicare la pratica?",
            showCancelButton: true,
            confirmButtonColor: '#34bfa3',
            cancelButtonColor: '#898b96',
            confirmButtonText: 'Si',
            cancelButtonText: 'No'
        }).then((result) => {
            if (result.value) {
                $.ajax({
                    url: "Operations?type=PubblicaPratica",
                    type: "POST",
                    data: {
                        endorse: endorse
                    },
                    success: function () {
                        refresh($("#m_table_1"), "Query?type=queryEccessi", $('#formsearch').serialize());
                        Swal.fire('Operazione completata', 'Pratica pubblicata con successo', 'success');
                    },
                    error: function () {
                        Swal.fire('Errore', 'Non &egrave; stato possibile pubblicare la pratica', 'error');
                    }
                });

            }
        });
    }
});