<%-- 
    Document   : dettagli
    Created on : 16-mar-2020, 15.54.28
    Author     : rcosco
--%>

<%@page import="rc.so.util.Utility"%>
<%@page import="rc.so.engine.Action"%>
<%@page import="rc.so.entity.Pratica"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html xml:lang="">
    <head>
        <meta charset="utf-8" />
        <title>Findomestic | Modifica Pratica</title>
        <meta name="description" content="Latest updates and statistic charts">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">

        <!--begin::Global Theme Styles -->
        <link href="assets/vendors/base/vendors.bundle.css" rel="stylesheet" type="text/css" />

        <link href="assets/demo/demo2/base/style.bundle.css" rel="stylesheet" type="text/css" />

        <link href="assets/soop/datatables.bundle.1.11.3.css" rel="stylesheet" type="text/css" />
        <script src="assets/app/fontawesome-free-5.12.1-web/js/all.js" data-auto-replace-svg="nest"></script>
        <!--end::Page Vendors Styles -->
        <link rel="shortcut icon" href="favicon.ico" />
    </head>
    <body class="page-content-white">

        <!-- begin:: Page -->


        <!-- begin::Header -->
        <!-- end::Header -->

        <!-- begin::Body -->
    <body class="m-page--wide m-header--fixed m-header--fixed-mobile m-footer--push m-aside--offcanvas-default">

        <!-- begin:: Page -->
        <div class="m-grid m-grid--hor m-grid--root m-page">

            <!-- begin::Header -->
            <%@ include file="menu/head.jsp"%>
            <!-- end::Header -->

            <!-- begin::Body -->
            <div class="m-grid__item m-grid__item--fluid  m-grid m-grid--ver-desktop m-grid--desktop 	m-container m-container--responsive m-container--xxl m-page__container m-body">
                <div class="m-grid__item m-grid__item--fluid m-wrapper">

                    <!-- BEGIN: Subheader -->
                    <div class="m-subheader ">
                        <div class="d-flex align-items-center">
                            <div class="mr-auto">
                                <h3 class="m-subheader__title ">Modifica dati documento</h3>
                            </div>
                        </div>
                    </div>

                    <!-- END: Subheader -->
                    <div class="m-content">
                        <%
                            String esito = Action.getRequestValue(request, "esito");
                            if (!esito.equals("")) {
                                String[] msg = Action.formatEsito(esito);
                                
                        %>
                        <div class="m-alert alert <%=msg[0]%> alert-dismissible" role="alert">
                            <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                            <span><%=msg[1]%></span>
                        </div>
                        <%}%>
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
                                    <div class="col-xl-6">
                                        <div class="row m-row--no-padding m-row--col-separator-xl">

                                            <div class="col-md-12 col-lg-12 col-xl-6">

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
                                                </div>

                                                <!--end:: Widgets/Stats2-1 -->
                                            </div>
                                            <div class="col-md-12 col-lg-12 col-xl-6">
                                                <div class="m-widget1">
                                                    <div class="m-widget1__item">
                                                        <div class="row m-row--no-padding align-items-center">
                                                            <div class="col">
                                                                <h3 class="m-widget1__title">Stato WS</h3>
                                                            </div>
                                                            <div class="col m--align-right">
                                                                <span class="m-widget1__number m--font-danger"><%=pr.getStatows()%></span>
                                                            </div>
                                                        </div>
                                                    </div>
                                                </div>
                                            </div>

                                            <!--end:: Widgets/Stats2-1 -->
                                        </div>
                                        <div class="row m-row--no-padding m-row--col-separator-xl">
                                            <hr>
                                        </div>
                                        <form action="Operations?type=editpratica" method="post" class="m-form m-form--fit m-form--label-align-right m-form--group-seperator-dashed">
                                            <input type="hidden" name="idoper" value="<%=pr.getIddocumento()%>"/>
                                            <div class="m-portlet__body">
                                                <div class="form-group m-form__group row">

                                                    <div class="col-lg-6">
                                                        <label>NUMERO PRATICA</label>
                                                        <input type="text" class="form-control m-input m-input--pill" 
                                                               name="pratica" placeholder="Numero pratica" value="<%=pr.getNumeropratica()%>" />
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <label>CODICE CIR</label>
                                                        <input type="text" class="form-control m-input m-input--pill" 
                                                               name="cir" placeholder="Codice CIR" value="<%=pr.getCir()%>" >
                                                    </div>

                                                </div>
                                                <div class="form-group m-form__group row">
                                                    <div class="col-lg-6">
                                                        <label>COGNOME CLIENTE</label>
                                                        <input type="text" class="form-control m-input m-input--pill" 
                                                               name="cognome" placeholder="Cognome" value="<%=pr.getCognome()%>" >
                                                    </div>
                                                    <div class="col-lg-6">
                                                        <label>NOME CLIENTE</label>
                                                        <input type="text" class="form-control m-input m-input--pill" 
                                                               name="nome" placeholder="Nome"value="<%=pr.getNome()%>" >
                                                    </div>
                                                </div>


                                            </div>
                                            <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                                <div class="m-form__actions m-form__actions--solid">
                                                    <button type="submit" class="btn btn-brand"><span class="fas fa-save"></span> Salva Modifiche</button>
                                                </div>
                                            </div>
                                        </form>
                                        <div class="row m-row--no-padding m-row--col-separator-xl">
                                            <hr>
                                        </div>
                                    </div>
                                    <div class="col-xl-6">
                                        <iframe height="800px" width="100%" title="Modifica Doc"
                                                allowfullscreen="false" 
                                                src="<%=Utility.getBase64(ido)%>" >
                                        </iframe>
                                    </div>
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
      </div>

            <!-- end::Body -->

            <!-- begin::Footer -->
            <%@ include file="menu/foot.jsp"%>
            <!-- end::Footer -->
        </div>

       
        <!--begin::Global Theme Bundle -->
        <script src="assets/vendors/base/vendors.bundle.js" type="text/javascript"></script>
        <script src="assets/demo/demo2/base/scripts.bundle.js" type="text/javascript"></script>

        <!--end::Global Theme Bundle -->

        <!--begin::Page Vendors -->
        <script src="assets/demo/default/custom/crud/forms/widgets/bootstrap-datepicker.js" type="text/javascript"></script>
        <!--end::Page Vendors -->

        <!--begin::Page Scripts -->
        <script src="assets/app/js/dashboard.js" type="text/javascript"></script>

        <script src="assets/soop/datatables.bundle.1.11.3.js" type="text/javascript"></script>
        <script src="assets/app/js/date.js" type="text/javascript"></script>
        <script src="assets/app/js/webfont.js" type="text/javascript"></script>
        <!--end::Page Vendors -->
        <!--begin::Page Scripts -->




        <!--end::Page Scripts -->
    </body>

    <!-- end::Body -->
</html>
