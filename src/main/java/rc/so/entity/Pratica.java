/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

/**
 *
 * @author rcosco
 */
public class Pratica {

    String tipolavorazione;
    String tipodocumento;
    String numeropratica;
    String cir;
    String tvei;
    String datalavorazione;
    String cliente;
    String ldv;
    String lotto;
    String statows;
    String numeroinvii;
    String iddocumento;
    String endorse;
    String corriere;
    String pagine;
    String utente;
    String barcode;
    String esitoco;
    String cognome;
    String nome;

    boolean statuserror;

    public boolean isStatuserror() {
        return statuserror;
    }

    public void setStatuserror(boolean statuserror) {
        this.statuserror = statuserror;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getNumeroinvii() {
        return numeroinvii;
    }

    public void setNumeroinvii(String numeroinvii) {
        this.numeroinvii = numeroinvii;
    }

    public String getEndorse() {
        return endorse;
    }

    public void setEndorse(String endorse) {
        this.endorse = endorse;
    }

    public String getCorriere() {
        return corriere;
    }

    public void setCorriere(String corriere) {
        this.corriere = corriere;
    }

    public String getPagine() {
        return pagine;
    }

    public void setPagine(String pagine) {
        this.pagine = pagine;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public String getEsitoco() {
        return esitoco;
    }

    public void setEsitoco(String esitoco) {
        this.esitoco = esitoco;
    }

    public String getIddocumento() {
        return iddocumento;
    }

    public void setIddocumento(String iddocumento) {
        this.iddocumento = iddocumento;
    }

    public String getTipolavorazione() {
        return tipolavorazione;
    }

    public void setTipolavorazione(String tipolavorazione) {
        this.tipolavorazione = tipolavorazione;
    }

    public String getTipodocumento() {
        return tipodocumento;
    }

    public void setTipodocumento(String tipodocumento) {
        this.tipodocumento = tipodocumento;
    }

    public String getNumeropratica() {
        return numeropratica;
    }

    public void setNumeropratica(String numeropratica) {
        this.numeropratica = numeropratica;
    }

    public String getCir() {
        return cir;
    }

    public void setCir(String cir) {
        this.cir = cir;
    }

    public String getTvei() {
        return tvei;
    }

    public void setTvei(String tvei) {
        this.tvei = tvei;
    }

    public String getDatalavorazione() {
        return datalavorazione;
    }

    public void setDatalavorazione(String datalavorazione) {
        this.datalavorazione = datalavorazione;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public String getLdv() {
        return ldv;
    }

    public void setLdv(String ldv) {
        this.ldv = ldv;
    }

    public String getLotto() {
        return lotto;
    }

    public void setLotto(String lotto) {
        this.lotto = lotto;
    }

    public String getStatows() {
        return statows;
    }

    public void setStatows(String statows) {
        this.statows = formatStatoWS(statows);
    }

    public String formatStatoWS(String statows) {
        if (statows.equals("-")) {
            return "DA INVIARE";
        }
        return statows;
    }

}
