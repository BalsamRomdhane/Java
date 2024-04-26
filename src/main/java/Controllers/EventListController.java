package Controllers;

import Models.Campagne;
import Models.Event;
import Services.ServiceEvent;
import Utils.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

//import static org.graalvm.compiler.hotspot.debug.BenchmarkCounters.getIndex;


public class EventListController implements Initializable {

    @FXML
    private Button add;

    @FXML
    private AnchorPane cart;

    @FXML
    private VBox cartevVBox;

    @FXML
    private ListView<Event> eventListView;

    @FXML
    private TextField typeLabel;

    private final ServiceEvent eventService;
    private ObservableList<Event> observableEvents;
   // private CampagneFront selectedCampagne =  idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));;


    public EventListController() {
        Connection cnx = DBConnection.getInstance().getCnx();
        this.eventService = new ServiceEvent();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
      //  int selectedCampagneId = selectedCampagne.getId();

        // Appelez loadEvents avec l'ID de la campagne sélectionnée
      //  loadEvents(0);

    }

    public void loadEvents(int id) {
        try {
            List<Event> events = eventService.getEventsForCampagne(id);
            observableEvents = FXCollections.observableArrayList(events);
            eventListView.setItems(observableEvents);
            eventListView.setCellFactory(eventListView -> new EventCell());
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Erreur lors du chargement des événements : " + e.getMessage());
        }
    }

    public void setData(Event event) {
    }

    public void addevent(ActionEvent actionEvent) {

        if (!validerChamps()) {
            afficherAlerte("Veuillez remplir tous les champs obligatoires.");
            return;
        }


        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/addevent.fxml"));
            Parent root = loader.load();

            AddEventController addEventController = loader.getController();
            // Peut-être que vous devez transmettre des données au contrôleur de la vue d'ajout d'événement ici

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();

            // Si vous souhaitez masquer la fenêtre actuelle après avoir ouvert la vue d'ajout d'événement, utilisez cette ligne
            ((Stage) add.getScene().getWindow()).close(); // close() fermera la fenêtre actuelle

        } catch (IOException e) {
            e.printStackTrace(); // Gérer l'exception selon les besoins
        }
    }
    private boolean validerChamps() {
        // Valider les champs obligatoires avant d'ajouter un événement
        if (typeLabel.getText().isEmpty()) {
            return false;
        }
        // Ajoutez ici d'autres validations selon vos besoins
        return true;
    }
    private void afficherAlerte(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private class EventCell extends javafx.scene.control.ListCell<Event> {
        private final VBox vbox = new VBox();
        private final Label nameLabel = new Label();
        private final Button editButton = new Button("Modifier");
        private final Button deleteButton = new Button("Supprimer");

        public EventCell() {
            super();
            HBox hbox = new HBox(nameLabel, editButton, deleteButton);
            vbox.getChildren().add(hbox);
            setGraphic(vbox);

            editButton.setOnAction(event -> {
                Event selectedEvent = getItem();
                // Mettez ici la logique pour modifier l'événement
                if (selectedEvent != null) {
                    // Implémentez la logique de modification ici
                    System.out.println("Modifier l'événement : " + selectedEvent);
                }
            });

            deleteButton.setOnAction(event -> {
                Event selectedEvent = getItem();
                // Mettez ici la logique pour supprimer l'événement
                if (selectedEvent != null) {
                    try {
                        eventService.delete(selectedEvent.getId());
                        observableEvents.remove(selectedEvent);
                        System.out.println("Supprimer l'événement : " + selectedEvent);
                    } catch (Exception e) {
                        e.printStackTrace();
                        System.err.println("Erreur lors de la suppression de l'événement : " + e.getMessage());
                    }
                }
            });
            editButton.setOnAction(event -> {
                Event selectedEvent = getItem();
                if (selectedEvent != null) {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/event_form.fxml"));
                    Parent root;
                    try {
                        root = loader.load();
                        ModifyEventController controller = loader.getController();
                        controller.setData(selectedEvent); // Transmettre l'événement sélectionné à AddEventController
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });

        }



        @Override
        protected void updateItem(Event event, boolean empty) {
            super.updateItem(event, empty);
            if (empty || event == null) {
                setGraphic(null);
            } else {
                nameLabel.setText(event.getLieu());
                setGraphic(vbox);
            }
        }
    }
}
