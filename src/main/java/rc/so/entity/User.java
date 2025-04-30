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
public class User {

    String username;
    String nome;
    String cognome;
    String email;
    String password;
    String tipo;
    String descrizionetipo;

    public User(String username, String nome, String cognome, String email, String password, String tipo) {
        this.username = username;
        this.nome = nome;
        this.cognome = cognome;
        this.email = email;
        this.password = password;
        this.tipo = tipo;
        switch (tipo) {
            case "1":
                this.descrizionetipo = "SETA";
                break;
            case "2":
                this.descrizionetipo = "FINDOMESTIC";
                break;
            default:
                this.descrizionetipo = "UTENTE SCONOSCIUTO";
                break;
        }
    }

    public String getDescrizionetipo() {
        return descrizionetipo;
    }

    public void setDescrizionetipo(String descrizionetipo) {
        this.descrizionetipo = descrizionetipo;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCognome() {
        return cognome;
    }

    public void setCognome(String cognome) {
        this.cognome = cognome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    @Override
    public String toString() {
        return "User{" + "username=" + username + ", nome=" + nome + ", cognome=" + cognome + ", email=" + email + ", tipo=" + tipo + ", descrizionetipo=" + descrizionetipo + '}';
    }

}