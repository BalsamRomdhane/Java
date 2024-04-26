package Controllers;

import Models.Campagne;
import Services.ServiceCampagne;
import Utils.DBConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;

public class ModifyCampagneController {

    @FXML
    private ImageView imageView;
    private static final String STORAGE_FOLDER = "C:\\Users\\balsa\\IdeaProjects\\demo\\src\\main\\images";

    private File selectedFile; // Déclarer la variable pour stocker le fichier sélectionné

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

    private Campagne campagne;

    public void initData(Campagne campagne) {
        this.campagne = campagne;

        nomCampagneTextField.setText(campagne.getNomcampagne());
        dateDebutPicker.setValue(campagne.getDatedeb());
        dateFinPicker.setValue(campagne.getDatefin());
        createurTextField.setText(campagne.getCreateur());
        descriptionTextArea.setText(campagne.getDescri());
    }

    @FXML
    public void modifierCampagne() {
        // Récupération des données des champs
        String nom = nomCampagneTextField.getText();
        LocalDate dateDebut = dateDebutPicker.getValue();
        LocalDate dateFin = dateFinPicker.getValue();
        String createur = createurTextField.getText();
        String description = descriptionTextArea.getText();

        // Vérification des champs obligatoires
        if (nom.isEmpty() || dateDebut == null || dateFin == null || createur.isEmpty() || description.isEmpty()) {
            afficherAlerte("Veuillez remplir tous les champs.");
            return;
        }

        // Vérification des dates
        if (dateFin.isBefore(dateDebut)) {
            afficherAlerte("La date de fin doit être postérieure à la date de début.");
            return;
        }

        if (dateDebut.isBefore(LocalDate.now())) {
            afficherAlerte("La date de début doit être postérieure ou égale à la date d'aujourd'hui.");
            return;
        }
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Images", "*.jpg", "*.png", "*.jpeg"));
        selectedFile = fileChooser.showSaveDialog(null); // Stocker le fichier sélectionné dans la variable


        // Récupération du chemin d'accès de l'image
        String imagePath = selectedFile.getName();

        // Mise à jour de l'objet Campagne avec les nouvelles informations
        campagne.setNomcampagne(nom);
        campagne.setDatedeb(dateDebut);
        campagne.setDatefin(dateFin);
        campagne.setCreateur(createur);
        campagne.setDescri(description);
        campagne.setImage(imagePath); // Enregistrement du chemin d'accès de l'image

        try {
            // Mise à jour de la campagne dans la base de données
            Connection cnx = DBConnection.getInstance().getCnx();
            ServiceCampagne serviceCampagne = new ServiceCampagne();
            serviceCampagne.update(campagne);
            afficherAlerte("Campagne modifiée avec succès.");
            fermerFenetre();
        } catch (SQLException e) {
            afficherAlerte("Erreur lors de la modification de la campagne.");
            e.printStackTrace();
        }
    }

    @FXML
    public void annuler() {
        fermerFenetre();
    }


    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void fermerFenetre() {
        Stage stage = (Stage) nomCampagneTextField.getScene().getWindow();
        stage.close();
    }

    // Méthode appelée lors du clic sur le bouton "Choisir une image"
    @FXML
    private void choisirImage() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Choisir une image");
        // Filtrer les types de fichiers pour afficher uniquement les images
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Fichiers image", "*.png", "*.jpg", "*.gif");
        fileChooser.getExtensionFilters().add(extFilter);
        // Afficher la boîte de dialogue pour choisir un fichier
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            // Charger l'image sélectionnée dans l'ImageView
            imageView.setImage(new javafx.scene.image.Image(selectedFile.toURI().toString()));
        }
    }
}
