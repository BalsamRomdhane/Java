package Services;

import Models.Campagne;
import Models.Event;
import Utils.DBConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ServiceCampagne {

    private Connection cnx;

    public ServiceCampagne( ) {
        cnx = DBConnection.getInstance().getCnx();
    }

    public void insert(Campagne campagne) throws SQLException {
        String req = "INSERT INTO `campagne`(`datedeb`, `datefin`, `image`, `createur`, `nomcampagne`, `descri`, `user_id`) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pst = this.cnx.prepareStatement(req)) {
            pst.setDate(1, java.sql.Date.valueOf(campagne.getDatedeb()));
            pst.setDate(2, java.sql.Date.valueOf(campagne.getDatefin()));
            pst.setString(3, campagne.getImage());
            pst.setString(4, campagne.getCreateur());
            pst.setString(5, campagne.getNomcampagne());
            pst.setString(6, campagne.getDescri());
            pst.setInt(7, campagne.getUserid());
            pst.executeUpdate();
            System.out.println("Campagne ajoutée avec succès !");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de la campagne.");
        }
    }

    public boolean update(Campagne campagne) throws SQLException {
        String req = "UPDATE campagne SET datedeb = ?, datefin = ?, image = ?, createur = ?, nomcampagne = ?, descri = ? WHERE id = ?";
        PreparedStatement st = this.cnx.prepareStatement(req);
        st.setDate(1, java.sql.Date.valueOf(campagne.getDatedeb()));
        st.setDate(2, java.sql.Date.valueOf(campagne.getDatefin()));
        st.setString(3, campagne.getImage());
        st.setString(4, campagne.getCreateur());
        st.setString(5, campagne.getNomcampagne());
        st.setString(6, campagne.getDescri());
        st.setInt(7, campagne.getId());
        st.executeUpdate();
        System.out.println("Campagne Updated !");
        return false;
    }

    public void DeleteCampagne(Campagne campagne) {
        String req = "DELETE FROM campagne WHERE id = ?";
        try (PreparedStatement st = this.cnx.prepareStatement(req)) {
            st.setInt(1, campagne.getId());
            int rowsAffected = st.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Campagne Deleted !");
            } else {
                System.out.println("No campagne found with id: " + campagne.getId());
            }
        } catch (SQLException e) {
            System.err.println("Error deleting campagne: " + e.getMessage());
            e.printStackTrace();
        }
    }


    public List<Campagne> getAllCampagnes() throws SQLException {
        List<Campagne> campagnes = new ArrayList<>();
        String query = "SELECT id, createur, datedeb, datefin FROM campagne";
        try (Statement stmt = cnx.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Campagne campagne = new Campagne();
                campagne.setId(rs.getInt("id"));
                campagne.setCreateur(rs.getString("createur"));
                campagne.setDatedeb(LocalDate.parse(rs.getString("datedeb")));
                campagne.setDatefin(LocalDate.parse(rs.getString("datefin")));
                campagnes.add(campagne);
            }
        }
        return campagnes;
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


}
