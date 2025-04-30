
import static rc.so.util.SendMailJet.sendMail;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author Administrator
 */
public class tester {
    public static void main(String[] args) {
        boolean res = sendMail("Findomestic", new String[]{"raffaele.cosco1@gmail.com"},
                        "Nuova utenza creata<br><br>username :" + "s" + "<br>password: "
                        + "s",
                        "Utente findomestic");
    }
}
