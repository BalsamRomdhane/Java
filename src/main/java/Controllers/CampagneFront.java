package Controllers;
import java.sql.PreparedStatement;

import Models.Campagne;
import Models.Event;
import Services.ServiceCampagne;
import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

public class CampagneFront {

    private static final String STORAGE_FOLDER = "C:\\Users\\balsa\\IdeaProjects\\demo\\src\\main\\images";

    private File selectedFile=null; // Déclarer la variable pour stocker le fichier sélectionné

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

    @FXML
    private TableView<Campagne> campagnesTableView;

    @FXML
    private TableColumn<Campagne, String> createurColumn;

    @FXML
    private TableColumn<Campagne, LocalDate> datedebColumn;

    @FXML
    private TableColumn<Campagne, LocalDate> datefinColumn;

    @FXML
    private TableColumn<Campagne, Button> editColumn;

    @FXML
    private TableColumn<Campagne, Button> deleteColumn;

    @FXML
    private TableColumn<Campagne, Integer> idColumn;

    @FXML
    private TableColumn<Campagne, Button> viewColumn;

    private ObservableList<Campagne> campagnesList;
    private Campagne selectedCampagne;

    public void initialize() {
        // Ensure all necessary objects are properly initialized
        if (createurColumn != null && datedebColumn != null && datefinColumn != null &&
                campagnesTableView != null && editColumn != null && deleteColumn != null && viewColumn!= null) {

            // Initialize the list of campaigns
            campagnesList = FXCollections.observableArrayList();

            // Define cell values for the table columns
            idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
            createurColumn.setCellValueFactory(new PropertyValueFactory<>("createur"));
            datedebColumn.setCellValueFactory(new PropertyValueFactory<>("datedeb"));
            datefinColumn.setCellValueFactory(new PropertyValueFactory<>("datefin"));

            // Load campaigns from the database
            loadCampagnesWithEvents();

            // Add campaigns to the table
            campagnesTableView.setItems(campagnesList);

            // Define actions for the "Modifier" button in the "editColumn"
            editColumn.setCellFactory(param -> new TableCell<Campagne, Button>() {
                private final Button editButton = new Button("Modifier");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(editButton);
                        editButton.setOnAction(event -> {
                            Campagne campagne = getTableView().getItems().get(getIndex());
                            modifierCampagne(campagne);
                        });
                    }
                }
            });

            // Define actions for the "Supprimer" button in the "deleteColumn"
            deleteColumn.setCellFactory(param -> new TableCell<Campagne, Button>() {
                private final Button deleteButton = new Button("Supprimer");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(deleteButton);
                        deleteButton.setOnAction(event -> {
                            Campagne campagne = getTableView().getItems().get(getIndex());
                            supprimerCampagne(campagne);
                        });
                    }
                }
            });
            viewColumn.setCellFactory(param -> new TableCell<Campagne, Button>() {
                private final Button viewButton = new Button("Voir");

                @Override
                protected void updateItem(Button item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setGraphic(null);
                    } else {
                        setGraphic(viewButton);
                        viewButton.setOnAction(event -> {
                            Campagne campagne = getTableView().getItems().get(getIndex());
                            viewCampagne(campagne);
                        });
                    }
                }
            });

        } else {
            System.out.println("One or more FXML elements are not properly initialized.");
        }
    }
    private void loadCampagnesWithEvents() {
        try {
            Connection cnx = DBConnection.getInstance().getCnx();
            ServiceCampagne serviceCampagne = new ServiceCampagne();
            List<Campagne> campagnes = serviceCampagne.getAllCampagnes();

            // Pour chaque campagne, récupérez les événements associés et mettez à jour la liste de campagnes
            for (Campagne campagne : campagnes) {
                List<Event> eventsForCampagne = serviceCampagne.getEventsForCampagne(campagne.getId());
                campagne.setEvents(eventsForCampagne); // Supposons que vous ayez une méthode setEvents dans la classe Campagne

                // Affichez les événements associés à chaque campagne
                for (Event event : eventsForCampagne) {
                    System.out.println("Événement associé à la campagne " + campagne.getNomcampagne() + ": " + event);
                }
            }

            campagnesList.addAll(campagnes); // Mettez à jour la liste des campagnes avec les événements associés
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors du chargement des campagnes.");
        }
    }

    private void viewCampagne(Campagne campagne) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_list.fxml"));
            Parent root = loader.load();
           EventListController controller = loader.getController();
           controller.loadEvents(campagne.getId());
           // controller.l
            // here call the method public void loadEvents(int id) from
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.showAndWait(); // Attend que la fenêtre soit fermée avant de revenir à la fenêtre principale
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    private void clearFields(){
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



    public void supprimerCampagne(Campagne campagne) {
        try {
            ServiceCampagne serviceCampagne = new ServiceCampagne();
            serviceCampagne.DeleteCampagne(campagne);
            afficherAlerte("Campagne supprimée avec succès.");
            campagnesList.remove(campagne); // Supprimer la campagne de la liste affichée dans la table
        } catch (Exception e) {
            e.printStackTrace();
            afficherAlerte("Erreur lors de la suppression de la campagne.");
        }
    }


    public void afficheform(ActionEvent actionEvent) {
        try {
            // Charger le fichier FXML du formulaire d'ajout de campagne
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/add_campagne.fxml"));
            Parent root = loader.load();

            // Créer une nouvelle scène
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Ajouter une campagne");

            // Rendre la nouvelle fenêtre modale
            stage.initModality(Modality.APPLICATION_MODAL);

            // Afficher la nouvelle fenêtre
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs de chargement du fichier FXML
        }
    }

//    public void modifierCampagne(Campagne campagne) {
//        try {
//            FXMLLoader loader = new FXMLLoader(getClass().getResource("/com/example/demo/modify_campagne.fxml"));
//            Parent root = loader.load();
//
//            ModifyCampagneController controller = loader.getController();
//            controller.initData(campagne);
//
//            Scene scene = new Scene(root);
//            Stage stage = new Stage();
//            stage.setScene(scene);
//            stage.setTitle("Modifier la campagne");
//            stage.initModality(Modality.APPLICATION_MODAL);
//            stage.showAndWait();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }
public void setSelectedCampagne(Campagne campagne) {
    this.selectedCampagne = campagne;
}

    // Méthode pour récupérer la campagne sélectionnée
    public Campagne getSelectedCampagne() {
        return selectedCampagne;
    }

    public void modifierCampagne(Campagne campagne) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/modify_campagne.fxml"));
            Parent root = loader.load();

            // Récupérer le contrôleur du fichier FXML de modification de la campagne
            ModifyCampagneController controller = loader.getController();

            // Initialiser les données de la campagne à modifier dans le contrôleur
            controller.initData(campagne);

            // Créer une nouvelle scène avec le contenu du fichier FXML
            Scene scene = new Scene(root);

            // Créer une nouvelle fenêtre pour la modification de la campagne
            Stage stage = new Stage();
            stage.setScene(scene);
            stage.setTitle("Modifier la campagne");
            stage.initModality(Modality.APPLICATION_MODAL); // Rendre la fenêtre modale

            // Afficher la fenêtre et attendre jusqu'à ce qu'elle soit fermée
            stage.showAndWait();
        } catch (IOException e) {
            e.printStackTrace();
            // Gérer les erreurs d'entrée/sortie
        }
    }

    public void initData(Campagne campagne) {
        // Implémentez cette méthode selon vos besoins
    }
}
