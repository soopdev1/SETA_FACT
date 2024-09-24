<%-- 
    Document   : login
    Created on : 6-mar-2020, 10.34.46
    Author     : rcosco
--%>

<%@page import="java.util.Date"%>
<%@page import="rc.so.engine.Action"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>


<html lang="en">

    <!-- begin::Head -->
    <head>
        <meta charset="utf-8" />
        <title>Findomestic | Login</title>
        <meta name="description" content="Latest updates and statistic charts">
        <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1, shrink-to-fit=no">
        <!--begin::Web font -->
        <!--end::Web font -->
        <!--begin::Global Theme Styles -->
        <link href="assets/vendors/base/vendors.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/demo/default/base/style.bundle.css" rel="stylesheet" type="text/css" />
        <link href="assets/vendors/custom/sweetalert2/dist/sweetalert2.css" rel="stylesheet" type="text/css"/>
        <!--end::Global Theme Styles -->
        <link rel="shortcut icon" href="favicon.ico" />
    </head>

    <!-- end::Head -->

    <!-- begin::Body -->
    <body class="m--skin- m-header--fixed m-header--fixed-mobile m-aside-left--enabled m-aside-left--skin-dark m-aside-left--fixed m-aside-left--offcanvas m-footer--push m-aside--offcanvas-default">

        <!-- begin:: Page -->
        <div class="m-grid m-grid--hor m-grid--root m-page">
            <div class="m-grid__item m-grid__item--fluid m-grid m-grid--hor m-login m-login--signin m-login--2 m-login-2--skin-3" id="m_login" 
                 style="background-image: url(assets/app/media/img/bg/bg-2.jpg);">
                <div class="m-grid__item m-grid__item--fluid	m-login__wrapper">
                    <div class="m-login__container">
                        <div class="m-login__logo">
                            <a href="#">
                                <img src="assets/app/media/img/logos/og_logo_findomestic.png" width="125px" alt="findomestic">
                            </a>
                        </div>
                        <div class="m-login__signin">
                            <div class="m-login__head">
                                <h3 class="m-login__title">Sign In</h3>
                            </div>
                            <form class="m-login__form m-form" action="Login?type=login" method="post">
                                <%
                                    String err = Action.getRequestValue(request, "error");
                                    if (!err.equals("")) {
                                        String msg = Action.formatLoginError(err);

                                %>
                                <div class="m-alert m-alert--outline alert alert-danger alert-dismissible" role="alert">
                                    <button type="button" class="close" data-dismiss="alert" aria-label="Close"></button>
                                    <span><%=msg%></span>
                                </div>
                                <%}%>
                                <div class="form-group m-form__group">
                                    <input class="form-control m-input" type="text" 
                                           placeholder="Username" name="username">
                                </div>
                                <div class="form-group m-form__group">
                                    <input class="form-control m-input m-login__form-input--last" 
                                           type="password" placeholder="Password" name="password" autocomplete="off"/>
                                </div>
                                <div class="row m-login__form-sub">
                                    <div class="col m--align-right m-login__form-right">
                                        <a href="javascript:;" id="m_login_forget_password" class="m-link">
                                            Password Dimenticata?
                                        </a>
                                    </div>
                                </div>
                                <div class="m-login__form-action">
                                    <button type="submit"
                                            class="btn btn-focus m-btn m-btn--pill m-btn--custom m-btn--air  m-login__btn">Sign In</button>
                                </div>
                            </form>
                        </div>
                        <div class="m-login__forget-password">
                            <div class="m-login__head">
                                <h3 class="m-login__title">Password dimenticata ?</h3>
                                <div class="m-login__desc">Inserisci il tuo username per effettuare il reset password:</div>
                            </div>
                            <form class="m-login__form m-form" id="m_form_pwd" action="Login" method="POST">
                                <input type="hidden" name="type" value="forgotPwd" />
                                <div class="form-group m-form__group">
                                    <input class="form-control m-input obbligatory" type="text" placeholder="Username o Email" 
                                           name="email" id="email" autocomplete="off" required>
                                </div>
                                <div class="m-login__form-action">
                                    <a href="javascript:void(0);" id="submit_pwd" class="btn btn-focus m-btn m-btn--pill m-btn--custom m-btn--air  m-login__btn">Continua</a>&nbsp;&nbsp;
                                    <button id="m_login_forget_password_cancel" class="btn btn-danger m-btn m-btn--pill m-btn--custom m-btn--air  m-login__btn ">Annulla</button>
                                </div>
                            </form>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- end:: Page -->

        <!--begin::Global Theme Bundle -->
        <script src="assets/fancy/js/jquery-3.3.1.min.js"></script>
        <script src="assets/soop/jquery-ui.js"></script>
        <script src="assets/soop/bootstrap.5.0.2.bundle.js"></script>
        <script src="assets/demo/demo2/base/scripts.bundle.js" type="text/javascript"></script>
        <!--end::Page Vendors -->
        <!--begin::Page Scripts -->

        <!--end::Global Theme Bundle -->
        <script src="assets/demo/demo2/base/scripts.bundle.js" type="text/javascript"></script>
        <script src="assets/vendors/custom/sweetalert2/dist/sweetalert2.js" type="text/javascript"></script>
        <script src="assets/vendors/custom/jquery-form/dist/jquery.form.min.js" type="text/javascript"></script>
        <script src="assets/soop/utility.js" type="text/javascript"></script>
        <script src="assets/app/js/webfont.js" type="text/javascript"></script>
        <script src="assets/soop/loginjs.js" type="text/javascript"></script>
        <!--begin::Page Scripts -->
        <!--end::Page Scripts -->
    </body>
    <!-- end::Body -->
</html>