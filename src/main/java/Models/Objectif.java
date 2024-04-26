package Models;

public class Objectif {
    private int id;
    private String descobj;
    private String resultatobj;
    private String imageobj;

    @Override
    public String toString() {
        return "Objectif{" +
                "id=" + id +
                ", descobj='" + descobj + '\'' +
                ", resultatobj='" + resultatobj + '\'' +
                ", imageobj='" + imageobj + '\'' +
                '}';
    }

    // Constructeur par défaut
    public Objectif() {
    }

    // Constructeur avec tous les attributs
    public Objectif(int id, String descobj, String resultatobj, String imageobj) {
        this.id = id;
        this.descobj = descobj;
        this.resultatobj = resultatobj;
        this.imageobj = imageobj;
    }

    // Constructeur sans l'ID (peut être utile si l'ID est généré automatiquement dans la base de données)
    public Objectif(String descobj, String resultatobj, String imageobj) {
        this.descobj = descobj;
        this.resultatobj = resultatobj;
        this.imageobj = imageobj;
    }

    // Getters et Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescobj() {
        return descobj;
    }

    public void setDescobj(String descobj) {
        this.descobj = descobj;
    }

    public String getResultatobj() {
        return resultatobj;
    }

    public void setResultatobj(String resultatobj) {
        this.resultatobj = resultatobj;
    }

    public String getImageobj() {
        return imageobj;
    }

    public void setImageobj(String imageobj) {
        this.imageobj = imageobj;
    }
}
