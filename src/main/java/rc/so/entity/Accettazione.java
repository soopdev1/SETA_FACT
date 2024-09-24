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
public class Accettazione {

    String ldv;
    String corriere;
    String colli_previsti;
    String colli_cosegnati;
    String utente;
    String distributore;
    String stato_consegna;
    String data;
    String ora;
    String numero_pratiche;
    String icr;
    String provincia;
    String sito;
    String tvei;
    String lavorazione;
    String timestamp;

    public Accettazione() {
    }

    public Accettazione(String ldv, String corriere, String colliprevisti, String collicosegnati,
            String utente, String distributore, String statoconsegna, String data, String ora, String numeropratiche,
            String icr, String provincia, String sito, String tvei, String lavorazione, String timestamp) {
        this.ldv = ldv;
        this.corriere = corriere;
        this.colli_previsti = colliprevisti;
        this.colli_cosegnati = collicosegnati;
        this.utente = utente;
        this.distributore = distributore;
        this.stato_consegna = statoconsegna;
        this.data = data;
        this.ora = ora;
        this.numero_pratiche = numeropratiche;
        this.icr = icr;
        this.provincia = provincia;
        this.sito = sito;
        this.tvei = tvei;
        this.lavorazione = lavorazione;
        this.timestamp = timestamp;
    }

    public String getLdv() {
        return ldv;
    }

    public void setLdv(String ldv) {
        this.ldv = ldv;
    }

    public String getCorriere() {
        return corriere;
    }

    public void setCorriere(String corriere) {
        this.corriere = corriere;
    }

    public String getColli_previsti() {
        return colli_previsti;
    }

    public void setColli_previsti(String colli_previsti) {
        this.colli_previsti = colli_previsti;
    }

    public String getColli_cosegnati() {
        return colli_cosegnati;
    }

    public void setColli_cosegnati(String colli_cosegnati) {
        this.colli_cosegnati = colli_cosegnati;
    }

    public String getUtente() {
        return utente;
    }

    public void setUtente(String utente) {
        this.utente = utente;
    }

    public String getDistributore() {
        return distributore;
    }

    public void setDistributore(String distributore) {
        this.distributore = distributore;
    }

    public String getStato_consegna() {
        return stato_consegna;
    }

    public void setStato_consegna(String stato_consegna) {
        this.stato_consegna = stato_consegna;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getOra() {
        return ora;
    }

    public void setOra(String ora) {
        this.ora = ora;
    }

    public String getNumero_pratiche() {
        return numero_pratiche;
    }

    public void setNumero_pratiche(String numero_pratiche) {
        this.numero_pratiche = numero_pratiche;
    }

    public String getIcr() {
        return icr;
    }

    public void setIcr(String icr) {
        this.icr = icr;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getSito() {
        return sito;
    }

    public void setSito(String sito) {
        this.sito = sito;
    }

    public String getTvei() {
        return tvei;
    }

    public void setTvei(String tvei) {
        this.tvei = tvei;
    }

    public String getLavorazione() {
        return lavorazione;
    }

    public void setLavorazione(String lavorazione) {
        this.lavorazione = lavorazione;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

}
