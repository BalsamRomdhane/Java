package Controllers;

import Models.Campagne;
import javafx.scene.control.ListCell;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;

public class CampagneCell extends ListCell<Campagne> {

    @Override
    protected void updateItem(Campagne campagne, boolean empty) {
        super.updateItem(campagne, empty);

        if (empty || campagne == null) {
            setText(null);
            setGraphic(null);
        } else {
            // Créer une disposition pour la carte de campagne
            AnchorPane cardPane = new AnchorPane();
            cardPane.setPrefSize(200, 150);

            // Ajouter les éléments de la carte (nom, description, etc.)
            Text nomLabel = new Text(campagne.getNomcampagne());
            nomLabel.setLayoutX(10);
            nomLabel.setLayoutY(20);

            Text descriptionLabel = new Text(campagne.getDescri());
            descriptionLabel.setLayoutX(10);
            descriptionLabel.setLayoutY(50);

            // Si vous avez une image associée à la campagne, vous pouvez l'afficher ici
            ImageView imageView = new ImageView();
            imageView.setLayoutX(10);
            imageView.setLayoutY(80);
            imageView.setFitWidth(50); // Ajustez la largeur de l'image selon vos besoins
            imageView.setPreserveRatio(true);
            imageView.setImage(new Image("file:" + campagne.getImage())); // Utilisez le chemin de l'image de la campagne

            cardPane.getChildren().addAll(nomLabel, descriptionLabel, imageView);

            setGraphic(cardPane);
        }
    }
}

