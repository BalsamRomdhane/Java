package Services;

import Models.Event;
import Utils.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ServiceEvent {

    private Connection cnx;

    public ServiceEvent() {
        cnx = DBConnection.getInstance().getCnx();
    }


    public void insert(Event event) throws SQLException {
        String query = "INSERT INTO event (campagne_id, lieu, type, nbparticipant, objectif) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = cnx.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            statement.setInt(1, event.getCampagne_id());
            statement.setString(2, event.getLieu());
            statement.setString(3, event.getType());
            statement.setInt(4, event.getNbparticipant());
            statement.setString(5, event.getObjectif());

            int rowsInserted = statement.executeUpdate();
            if (rowsInserted > 0) {
                try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
                    if (generatedKeys.next()) {
                        event.setId(generatedKeys.getInt(1));
                    } else {
                        throw new SQLException("Creating event failed, no ID obtained.");
                    }
                }
            }
        }
    }

    private final String url = "jdbc:mysql://localhost:3306/landforlife";
    private final String user = "root";
    private final String password = "password";

    public List<Event> getAllEvents() {
        List<Event> events = new ArrayList<>();
        String query = "SELECT * FROM event";

        try (PreparedStatement statement = cnx.prepareStatement(query);
             ResultSet resultSet = statement.executeQuery()) {

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int campagne_id = resultSet.getInt("campagne_id");
                String lieu = resultSet.getString("lieu");
                String type = resultSet.getString("type");
                int nbparticipant = resultSet.getInt("nbparticipant");
                String objectif = resultSet.getString("objectif");

                Event event = new Event(id, campagne_id, lieu, type, nbparticipant, objectif);
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }
    public void update(Event event) throws SQLException {
        String query = "UPDATE event SET campagne_id = ?, lieu = ?, type = ?, nbparticipant = ?, objectif = ? WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, event.getCampagne_id());
            statement.setString(2, event.getLieu());
            statement.setString(3, event.getType());
            statement.setInt(4, event.getNbparticipant());
            statement.setString(5, event.getObjectif());
            statement.setInt(6, event.getId());

            int rowsUpdated = statement.executeUpdate();
            if (rowsUpdated == 0) {
                throw new SQLException("Updating event failed, no rows affected.");
            }
        }
    }
    public List<Event> getEventsForCampagne(int campagneId) {
        List<Event> eventsForCampagne = new ArrayList<>();
        String query = "SELECT * FROM event WHERE campagne_id = ?";

        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, campagneId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                int campagne_id = resultSet.getInt("campagne_id");
                String lieu = resultSet.getString("lieu");
                String type = resultSet.getString("type");
                int nbparticipant = resultSet.getInt("nbparticipant");
                String objectif = resultSet.getString("objectif");

                Event event = new Event(id, campagne_id, lieu, type, nbparticipant, objectif);
                eventsForCampagne.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erreur lors de la récupération des événements pour la campagne : " + e.getMessage());
        }

        return eventsForCampagne;
    }


    public void delete(int eventId) throws SQLException {
        String query = "DELETE FROM event WHERE id = ?";
        try (PreparedStatement statement = cnx.prepareStatement(query)) {
            statement.setInt(1, eventId);
            int rowsDeleted = statement.executeUpdate();
            if (rowsDeleted == 0) {
                throw new SQLException("Deleting event failed, no rows affected.");
            }
        }
    }
}
