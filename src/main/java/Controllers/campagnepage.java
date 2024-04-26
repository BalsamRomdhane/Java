package Controllers;

import Models.Campagne;
import Services.ServiceCampagne;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class campagnepage implements Initializable {

    @FXML
    private HBox cartev;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
            try {
                ServiceCampagne ServiceCampagne = new ServiceCampagne();
                List<Campagne> Campagnes = ServiceCampagne.getAllCampagnes();
                for (Campagne campagne : Campagnes) {
                    FXMLLoader CampFrontLoader = new FXMLLoader(getClass().getResource("/campfront.fxml"));
                    HBox carteCampagneBox = CampFrontLoader.load();
                    CampFront CampFront = CampFrontLoader.getController();
                   // CampFront.setData(campagne);
                    cartev.getChildren().add(carteCampagneBox);
                }
            } catch (IOException e) {
                System.err.println("Error loading cartearticle.fxml: " + e.getMessage());
                e.printStackTrace();
            } catch (Exception e) {
                System.err.println("An unexpected error occurred: " + e.getMessage());
                e.printStackTrace();
            }
        }
    }
