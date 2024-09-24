<%@page import="rc.so.engine.Action"%>
<%
    String link_value = Action.verifyUser(request);
    if (link_value != null) {
        Action.redirect(request, response, link_value);
    }else{
    String us_nome = session.getAttribute("us_nome").toString();
    String us_cognome = session.getAttribute("us_cognome").toString();
    String us_role = session.getAttribute("us_role").toString();
    String us_username = session.getAttribute("us_cod").toString();
    
%>
<script type="text/javascript">
    function logout() {
        $.ajax({
            type: "POST",
            url: "Login?type=logout"
        });
        window.location.href = "login.jsp";
    }
</script>

<header id="m_header" class="m-grid__item m-header " m-minimize="minimize" m-minimize-offset="200" m-minimize-mobile-offset="200">
    <div class="m-header__top">
        <div class="m-container m-container--responsive m-container--xxl m-container--full-height m-page__container">
            <div class="m-stack m-stack--ver m-stack--desktop">

                <!-- begin::Brand -->
                <div class="m-stack__item m-brand">
                    <div class="m-stack m-stack--ver m-stack--general m-stack--inline">
                        <div class="m-stack__item m-stack__item--middle m-brand__logo">
                            <a href="dash.jsp" class="m-brand__logo-wrapper">
                                <img alt="" <img src="assets/app/media/img/logos/og_logo_findomestic.png" width="50px">
                            </a>
                        </div>
                    </div>
                </div>

                <!-- end::Brand -->

                <!-- begin::Topbar -->
                <div class="m-stack__item m-stack__item--fluid m-header-head" id="m_header_nav">
                    <div id="m_header_topbar" class="m-topbar  m-stack m-stack--ver m-stack--general">
                        <div class="m-stack__item m-topbar__nav-wrapper">
                            <ul class="m-topbar__nav m-nav m-nav--inline">
                                <li class="m-nav__item m-topbar__user-profile m-topbar__user-profile--img  m-dropdown m-dropdown--medium m-dropdown--arrow m-dropdown--header-bg-fill m-dropdown--align-right m-dropdown--mobile-full-width m-dropdown--skin-light"
                                    m-dropdown-toggle="click">
                                    <a href="#" class="m-nav__link m-dropdown__toggle">
                                        <span class="m-topbar__userpic m--hide">
                                            <img src="assets/app/media/img/users/user4.jpg" class="m--img-rounded m--marginless m--img-centered" alt="" />
                                        </span>
                                        <span class="m-topbar__welcome">Ciao,&nbsp;</span>
                                        <span class="m-topbar__username"><%=us_username%></span>
                                    </a>
                                    <div class="m-dropdown__wrapper">
                                        <span class="m-dropdown__arrow m-dropdown__arrow--right m-dropdown__arrow--adjust"></span>
                                        <div class="m-dropdown__inner">
                                            <div class="m-dropdown__header m--align-center" style="background: url(assets/app/media/img/misc/user_profile_bg.jpg); background-size: cover;">
                                                <div class="m-card-user m-card-user--skin-dark">
                                                    
                                                    <div class="m-card-user__details">
                                                        <span class="m-card-user__name m--font-weight-500"><%=us_nome%> <%=us_cognome%></span>
                                                        <a href="" class="m-card-user__email m--font-weight-300 m-link"><%=us_role%></a>
                                                    </div>
                                                </div>
                                            </div>
                                            <div class="m-dropdown__body">
                                                <div class="m-dropdown__content">
                                                    <ul class="m-nav m-nav--skin-light">
                                                        <li class="m-nav__section m--hide">
                                                            <span class="m-nav__section-text">Section</span>
                                                        </li>
                                                        <li class="m-nav__item">
                                                            <a href="password.jsp" class="m-nav__link">
                                                                <i class="m-nav__link-icon flaticon-profile-1"></i>
                                                                <span class="m-nav__link-title">
                                                                    <span class="m-nav__link-wrap">
                                                                        <span class="m-nav__link-text">Modifica Password</span>
                                                                    </span>
                                                                </span>
                                                            </a>
                                                        </li>
                                                        <li class="m-nav__separator m-nav__separator--fit">
                                                        </li>
                                                        <li class="m-nav__item">
                                                            <button onclick="return logout();"
                                                               class="btn m-btn--pill btn-secondary m-btn m-btn--custom m-btn--label-brand m-btn--bolder">
                                                                <i class="fas fa-sign-out-alt"></i> Logout
                                                            </button>
                                                        </li>
                                                    </ul>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                </li>
                            </ul>
                        </div>
                    </div>
                </div>

                <!-- end::Topbar -->
            </div>
        </div>
    </div>
    <div class="m-header__bottom">
        <div class="m-container m-container--responsive m-container--xxl m-container--full-height m-page__container">
            <div class="m-stack m-stack--ver m-stack--desktop">
                <!-- begin::Horizontal Menu -->
                <div class="m-stack__item m-stack__item--middle m-stack__item--fluid">
                    <button class="m-aside-header-menu-mobile-close  m-aside-header-menu-mobile-close--skin-light " id="m_aside_header_menu_mobile_close_btn"><i class="la la-close"></i></button>
                    <div id="m_header_menu" class="m-header-menu m-aside-header-menu-mobile m-aside-header-menu-mobile--offcanvas  m-header-menu--skin-dark m-header-menu--submenu-skin-light m-aside-header-menu-mobile--skin-light m-aside-header-menu-mobile--submenu-skin-light ">
                        <ul class="m-menu__nav  m-menu__nav--submenu-arrow ">
                            
                            <!--<li class="m-menu__item  m-menu__item--active " aria-haspopup="true"><a href="dash.jsp" class="m-menu__link "><span class="m-menu__item-here"></span><span class="m-menu__link-text">Dashboard</span></a></li>
                            -->
                            <li class="m-menu__item m-menu__item--active m-menu__item--submenu m-menu__item--rel" m-menu-submenu-toggle="click" aria-haspopup="true"><a href="javascript:;" class="m-menu__link m-menu__toggle" title="Non functional dummy link"><span class="m-menu__item-here"></span><span
                                        class="m-menu__link-text">Consultazione</span><i class="m-menu__hor-arrow la la-angle-down"></i><i class="m-menu__ver-arrow la la-angle-right"></i></a>
                                <div class="m-menu__submenu m-menu__submenu--classic m-menu__submenu--left"><span class="m-menu__arrow m-menu__arrow--adjust"></span>
                                    <ul class="m-menu__subnav">
                                        <li class="m-menu__item m-menu__item--active " aria-haspopup="true"><a href="pratiche.jsp" class="m-menu__link "><i class="m-menu__link-icon flaticon-diagram"></i><span class="m-menu__link-title"> <span class="m-menu__link-wrap"> <span class="m-menu__link-text">Pratiche</span> <span class="m-menu__link-badge"><span class="m-badge m-badge--success">1</span></span> </span></span></a></li>
                                        <li class="m-menu__item " aria-haspopup="true"><a href="accettazione.jsp" class="m-menu__link "><i class="m-menu__link-icon flaticon-business"></i><span class="m-menu__link-title"> <span class="m-menu__link-wrap"> <span class="m-menu__link-text">Accettazione</span> <span class="m-menu__link-badge"><span class="m-badge m-badge--danger">2</span></span> </span></span></a></li>
              
                                    </ul>
                                </div>
                            </li>
                            <li class="m-menu__item m-menu__item--submenu m-menu__item--rel" m-menu-submenu-toggle="click" aria-haspopup="true"><a href="javascript:;" class="m-menu__link m-menu__toggle" title="Non functional dummy link"><span class="m-menu__item-here"></span><span
                                        class="m-menu__link-text">Reports</span><i class="m-menu__hor-arrow la la-angle-down"></i><i class="m-menu__ver-arrow la la-angle-right"></i></a>
                                <div class="m-menu__submenu m-menu__submenu--classic m-menu__submenu--left"><span class="m-menu__arrow m-menu__arrow--adjust"></span>
                                    <ul class="m-menu__subnav">
                                        <li class="m-menu__item " aria-haspopup="true"><a href="lavorazione.jsp" class="m-menu__link "><i class="m-menu__link-icon flaticon-diagram"></i><span class="m-menu__link-title"> <span class="m-menu__link-wrap"> <span class="m-menu__link-text">Report Lavorazione</span> <span class="m-menu__link-badge"><span class="m-badge m-badge--success">1</span></span> </span></span></a></li>                                        
                                    </ul>
                                </div>
                            </li>
                        </ul>
                    </div>
                </div>

                <!-- end::Horizontal Menu -->

                <!--begin::Search-->
                

                <!--end::Search-->
            </div>
        </div>
    </div>
</header>
<%}%>