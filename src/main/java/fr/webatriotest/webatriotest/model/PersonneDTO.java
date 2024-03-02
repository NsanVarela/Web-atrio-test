package fr.webatriotest.webatriotest.model;

import java.time.LocalDate;
import java.util.List;

public class PersonneDTO {
    private Long id;
    private String nom;
    private String prenom;
    private LocalDate dateNaissance;
    private int age;
    private List<EmploiDTO> emplois;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDate getDateNaissance() {
        return dateNaissance;
    }

    public void setDateNaissance(LocalDate dateNaissance) {
        this.dateNaissance = dateNaissance;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public List<EmploiDTO> getEmplois() {
        return emplois;
    }

    public void setEmplois(List<EmploiDTO> emplois) {
        this.emplois = emplois;
    }
}
