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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.*;
import javafx.util.Duration;

import java.io.File;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;

public class HelloController implements Initializable {

    // 100 at 33x66
    // x distance = 45.8
    // y distance = 65.3
    // 1 at 33x653.7

    // ignore
    public int a = 1;

    //Player turn
    public int turn = 1;

    // For roll()
    int dc = 0;
    Random random = new Random();
    public ImageView gameBoard;
    public ImageView piece1;
    public ImageView piece2;

    @FXML
    private ImageView p1Inactive;
    @FXML
    private ImageView p2Inactive;
    @FXML
    private ImageView p1;
    @FXML
    private ImageView p2;

    @FXML
    private ImageView diceView;
    @FXML
    private Pane gamePane;
    @FXML
    private Button tempB;

    @FXML
    private ImageView imageview;
    @FXML
    private ImageView imageview2;
    @FXML
    private ImageView imageview3;

    @FXML
    private Button helB;
    @FXML
    private ImageView arrow;
    @FXML
    private Label welcomeText;

    @FXML
    private Cylinder abc;

    //TODO: IDK what this is? -Pratyush
    @FXML
    protected void handleButtonAction() {
        gamePane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    //TODO: Wonky af, needs rethinking -Pratyush
    @FXML
    protected void onHelloButtonClick(){

        if (a%3 == 1) {
            imageview = imageview3;
        } else {
            imageview = imageview2;
        }
        a++;
        welcomeText.setText("Welcome to JavaFX Application!");
        Path path = new Path();
//        path.getElements().add(new MoveTo (0f, 0f));
//        path.getElements().add(new CubicCurveTo (40f, 30f, 50f, 34f, 60f, 50f));
//        path.getElements().add(new CubicCurveTo (80f, 70f, 90f, 84f, 100f, 100f));
        MoveTo moveTo = new MoveTo();
        moveTo.setX(imageview.getLayoutX());
        moveTo.setY(imageview.getLayoutY());
        moveTo.isAbsolute();

        double ogx = imageview.getLayoutX();
        double ogy = imageview.getLayoutY();

        double finxlx = imageview.getLayoutX() + 200f;
        double inc = 50f;
        double finxly = imageview.getLayoutY();
        path.getElements().add(moveTo);


        while(ogx < finxlx) {
            path.getElements().add(new QuadCurveTo(ogx + (inc/2), 0.0f, ogx + inc, 50.0f));
            ogx += inc;
//            path.getElements().add(new QuadCurveTo(75.0f, 100.0f, 100.0f, 50.0f));
//            path.getElements().add(new QuadCurveTo(125.0f, 0.0f, 150.0f, 50.0f));
        }

        path.getElements().add(new MoveTo(finxlx, finxly));



//        path.getElements().add(new CubicCurveTo(130f, 10f, -75f, -100f, 120f, 150f));
        welcomeText.setText(String.format("%f and %f.", imageview.getLayoutX(), imageview.getLayoutY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(imageview);
        pathTransition.setPath(path);
//        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();

        imageview.setLayoutX(finxlx);
        imageview.setLayoutY(finxly);
        welcomeText.setText(String.format("%f and %f.", imageview.getLayoutX(), imageview.getLayoutY()));
    }


    Timeline timeline = new Timeline(new KeyFrame(Duration.millis(25), new EventHandler<ActionEvent>() {

        double deltaY = 2;

        @Override
        public void handle(ActionEvent actionEvent) {

            arrow.setLayoutY(arrow.getLayoutY() + deltaY);

            //Bounds bounds = scene.getBoundsInLocal();
            boolean bottomBorder = arrow.getLayoutY() >= (727);
            boolean topBorder = arrow.getLayoutY() <= (707);


            if (bottomBorder || topBorder) {
                deltaY *= -1;
            }
        }
    }));


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        p2Inactive.setOpacity(0.5);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        tempPlayer p1 = new tempPlayer(imageview2, 0);
        tempPlayer p2 = new tempPlayer(imageview3, 0);
        p1.getCoords();
        p2.getCoords();

    }


    // Dice audio
    private void playDiceAudio(){
        File diceAudioFile = new File("src\\main\\resources\\ap\\ap_project\\diceRollAudio.mp3");
        Media diceAudio = new Media(diceAudioFile.toURI().toString());
        MediaPlayer mediaPlayer = new MediaPlayer(diceAudio);
        mediaPlayer.play();
    }


    // Called when dice is pressed, dice disabled for the moment.
    public void roll(MouseEvent mouseEvent) {
        diceView.setDisable(true);


        // Thread: hide arrow, play dice animation and audio, set dice face correspondingly, enable dice again.
        Thread thread = new Thread() {
            public void run(){
                System.out.println("aaa");
                try{
                    arrow.setVisible(false);
                    dc = (random.nextInt(6)+1);
                    playDiceAudio();
                    for (int i = 0; i < 15; i++) {
                        File diceFile = new File("src\\main\\resources\\ap\\ap_project\\diceRoll" + (i+1)+ ".png");
                        //TODO: Remove debug comments.
//                        System.out.println(diceFile.getAbsolutePath());
                        diceView.setImage(new Image(diceFile.toURI().toString()));
                        Thread.sleep(100);
                    }

                    File diceFile = new File("src\\main\\resources\\ap\\ap_project\\dice" + dc + ".png");
//                    System.out.println(diceFile.getAbsolutePath());
                    diceView.setImage(new Image(diceFile.toURI().toString()));
                    arrow.setVisible(true);

                    //TODO: Needs to be looked at.
                    if(p1Inactive.getOpacity()==0)
                    {p1Inactive.setOpacity(0.5);p2Inactive.setOpacity(0);}
                    else{p1Inactive.setOpacity(0);p2Inactive.setOpacity(0.5);}


                    Thread.sleep(100);

                    diceView.setDisable(false);

                    //TODO:
                    // if turn == 1, call player1.move
                    // if turn == 2, call player2.move


                    //TODO: Remove debug comments.
                    System.out.println(dc);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
        // To reset, dk where to place
//        p1Inactive.setOpacity(0.0);
//        p2Inactive.setOpacity(0.0);
    }

}