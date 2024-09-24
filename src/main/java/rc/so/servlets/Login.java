/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package rc.so.servlets;

import com.google.gson.JsonObject;
import rc.so.db.FindoDB;
import static rc.so.engine.Action.getRequestValue;
import static rc.so.engine.Action.getUserByEmailOrUsername;
import static rc.so.engine.Action.insertTR;
import static rc.so.engine.Action.log;
import static rc.so.engine.Action.redirect;
import static rc.so.engine.Action.updatePassword;
import rc.so.entity.User;
import static rc.so.util.SendMailJet.sendMail;
import static rc.so.util.Utility.RESULT;
import static rc.so.util.Utility.USPWD;
import static rc.so.util.Utility.estraiEccezione;
import static rc.so.util.Utility.getRandomString;
import java.io.IOException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import static org.apache.commons.codec.digest.DigestUtils.md5Hex;
import static org.apache.commons.lang3.StringUtils.capitalize;
import static rc.so.util.Utility.TRUE;

/**
 *
 * @author rcosco
 */
public class Login extends HttpServlet {

    private static final long serialVersionUID = 1L;

    protected void changepassword(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession se = request.getSession();
        insertTR("I", request.getSession(), "RICHIESTA CAMBIO PASSWORD");
        String pattern = "(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@!#$%^&+=])(?=\\S+$).{8,}";
        String attuale = getRequestValue(request, "attuale");
        String nuova = getRequestValue(request, "nuova");
        String conferma = getRequestValue(request, "conferma");
        String us_pwd = (String) se.getAttribute(USPWD);
        String username = (String) se.getAttribute("us_cod");
        String md5_attuale = md5Hex(attuale);
        String md5_nuova = md5Hex(nuova);

        if (us_pwd.equals(md5_attuale) && nuova.equals(conferma) && !attuale.equals(nuova) && nuova.matches(pattern)) {
            FindoDB db = new FindoDB(log);
            boolean es = db.updatePassword(username, md5_nuova);
            db.closeDB();
            if (es) {
                se.setAttribute(USPWD, md5_nuova);
            }
            redirect(request, response, "password.jsp?esito=" + es);
        } else {
            if (!us_pwd.equals(md5_attuale)) {
                redirect(request, response, "password.jsp?esito=passerr1");
            } else if (!nuova.equals(conferma)) {
                redirect(request, response, "password.jsp?esito=passerr2");
            } else if (attuale.equals(nuova)) {
                redirect(request, response, "password.jsp?esito=passerr3");
            } else {
                redirect(request, response, "password.jsp?esito=passerr4");
            }
        }
    }

    protected void logout(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        insertTR("I", request.getSession(), "LOGOUT");
        request.getSession().invalidate();
    }

    protected void forgotPwd(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        JsonObject result = new JsonObject();

        User u = getUserByEmailOrUsername(request.getParameter("email"));
        if (u != null) {
            String pwd = getRandomString(8);
            if (updatePassword(u.getUsername(), md5Hex(pwd))) {
                result.addProperty(RESULT, TRUE);
                try {
                    sendMail("Findomestic", new String[]{u.getEmail()}, "utente: " + u.getUsername() + "<br>La nuova password : " + pwd + "<br><br>La password puo' essere cambiata dalle impostazioni profilo", "Recupero Password");
                } catch (Exception e) {
                    result.addProperty(RESULT, TRUE);
//                    result.addProperty(RESULT, FALSE);
//                    result.addProperty(MESSAGE, "non e' stato possibile mandare la email");
                }
            } else {
                result.addProperty(RESULT, TRUE);
//                result.addProperty(RESULT, FALSE);
//                result.addProperty(MESSAGE, "non e' stato possibile cambiare la password");
            }
        } else {
            result.addProperty(RESULT, TRUE);
//            result.addProperty(RESULT, FALSE);
//            result.addProperty(MESSAGE, "Uesername o Email errati");
        }
        response.getWriter().write(result.toString());
        response.getWriter().close();
    }

    protected void login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        String username = request.getParameter("username");
        insertTR("I", username, "LOGIN");
        String pass = request.getParameter("password");
        if (username != null && pass != null) {
            if (!username.trim().equals("") && !pass.trim().equals("")) {

                FindoDB db = new FindoDB(log);
                User user = db.login(username, md5Hex(pass));
                db.closeDB();

                if (user != null) {
                    HttpSession se = request.getSession();
                    se.setAttribute("us_cod", user.getUsername());
                    se.setAttribute("us_nome", capitalize(user.getNome().toLowerCase()));
                    se.setAttribute("us_pwd", user.getPassword());
                    se.setAttribute("us_cognome", capitalize(user.getCognome().toLowerCase()));
                    se.setAttribute("us_rolecod", user.getTipo());
                    se.setAttribute("us_role", user.getDescrizionetipo());
                    redirect(request, response, "pratiche.jsp");
                } else {
                    redirect(request, response, "login.jsp?error=1");
                }

            } else {
                redirect(request, response, "login.jsp?error=2");

            }
        } else {
            redirect(request, response, "login.jsp?error=3");
        }

    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response) {
        try {
            response.setContentType("text/html;charset=UTF-8");
            String type = request.getParameter("type");
            switch (type) {
                case "login":
                    login(request, response);
                    break;
                case "changepassword":
                    changepassword(request, response);
                    break;
                case "logout":
                    logout(request, response);
                    break;
                case "forgotPwd":
                    forgotPwd(request, response);
                    break;
                default:
                    String p = request.getContextPath();
                    request.getSession().invalidate();
                    redirect(request, response, p);
                    break;
            }
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
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
