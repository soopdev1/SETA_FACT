/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.util;

import com.mailjet.client.ClientOptions;
import static com.mailjet.client.ClientOptions.builder;
import com.mailjet.client.MailjetClient;
import com.mailjet.client.MailjetRequest;
import com.mailjet.client.MailjetResponse;
import static com.mailjet.client.resource.Emailv31.MESSAGES;
import static com.mailjet.client.resource.Emailv31.Message.ATTACHMENTS;
import static com.mailjet.client.resource.Emailv31.Message.BCC;
import static com.mailjet.client.resource.Emailv31.Message.CC;
import static com.mailjet.client.resource.Emailv31.Message.FROM;
import static com.mailjet.client.resource.Emailv31.Message.HTMLPART;
import static com.mailjet.client.resource.Emailv31.Message.SUBJECT;
import static com.mailjet.client.resource.Emailv31.Message.TO;
import static com.mailjet.client.resource.Emailv31.resource;
import static rc.so.engine.Action.log;
import static rc.so.util.Utility.EMAIL;
import static rc.so.util.Utility.estraiEccezione;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import static java.lang.String.valueOf;
import static java.nio.file.Files.probeContentType;
import static java.util.concurrent.TimeUnit.SECONDS;
import static java.util.logging.Level.INFO;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import static okhttp3.logging.HttpLoggingInterceptor.Level.BASIC;
import static org.apache.commons.codec.binary.Base64.encodeBase64;
import static org.apache.commons.io.IOUtils.toByteArray;
import org.json.JSONArray;
import org.json.JSONObject;
import static rc.so.util.Utility.conf;

/**
 *
 * @author rcosco
 */
public class SendMailJet {

    public static void sendMail(String name, String[] to, String[] bcc, String txt, String subject) {
        sendMail(name, to, new String[]{}, bcc, txt, subject, null);
    }

    public static void sendMail(String name, String[] to, String txt, String subject) {
        sendMail(name, to, new String[]{}, new String[]{}, txt, subject, null);
    }

    public static boolean sendMail(String name, String[] to, String[] cc, String[] bcc, String txt, String subject, File file) {
        MailjetClient client;
        MailjetRequest request;
        MailjetResponse response;

        String filename = "";
        String content_type = "";
        String b64 = "";

        String mailjet_api = conf.getString("mailjet_api");
        String mailjet_secret = conf.getString("mailjet_secret");
        String mailjet_name = conf.getString("mailjet_name");

        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(BASIC);
        OkHttpClient customHttpClient = new OkHttpClient.Builder()
                .connectTimeout(60, SECONDS)
                .readTimeout(60, SECONDS)
                .writeTimeout(60, SECONDS)
                .addInterceptor(logging)
                .build();

        ClientOptions options = builder()
                .apiKey(mailjet_api)
                .apiSecretKey(mailjet_secret)
                .okHttpClient(customHttpClient)
                .build();

        client = new MailjetClient(options);

        JSONArray dest = new JSONArray();
        JSONArray ccn = new JSONArray();
        JSONArray ccj = new JSONArray();

        try {
            for (String s : to) {
                dest.put(new JSONObject().put(EMAIL, s)
                        .put("Name", ""));
            }
        } catch (Exception e) {
            dest.put(new JSONObject().put(EMAIL, "")
                    .put("Name", ""));
        }

        try {
            for (String s : cc) {
                ccj.put(new JSONObject().put(EMAIL, s)
                        .put("Name", ""));
            }
        } catch (Exception e) {
            ccj.put(new JSONObject().put(EMAIL, "")
                    .put("Name", ""));
        }

        try {
            for (String s : bcc) {
                ccn.put(new JSONObject().put(EMAIL, s)
                        .put("Name", ""));
            }
        } catch (Exception e) {
            ccn.put(new JSONObject().put(EMAIL, "")
                    .put("Name", ""));
        }

        JSONObject mail = new JSONObject().put(FROM, new JSONObject()
                .put(EMAIL, mailjet_name)
                .put("Name", name))
                .put(TO, dest)
                .put(CC, ccj)
                .put(BCC, ccn)
                .put(SUBJECT, subject)
                .put(HTMLPART, txt);

        if (file != null) {
            try {
                filename = file.getName();
                content_type = probeContentType(file.toPath());
                try (InputStream i = new FileInputStream(file)) {
                    b64 = valueOf(encodeBase64(toByteArray(i)));
                }
            } catch (Exception ex) {
                log.severe(estraiEccezione(ex));
            }
            mail.put(ATTACHMENTS, new JSONArray()
                    .put(new JSONObject()
                            .put("ContentType", content_type)
                            .put("Filename", filename)
                            .put("Base64Content", b64)));
        }

        request = new MailjetRequest(resource)
                .property(MESSAGES, new JSONArray()
                        .put(mail));

        try {
            response = client.post(request);
            log.log(INFO, "MAIL TO {0} : {1} -- {2}", new Object[]{dest.toList(), response.getStatus(), response.getData()});
            return response.getStatus() == 200;
        } catch (Exception ex) {
            log.severe(estraiEccezione(ex));
        }
        return false;
    }

}
