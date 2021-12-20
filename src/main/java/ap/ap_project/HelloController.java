package ap.ap_project;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.animation.PathTransition;
import javafx.animation.PathTransition.OrientationType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.shape.*;
import javafx.util.Duration;

public class HelloController {

    public int a = 1;
    public ImageView gameBoard;
    public ImageView piece1;
    public ImageView piece2;

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
    private Label welcomeText;

    @FXML
    private Cylinder abc;

    @FXML
    protected void handleButtonAction() {
        gamePane.setBackground(new Background(new BackgroundFill(javafx.scene.paint.Color.BLUE, CornerRadii.EMPTY, Insets.EMPTY)));
    }
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
        moveTo.setX(imageview.getTranslateX());
        moveTo.setY(imageview.getTranslateY());

        double ogx = imageview.getTranslateX();
        double ogy = imageview.getTranslateY();

        double finxlx = imageview.getTranslateX() + 200f;
        double inc = 50f;
        double finxly = imageview.getTranslateY();
        path.getElements().add(moveTo);


        while(ogx < finxlx) {
            path.getElements().add(new QuadCurveTo(ogx + (inc/2), 0.0f, ogx + inc, 50.0f));
            ogx += inc;
//            path.getElements().add(new QuadCurveTo(75.0f, 100.0f, 100.0f, 50.0f));
//            path.getElements().add(new QuadCurveTo(125.0f, 0.0f, 150.0f, 50.0f));
        }

        path.getElements().add(new MoveTo(finxlx, finxly));



//        path.getElements().add(new CubicCurveTo(130f, 10f, -75f, -100f, 120f, 150f));
        welcomeText.setText(String.format("%f and %f.", imageview.getTranslateX(), imageview.getTranslateY()));
        PathTransition pathTransition = new PathTransition();
        pathTransition.setDuration(Duration.millis(2000));
        pathTransition.setNode(imageview);
        pathTransition.setPath(path);
//        pathTransition.setOrientation(OrientationType.ORTHOGONAL_TO_TANGENT);
        pathTransition.setCycleCount(1);
        pathTransition.setAutoReverse(false);
        pathTransition.play();

        imageview.setTranslateX(finxlx);
        imageview.setTranslateY(finxly);
        welcomeText.setText(String.format("%f and %f.", imageview.getTranslateX(), imageview.getTranslateY()));
    }




}
