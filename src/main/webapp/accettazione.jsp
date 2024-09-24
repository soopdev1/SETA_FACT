<%-- 
    Document   : dash
    Created on : 6-mar-2020, 11.22.37
    Author     : rcosco
--%>
<%@page import="java.util.List"%>
<%@page import="rc.so.engine.Action"%>
<%@page import="rc.so.entity.Corriere"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>



<html lang="en">

    <!-- begin::Head -->
    <head>
        <meta charset="utf-8" />
        <title>Findomestic | Accettazione</title>
        <meta name="description" content="Latest updates and statistic charts">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
        <link href="assets/soop/bootstrap.5.0.2.css" rel="stylesheet" type="text/css" />
        <!--begin::Global Theme Styles -->
        <link href="assets/vendors/base/vendors.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/demo/demo2/base/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/soop/datatables.bundle.1.11.3.css" rel="stylesheet" type="text/css" />
        <script src="assets/app/fontawesome-free-5.12.1-web/js/all.js" data-auto-replace-svg="nest"></script>

        <!--end::Page Vendors Styles -->
        <link rel="shortcut icon" href="favicon.ico" />
    </head>

    <!-- end::Head -->
    <%

        List<Corriere> corrieri = Action.list_Corriere_show();
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
                                <h3 class="m-subheader__title ">Consultazione - Accettazione</h3>
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
                                    <form action="accettazione.jsp" method="post" id="formsearch" class="m-form m-form--fit m-form--label-align-right m-form--group-seperator-dashed">
                                        <input type="hidden" name="shr1" value="<%="rc051986"%>"/>
                                        <div class="m-portlet__body">
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>LDV</label>
                                                    <input type="text" name="ldv" class="form-control m-input m-input--pill" placeholder="LDV">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label class="">CORRIERE</label>
                                                    <select class="form-control m-select2" name="corriere">
                                                        <option></option>
                                                        <%for (int i = 0; i < corrieri.size(); i++) {%>
                                                        <option value="<%=corrieri.get(i).getId()%>"><%=corrieri.get(i).getDescr()%></option> 
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA DA</label>
                                                    <input type="text" name="da1"  class="form-control  m-input m-input--pill datepicker" placeholder="Data da"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA A</label>
                                                    <input type="text" name="da2"  class="form-control m-input m-input--pill datepicker" placeholder="Data a"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                            <div class="m-form__actions m-form__actions--solid">
                                                <button type="submit" class="btn btn-brand">Ricerca</button>
                                                &nbsp;
                                                <a href="accettazione.jsp" class="btn btn-secondary">Cancella</a>
                                            </div>
                                        </div>
                                    </form>
                                    <!--begin::Form-->
                                    <!--end::Form-->
                                </div>
                                <%} else {

                                    String ldv = request.getParameter("ldv");
                                    String corriere = request.getParameter("corriere");
                                    String da1 = request.getParameter("da1");
                                    String da2 = request.getParameter("da2");

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
                                    <form action="accettazione.jsp" method="post" id="formsearch" class="m-form m-form--fit m-form--label-align-right m-form--group-seperator-dashed">
                                        <input type="hidden" name="shr1" value="<%="rc051986"%>"/>
                                        <div class="m-portlet__body">
                                            <div class="form-group m-form__group row">
                                                <div class="col-lg-3">
                                                    <label>LDV</label>
                                                    <input type="text" name="ldv" value="<%=ldv%>"  class="form-control m-input m-input--pill" placeholder="LDV">
                                                </div>
                                                <div class="col-lg-3">
                                                    <label class="">CORRIERE</label>
                                                    <select class="form-control m-select2" name="corriere">
                                                        <option></option>
                                                        <%for (int i = 0; i < corrieri.size(); i++) {
                                                                String sel = "";
                                                                if (corriere.equalsIgnoreCase(corrieri.get(i).getId())) {
                                                                    sel = "selected";
                                                                }

                                                        %>
                                                        <option <%=sel%> value="<%=corrieri.get(i).getId()%>"><%=corrieri.get(i).getDescr()%></option> 
                                                        <%}%>
                                                    </select>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA DA</label>
                                                    <input type="text" name="da1"  value="<%=da1%>" class="form-control m-input m-input--pill datepicker" placeholder="Data da"/>
                                                </div>
                                                <div class="col-lg-3">
                                                    <label>DATA A</label>
                                                    <input type="text" name="da2"  value="<%=da2%>" class="form-control m-input m-input--pill datepicker" placeholder="Data a"/>
                                                </div>
                                            </div>
                                        </div>
                                        <div class="m-portlet__foot m-portlet__no-border m-portlet__foot--fit">
                                            <div class="m-form__actions m-form__actions--solid">
                                                <button type="submit" class="btn btn-brand">Ricerca</button>
                                                &nbsp;
                                                <a href="accettazione.jsp" class="btn btn-secondary">Cancella</a>
                                            </div>
                                        </div>
                                    </form>
                                    <!--begin::Form-->
                                    <!--end::Form-->
                                </div>
                                <div class="m-portlet__body">
                                    <!--begin: Datatable -->
                                    <table class="table table-striped- table-bordered table-hover" id="m_table_1">
                                        <caption>Result Accettazione</caption>
                                        <thead>
                                            <tr>
                                                <th scope="col">LDV</th>
                                                <th scope="col">Corriere</th>
                                                <th scope="col">Data</th>
                                                <th scope="col">Lavorazione</th>
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

    <!-- end::Scroll Top -->

    <!-- begin::Quick Nav -->

    <!-- begin::Quick Nav -->



    <!--end::Global Theme Bundle -->

    <!--begin::Page Vendors -->
    <!--Visualizzazione PDF in fancybox (25/02/21) start-->
    <script src="assets/soop/bootstrap.5.0.2.bundle.js" type="text/javascript"></script>
    <script src="assets/fancy/js/jquery-3.3.1.min.js" type="text/javascript"></script>
    <script src="assets/fancy/js/jquery.fancybox.2.1.5.min.js" type="text/javascript"></script>
    <link rel="stylesheet" type="text/css" href="assets/fancy/css/jquery.fancybox.css?v=2.1.5" media="screen" />
    <script type="text/javascript" src="assets/fancy/js/fancy.js"></script>
    <script src="assets/soop/jquery-ui.js" type="text/javascript"></script>
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
    <script src="assets/soop/accettazione_js.js" type="text/javascript"></script>
    <!--end::Page Vendors -->
    <!--begin::Page Scripts -->
    <!--end::Page Scripts -->
</body>

<!-- end::Body -->
</html>
