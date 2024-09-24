<%-- 
    Document   : dettagli
    Created on : 16-mar-2020, 15.54.28
    Author     : rcosco
--%>

<%@page import="java.util.Date"%>
<%@page import="rc.so.engine.Action"%>
<%@page import="rc.so.entity.Pratica"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    if (session.getAttribute("us_cod") == null) {
        response.sendRedirect("login.jsp");
    } else {
%>
<!DOCTYPE html>
<html xml:lang="">
    <head>
        <meta charset="utf-8" />
        <title>Findomestic | Dettagli Pratica</title>
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
    <body class="page-content-white">

        <!-- begin:: Page -->


        <!-- begin::Header -->
        <!-- end::Header -->

        <!-- begin::Body -->
        <div class="m-grid__item m-grid__item--fluid  m-grid m-grid--ver-desktop m-grid--desktop 	m-container m-container--responsive m-container--xxl m-page__container m-body">
            <div class="m-grid__item m-grid__item--fluid m-wrapper">

                <!-- BEGIN: Subheader -->


                <!-- END: Subheader -->
                <div class="m-content">
                    <div class="d-flex align-items-center">
                        <div class="mr-auto">
                            <h3 class="m-subheader__title ">Dettagli documento</h3>
                        </div>
                    </div>
                    <!--begin:: Widgets/Stats-->


                    <!--end:: Widgets/Stats-->
                    <%
                        String ido = request.getParameter("ido");
                        if (ido != null) {
                            Pratica pr = Action.getPratica_doc(ido);
                            if (pr != null) {%>
                    <div class="m-portlet">
                        <div class="m-portlet__body m-portlet__body--no-padding">
                            <div class="row m-row--no-padding m-row--col-separator-xl">
                                <div class="col-md-12 col-lg-12 col-xl-4">

                                    <!--begin:: Widgets/Stats2-1 -->
                                    <div class="m-widget1">
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Id Documento</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-brand"><%=pr.getIddocumento()%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Endorse</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-danger"><%=pr.getEndorse()%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Corriere</h3>

                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-success"><%=pr.getCorriere()%></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--end:: Widgets/Stats2-1 -->
                                </div>
                                <div class="col-md-12 col-lg-12 col-xl-4">

                                    <!--begin:: Widgets/Stats2-2 -->
                                    <div class="m-widget1">
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Pagine Documento</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-accent"><%=pr.getPagine()%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Utente Lavorazione</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-info"><%=pr.getUtente()%></span>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Barcode</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-warning"><%=pr.getBarcode()%></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>

                                    <!--begin:: Widgets/Stats2-2 -->
                                </div>
                                <div class="col-md-12 col-lg-12 col-xl-4">

                                    <!--begin:: Widgets/Stats2-3 -->
                                    <div class="m-widget1">
                                        <div class="m-widget1__item">
                                            <div class="row m-row--no-padding align-items-center">
                                                <div class="col">
                                                    <h3 class="m-widget1__title">Esito Conformita'</h3>
                                                </div>
                                                <div class="col m--align-right">
                                                    <span class="m-widget1__number m--font-success"><%=pr.getEsitoco()%></span>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <!--begin:: Widgets/Stats2-3 -->
                                </div>
                            </div>
                        </div>
                    </div>
                    <%} else {

                            }
                        }
                    %>
                    <!--Begin::Section-->

                    <!--end::Portlet-->
                </div>
                <!--End::Section-->


            </div>
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
        <script src="assets/soop/select2.4.1.0.rc.min.js"></script>
        <script src="assets/soop/tableUtils.js" type="text/javascript"></script>            
        <script src="assets/soop/utility.js" type="text/javascript"></script>
        <script src="assets/app/js/webfont.js" type="text/javascript"></script>
        <!--end::Page Vendors -->
        <!--begin::Page Scripts -->
        <!--end::Page Scripts -->
    </body>

    <!-- end::Body -->
</html>
<%}%>