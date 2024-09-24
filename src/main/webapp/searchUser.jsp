<%-- 
    Document   : dash
    Created on : 6-mar-2020, 11.22.37
    Author     : rcosco
--%>
<%@page import="rc.so.entity.Lavorazione"%>
<%@page import="rc.so.engine.Action"%>
<%@page import="rc.so.entity.Corriere"%>
<%@page import="rc.so.entity.Corriere"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html lang="en">

    <!-- begin::Head -->
    <head>
        <meta charset="utf-8" />
        <title>Findomestic | Trova Utente</title>
        <meta name="description" content="Latest updates and statistic charts">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">

        <!--end::Web font -->
        <!--begin::Global Theme Styles -->
        <link href="assets/soop/bootstrap.5.0.2.css" rel="stylesheet" type="text/css" />
        <link href="assets/vendors/base/vendors.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/demo/default/base/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/demo/demo2/base/style.bundle.css" rel="stylesheet" type="text/css" />
            <script src="assets/fancy/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="assets/fancy/js/jquery.fancybox.2.1.5.min.js" type="text/javascript"></script>
<script type="text/javascript" src="assets/fancy/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/fancy/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        <script type="text/javascript" src="assets/fancy/js/fancy.js"></script>
        <link href="assets/vendors/custom/jquery-ui/jquery-ui.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/soop/datatables.bundle.1.11.3.css" rel="stylesheet" type="text/css" />
        <script src="assets/app/fontawesome-free-5.12.1-web/js/all.js" data-auto-replace-svg="nest"></script>
        <link rel="shortcut icon" href="favicon.ico" />

    </head>

    <!-- end::Head -->
    <!-- begin::Body -->
    <body class="m-page--wide m-header--fixed m-header--fixed-mobile m-footer--push m-aside--offcanvas-default">
        <div class="m-grid m-grid--hor m-grid--root m-page">
            <%@ include file="menu/head.jsp"%>
            <div class="m-grid__item m-grid__item--fluid  m-grid m-grid--ver-desktop m-grid--desktop 	m-container m-container--responsive m-container--xxl m-page__container m-body">
                <div class="m-grid__item m-grid__item--fluid m-wrapper">
                    <div class="m-subheader ">
                        <div class="d-flex align-items-center">
                            <div class="mr-auto">
                                <h3 class="m-subheader__title ">Utenti - Cerca</h3>
                            </div>
                        </div>
                    </div>
                    <div class="m-content">
                        <div class="row">
                            <div class="col-xl-12">
                                <div class="m-portlet m-portlet--tab">
                                    <div class="m-portlet__head">
                                        <div class="m-portlet__head-caption">
                                            <div class="m-portlet__head-title">
                                                <span class="m-portlet__head-icon m--hide">
                                                    <span class="la la-gear"></span>
                                                </span>
                                                <h3 class="m-portlet__head-text">
                                                    <span class="fas fa-search"></span>&nbsp; RICERCA:
                                                </h3>
                                            </div>
                                        </div>
                                    </div>
                                    <form action="#" method="post" id="formsearch" class="m-form m-form--fit m-form--label-align-right ">
                                        <div class="m-portlet__body">
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>NOME</label>
                                                    <input type="text" class="form-control m-input m-input--pill obbligatory" name="nome" id ="nome" placeholder="nome">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>COGNOME</label>
                                                    <input type="text" class="form-control m-input m-input--pill obbligatory" name="cognome" id="cognome" placeholder="cognome">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>USERNAME</label>
                                                    <input type="text" name="username" id="username"  class="form-control  m-input m-input--pill obbligatory" placeholder="username"/>
                                                </div>
                                                <%if (us_rolecod.equals("1")) {%>
                                                <div class="col-lg-3">
                                                    <label>TIPO</label>
                                                    <select class="form-control m-select2" name="tipo">
                                                        <option value="-">. . .</option>
                                                        <option value="2">FINDOMESTIC</option>
                                                        <option value="1">SETA/ENG</option>
                                                    </select>
                                                </div>
                                            </div>
                                            <%} else if (us_rolecod.equals("2")) {%>
                                            <input value="2" name="tipo" type="hidden">
                                            <%}%>
                                        </div>
                                        <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                            <div class="m-form__actions m-form__actions--solid">
                                                <a href="javascript:void(0);" id="search"  class="btn btn-brand">Ricerca</a>
                                                &nbsp;
                                                <a href="<%=pageName%>" class="btn btn-secondary">Reset</a>
                                            </div>
                                        </div>
                                    </form>
                                    <div class="m-portlet__body" id="offsetresult">
                                        <!--begin: Datatable -->
                                        <table class="table table-striped- table-bordered table-hover" id="m_table_1">
                                            <caption>Result Users</caption>
                                            <thead>
                                                <tr>
                                                    <th scope="col">Azioni</th>
                                                    <th scope="col">Username</th>
                                                    <th scope="col">Nome</th>
                                                    <th scope="col">Cognome</th>
                                                    <th scope="col">Email</th>
                                                    <th scope="col">Tipo</th>
                                                </tr>
                                            </thead>
                                            <tbody>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!--end::Portlet-->
                    </div>
                    <!--End::Section-->
                </div>
            </div>
        </div>
        <!-- end::Body -->
        <!-- begin::Footer -->
        <%@ include file="menu/foot.jsp"%>
        <!-- end::Footer -->
    </div>
    <!-- end:: Page -->
    <!-- begin::Quick Sidebar -->

    <!-- end::Quick Sidebar -->
    <!-- begin::Scroll Top -->
    <div id="m_scroll_top" class="m-scroll-top">
        <span class="la la-arrow-up"></span>
    </div>



    <script src="assets/soop/bootstrap.5.0.2.bundle.js" type="text/javascript"></script>
    <script src="assets/soop/jquery-ui.js"></script>
    <link rel="stylesheet" type="text/css" href="assets/fancy/css/jquery.fancybox.css?v=2.1.5" media="screen" />
    <script type="text/javascript" src="assets/fancy/js/fancy.js"></script>
    <script src="assets/demo/demo2/base/scripts.bundle.js" type="text/javascript"></script>
    <script src="assets/soop/bootstrap-datepicker.1.9.min.js" type="text/javascript"></script>
    <!--end::Page Vendors -->
    <!--begin::Page Scripts -->
    <script src="assets/soop/datatables.bundle.1.11.3.js"></script>
    <script src="assets/app/js/date.js" type="text/javascript"></script>
    <script src="assets/app/js/webfont.js" type="text/javascript"></script>
    <script src="assets/soop/select2.4.1.0.rc.min.js"></script>
    <script src="assets/soop/tableUtils.js" type="text/javascript"></script>    
    <script src="assets/soop/utility.js" type="text/javascript"></script>
    <script src="assets/soop/searchuser_js.js" type="text/javascript"></script>
    <!--end::Page Scripts -->
</body>
<!-- end::Body -->
</html>
