package ap.ap_project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

//import static javafx.scene.web.WebEngine.PulseTimer.animation;

public class DiceTest implements Initializable {

    Random random;
    int a;

    @FXML
    private Label welcomeText;
    @FXML
    private ImageView dice;
    @FXML
    private Button diceButton;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        random = new Random();
        a = 0;
    }


    public void roll(MouseEvent mouseEvent) {
        dice.setDisable(true);

        Thread thread = new Thread() {
            public void run(){
                System.out.println("aaa");
                try{
                    a = (random.nextInt(6)+1);
                    for (int i = 0; i < 15; i++) {
                        File diceFile = new File("dice" + a + ".png");
                        dice.setImage(new javafx.scene.image.Image(diceFile.toURI().toString()));
                        Thread.sleep(100);
                    }

                    dice.setDisable(false);
                    welcomeText.setText("" + a);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
    }
}