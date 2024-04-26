package Controllers;

import Models.Event;
import Services.ServiceEvent;
import Utils.DBConnection;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.List;
import java.util.ResourceBundle;

public class EventPageController implements Initializable {

    @FXML
    private HBox centerCards;

    @FXML
    private HBox carte11;

    private ServiceEvent serviceEvent;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        try {
            Connection cnx = DBConnection.getInstance().getCnx();
            serviceEvent = new ServiceEvent();
            List<Event> events = serviceEvent.getAllEvents();
            for (Event event : events) {
                FXMLLoader eventListLoader = new FXMLLoader(getClass().getResource("/event_list.fxml"));
                AnchorPane eventList = eventListLoader.load();
                EventListController eventListController = eventListLoader.getController();
                eventListController.setData(event);
                carte11.getChildren().add(eventList);
            }
        } catch (IOException e) {
            System.err.println("Error loading event_list.fxml: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("An unexpected error occurred: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
