package Controllers;

import Models.Campagne;
import Services.ServiceCampagne;
import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CampFront {


    private static final String STORAGE_FOLDER = "C:\\Users\\balsa\\IdeaProjects\\demo\\src\\main\\images";

    private File selectedFile = null;

    @FXML
    private TextField nomCampagneTextField;

    @FXML
    private DatePicker dateDebutPicker;

    @FXML
    private DatePicker dateFinPicker;

    @FXML
    private TextField createurTextField;

    @FXML
    private TextArea descriptionTextArea;
    private ObservableList<Campagne> campagnesList = FXCollections.observableArrayList();

    public void selectImage(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg"));
        selectedFile = fileChooser.showSaveDialog(null); // Stocker le fichier sélectionné dans la variable

        if (selectedFile != null) {
            try {
                File storageFolder = new File(STORAGE_FOLDER);
                if (!storageFolder.exists()) {
                    storageFolder.mkdirs(); // Crée tous les répertoires nécessaires
                }

                Files.copy(selectedFile.toPath(), Paths.get(STORAGE_FOLDER + File.separator + selectedFile.getName()), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
                afficherAlerte("Erreur lors de la copie de l'image.");
            }
        }
    }
    public void ajouterCampagne() {
        String nom = nomCampagneTextField.getText();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        String createur = createurTextField.getText();
        String description = descriptionTextArea.getText();

        // Validation des champs obligatoires
        if (nom.isEmpty() || dateDebut == null || dateFin == null || createur.isEmpty() || description.isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return;
        }

        // Validation de la date de fin
        if (dateFin.isBefore(dateDebut)) {
            afficherAlerte("La date de fin doit être postérieure à la date de début.");
            return;
        }

        // Validation de la date de début
        LocalDate dateActuelle = LocalDate.now();
        if (dateDebut.isBefore(dateActuelle)) {
            afficherAlerte("La date de début doit être postérieure ou égale à la date d'aujourd'hui.");
            return;
        }

        // Validation de l'image
        if (selectedFile == null) {
            afficherAlerte("Veuillez sélectionner une image.");
            return;
        }

        // Chemin de l'image dans la base de données
        String cheminImage = selectedFile.getName();

        Campagne campagne = new Campagne(dateDebut, dateFin, cheminImage, createur, nom, description, 1);

        try {
            ServiceCampagne serviceCampagne = new ServiceCampagne();
            serviceCampagne.insert(campagne);
            afficherAlerte("Campagne ajoutée avec succès.");
            // Rafraîchir la table des campagnes
            campagnesList.add(campagne);
            clearFields();
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de l'ajout de la campagne.");
        }
    }
    private void clearFields() {
        nomCampagneTextField.clear();
        createurTextField.clear();
        descriptionTextArea.clear();
    }

    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
