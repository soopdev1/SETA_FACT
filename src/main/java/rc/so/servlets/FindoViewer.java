
package rc.so.servlets;

import static rc.so.engine.Action.getNomeFile;
import static rc.so.engine.Action.loginFindo;
import static rc.so.engine.Action.redirect;
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
import static rc.so.engine.Action.log;
import static rc.so.util.Utility.estraiEccezione;

/**
 *
 * @author rcosco
 */
public class FindoViewer extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void showPDF(HttpServletRequest request, HttpServletResponse response) {
        String ido = request.getParameter("ido");
        String path = getNomeFile(ido);
        File downloadFile = new File(path);
        if (downloadFile.exists()) {
            try {
                
                try ( FileInputStream inStream = new FileInputStream(downloadFile)) {
                    String mimeType = probeContentType(downloadFile.toPath());
                    if (mimeType == null) {
                        mimeType = "application/octet-stream";
                    }
                    response.setContentType(mimeType);
                    String headerKey = "Content-Disposition";
                    String headerValue = format("inline; filename=\"%s\"", downloadFile.getName());
                    response.setHeader(headerKey, headerValue);
                    try ( OutputStream outStream = response.getOutputStream()) {
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
            } catch (Exception e) {
                log.severe(estraiEccezione(e));
            }
        }
    }

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        String user = request.getParameter("username");
        String password = request.getParameter("password");
        boolean loginFindo = loginFindo(user, password);
        if (loginFindo) {
            showPDF(request, response);
        } else {
            String p = request.getContextPath();
            request.getSession().invalidate();
            redirect(request, response, p);
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
        processRequest(request, response);
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
