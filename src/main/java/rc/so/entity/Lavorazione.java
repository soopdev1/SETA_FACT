/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rc.so.entity;

import static rc.so.engine.Action.visualDatatable;

/**
 *
 * @author rcosco
 */
public class Lavorazione {

    String id;
    String descrizione;

    String datarif;
    String txt;
    String csv;

    public Lavorazione(String id, String descrizione) {
        this.id = id;
        this.descrizione = visualDatatable(descrizione);
    }

    public Lavorazione(String id, String datarif, String txt, String csv) {
        this.id = id;
        this.datarif = datarif;
        this.txt = txt;
        this.csv = csv;
    }

    public String getDatarif() {
        return datarif;
    }

    public void setDatarif(String datarif) {
        this.datarif = datarif;
    }

    public String getTxt() {
        return txt;
    }

    public void setTxt(String txt) {
        this.txt = txt;
    }

    public String getCsv() {
        return csv;
    }

    public void setCsv(String csv) {
        this.csv = csv;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescrizione() {
        return descrizione;
    }

    public void setDescrizione(String descrizione) {
        this.descrizione = descrizione;
    }
}
