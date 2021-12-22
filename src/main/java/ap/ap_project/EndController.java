package ap.ap_project;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class EndController implements Initializable {


    @FXML
    private ImageView winPopUp;
    @FXML
    private Button exitButton;
    @FXML
    private Button replayButton;
    @FXML
    private ImageView p2end;
    @FXML
    private ImageView p1end;


    private ImageView win, lose;


    // Retrieve winner's info and show it on the screen.
    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if (WinInfo.getWinner() == 1){
            this.win = p1end;
            this.lose = p2end;
        } else if (WinInfo.getWinner() == 2){
            this.win = p2end;
            this.lose = p1end;
        } else {
            System.out.println("Error in switching scenes.");
            System.exit(1);
        }

        win.setLayoutX(WinInfo.getWinnerX());
        win.setLayoutY(WinInfo.getWinnerY());
        lose.setLayoutX(WinInfo.getLoserX());
        lose.setLayoutY(WinInfo.getLoserY());
        winPopUp.setImage(new Image(WinInfo.getWinFile().toURI().toString()));

        System.out.println("------------------------------------------------------");
        System.out.println("Press menu to exit. Replay to replay the game.");
        System.out.println("------------------------------------------------------");


    }

    public void exitCall(){
        System.out.println("------------------------------------------------------");
        System.out.println("Exiting...");
        System.out.println("------------------------------------------------------");
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

/* Storage class for winner info */

class WinInfo {

    private static double winnerX, winnerY, loserX, loserY;
    private static int winner;
    private static File winFile;

    public static int getWinner(){
        return WinInfo.winner;
    }

    public static double getWinnerX(){
        return WinInfo.winnerX;
    }

    public static double getWinnerY(){
        return WinInfo.winnerY;
    }

    public static double getLoserX(){
        return WinInfo.loserX;
    }

    public static double getLoserY(){
        return WinInfo.loserY;
    }

    public static File getWinFile(){
        return WinInfo.winFile;
    }

    public static void setWinner(int winner){
        WinInfo.winner = winner;
    }

    public static void setWinnerX(double winnerX){
        WinInfo.winnerX = winnerX;
    }

    public static void setWinnerY(double winnerY){
        WinInfo.winnerY = winnerY;
    }

    public static void setLoserX(double loserX){
        WinInfo.loserX = loserX;
    }

    public static void setLoserY(double loserY){
        WinInfo.loserY = loserY;
    }

    public static void setWinFile(File winFile){
        WinInfo.winFile = winFile;
    }

}