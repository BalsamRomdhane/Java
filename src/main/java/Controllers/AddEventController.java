package Controllers;

import Models.Event;
import Services.ServiceEvent;
import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.sql.Connection;
import java.sql.SQLException;

public class AddEventController {
    @FXML
    private ComboBox<String> typeComboBox;

    @FXML
    private TextField lieuTextField;
    private Event selectedEvent;

    @FXML
    private TextField typeTextField;

    @FXML
    private TextField nbParticipantsTextField;

    @FXML
    private TextField objectifTextField;
    public void initialize() {
        // Initialiser les options de la liste déroulante
        ObservableList<String> options = FXCollections.observableArrayList(
                "Don",
                "Event"
        );
        typeComboBox.setItems(options);
    }
    public void setData(Event event) {
        selectedEvent = event;
        if (selectedEvent != null) {
            lieuTextField.setText(selectedEvent.getLieu());
            typeComboBox.setValue(selectedEvent.getType());
            nbParticipantsTextField.setText(String.valueOf(selectedEvent.getNbparticipant()));
            objectifTextField.setText(selectedEvent.getObjectif());
        }
    }

    public void ajouterEvent(ActionEvent actionEvent) {
        // Récupérer les valeurs saisies dans les champs
        String lieu = lieuTextField.getText();
        String type = typeComboBox.getValue();
        String nbParticipantsText = nbParticipantsTextField.getText();
        String objectif = objectifTextField.getText();

        // Valider les champs obligatoires
        if (lieu.isEmpty() || type == null || nbParticipantsText.isEmpty() || objectif.isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs obligatoires.");
            return;
        }

        // Valider le nombre de participants
        int nbParticipants;
        try {
            nbParticipants = Integer.parseInt(nbParticipantsText);
            if (nbParticipants <= 0) {
                throw new NumberFormatException();
            }
        } catch (NumberFormatException e) {
            afficherAlerte("Veuillez saisir un nombre valide pour le nombre de participants.");
            return;
        }

        // Ajouter votre logique d'ajout d'événement ici...
        Connection cnx = DBConnection.getInstance().getCnx();
        Event event = new Event(0, 1, lieu, type, nbParticipants, objectif); // Ici 1 représente l'ID de la campagne, à adapter selon votre logique

        try {
            ServiceEvent serviceEvent = new ServiceEvent();
            serviceEvent.insert(event);
            System.out.println("Event ajouté avec succès.");
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Erreur lors de l'ajout de l'événement.");
        }
    }
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

}
