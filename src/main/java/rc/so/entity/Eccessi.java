/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import java.util.Date;

/**
 *
 * @author rcosco
 */
public class Eccessi {

    String endorse;
    String pratica;
    String codice_cir;
    String ldv;
    String tvei;
    Date data_accettazione;
    Date data;
    int pubblica;

    public Eccessi(String endorse, String pratica, String codice_cir, String ldv, String tvei, Date data_accettazione, Date data) {
        this.endorse = endorse;
        this.pratica = pratica;
        this.codice_cir = codice_cir;
        this.ldv = ldv;
        this.tvei = tvei;
        this.data_accettazione = data_accettazione;
        this.data = data;
    }

    public Eccessi(String endorse, String pratica, String codice_cir, String ldv,
            String tvei, Date data_accettazione, Date data, int pubblica) {
        this.endorse = endorse;
        this.pratica = pratica;
        this.codice_cir = codice_cir;
        this.ldv = ldv;
        this.tvei = tvei;
        this.data_accettazione = data_accettazione;
        this.data = data;
        this.pubblica = pubblica;
    }

    public String getEndorse() {
        return endorse;
    }

    public void setEndorse(String endorse) {
        this.endorse = endorse;
    }

    public String getPratica() {
        return pratica;
    }

    public void setPratica(String pratica) {
        this.pratica = pratica;
    }

    public String getCodice_cir() {
        return codice_cir;
    }

    public void setCodice_cir(String codice_cir) {
        this.codice_cir = codice_cir;
    }

    public String getLdv() {
        return ldv;
    }

    public void setLdv(String ldv) {
        this.ldv = ldv;
    }

    public String getTvei() {
        return tvei;
    }

    public void setTvei(String tvei) {
        this.tvei = tvei;
    }

    public Date getData_accettazione() {
        return data_accettazione;
    }

    public void setData_accettazione(Date data_accettazione) {
        this.data_accettazione = data_accettazione;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public int getPubblica() {
        return pubblica;
    }

    public void setPubblica(int pubblica) {
        this.pubblica = pubblica;
    }

}
