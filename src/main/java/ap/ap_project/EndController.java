package ap.ap_project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.util.Duration;

//import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;

public class EndController implements Initializable {



    @FXML
    private Button exitButton;
    @FXML
    private Button replayButton;
    @FXML
    private ImageView diceEnd;
    @FXML
    private ImageView p2end;
    @FXML
    private ImageView p1end;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    public void exitCall(){
        System.exit(0);
    }

    public void replayScene(MouseEvent mouseEvent) {

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("temp.fxml")));
            Stage stage = (Stage)((Node)mouseEvent.getSource()).getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}