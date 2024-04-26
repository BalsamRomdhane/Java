package Models;

public class Event {
    private int id;
    private int campagne_id;
    private String lieu;
    private String type;
    private int nbparticipant;
    private String objectif;

    public Event() {
    }

    public Event(int id, int campagne_id, String lieu, String type, int nbparticipant, String objectif) {
        this.id = id;
        this.campagne_id = campagne_id;
        this.lieu = lieu;
        this.type = type;
        this.nbparticipant = nbparticipant;
        this.objectif = objectif;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCampagne_id() {
        return campagne_id;
    }

    public void setCampagne_id(int campagne_id) {
        this.campagne_id = campagne_id;
    }

    public String getLieu() {
        return lieu;
    }

    public void setLieu(String lieu) {
        this.lieu = lieu;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNbparticipant() {
        return nbparticipant;
    }

    public void setNbparticipant(int nbparticipant) {
        this.nbparticipant = nbparticipant;
    }

    public String getObjectif() {
        return objectif;
    }

    public void setObjectif(String objectif) {
        this.objectif = objectif;
    }

    @Override
    public String toString() {
        return "Event{" +
                "id=" + id +
                ", campagne_id=" + campagne_id +
                ", lieu='" + lieu + '\'' +
                ", type='" + type + '\'' +
                ", nbparticipant=" + nbparticipant +
                ", objectif='" + objectif + '\'' +
                '}';
    }
}
