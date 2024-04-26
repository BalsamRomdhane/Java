package Models;

import java.time.LocalDate;
import java.util.List;

public class Campagne {
    private LocalDate datedeb;
    private LocalDate datefin;
    private String image;
    private String createur;
    private String nomcampagne;
    private String descri;
    private int user_id;
    private int id;
    private List<Event> events;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public  Campagne(){}
    public Campagne(LocalDate datedeb, LocalDate datefin, String image, String createur, String nomcampagne, String descri, int user_id) {
        this.datedeb = datedeb;
        this.datefin = datefin;
        this.image = image;
        this.createur = createur;
        this.nomcampagne = nomcampagne;
        this.descri = descri;
        this.user_id = user_id;
    }

    public LocalDate getDatedeb() {
        return datedeb;
    }

    public void setDatedeb(LocalDate datedeb) {
        this.datedeb = datedeb;
    }

    public LocalDate getDatefin() {
        return datefin;
    }

    public void setDatefin(LocalDate datefin) {
        this.datefin = datefin;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getCreateur() {
        return createur;
    }

    public void setCreateur(String createur) {
        this.createur = createur;
    }

    public String getNomcampagne() {
        return nomcampagne;
    }

    public void setNomcampagne(String nomcampagne) {
        this.nomcampagne = nomcampagne;
    }

    public String getDescri() {
        return descri;
    }

    public void setDescri(String descri) {
        this.descri = descri;
    }

    public int getUserid() {
        return user_id;
    }

    public void setUserid(int user_id) {
        this.user_id = user_id;
    }

    public List<Event> getEvents() {
        return events;
    }

    public void setEvents(List<Event> events) {
        this.events = events;
    }
    @Override
    public String toString() {
        return "Campagne{" +
                "datedeb=" + datedeb +
                ", datefin=" + datefin +
                ", image='" + image + '\'' +
                ", createur='" + createur + '\'' +
                ", nomcampagne='" + nomcampagne + '\'' +
                ", descri='" + descri + '\'' +
                ", user_id=" + user_id +
                '}';
    }
}
