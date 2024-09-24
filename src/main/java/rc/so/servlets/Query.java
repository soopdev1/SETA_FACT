/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import rc.so.db.FindoDB;
import rc.so.engine.Action;
import static rc.so.engine.Action.formatStringtoStringDate;
import static rc.so.engine.Action.getEccessiList;
import static rc.so.engine.Action.getFilter;
import static rc.so.engine.Action.getUserByEmailOrUsername;
import static rc.so.engine.Action.getUserList;
import static rc.so.engine.Action.insertTR;
import static rc.so.engine.Action.is_outsourcer;
import static rc.so.engine.Action.PAT_2;
import static rc.so.engine.Action.PAT_6;
import static rc.so.engine.Action.getRequestValue;
import static rc.so.engine.Action.redirect;
import rc.so.entity.Accettazione;
import rc.so.entity.Eccessi;
import rc.so.entity.Lavorazione;
import rc.so.entity.Pratica;
import rc.so.entity.User;
import static rc.so.util.Utility.AADATA;
import static rc.so.util.Utility.APPJSON;
import static rc.so.util.Utility.COGNOME;
import static rc.so.util.Utility.CONTENTTYPE;
import static rc.so.util.Utility.ITOTALDISPLAY;
import static rc.so.util.Utility.ITOTALRECORDS;
import static rc.so.util.Utility.RECORDID;
import static rc.so.util.Utility.SCOLUMS;
import static rc.so.util.Utility.SECHO;
import static rc.so.util.Utility.USERNAME;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.apache.commons.lang3.StringUtils.join;

/**
 *
 * @author rcosco
 */
public class Query extends HttpServlet {

    private static final long serialVersionUID = 1L;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void reportLav(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> filter = getFilter(request, false);

        insertTR("I", request.getSession(), "QUERY REPORT LAVORAZIONE: " + join(filter));

        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            FindoDB db1 = new FindoDB(Action.log);
            List<Lavorazione> result = db1.list_report_lav(filter);
            db1.closeDB();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {

                String txt = "<form action='Download' method='post' target='_blank'>"
                        + "<input type='hidden' name='type' value ='onlydownload' />"
                        + "<input type='hidden' name='content' value ='" + res.getTxt() + "' />"
                        + "<input type='hidden' name='contentname' value ='LAVORATO_" + res.getDatarif() + ".txt' />"
                        + "<button "
                        + "class='btn btn-sm btn-outline-success m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
                        + "data-toggle='popover' data-placement='right' title='Scarica' data-content='Scarica Report in formato TXT.'><i class='fas fa-file-alt'></i></button></form>";
                String csv = "<form action='Download' method='post' target='_blank'>"
                        + "<input type='hidden' name='type' value ='onlydownload' />"
                        + "<input type='hidden' name='content' value ='" + res.getCsv() + "' />"
                        + "<input type='hidden' name='contentname' value ='LAVORATO_" + res.getDatarif() + ".csv' />"
                        + "<button "
                        + "class='btn btn-sm btn-outline-success m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
                        + "data-toggle='popover' data-placement='right' title='Scarica' data-content='Scarica Report in formato CSV.'><i class='fas fa-file-csv'></i></button></form>";

                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("azionitxt", txt);
                data_value.addProperty("azionicsv", csv);
                data_value.addProperty("data", formatStringtoStringDate(res.getDatarif(), PAT_6, PAT_2));
                at.addAndGet(1);
                data.add(data_value);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void queryPratiche(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        boolean useroutsourcer = is_outsourcer(request.getSession());

        Map<String, String> filter = getFilter(request, false);
        insertTR("I", request.getSession(), "QUERY PRATICHE: " + join(filter));
        JsonObject jMembers;
        try (PrintWriter out = response.getWriter()) {
            jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            FindoDB db1 = new FindoDB(Action.log);
            List<Pratica> result = db1.list_pratica(filter);
            db1.closeDB();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {

                String dettagli = "<a href='dettagli.jsp?ido=" + res.getIddocumento() + "' "
                        + "class='fancybox fancypdf btn btn-sm btn-outline-success m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
                        + "data-toggle='popover' data-placement='right' title='Dettagli' data-content='Visualizza dettagli documento.'><i class='fas fa-info'></i></a> ";

                String pdf
                        = "<a href='javascript:void(0)' onclick='return $(\"#frm_" + res.getIddocumento() + "\").submit();' class='btn btn-sm btn-outline-primary m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
                        + "data-toggle='popover' data-placement='right' title='Visualizza File' data-content='Visualizza file documento.'><i class='far fa-file-pdf'></i></a>"
                        + "<form id='frm_" + res.getIddocumento() + "' target='_blank' method='POST' action='Operations'>"
                        + "<input type='hidden' name='type' value='showPDF'/>"
                        + "<input type='hidden' name='ido' value='" + res.getIddocumento() + "' />"
                        + "</form>";

                String edit = "";

                if (!useroutsourcer) {
                    dettagli = "";
                } else {

                    if (res.isStatuserror()) {
                        edit = "<a target='_blank' href='modifica.jsp?ido=" + res.getIddocumento() + "'"
                                + "class='btn btn-sm btn-outline-danger m-btn m-btn--icon m-btn--icon-only m-btn--custom m-btn--pill m-btn--air' "
                                + "data-toggle='popover' data-placement='right' "
                                + "title='Modifica' data-content='Modifica dati documento.'><i class='fas fa-edit'></i></a>";
                    }
                }

                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("azioni", dettagli + pdf + edit);
                data_value.addProperty("tipodoc", res.getTipodocumento());
                data_value.addProperty("lavorazione", res.getTipolavorazione());
                data_value.addProperty("pratica", res.getNumeropratica());
                data_value.addProperty("cir", res.getCir());
                data_value.addProperty("cliente", res.getCliente());
                data_value.addProperty("tvei", res.getTvei());
                data_value.addProperty("ldv", res.getLdv());
                data_value.addProperty("lotto", res.getLotto());
                data_value.addProperty("data", res.getDatalavorazione());
                data_value.addProperty("ws", res.getStatows());
                data_value.addProperty("invii", res.getNumeroinvii());
                data.add(data_value);

                at.addAndGet(1);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void queryAccettazione(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Map<String, String> filter = getFilter(request, false);
        insertTR("I", request.getSession(), "QUERY ACCETTAZIONE: " + join(filter));
        try (PrintWriter out = response.getWriter()) {
            JsonObject jMembers = new JsonObject();
            JsonArray data = new JsonArray();
            FindoDB db1 = new FindoDB(Action.log);
            List<Accettazione> result = db1.list_accettazione(filter);
            db1.closeDB();
            jMembers.addProperty(ITOTALRECORDS, result.size());
            jMembers.addProperty(ITOTALDISPLAY, result.size());
            jMembers.addProperty(SECHO, 0);
            jMembers.addProperty(SCOLUMS, "");
            AtomicInteger at = new AtomicInteger(1);
            result.forEach(res -> {
                JsonObject data_value = new JsonObject();
                data_value.addProperty(RECORDID, at.get());
                data_value.addProperty("ldv", res.getLdv());
                data_value.addProperty("corriere", res.getCorriere());
                data_value.addProperty("data", formatStringtoStringDate(res.getData(), PAT_6, PAT_2) + " " + res.getOra());
                data_value.addProperty("lavorazione", res.getLavorazione());
                at.addAndGet(1);
                data.add(data_value);
            });
            jMembers.add(AADATA, data);
            response.setContentType(APPJSON);
            response.setHeader(CONTENTTYPE, APPJSON);
            out.print(jMembers.toString());
        }
    }

    protected void checkUsername(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPJSON);
        User u = getUserByEmailOrUsername(request.getParameter(USERNAME));

        Gson gson = new Gson();
        response.getWriter().write(gson.toJson(u));
        response.getWriter().close();
    }

    protected void queryUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String tipo = "2";
        if (request.getSession().getAttribute("us_rolecod").toString().equals("1")) { //SOLO ADMIN VISUALIZZA LATRI UTENTI
            tipo = getRequestValue(request, "tipo");
        }

        ArrayList<User> list = getUserList(
                getRequestValue(request, "nome"),
                getRequestValue(request, COGNOME),
                getRequestValue(request, USERNAME),
                tipo);
        writeJsonResponse(response, list);
    }

    protected void queryEccessi(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        ArrayList<Eccessi> list = getEccessiList(
                getRequestValue(request, "endorse"),
                getRequestValue(request, "pratica"),
                getRequestValue(request, "cir"),
                getRequestValue(request, "from"),
                getRequestValue(request, "to"),
                getRequestValue(request, "ldv"),
                getRequestValue(request, "tvei")
        );
        writeJsonResponse(response, list);
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        if ((String) request.getSession().getAttribute("us_cod") == null) {
            String p = request.getContextPath();
            request.getSession().invalidate();
            redirect(request, response, p);
        } else {
            String type = request.getParameter("type");
            switch (type) {
                case "queryAccettazione":
                    queryAccettazione(request, response);
                    break;
                case "reportLav":
                    reportLav(request, response);
                    break;
                case "queryPratiche":
                    queryPratiche(request, response);
                    break;
                case "checkUsername":
                    checkUsername(request, response);
                    break;
                case "queryUser":
                    queryUser(request, response);
                    break;
                case "queryEccessi":
                    queryEccessi(request, response);
                    break;
                default:
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
            }
        }
    }

    private void writeJsonResponse(HttpServletResponse response, Object list)
            throws ServletException, IOException {
        JsonObject jMembers = new JsonObject();
        StringWriter sw = new StringWriter();
        ObjectMapper mapper = new ObjectMapper();
        mapper.writeValue(sw, list);
        JsonElement tradeElement = JsonParser.parseString(sw.toString());
        jMembers.add("aaData", tradeElement.getAsJsonArray());
        response.setContentType("application/json");
        response.setHeader("Content-Type", "application/json");
        response.getWriter().print(jMembers.toString());
        response.getWriter().close();
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String p = request.getContextPath();
        request.getSession().invalidate();
        redirect(request, response, p);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
