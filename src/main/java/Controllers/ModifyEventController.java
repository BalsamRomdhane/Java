package Controllers;

import Models.Event;
import Services.ServiceEvent;
import Utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.sql.Connection;
import java.sql.SQLException;

public class ModifyEventController {

    private Event Event;

    @FXML
    private ComboBox<String> comboBoxType;

    @FXML
    private TextField txtCampagneId;

    @FXML
    private TextField txtLieu;

    @FXML
    private TextField txtNbParticipants;

    @FXML
    private TextField txtObjectif;

    // Méthode pour initialiser les données de l'événement à modifier
    public void setData(Event event) {
        this.Event = event;
        // Afficher les données de l'événement dans les champs de l'IHM
        txtCampagneId.setText(String.valueOf(event.getCampagne_id()));
        txtLieu.setText(event.getLieu());
        txtNbParticipants.setText(String.valueOf(event.getNbparticipant()));
        txtObjectif.setText(event.getObjectif());
        comboBoxType.setValue(event.getType());
    }

    @FXML
    void modify(ActionEvent event) {
        // Récupérer les nouvelles valeurs des champs dans l'IHM
        int campagneId;
        String lieu = txtLieu.getText();
        int nbParticipants;
        String objectif = txtObjectif.getText();
        String type = comboBoxType.getValue();

        // Valider le champ Campagne ID
        try {
            campagneId = Integer.parseInt(txtCampagneId.getText());
        } catch (NumberFormatException e) {
            afficherAlerte("Veuillez saisir un identifiant de campagne valide.");
            return;
        }

        // Valider le champ Nombre de participants
        try {
            nbParticipants = Integer.parseInt(txtNbParticipants.getText());
            if (nbParticipants <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Veuillez saisir un nombre valide pour le nombre de participants.");
            return;
        }

        // Mettre à jour l'objet Event avec les nouvelles valeurs
        Event.setCampagne_id(campagneId);
        Event.setLieu(lieu);
        Event.setNbparticipant(nbParticipants);
        Event.setObjectif(objectif);
        Event.setType(type);

        // Appeler le service pour mettre à jour l'événement dans la base de données
        try {
            Connection cnx = DBConnection.getInstance().getCnx();
            ServiceEvent serviceEvent = new ServiceEvent();
            serviceEvent.update(Event);
            afficherAlerte("Événement modifié avec succès.");
            // Fermer la fenêtre de modification de l'événement
            fermerFenetre();
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la modification de l'événement.");
            e.printStackTrace();
        }
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) txtCampagneId.getScene().getWindow();
        stage.close();
    }
}
