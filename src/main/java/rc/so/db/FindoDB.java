package rc.so.db;

import static rc.so.engine.Action.PAT_1;
import static rc.so.engine.Action.PAT_2;
import static rc.so.engine.Action.PAT_5;
import static rc.so.engine.Action.PAT_6;
import static rc.so.engine.Action.PAT_8;
import static rc.so.engine.Action.formatStringtoStringDate;
import static rc.so.engine.Action.generaId;
import static rc.so.engine.Action.getMap;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Properties;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import rc.so.entity.Accettazione;
import rc.so.entity.Corriere;
import rc.so.entity.Distributore;
import rc.so.entity.Eccessi;
import rc.so.entity.Lavorazione;
import rc.so.entity.Pratica;
import rc.so.entity.StatoConsegna;
import rc.so.entity.Tipodoc;
import rc.so.entity.User;
import static rc.so.util.Utility.COGNOME;
import static rc.so.util.Utility.CORRIERE;
import static rc.so.util.Utility.DESCSTATOWS;
import static rc.so.util.Utility.ENDORSE;
import static rc.so.util.Utility.LAVOR;
import static rc.so.util.Utility.PASSWORD;
import static rc.so.util.Utility.PRATICA;
import static rc.so.util.Utility.STATOWS;
import static rc.so.util.Utility.SYSTEM;
import static rc.so.util.Utility.WHERE;
import static rc.so.util.Utility.estraiEccezione;
import java.sql.DriverManager;
import static java.util.logging.Level.SEVERE;
import static rc.so.util.Utility.conf;

/**
 *
 * @author rcosco
 */
public class FindoDB {

    Connection c;
    Logger log;

    private final String user = conf.getString("db.user");
    private final String pwd = conf.getString("db.pass");
    private final String host = conf.getString("db.ip") + ":3306/findofact";
    private final String drivername = conf.getString("db.driver");
    private final String typedb = conf.getString("db.tipo");

    public FindoDB(Logger l) {
        this.log = l;
        try {
            Class.forName(drivername).newInstance();
            Properties p = new Properties();
            p.put("user", user);
            p.put("password", pwd);
            p.put("useUnicode", "true");
            p.put("characterEncoding", "UTF-8");
            p.put("connectTimeout", "1000");
            p.put("useUnicode", "true");
            p.put("serverTimezone", "Europe/Rome");
            this.c = DriverManager.getConnection("jdbc:" + typedb + "://" + host, p);
        } catch (Exception ex) {
            this.log.log(SEVERE, "jdbc:{0}://{1}", new Object[]{typedb, host});
            this.log.log(SEVERE, estraiEccezione(ex));
            this.c = null;
        }
    }

//    private static final String PASSWORDPROD = "Findo012!";
//    private static final String HOSTPROD = "10.72.107.100:3306/findofact";
//
//    public FindoDB(Logger l) {
//        String user = "root";
//        this.log = l;
//        try {
//            forName("com.mysql.cj.jdbc.Driver");
//            Properties p = new Properties();
//            p.put("user", user);
//            p.put(PASSWORD, PASSWORDPROD);
//            p.put("characterEncoding", UTF8);
//            p.put("passwordCharacterEncoding", UTF8);
//            p.put("useSSL", FALSE);
//            p.put("connectTimeout", "1000");
//            p.put("useUnicode", "true");
//            p.put("serverTimezone", "CET");
//            this.c = getConnection("jdbc:mysql://" + HOSTPROD, p);
//        } catch (Exception ex) {
//            this.log.log(SEVERE, estraiEccezione(ex));
//            this.c = null;
//        }
//    }

    public Connection getC() {
        return c;
    }

    public void setC(Connection c) {
        this.c = c;
    }

    public void closeDB() {
        try {
            if (this.c != null) {
                this.c.close();
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
    }

    public Pratica getPratica_doc(String iddoc) {
        List<Tipodoc> tipodc = list_Tipodoc();
        List<Lavorazione> tipola = list_Lavorazione();
        List<Corriere> tipocor = list_Corriere();
        try {
            String sql = "SELECT * FROM pratica WHERE iddocumento = ? ORDER BY timestamp DESC LIMIT 1";
            try (PreparedStatement ps = this.c.prepareStatement(sql)) {
                ps.setString(1, iddoc);
                try (ResultSet rs = ps.executeQuery()) {
                    if (rs.next()) {
                        Pratica pr = new Pratica();
                        pr.setCir(rs.getString("codaut"));
                        pr.setCliente(rs.getString(COGNOME) + " " + rs.getString("nome"));
                        pr.setCognome(rs.getString(COGNOME));
                        pr.setNome(rs.getString("nome"));
                        pr.setDatalavorazione(
                                formatStringtoStringDate(rs.getString("dataLav") + rs.getString("oraLav"), PAT_8, PAT_1));
                        pr.setIddocumento(rs.getString("iddocumento"));
                        pr.setLdv(rs.getString("codBusta"));
                        pr.setLotto(rs.getString("lotto"));
                        pr.setNumeropratica(rs.getString("codPrt"));
                        pr.setStatows(rs.getString(DESCSTATOWS));
                        pr.setNumeroinvii(rs.getString("richiamows"));

                        pr.setTipodocumento(rs.getString("tipoDoc"));
                        Optional<String> tipodoc = tipodc.stream().filter(td -> td.getId().equals(pr.getTipodocumento()))
                                .findAny().map(td -> td.getVisualizza());
                        if (tipodoc.isPresent()) {
                            pr.setTipodocumento(tipodoc.get());
                        }

                        pr.setTipolavorazione(rs.getString("lavorazione"));
                        Optional<String> tipolav = tipola.stream().filter(td -> td.getId().equals(pr.getTipolavorazione()))
                                .findAny().map(td -> td.getDescrizione());
                        if (tipolav.isPresent()) {
                            pr.setTipolavorazione(tipolav.get());
                        }

                        pr.setTvei(rs.getString("tvei"));

                        pr.setEndorse(rs.getString(ENDORSE));

                        pr.setCorriere(rs.getString(CORRIERE));

                        Optional<String> tipocorr = tipocor.stream().filter(td -> td.getId().equals(pr.getCorriere())).findAny()
                                .map(td -> td.getDescr());
                        if (tipocorr.isPresent()) {
                            pr.setCorriere(tipocorr.get());
                        }
                        pr.setPagine(rs.getString("pagine"));
                        pr.setUtente(rs.getString("utente"));
                        pr.setBarcode(rs.getString("barcode"));
                        pr.setEsitoco(rs.getString("esitoconformita"));
                        return pr;
                    }
                }
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return null;

    }

    public List<Lavorazione> list_report_lav(Map<String, String> filter) {
        List<Lavorazione> out = new ArrayList<>();
        try {
            String sql = "SELECT * FROM reportlavorazione WHERE data_riferimento >= ? ORDER BY data_riferimento DESC LIMIT 50";
            try (PreparedStatement ps = this.c.prepareStatement(sql)) {
                if (filter.get("da1") != null && !filter.get("da1").trim().equals("")
                        && !filter.get("da1").trim().equals("-")) {
                    ps.setString(1, formatStringtoStringDate(filter.get("da1"), PAT_2, PAT_6));
                } else {
                    ps.setString(1, new DateTime().toString(PAT_6));
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        out.add(new Lavorazione(rs.getString(1), rs.getString(4), rs.getString(2), rs.getString(3)));
                    }
                }
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    /**
     *
     * @param filter
     * @return
     */
    public List<Pratica> list_pratica(Map<String, String> filter) {
        List<Pratica> out = new ArrayList<>();
        List<Tipodoc> tipodc = list_Tipodoc();
        List<Lavorazione> tipola = list_Lavorazione();

        try {

            String da1 = getMap(filter, "da1");
            String da2 = getMap(filter, "da2");
            String tvei = getMap(filter, "tvei");
            String lavor = getMap(filter, LAVOR);
            String cognome = getMap(filter, COGNOME);
            String nome = getMap(filter, "nome");
            String ldv = getMap(filter, "ldv");
            String cir = getMap(filter, "cir");
            String pratica = getMap(filter, PRATICA);
            String statows = getMap(filter, STATOWS);

            List<Param> params = new ArrayList<>();

            StringBuilder sql = new StringBuilder("SELECT * FROM pratica WHERE 1=1 ");

            switch (da1) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND STR_TO_DATE(dataLav, '%d%m%Y') >= ? ");
                    params.add(new Param(1, formatStringtoStringDate(da1, PAT_2, PAT_6)));
                    break;
            }
            switch (da2) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND STR_TO_DATE(dataLav, '%d%m%Y') <= ? ");
                    params.add(new Param(2, formatStringtoStringDate(da2, PAT_2, PAT_6)));
                    break;
            }
            switch (lavor) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND lavorazione = ? ");
                    params.add(new Param(3, lavor));
                    break;
            }
            switch (tvei) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND tvei = ? ");
                    params.add(new Param(4, tvei));
                    break;
            }
            switch (cognome) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND cognome LIKE ? ");
                    params.add(new Param(5, "%" + cognome + "%"));
                    break;
            }
            switch (nome) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND nome LIKE ? ");
                    params.add(new Param(6, "%" + nome + "%"));
                    break;
            }
            switch (ldv) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND codBusta LIKE ? ");
                    params.add(new Param(7, "%" + ldv + "%"));
                    break;
            }
            switch (cir) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND codaut LIKE ? ");
                    params.add(new Param(8, "%" + cir + "%"));
                    break;
            }
            switch (pratica) {
                case "":
                case "-":
                    break;
                default:
                    sql.append(" AND codPrt = ? ");
                    params.add(new Param(9, pratica));
                    break;
            }
            switch (statows) {
                case "":
                case "-":
                    break;
                case "0":
                    sql.append(" AND (desc_stato_ws ='-' OR desc_stato_ws ='0000' OR desc_stato_ws ='1010') ");
                    break;
                case "1":
                    sql.append(" AND desc_stato_ws <>'-' AND desc_stato_ws <>'0000' AND desc_stato_ws <>'1010' ");
                    break;
                default:
                    break;
            }

            sql.append(" ORDER BY timestamp DESC LIMIT 50");
            try (PreparedStatement ps = this.c.prepareStatement(sql.toString())) {
                int index = 1;
                for (Param param : params) {
                    ps.setString(index, param.getValue().toString());
                    index++;
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Pratica pr = new Pratica();
                        pr.setCir(rs.getString("codaut"));
                        pr.setCliente(rs.getString(COGNOME) + " " + rs.getString("nome"));
                        pr.setCognome(rs.getString(COGNOME));
                        pr.setNome(rs.getString("nome"));
                        pr.setDatalavorazione(
                                formatStringtoStringDate(rs.getString("dataLav") + rs.getString("oraLav"), PAT_8, PAT_1));
                        pr.setIddocumento(rs.getString("iddocumento"));
                        pr.setLdv(rs.getString("codBusta"));
                        pr.setLotto(rs.getString("lotto"));
                        pr.setNumeropratica(rs.getString("codPrt"));
                        pr.setStatows(rs.getString(DESCSTATOWS));
                        pr.setNumeroinvii(rs.getString("richiamows"));
                        pr.setTipodocumento(rs.getString("tipoDoc"));
                        Optional<String> tipodoc = tipodc.stream().filter(td -> td.getId().equals(pr.getTipodocumento()))
                                .findAny().map(td -> td.getVisualizza());
                        if (tipodoc.isPresent()) {
                            pr.setTipodocumento(tipodoc.get());
                        }
                        pr.setTipolavorazione(rs.getString("lavorazione"));
                        Optional<String> tipolav = tipola.stream().filter(td -> td.getId().equals(pr.getTipolavorazione()))
                                .findAny().map(td -> td.getDescrizione());
                        if (tipolav.isPresent()) {
                            pr.setTipolavorazione(tipolav.get());
                        }
                        pr.setTvei(rs.getString("tvei"));
                        pr.setEndorse(rs.getString(ENDORSE));
                        pr.setCorriere(rs.getString(CORRIERE));
                        pr.setPagine(rs.getString("pagine"));
                        pr.setUtente(rs.getString("utente"));
                        pr.setBarcode(rs.getString("barcode"));
                        pr.setEsitoco(rs.getString("esitoconformita"));

                        boolean statuserror = rs.getString(DESCSTATOWS).equals("-")
                                || rs.getString(DESCSTATOWS).equals("0000") || rs.getString(DESCSTATOWS).equals("1010");
                        pr.setStatuserror(!statuserror);
                        out.add(pr);
                    }
                }
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    public List<Accettazione> list_accettazione(Map<String, String> filter) {
        List<Lavorazione> tipola = list_Lavorazione();
        List<Accettazione> out = new ArrayList<>();
        try {

            List<Param> params = new ArrayList<>();
            StringBuilder sql = new StringBuilder("SELECT * FROM accettazione WHERE 1=1 ");

            if (filter.get("da1") != null && !filter.get("da1").trim().equals("")
                    && !filter.get("da1").trim().equals("-")) {
                sql.append(" AND data >= ? ");
                params.add(new Param(1, formatStringtoStringDate(filter.get("da1"), PAT_2, PAT_6)));
            }

            if (filter.get("da2") != null && !filter.get("da2").trim().equals("")
                    && !filter.get("da2").trim().equals("-")) {
                sql.append(" AND data <= ? ");
                params.add(new Param(2, formatStringtoStringDate(filter.get("da2"), PAT_2, PAT_6)));
            }

            if (filter.get(CORRIERE) != null && !filter.get(CORRIERE).trim().equals("")
                    && !filter.get(CORRIERE).trim().equals("-")) {
                sql.append(" AND corriere = ? ");
                params.add(new Param(3, filter.get(CORRIERE)));
            }

            if (filter.get("ldv") != null && !filter.get("ldv").trim().equals("")
                    && !filter.get("ldv").trim().equals("-")) {
                sql.append(" AND ldv LIKE ? ");
                params.add(new Param(4, "%" + filter.get("ldv") + "%"));
            }
            sql.append(" ORDER BY data DESC,ora DESC");

            try (PreparedStatement ps = this.c.prepareStatement(sql.toString())) {
                int index = 1;
                for (Param param : params) {
                    ps.setString(index, param.getValue().toString());
                    index++;
                }
                try (ResultSet rs = ps.executeQuery()) {
                    while (rs.next()) {
                        Accettazione ac = new Accettazione(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4),
                                rs.getString(5), rs.getString(6), rs.getString(7), rs.getString(8), rs.getString(9),
                                rs.getString(10), rs.getString(11), rs.getString(12), rs.getString(13), rs.getString(14),
                                rs.getString(15), rs.getString(16));
                        Optional<String> tipolav = tipola.stream().filter(td -> td.getId().equals(ac.getLavorazione()))
                                .findFirst().map(td -> td.getDescrizione());
                        if (tipolav.isPresent()) {
                            ac.setLavorazione(tipolav.get());
                        }

                        out.add(ac);
                    }
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    public List<Corriere> list_Corriere_show() {
        List<Corriere> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM corriere WHERE visualizza = ?";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1)) {
                ps1.setString(1, "SI");
                try (ResultSet rs = ps1.executeQuery()) {
                    while (rs.next()) {
                        out.add(new Corriere(rs.getString(1), rs.getString(2), rs.getString(3)));
                    }
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }

        return out;
    }

    public List<Corriere> list_Corriere() {
        List<Corriere> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM corriere";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1); ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    out.add(new Corriere(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    public List<Tipodoc> list_Tipodoc() {
        List<Tipodoc> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM codice_tipo_doc";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1); ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    out.add(new Tipodoc(rs.getString(1), rs.getString(2), rs.getString(3)));
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    public List<Distributore> list_Distributore() {
        List<Distributore> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM distributore";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1); ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    out.add(new Distributore(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        return out;
    }

    public List<StatoConsegna> list_StatoConsegna() {
        List<StatoConsegna> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM stato_consegna";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1); ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    out.add(new StatoConsegna(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }

        return out;
    }

    public List<Lavorazione> list_Lavorazione() {
        List<Lavorazione> out = new ArrayList<>();
        try {
            String sql1 = "SELECT * FROM lavorazione ORDER BY descrizione";
            try (PreparedStatement ps1 = this.c.prepareStatement(sql1); ResultSet rs = ps1.executeQuery()) {
                while (rs.next()) {
                    out.add(new Lavorazione(rs.getString(1), rs.getString(2)));
                }
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }

        return out;
    }

    public String getNomeFile(String iddoc) {
        PreparedStatement ps1 = null;
        try {
            String sql1 = "SELECT nomefile FROM pratica WHERE iddocumento = ?";
            ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, iddoc);
            ResultSet rs = ps1.executeQuery();
            while (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps1 != null) {
                try {
                    ps1.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return null;
    }

    public User login(String usr, String pwd) {
        PreparedStatement ps1 = null;
        try {
            String sql1 = "SELECT * FROM utente WHERE username = ? AND password = ?";
            ps1 = this.c.prepareStatement(sql1);
            ps1.setString(1, usr);
            ps1.setString(2, pwd);
            ResultSet rs = ps1.executeQuery();
            if (rs.next()) {
                return new User(rs.getString(1), rs.getString(2), rs.getString(3), rs.getString(4), rs.getString(5),
                        rs.getString(6));
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps1 != null) {
                try {
                    ps1.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return null;
    }

    public boolean updatePratica(String idoper, String pratica, String cir, String cognome, String nome) {
        PreparedStatement ps = null;
        try {
            String update = "UPDATE pratica SET codPrt = ?, codAut = ?, nome = ?, cognome = ? WHERE iddocumento = ?";
            ps = this.c.prepareStatement(update);
            ps.setString(1, pratica);
            ps.setString(2, cir);
            ps.setString(3, nome);
            ps.setString(4, cognome);
            ps.setString(5, idoper);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    public boolean updatePassword(String username, String password) {
        PreparedStatement ps = null;
        try {
            String update = "UPDATE utente SET password = ? WHERE username = ?";
            ps = this.c.prepareStatement(update);
            ps.setString(1, password);
            ps.setString(2, username);
            return ps.executeUpdate() > 0;
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    public void insertTR(String type, String user, String descr) {
        PreparedStatement ps = null;
        try {
            String id = generaId(100);
            String timestamp = getNow();
            ps = this.c.prepareStatement("INSERT INTO tr VALUES (?,?,?,?,?)");
            ps.setString(1, id);
            ps.setString(2, type);
            ps.setString(3, user);
            ps.setString(4, descr);
            ps.setString(5, timestamp);
            ps.execute();
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    log.severe(estraiEccezione(ex1));
                }
            }
        }
    }

    public String getNow() {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT now()";
            ps = this.c.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return new DateTime().toString(PAT_5);
    }

    public User getUserByEmailOrUsername(String email) {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM utente WHERE email = ? OR username = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, email);
            ResultSet rs = ps.executeQuery();
            return rs.next()
                    ? new User(rs.getString("username"), rs.getString("nome"), rs.getString(COGNOME),
                            rs.getString("email"), "ENC PASSWORD", rs.getString("tipo"))
                    : null;
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return null;
    }

    public boolean createUser(String nome, String cognome, String email, String username, String password,
            String tipo) {
        PreparedStatement ps = null;
        try {
            String sql = "INSERT INTO utente SET nome = ?, cognome = ?, email = ?, username = ?, password = ?, tipo = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, nome);
            ps.setString(2, cognome);
            ps.setString(3, email);
            ps.setString(4, username);
            ps.setString(5, password);
            ps.setString(6, tipo);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    /**
     *
     * @param nome
     * @param cognome
     * @param username
     * @param tipo
     * @return
     */
    public ArrayList<User> getUserList(String nome, String cognome, String username, String tipo) {
        ArrayList<User> out = new ArrayList<>();
        ArrayList<String> parm = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM utente ";

            switch (nome) {
                case "":
                    break;
                default:
                    sql += sql.toLowerCase().contains(WHERE) ? "AND nome LIKE ? " : "WHERE nome LIKE ? ";
                    parm.add(nome + "%");
                    break;
            }
            switch (cognome) {
                case "":
                    break;
                default:
                    sql += sql.toLowerCase().contains(WHERE) ? "AND cognome LIKE ? " : "WHERE cognome LIKE ? ";
                    parm.add(cognome + "%");
                    break;
            }
            switch (username) {
                case "":
                    break;
                default:
                    sql += sql.toLowerCase().contains(WHERE) ? "AND username=? " : "WHERE username=? ";
                    parm.add(username);
                    break;
            }
            switch (tipo) {
                case "":
                case "-":
                    break;
                default:
                    sql += sql.toLowerCase().contains(WHERE) ? "AND tipo=? " : "WHERE tipo=? ";
                    parm.add(tipo);
                    break;
            }

            ps = this.c.prepareStatement(sql);
            for (int i = 0; i < parm.size(); i++) {
                ps.setString(i + 1, parm.get(i));
            }
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(new User(rs.getString("username"), rs.getString("nome"), rs.getString(COGNOME),
                        rs.getString("email"), "ENCRYPTED", rs.getString("tipo")));
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return out;
    }

    public ArrayList<Eccessi> getEccessiList(String endorse, String pratica, String cir, String from, String to,
            String ldv, String tvei) {
        ArrayList<Eccessi> out = new ArrayList<>();
        ArrayList<String> parm = new ArrayList<>();
        PreparedStatement ps = null;
        try {
            String sql = "SELECT * FROM eccessi ";
            if (endorse != null && !endorse.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND endorse = ? " : "WHERE endorse = ? ";
                parm.add(endorse);
            }
            if (pratica != null && !pratica.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND pratica = ? " : "WHERE pratica = ? ";
                parm.add(pratica);
            }
            if (cir != null && !cir.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND codice_cir=? " : "WHERE codice_cir=? ";
                parm.add(cir);
            }
            if (ldv != null && !ldv.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND ldv=? " : "WHERE ldv=? ";
                parm.add(ldv);
            }
            if (tvei != null && !tvei.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND tvei=? " : "WHERE tvei=? ";
                parm.add(tvei);
            }
            if (from != null && !from.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND data>=STR_TO_DATE(?, '%d/%m/%Y') "
                        : "WHERE data>=STR_TO_DATE(?, '%d/%m/%Y') ";
                parm.add(from);
            }
            if (to != null && !to.trim().isEmpty()) {
                sql += sql.toLowerCase().contains(WHERE) ? "AND data<=STR_TO_DATE(?, '%d/%m/%Y') "
                        : "WHERE data<=STR_TO_DATE(?, '%d/%m/%Y') ";
                parm.add(to);
            }

            ps = this.c.prepareStatement(sql);
            for (int i = 0; i < parm.size(); i++) {
                ps.setString(i + 1, parm.get(i));
            }

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                out.add(new Eccessi(rs.getString(ENDORSE), rs.getString(PRATICA), rs.getString("codice_cir"),
                        rs.getString("ldv"), rs.getString("tvei"),
                        new Date(rs.getTimestamp("data_accettazione").getTime()),
                        new Date(rs.getDate("data").getTime()), rs.getInt("pubblica")));
            }
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        }
        if (ps != null) {
            try {
                ps.close();
            } catch (SQLException ex1) {
                insertTR("E", SYSTEM, estraiEccezione(ex1));
            }
        }
        return out;
    }

    public boolean deleteUser(String username) {
        PreparedStatement ps = null;
        try {
            String sql = "DELETE FROM utente WHERE username = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, username);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    public boolean pubblicaPratica(String endorse) {
        PreparedStatement ps = null;
        try {
            String sql = "UPDATE eccessi SET pubblica = ? WHERE endorse = ?";
            ps = this.c.prepareStatement(sql);
            ps.setInt(1, 1);
            ps.setString(2, endorse);
            return ps.executeUpdate() > 0;
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    public boolean loginFindo(String usr, String pwd) {
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        try {
            String sql = "select * from findofact.config where codice='userViever' and descrizione='" + usr + "'";
            String sql2 = "select * from findofact.config where codice='passwordViewer' and descrizione='" + pwd + "'";
            ps = this.c.prepareStatement(sql);
            ps2 = this.c.prepareStatement(sql2);
            ResultSet rs = ps.executeQuery();
            ResultSet rs2 = ps2.executeQuery();
            if (rs.next() && rs2.next()) {
                return true;
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
            return false;
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
            if (ps2 != null) {
                try {
                    ps2.close();
                } catch (SQLException ex2) {
                    insertTR("E", SYSTEM, estraiEccezione(ex2));
                }
            }
        }
        return false;
    }

    public boolean ctrlDismissione(String iddoc) {
        PreparedStatement ps = null;
        String query = "select iddocumento from pratica where iddocumento=? and dismissione=1";
        try {
            ps = this.c.prepareStatement(query);
            ps.setString(1, iddoc);
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return false;
    }

    public String getConfig(String id) {
        PreparedStatement ps = null;
        try {
            String sql = "SELECT descrizione FROM config WHERE codice = ?";
            ps = this.c.prepareStatement(sql);
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString(1);
            }
        } catch (SQLException ex) {
            insertTR("E", SYSTEM, estraiEccezione(ex));
        } finally {
            if (ps != null) {
                try {
                    ps.close();
                } catch (SQLException ex1) {
                    insertTR("E", SYSTEM, estraiEccezione(ex1));
                }
            }
        }
        return "-";
    }

}

class Param {

    int type;
    Object value;

    public Param(int type, Object value) {
        this.type = type;
        this.value = value;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }

}
