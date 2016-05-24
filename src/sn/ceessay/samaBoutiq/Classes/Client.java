
/*
* To change this license header, choose License Headers in Project Properties.
* To change this template file, choose Tools | Templates
* and open the template in the editor.
 */
package sn.ceessay.samaBoutiq.Classes;

//~--- JDK imports ------------------------------------------------------------

import java.util.ArrayList;

/**
 *
 * @author ceessay
 */
public class Client {
    private Integer idClient;
    private String  nom;
    private String  prenom;
    private Integer age;
    private String  telephone;
    private String  email;
    private String  sexe;
    private String  ville;
    private String  region;
    private String  adresse;
    private Integer idCompte;

    public Client() {
        idClient = null;
        nom      = "client";
        prenom   = "Cher";
        idCompte = null;
    }

    public Client(Integer idClinet, String nom, String prenom) {
        this.idClient = idClinet;
        this.nom      = nom;
        this.prenom   = prenom;
    }

    public Client(Integer idClinet, String nom, String prenom, Integer idCompte) {
        this.idClient = idClinet;
        this.nom      = nom;
        this.prenom   = prenom;
        this.idCompte = idCompte;
    }

    public Client(Integer idClient, String nom, String prenom, Integer age, String telephone, String email,
                  String sexe, String ville, String region, String adresse, Integer idCompte) {
        this.idClient  = idClient;
        this.nom       = nom;
        this.prenom    = prenom;
        this.age       = age;
        this.telephone = telephone;
        this.email     = email;
        this.sexe      = sexe;
        this.ville     = ville;
        this.region    = region;
        this.adresse   = adresse;
        this.idCompte  = idCompte;
    }

    public Integer getIdClient() {
        return idClient;
    }

    public void setIdClinet(Integer idClinet) {
        this.idClient = idClinet;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPrenom() {
        return prenom;
    }

    public void setPrenom(String prenom) {
        this.prenom = prenom;
    }

    public Integer getIdCompte() {
        return idCompte;
    }

    public void setIdCompte(Integer idCompte) {
        this.idCompte = idCompte;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSexe() {
        return sexe;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public void afficherClient() {
        System.out.println(this.prenom + ", " + this.prenom + ", " + this.idCompte + ", " + this.age + ", "
                           + this.email + ", " + this.telephone + ", " + this.sexe + ", " + this.adresse);
    }
}


//~ Formatted by Jindent --- http://www.jindent.com
