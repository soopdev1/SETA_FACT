/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

import rc.so.db.FindoDB;
import rc.so.engine.Action;
import java.io.File;
import java.util.Random;
import java.util.ResourceBundle;
import org.apache.commons.io.FileUtils;
import static org.apache.commons.lang3.exception.ExceptionUtils.getStackTrace;
import static rc.so.engine.Action.log;

/**
 *
 * @author rcosco
 */
public class Utility {

    public static final ResourceBundle conf = ResourceBundle.getBundle("conf.conf");

    public static String getRandomString(int length) {
        String capitalCaseLetters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String lowerCaseLetters = "abcdefghijklmnopqrstuvwxyz";
        String specialCharacters = "!@#$";
        String numbers = "1234567890";
        String combinedChars = capitalCaseLetters + lowerCaseLetters + specialCharacters + numbers;
        Random random = new Random();
        char[] password = new char[length];
        password[0] = lowerCaseLetters.charAt(random.nextInt(lowerCaseLetters.length()));
        password[1] = capitalCaseLetters.charAt(random.nextInt(capitalCaseLetters.length()));
        password[2] = specialCharacters.charAt(random.nextInt(specialCharacters.length()));
        password[3] = numbers.charAt(random.nextInt(numbers.length()));
        for (int i = 4; i < length; i++) {
            password[i] = combinedChars.charAt(random.nextInt(combinedChars.length()));
        }
        return new String(password);
    }

//    public static String getRandomString(int length) {
//        boolean useLetters = true;
//        boolean useNumbers = false;
//        return random(length, useLetters, useNumbers);
//    }

    public static String estraiEccezione(Exception ec1) {
        try {
            return ec1.getStackTrace()[0].getMethodName() + " - " + getStackTrace(ec1);
        } catch (Exception e) {            
        }
        return ec1.getMessage();

    }

    public static String getBase64(String ido) {
        try {
            FindoDB db = new FindoDB(Action.log);
            String path = db.getNomeFile(ido);
            db.closeDB();
            File f1 = new File(path);
            if (f1.exists()) {
                return "data:application/pdf;base64," + org.apache.commons.codec.binary.Base64.encodeBase64String(FileUtils.readFileToByteArray(f1));
            }
        } catch (Exception e) {
            log.severe(estraiEccezione(e));
        }
        return null;
    }
    
    public static final String PASSWORD = "password";
    public static final String UTF8 = "UTF-8";
    public static final String TRUE = "true";
    public static final String FALSE = "false";
    public static final String SYSTEM = "SYSTEM";
    public static final String COGNOME = "cognome";
    public static final String DESCSTATOWS = "desc_stato_ws";
    public static final String ENDORSE = "endorse";
    public static final String CORRIERE = "corriere";
    public static final String PRATICA = "pratica";
    public static final String STATOWS = "statows";
    public static final String LAVOR = "lavor";
    public static final String WHERE = "where";
    public static final String USPWD = "us_pwd";
    public static final String RESULT = "result";
    public static final String MESSAGE = "message";
    public static final String USERNAME = "username";
    public static final String APPJSON = "application/json";
    public static final String ITOTALRECORDS = "iTotalRecords";
    public static final String ITOTALDISPLAY = "iTotalDisplayRecords";
    public static final String SECHO = "sEcho";
    public static final String SCOLUMS = "sColumns";
    public static final String AADATA = "aaData";
    public static final String RECORDID = "RecordID";
    public static final String CONTENTTYPE = "Content-Type";
    public static final String EMAIL = "Email";
    public static final String EMAILPERSONAL = "Service Findomestic documentaleseta";
}
