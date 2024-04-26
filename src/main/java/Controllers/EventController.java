package Controllers;

import Models.Event;
import Services.ServiceEvent;
import Utils.DBConnection;
import javafx.event.ActionEvent;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class EventController {

    private ServiceEvent serviceEvent;
    Event event;
    public EventController() {
        Connection cnx = DBConnection.getInstance().getCnx(); // Get a valid connection
        serviceEvent = new ServiceEvent(); // Initialize serviceEvent correctly
    }
    public void saveEvent(Event event) {
        try {
            serviceEvent.insert(event);
            System.out.println("Événement ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'événement.");
        }
    }

    public List<Event> getAllEvents() {
        return serviceEvent.getAllEvents();
    }

    public void updateEvent(Event event) {
        try {
            serviceEvent.update(event);
            System.out.println("Événement mis à jour avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la mise à jour de l'événement.");
        }
    }

    public void deleteEvent(int id) {
        try {
            serviceEvent.delete(id);
            System.out.println("Événement supprimé avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de la suppression de l'événement.");
        }
    }

    public void saveEvent(ActionEvent actionEvent) {
        try {

            serviceEvent.insert(event);
            System.out.println("Événement ajouté avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'événement.");
        }
    }


}
