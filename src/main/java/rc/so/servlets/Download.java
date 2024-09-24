/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.servlets;

import rc.so.db.FindoDB;
import rc.so.engine.Action;
import static rc.so.engine.Action.getRequestValue;
import static rc.so.engine.Action.insertTR;
import static rc.so.engine.Action.redirect;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import static java.lang.String.format;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import static org.apache.commons.codec.binary.Base64.decodeBase64;
import static org.apache.commons.io.FileUtils.readFileToByteArray;

/**
 *
 * @author rcosco
 */
public class Download extends HttpServlet {

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
    protected void onlydownload(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String contentname = getRequestValue(request, "contentname");
        String content = getRequestValue(request, "content");
        insertTR("I", request.getSession(), "DOWNLOAD FILE " + contentname);
        String headerKey = "Content-Disposition";
        String headerValue = format("attachment; filename=\"%s\"", contentname);
        response.setHeader(headerKey, headerValue);
        try (OutputStream outStream = response.getOutputStream()) {
            outStream.write(decodeBase64(content));
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
    protected void downloaddoc(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String ido = getRequestValue(request, "ido");
        FindoDB db = new FindoDB(Action.log);
        String path = db.getNomeFile(ido);
        db.closeDB();
        File f = new File(path);
        if (f.exists()) {
            String contentname = ido + ".pdf";
            insertTR("I", request.getSession(), "DOWNLOAD FILE " + contentname);
            String headerKey = "Content-Disposition";
            String headerValue1 = format("attachment; filename=\"%s\"", contentname);
            response.setHeader(headerKey, headerValue1);
            try (OutputStream outStream = response.getOutputStream()) {
                outStream.write(readFileToByteArray(f));
            }
        } else {
            response.getWriter().print(path + " NOT FOUND");
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
                case "downloaddoc":
                    downloaddoc(request, response);
                    break;
                case "onlydownload":
                    onlydownload(request, response);
                    break;
                default:
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
            }
        }
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
