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
public class Tipodoc {

    String id;
    String descrizione;
    String visualizza;

    public Tipodoc(String id, String descrizione, String visualizza) {
        this.id = id;
        this.descrizione = descrizione;
        this.visualizza = visualDatatable(visualizza);
    }

    public String getVisualizza() {
        return visualizza;
    }

    public void setVisualizza(String visualizza) {
        this.visualizza = visualizza;
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
