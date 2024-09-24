/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rc.so.servlets;

import com.google.gson.JsonObject;
import rc.so.db.FindoDB;
import rc.so.engine.Action;
import static rc.so.engine.Action.createUser;
import static rc.so.engine.Action.ctrlDismissione;
import static rc.so.engine.Action.getConfig;
import static rc.so.engine.Action.getNomeFile;
import static rc.so.engine.Action.getRequestValue;
import static rc.so.engine.Action.insertTR;
import static rc.so.engine.Action.pubblicaPratica;
import static rc.so.engine.Action.redirect;
import static rc.so.util.SendMailJet.sendMail;
import static rc.so.util.Utility.APPJSON;
import static rc.so.util.Utility.MESSAGE;
import static rc.so.util.Utility.RESULT;
import static rc.so.util.Utility.USERNAME;
import static rc.so.util.Utility.getRandomString;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
import static java.nio.file.Files.probeContentType;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static rc.so.engine.Action.log;
import static rc.so.util.Utility.estraiEccezione;

/**
 *
 * @author rcosco
 */
public class Operations extends HttpServlet {

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
    protected void editpratica(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String idoper = getRequestValue(request, "idoper");
        String pratica = getRequestValue(request, "pratica");
        String cir = getRequestValue(request, "cir");
        String cognome = getRequestValue(request, "cognome").toUpperCase();
        String nome = getRequestValue(request, "nome").toUpperCase();
        insertTR("W", request.getSession(),
                "Modifica pratica: " + idoper + " - VALORI: " + pratica + " ; " + cir + " ; " + cognome + " ; " + nome);
        FindoDB db = new FindoDB(Action.log);
        boolean es = db.updatePratica(idoper, pratica, cir, cognome, nome);
        db.closeDB();
        redirect(request, response, "modifica.jsp?ido=" + idoper + "&esito=" + es);
    }

    protected void addUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType(APPJSON);
        JsonObject resp = new JsonObject();

        String password = getRandomString(8);

        if (createUser(request.getParameter("nome"), request.getParameter("cognome"),
                request.getParameter("email"), request.getParameter(USERNAME), md5Hex(password),
                request.getParameter("tipo"))) {
            try {
                sendMail("Findomestic", new String[]{request.getParameter("email")},
                        "Nuova utenza creata<br><br>username :" + request.getParameter("username") + "<br>password: "
                        + password,
                        "Utente findomestic");
                resp.addProperty(RESULT, true);
            } catch (Exception e) {
                log.severe(estraiEccezione(e));
                resp.addProperty(RESULT, false);
                resp.addProperty(MESSAGE, "Errore: utenza creta ma non e' stato possibile inviare la mail.");
            }

        } else {
            resp.addProperty(RESULT, false);
            resp.addProperty(MESSAGE, "Errore: non &egrave; stato possibile creare l'utenza.");
        }

        response.getWriter().write(resp.toString());
        response.getWriter().close();
    }

    protected void deleteUser(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject resp = new JsonObject();

        if (Action.deleteUser(request.getParameter("username"))) {
            resp.addProperty(RESULT, true);
        } else {
            resp.addProperty(RESULT, false);
            resp.addProperty(MESSAGE, "Errore: non &egrave; stato possibile eliminare l'utenza.");
        }

        response.getWriter().write(resp.toString());
        response.getWriter().close();
    }

    /* Pubblicare la pratica in eccesso tramite sweetalert (25/02/21) */
    protected void PubblicaPratica(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject resp = new JsonObject();
        resp.addProperty("result", pubblicaPratica(request.getParameter("endorse")));
        response.getWriter().write(resp.toString());
        response.getWriter().close();
    }

    /* Visualizzazione PDF in fancybox (25/02/21) */
    protected void showPDF(HttpServletRequest request, HttpServletResponse response) {

        String ido = request.getParameter("ido");
        String path;
        if (ctrlDismissione(ido)) {
            path = getConfig("dismissione");
        } else {
            path = getNomeFile(ido);
        }
        File downloadFile = new File(path);
        if (downloadFile.exists()) {

            try {

                try (FileInputStream inStream = new FileInputStream(downloadFile)) {
                    String mimeType = probeContentType(downloadFile.toPath());
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("inline; filename=\"%s\"", downloadFile.getName());
                    response.setHeader(headerKey, headerValue);
                    try (OutputStream outStream = response.getOutputStream()) {
                        byte[] buffer = new byte[4096 * 4096];
                        int bytesRead;
                        while ((bytesRead = inStream.read(buffer)) != -1) {
                            outStream.write(buffer, 0, bytesRead);
                        }
                    }
                }

            } catch (Exception e) {
                log.severe(estraiEccezione(e));
            }
        } else {
            try {
                response.getWriter().print(path + " NOT FOUND");
            } catch (IOException e) {
                log.severe(estraiEccezione(e));
            }

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
                case "editpratica":
                    editpratica(request, response);
                    break;
                case "addUser":
                    addUser(request, response);
                    break;
                case "deleteUser":
                    deleteUser(request, response);
                    break;
                case "PubblicaPratica":
                    PubblicaPratica(request, response);
                    break;
                case "showPDF":
                    showPDF(request, response);
                    break;
                default:
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
            }
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the
    // + sign on the left to edit the code.">
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
