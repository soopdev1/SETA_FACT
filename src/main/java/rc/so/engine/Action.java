/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.engine;

import static com.google.common.base.Charsets.ISO_8859_1;
import static com.google.common.base.Charsets.UTF_8;
import rc.so.db.FindoDB;
import rc.so.entity.Corriere;
import rc.so.entity.Eccessi;
import rc.so.entity.Lavorazione;
import rc.so.entity.Pratica;
import rc.so.entity.User;
import static rc.so.util.Utility.estraiEccezione;
import java.io.File;
import static java.io.File.separator;
import java.io.IOException;
import java.util.ArrayList;
import static java.util.Comparator.naturalOrder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import static java.util.Locale.ITALY;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.FileHandler;
import static java.util.logging.Level.INFO;
import java.util.logging.Logger;
import static java.util.logging.Logger.getLogger;
import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.commons.fileupload2.core.FileItem;
import org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload;
import static org.apache.commons.fileupload2.jakarta.JakartaServletFileUpload.isMultipartContent;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;
import static org.apache.commons.lang3.StringUtils.normalizeSpace;
import static org.apache.commons.lang3.StringUtils.replace;
import org.joda.time.DateTime;
import static org.joda.time.format.DateTimeFormat.forPattern;
import org.joda.time.format.DateTimeFormatter;

/**
 *
 * @author rcosco
 */
public class Action {

    public static final Logger log = createLog("FindoFact");
    public static final String PAT_1 = "dd/MM/yyyy HH:mm:ss";
    public static final String PAT_2 = "dd/MM/yyyy";
    public static final String PAT_3 = "HHmmss";
    public static final String PAT_4 = "ddMMyyyy";
    public static final String PAT_5 = "yyyy-MM-dd HH:mm:ss";
    public static final String PAT_6 = "yyyy-MM-dd";
    public static final String PAT_7 = "yyyyMMddHHmmssSSS";
    public static final String PAT_8 = "ddMMyyyyHHmmss";
    public static final String PAT_9 = "yyMMddHHmmssSSS";
    public static final String PATHLOG = "/data/datastore/log/";

    private static Logger createLog(String appname) {
        Logger logger = getLogger(appname);
        try {
            String dataOdierna = new DateTime().toString(PAT_4);

            File logdir = new File(PATHLOG);
            if (!logdir.exists()) {
                logdir.mkdir();
            }
            String ora = new DateTime().toString(PAT_3);
            String pathLog = PATHLOG + dataOdierna;
            File dirLog = new File(pathLog);
            if (!dirLog.exists()) {
                dirLog.mkdirs();
            }
            FileHandler fh = new FileHandler(pathLog + separator + appname + "_" + ora + ".log", true);
            logger.addHandler(fh);
        } catch (Exception ex) {
            logger.severe(ex.getMessage());
        }
        return logger;
    }

    public static List<Corriere> list_Corriere_show() {
        FindoDB fdb = new FindoDB(log);
        List<Corriere> li = fdb.list_Corriere_show();
        fdb.closeDB();
        return li;
    }

    public static List<Lavorazione> list_Lavorazione() {
        FindoDB fdb = new FindoDB(log);
        List<Lavorazione> li = fdb.list_Lavorazione();
        fdb.closeDB();
        return li;
    }

    public static Pratica getPratica_doc(String iddoc) {
        FindoDB fdb = new FindoDB(log);
        Pratica pr = fdb.getPratica_doc(iddoc);
        fdb.closeDB();
        return pr;
    }

    public static Map<String, String> getFilter(HttpServletRequest request, boolean print) {
        Map<String, String[]> parameterNames = request.getParameterMap();
        Iterator<String> li = parameterNames.keySet().iterator();
        List<String> key = new LinkedList<>();
        List<String> val = new LinkedList<>();

        List<String> filter = new LinkedList<>();
        while (li.hasNext()) {
            filter.add(li.next());
        }

        filter.sort(naturalOrder());
        filter.forEach(re -> {
            if (re.contains("[name")) {
                key.add(parameterNames.get(re)[0]);
            } else if (re.contains("[value")) {
                val.add(parameterNames.get(re)[0]);
            } else {
                key.add(re);
                val.add(new String(request.getParameterValues(re)[0].getBytes(ISO_8859_1), UTF_8));
            }
        });

        Map<String, String> listValues = new HashMap<>();
        AtomicInteger ind = new AtomicInteger(0);
        key.forEach(k1 -> {
            if (listValues.get(k1) == null) {
                listValues.put(k1, val.get(ind.get()));
            } else {
                listValues.put(k1, listValues.get(k1) + ";" + val.get(ind.get()));
            }
            ind.addAndGet(1);
        });

        if (print) {
            Iterator<String> finale = listValues.keySet().iterator();
            finale.forEachRemaining(re
                    -> log.log(INFO, "{0} - {1}", new Object[]{re, listValues.get(re)})
            );
        }

        return listValues;
    }

    public static String formatStringtoStringDate(String dat, String pattern1, String pattern2) {
        try {
            if (dat.length() == 21) {
                dat = dat.substring(0, 19);
            }
            if (dat.length() == pattern1.length()) {
                DateTimeFormatter formatter = forPattern(pattern1);
                DateTime dt = formatter.parseDateTime(dat);
                return dt.toString(pattern2, ITALY);
            }
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
        }
        return "No correct date";
    }

    public static void printRequest(HttpServletRequest request) {
        Enumeration<String> parameterNames = request.getParameterNames();
        while (parameterNames.hasMoreElements()) {
            String paramName = parameterNames.nextElement();
            String[] paramValues = request.getParameterValues(paramName);
            for (String paramValue : paramValues) {
                log.log(INFO, "NORMAL FIELD - {0} : {1}", new Object[]{paramName, new String(paramValue.getBytes(ISO_8859_1), UTF_8)});
            }
        }
        boolean isMultipart = isMultipartContent(request);
        if (isMultipart) {
            try {
                JakartaServletFileUpload upload = new JakartaServletFileUpload();
                List<FileItem> items = upload.parseRequest(request);
                for (FileItem item : items) {
                    if (item.isFormField()) {
                        String fieldName = item.getFieldName();
                        String value = new String(item.getString().getBytes(ISO_8859_1), UTF_8);
                        log.log(INFO, "MULTIPART FIELD - {0} : {1}", new Object[]{fieldName, value});
                    } else {
                        String fieldName = item.getFieldName();
                        String fieldValue = item.getName();
                        log.log(INFO, "MULTIPART FILE - {0} : {1}", new Object[]{fieldName, fieldValue});
                    }
                }
            } catch (Exception ex) {
                log.severe(estraiEccezione(ex));
            }
        }
    }

    public static void redirect(HttpServletRequest request, HttpServletResponse response, String destination) throws ServletException, IOException {

//        String domain = "";
        String domain = "https://documentaleseta.servizi.findomestic.local/";
        if (response.isCommitted()) {
            RequestDispatcher dispatcher = request.getRequestDispatcher(domain + destination);
            dispatcher.forward(request, response);
        } else {
            response.sendRedirect(domain + destination);
        }

    }

    public static String getRequestValue(HttpServletRequest request, String fieldname) {
        String out = request.getParameter(fieldname);
        if (out == null) {
            out = "";
        } else {
            out = out.trim();
        }
        return out;
    }

    public static String[] formatEsito(String esito) {
        if (esito.startsWith("OK") || esito.startsWith("true")) {
            return new String[]{"alert-success", "<i class='fas fa-check'></i> Operazione completata con successo."};
        } else {
            String alert = "alert-danger";
            String icon = "<i class='fas fa-exclamation-triangle'></i>";
            if (esito.startsWith("passerr")) {
                if (esito.equalsIgnoreCase("passerr1")) {
                    return new String[]{alert, icon
                        + " La 'password attuale' inserita risulta essere errata. Riprovare."};

                } else if (esito.equalsIgnoreCase("passerr2")) {
                    return new String[]{alert, icon
                        + " 'Nuova password' e 'Conferma nuova password' non coincidono. Riprovare."};

                } else if (esito.equalsIgnoreCase("passerr3")) {
                    return new String[]{alert, icon
                        + " La 'Nuova password' deve essere diversa dalla 'password attuale'. Riprovare."};

                } else if (esito.equalsIgnoreCase("passerr4")) {
                    return new String[]{alert, icon
                        + " La 'Nuova password' non soddisfa le caratteristiche minime indicate. Riprovare."};

                }
            }
            return new String[]{alert, icon
                + " Si &#232; verificato un errore durante l'operazione. Riprovare."};
        }
    }

    public static String formatLoginError(String err) {
        return "LOGIN ERROR";
//        if (err.equals("1")) {
//            return "UTENTE NON TROVATO";
//        } else if (err.equals("2") || err.equals("3")) {
//            return "'USERNAME' E 'PASSWORD' SONO OBBLIGATORI";
//        }
//        return "ERRORE GENERICO";
    }

    public static String verifyUser(HttpServletRequest req) {
        if (req.getSession() != null) {
            String us_cod = (String) req.getSession().getAttribute("us_cod");
            if (us_cod != null) {
                return null;
            } else {
                return "login.jsp";
            }
        } else {
            return "login.jsp";
        }
    }

    public static boolean is_outsourcer(HttpSession session) {
        try {
            return session.getAttribute("us_rolecod").toString().equals("1");
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
            return false;
        }
    }

    public static String visualDatatable(String ing) {
        if (ing == null) {
            return "";
        }
        ing = replace(ing, "\\'", "'");
        ing = replace(ing, "\'", "'");
        ing = replace(ing, "\"", "'");
        ing = replace(ing, "\\\\", "/");
        ing = replace(ing, "\\", "/");
        ing = normalizeSpace(ing);
        return ing.trim().toUpperCase();
    }

    public static void insertTR(String type, HttpSession session, String descr) {
        String user = (String) session.getAttribute("us_cod");
        if (user == null) {
            user = "service";
        }

        FindoDB db = new FindoDB(log);
        db.insertTR(type, user, descr);
        db.closeDB();
    }

    public static void insertTR(String type, String user, String descr) {
        FindoDB db = new FindoDB(log);
        db.insertTR(type, user, descr);
        db.closeDB();
    }

    public static String generaId(int length) {
        String random = randomAlphanumeric(length - 15).trim();
        return new DateTime().toString(PAT_9) + random;
    }

    public static User getUserByEmailOrUsername(String email) {
        FindoDB fdb = new FindoDB(log);
        User out = fdb.getUserByEmailOrUsername(email);
        fdb.closeDB();
        return out;
    }

    public static boolean updatePassword(String username, String password) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.updatePassword(username, password);
        fdb.closeDB();
        return out;
    }

    public static boolean createUser(String nome, String cognome, String email, String username, String password, String tipo) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.createUser(nome, cognome, email, username, password, tipo);
        fdb.closeDB();
        return out;
    }

    public static ArrayList<User> getUserList(String nome, String cognome, String username, String tipo) {
        FindoDB fdb = new FindoDB(log);
        ArrayList<User> out = fdb.getUserList(nome, cognome, username, tipo);
        fdb.closeDB();
        return out;
    }

    public static ArrayList<Eccessi> getEccessiList(String endorse, String pratica, String cir, String from, String to, String ldv, String tvei) {
        FindoDB fdb = new FindoDB(log);
        ArrayList<Eccessi> out = fdb.getEccessiList(endorse, pratica, cir, from, to, ldv, tvei);
        fdb.closeDB();
        return out;
    }

    public static boolean deleteUser(String username) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.deleteUser(username);
        fdb.closeDB();
        return out;
    }

    public static boolean pubblicaPratica(String endorse) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.pubblicaPratica(endorse);
        fdb.closeDB();
        return out;
    }

    public static String getNomeFile(String id) {
        FindoDB fdb = new FindoDB(log);
        String out = fdb.getNomeFile(id);
        fdb.closeDB();
        return out;
    }

    public static boolean loginFindo(String usr, String pwd) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.loginFindo(usr, pwd);
        fdb.closeDB();
        return out;
    }

    public static boolean ctrlDismissione(String iddoc) {
        FindoDB fdb = new FindoDB(log);
        boolean out = fdb.ctrlDismissione(iddoc);
        fdb.closeDB();
        return out;
    }

    public static String getConfig(String id) {
        FindoDB fdb = new FindoDB(log);
        String output = fdb.getConfig(id);
        fdb.closeDB();
        return output;
    }

    public static String getMap(Map<String, String> filter, String index) {
        try {
            String da1 = filter.get(index);
            return da1.trim();
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
        }
        return "";
    }

}
