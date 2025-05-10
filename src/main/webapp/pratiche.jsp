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
        <title>Findomestic | Pratiche</title>
        <meta name="description" content="Latest updates and statistic charts">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">

        <link href="assets/soop/bootstrap.5.0.2.css" rel="stylesheet" type="text/css" />
        <!--end::Web font -->
        <link href="assets/soop/select2.4.1.0.rc.min.css" rel="stylesheet" />

        <!--begin::Global Theme Styles -->
        <link href="assets/vendors/base/vendors.bundle.css" rel="stylesheet" type="text/css" />

        <link href="assets/demo/demo2/base/style.bundle.css" rel="stylesheet" type="text/css" />
        <script src="assets/fancy/js/jquery-3.3.1.min.js" type="text/javascript"></script>
        <script src="assets/fancy/js/jquery.fancybox.2.1.5.min.js" type="text/javascript"></script>
        <script type="text/javascript" src="assets/fancy/js/jquery.fancybox.js?v=2.1.5"></script>
        <link rel="stylesheet" type="text/css" href="assets/fancy/css/jquery.fancybox.css?v=2.1.5" media="screen" />
        
        <link href="assets/soop/datatables.bundle.1.11.3.css" rel="stylesheet" type="text/css" />
        <script src="assets/app/fontawesome-free-5.12.1-web/js/all.js" data-auto-replace-svg="nest"></script>

        <!--end::Page Vendors Styles -->
        <link rel="shortcut icon" href="favicon.ico" />
        <style>

        </style>
    </head>

    <%

        List<Lavorazione> tipolav = Action.list_Lavorazione();
        boolean search = false;
        String shr1 = request.getParameter("shr1");
        if (shr1 != null) {
            if (shr1.equals("rc051986")) {
                search = true;
            }
        }
    %>
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
                                <h3 class="m-subheader__title ">Consultazione - Pratiche</h3>
                            </div>
                        </div>
                    </div>

                    <!-- END: Subheader -->
                    <div class="m-content">

                        <!--begin:: Widgets/Stats-->


                        <!--end:: Widgets/Stats-->

                        <!--Begin::Section-->
                        <div class="row">
                            <div class="col-xl-12">


                                <!--end:: Widgets/New Users-->
                                <%if (!search) {%>
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
                                    <form action="pratiche.jsp" method="post" id="formsearch" class="m-form m-form--fit m-form--label-align-right m-form--group-seperator-dashed">
                                        <input type="hidden" name="shr1" value="<%="rc051986"%>"/>
                                        <div class="m-portlet__body">
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>TIPO LAVORAZIONE</label>
                                                    <select class="form-control m-select2" name="lavor">
                                                        <option></option>
                                                        <%for (int i = 0; i < tipolav.size(); i++) {%>
                                                        <option value="<%=tipolav.get(i).getId()%>"><%=tipolav.get(i).getDescrizione().toUpperCase()%></option> 
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>NUMERO PRATICA</label>
                                                    <input type="text" class="form-control m-input m-input--pill" name="pratica" placeholder="Numero pratica">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>CODICE CIR</label>
                                                    <input type="text" class="form-control m-input m-input--pill" name="cir" placeholder="Codice CIR">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>TVEI</label>
                                                    <input type="text" class="form-control m-input m-input--pill" name="tvei" placeholder="TVEI">
                                                </div>
                                            </div>
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>DATA DA</label>
                                                    <input type="text" name="da1"  class="form-control  m-input m-input--pill datepicker" placeholder="Data Lavorazione da"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA A</label>
                                                    <input type="text" name="da2"  class="form-control m-input m-input--pill datepicker" placeholder="Data Lavorazione a"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>COGNOME CLIENTE</label>
                                                    <input type="text" class="form-control m-input m-input--pill" name="cognome" placeholder="Cognome">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>NOME CLIENTE</label>
                                                    <input type="text" class="form-control m-input m-input--pill" name="nome" placeholder="Nome">
                                                </div>
                                            </div>
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>LDV</label>
                                                    <input type="text" class="form-control m-input m-input--pill" 
                                                           name="ldv" placeholder="LDV">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>STATO WS</label>
                                                    <select class="form-control m-select2" name="statows" placeholder="STATO WS">
                                                        <option></option>
                                                        <option value="0">OK / DA INVIARE</option>
                                                        <option value="1">ERRORE</option>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                            <div class="m-form__actions m-form__actions--solid">

                                                <button type="submit" class="btn btn-brand">Ricerca</button>
                                                &nbsp;
                                                <a href="pratiche.jsp" class="btn btn-secondary">Cancella</a>

                                            </div>
                                        </div>
                                    </form>
                                    <!--begin::Form-->
                                    <!--end::Form-->
                                </div>

                                <%} else {

                                    String lavor = request.getParameter("lavor");
                                    String pratica = request.getParameter("pratica");
                                    String cir = request.getParameter("cir");
                                    String ldv = request.getParameter("ldv");
                                    String tvei = request.getParameter("tvei");
                                    String da1 = request.getParameter("da1");
                                    String da2 = request.getParameter("da2");
                                    String cognome = request.getParameter("cognome");
                                    String nome = request.getParameter("nome");

                                    String statows = request.getParameter("statows");
                                    String sel0 = "";
                                    if (statows.equals("0")) {
                                        sel0 = "selected";
                                    }
                                    String sel1 = "";
                                    if (statows.equals("1")) {
                                        sel1 = "selected";
                                    }
                                %>
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
                                    <form action="pratiche.jsp" method="post" id="formsearch" class="m-form m-form--fit m-form--label-align-right m-form--group-seperator-dashed">
                                        <input type="hidden" name="shr1" value="<%="rc051986"%>"/>
                                        <div class="m-portlet__body">
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>TIPO LAVORAZIONE</label>
                                                    <select class="form-control m-select2" name="lavor">
                                                        <option></option>
                                                        <%for (int i = 0; i < tipolav.size(); i++) {
                                                                String sel = "";
                                                                if (lavor.equalsIgnoreCase(tipolav.get(i).getId())) {
                                                                    sel = "selected";
                                                                }
                                                        %>
                                                        <option <%=sel%> value="<%=tipolav.get(i).getId()%>"><%=tipolav.get(i).getDescrizione().toUpperCase()%></option> 
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>NUMERO PRATICA</label>
                                                    <input type="text" value="<%=pratica%>" class="form-control m-input m-input--pill" name="pratica" placeholder="Numero pratica">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>CODICE CIR</label>
                                                    <input type="text" value="<%=cir%>" class="form-control m-input m-input--pill" name="cir" placeholder="Codice CIR">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>TVEI</label>
                                                    <input type="text" value="<%=tvei%>" class="form-control m-input m-input--pill" name="tvei" placeholder="TVEI">
                                                </div>
                                            </div>
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>DATA DA</label>
                                                    <input type="text" name="da1" value="<%=da1%>" class="form-control  m-input m-input--pill datepicker" placeholder="Data Lavorazione da"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA A</label>
                                                    <input type="text" name="da2" value="<%=da2%>" class="form-control m-input m-input--pill datepicker" placeholder="Data Lavorazione a"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>COGNOME CLIENTE</label>
                                                    <input type="text" value="<%=cognome%>" class="form-control m-input m-input--pill" name="cognome" placeholder="Cognome">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>NOME CLIENTE</label>
                                                    <input type="text" value="<%=nome%>" class="form-control m-input m-input--pill" name="nome" placeholder="Nome">
                                                </div>
                                            </div>
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>LDV</label>
                                                    <input type="text" value="<%=ldv%>" class="form-control m-input m-input--pill" 
                                                           name="ldv" placeholder="LDV">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>STATO WS</label>
                                                    <select class="form-control m-select2" name="statows" placeholder="STATO WS">
                                                        <option></option>
                                                        <option <%=sel0%> value="0">OK / DA INVIARE</option>
                                                        <option <%=sel1%> value="1">ERRORE</option>
                                                    </select>
                                                </div>
                                            </div>

                                        </div>
                                        <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                            <div class="m-form__actions m-form__actions--solid">

                                                <button type="submit" class="btn btn-brand">Ricerca</button>
                                                &nbsp;
                                                <a href="pratiche.jsp" class="btn btn-secondary">Cancella</a>

                                            </div>
                                        </div>
                                    </form>
                                    <!--begin::Form-->
                                    <!--end::Form-->
                                </div>
                                <div class="m-portlet__body">
                                    <!--begin: Datatable -->
                                    <table class="table table-striped- table-bordered table-hover" id="m_table_1">
                                        <caption>Result Pratiche</caption>
                                        <thead>
                                            <tr>
                                                <th scope="col">Azioni</th>
                                                <th scope="col">Tipo Documento</th>
                                                <th scope="col">Tipo Lavorazione</th>
                                                <th scope="col">Numero Pratica</th>
                                                <th scope="col">Codice CIR</th>
                                                <th scope="col">Cliente</th>
                                                <th scope="col">TVEI</th>
                                                <th scope="col">LDV</th>
                                                <th scope="col">Lotto</th>
                                                <th scope="col">Data</th>
                                                <th scope="col">Stato WS</th>
                                                <th scope="col">Numero Invii WS</th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                        </tbody>
                                    </table>
                                </div>


                                <%}%>
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

    <div id="m_scroll_top" class="m-scroll-top">
        <span class="la la-arrow-up"></span>
    </div>

    <!-- end::Scroll Top -->

    <!-- begin::Quick Nav -->



    <script src="assets/soop/jquery-ui.js"></script>
    <script src="assets/soop/bootstrap.5.0.2.bundle.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="assets/fancy/css/jquery.fancybox.css?v=2.1.5" media="screen" />
    <script type="text/javascript" src="assets/fancy/js/fancy.js"></script>
    <script src="assets/demo/demo2/base/scripts.bundle.js" type="text/javascript"></script>
    <script src="assets/soop/bootstrap-datepicker.1.9.min.js" type="text/javascript"></script>
    <!--end::Page Vendors -->
    <!--begin::Page Scripts -->
    <script src="assets/soop/datatables.bundle.1.11.3.js" type="text/javascript"></script>
    <script src="assets/app/js/date.js" type="text/javascript"></script>
    <script src="assets/soop/select2.4.1.0.rc.min.js"></script>
    <script src="assets/soop/tableUtils.js" type="text/javascript"></script>    
    <script src="assets/soop/utility.js" type="text/javascript"></script>
    <script src="assets/app/js/webfont.js" type="text/javascript"></script>
    <script src="assets/soop/pratiche_js.js" type="text/javascript"></script>
    <!--end::Page Scripts -->
</body>

<!-- end::Body -->
</html>
