package ap.ap_project;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.Random;
import java.util.ResourceBundle;



public class HelloController implements Initializable {

    // 100 at 33x66
    // x distance = 45.8
    // y distance = 65.3
    // 1 at 33x653.7

    // Custom variables
    private TempPlayer player1;
    private TempPlayer player2;
    //Player turn
    private int turn = 1;
    // For roll()
    private int dc = 0;

    @FXML
    private ImageView backButton;
    @FXML
    private ImageView backOverlay;
    @FXML
    private ImageView backPopUp;
    @FXML
    private ImageView popBack;
    @FXML
    private ImageView popOkay;
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
    private ImageView imageview2;
    @FXML
    private ImageView imageview3;
    @FXML
    private ImageView arrow;





    // For the arrow, infinite movement for the arrow over the course of the game, direction depends on bounds.
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


    /* Logic:
    *  For init, set p2 banner as inactive
    * play arrow animation indefinitely
    * define 2 players: contains imageview of piece, position, layout coordinates and overlap flag
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        p2Inactive.setOpacity(0.5);

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        player1 = new TempPlayer(imageview2, 0, "Player1");
        player2 = new TempPlayer(imageview3, 0, "Player2");

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
                Random random = new Random();
                try{
                    arrow.setVisible(false);
                    dc = (random.nextInt(6)+1);
                    playDiceAudio();
                    for (int i = 0; i < 15; i++) {
                        File diceFile = new File("src\\main\\resources\\ap\\ap_project\\diceRoll" + (i+1)+ ".png");
                        diceView.setImage(new Image(diceFile.toURI().toString()));
                        Thread.sleep(100);
                    }

                    File diceFile = new File("src\\main\\resources\\ap\\ap_project\\dice" + dc + ".png");
                    diceView.setImage(new Image(diceFile.toURI().toString()));
                    System.out.println("\nDice roll: " + dc);
                    System.out.println("Player " + turn + "'s turn.");

                    // if turn == 1, call player1.move after checking for overlap, switch inactivity of p2 banner
                    // if turn == 2, call player2.move, same way


                    MoveThread mt = new MoveThread(player1, player2, dc, turn);
                    mt.start();
                    if (turn == 1) {
                        turn = 2;
                        p1Inactive.setOpacity(0.5);
                        p2Inactive.setOpacity(0);
                    } else {
                        turn = 1;
                        p1Inactive.setOpacity(0);
                        p2Inactive.setOpacity(0.5);
                    }

                    Thread.sleep(500);

                    // Check for game finish
                    FinishThread ft = new FinishThread(player1, player2);
                    Platform.runLater(ft);

                    // If not, continue
                    arrow.setVisible(true);
                    diceView.setDisable(false);

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };

        thread.start();
    }

    // Bring up menu when backButton is pressed.
    public void changeScene(MouseEvent mouseEvent){

        backOverlay.setMouseTransparent(false);
        backOverlay.setOpacity(0.5);
        backPopUp.setOpacity(1);
        backPopUp.setMouseTransparent(false);
        popBack.setMouseTransparent(false);
        popOkay.setMouseTransparent(false);

    }

    // Move back to playing screen, called when popBack is pressed.
    public void popBackButton(MouseEvent mouseEvent) {
        backOverlay.setMouseTransparent(true);
        backOverlay.setOpacity(0);
        backPopUp.setOpacity(0);
        backPopUp.setMouseTransparent(true);
        popBack.setMouseTransparent(true);
        popOkay.setMouseTransparent(true);
    }


    // Exit mid game, called when popOkay is pressed.
    public void popOkayButton(MouseEvent mouseEvent) {
        System.out.println("------------------------------------------------------");
        System.out.println("Exiting...");
        System.out.println("------------------------------------------------------");
        System.exit(0);
    }



}

// Thread to move player based on die number.
class MoveThread extends Thread{

    private final TempPlayer player1;
    private final TempPlayer player2;
    private final int dcnumber;
    private final int turn;


    public MoveThread(TempPlayer player1, TempPlayer player2, int dcnumber, int turn){
        this.player1 = player1;
        this.player2 = player2;
        this.dcnumber = dcnumber;
        this.turn = turn;
    }


    /* Alternate logic, deprecated

     * TempPlayer.overlap = 0;
     * if player.overlap == 0
     * currentPlayer.move(dcnumber, player2, imageview overlap);
     * curretplayer pos + dice == player2 pos
     * currentPlayer.overlap = 1; player2.overlap = 1;
     * players.opacity0
     * overlap.seropacity 1
     * overlap.set x, sety lxly


     * else:
     * overlap.seropacity 0
     * players.opacity0
     * overlap = 0;

     */


    @Override
    public void run(){

        System.out.println("\nPlayer1 current position: "+player1.getPosition());
        System.out.println("Player2 current position: "+player2.getPosition()+"\n");


        /* Logic:
         * if turn == 1, if overlapped, un-overlap, adjust images, move
         * either way, after move, see if position == player2, if so, overlap=1, adjust images
         * sam if turn == 2, for player 2
         */

        if( this.turn == 1 ){

            if ( player1.getOverlap()==1 )
            {
                player1.setOverlap(0);
                player2.setOverlap(0);
                File diceFile = new File("src\\main\\resources\\ap\\ap_project\\piece2.png");
                player1.getPiece().setImage(new Image(diceFile.toURI().toString()));
                player2.getPiece().setOpacity(1);
            }

            player1.move(dcnumber);

            if ( ( player2.getPosition() == player1.getPosition() ) && player1.getPosition()!=0 )
            {
                player1.setOverlap(1);
                player2.setOverlap(1);
                File diceFile = new File("src\\main\\resources\\ap\\ap_project\\overlap.png");
                System.out.println("Player1 and 2 overlap at position: " + player1.getPosition());
                player2.getPiece().setImage(new Image(diceFile.toURI().toString()));
                player1.getPiece().setOpacity(0);

            }
        }

        else{

            if ( player2.getOverlap() == 1 )
            {
                player2.setOverlap(0);
                player1.setOverlap(0);
                File diceFile = new File("src\\main\\resources\\ap\\ap_project\\piece1.png");
                player2.getPiece().setImage(new Image(diceFile.toURI().toString()));
                player1.getPiece().setOpacity(1);

            }

            player2.move(dcnumber);

            if ( (player2.getPosition()== player1.getPosition() ) && player2.getPosition()!=0 )
            {
                player1.setOverlap(1);
                player2.setOverlap(1);
                System.out.println("Player2 and 1 overlap at position: " + player2.getPosition());
                File diceFile = new File("src\\main\\resources\\ap\\ap_project\\overlap.png");
                player1.getPiece().setImage(new Image(diceFile.toURI().toString()));
                player2.getPiece().setOpacity(0);

            }
        }

        System.out.println("\nPlayer1 current position: "+player1.getPosition());
        System.out.println("Player2 current position: "+player2.getPosition()+"\n");


    }
}


class FinishThread implements Runnable {

    /* Logic: Thread to finish game, runs after every dice roll.
     * if player1 or player2 is at 100, a static class' variables are set for next scene to retrieve
     * If no one at 100, exit thread
     * else, load finishing sequence to the scene, from there, exit or restart.
     */

    private final TempPlayer player1;
    private final TempPlayer player2;

    public FinishThread(TempPlayer player1, TempPlayer player2){
        this.player1 = player1;
        this.player2 = player2;
    }



    @Override
    public void run(){
        if(player1.getPosition() == 100){
            System.out.println("\n--------Player 1 wins!--------\n");
            WinInfo.setWinner(1);
            WinInfo.setWinnerX(player1.getX());
            WinInfo.setWinnerY(player1.getY());
            WinInfo.setLoserX(player2.getX());
            WinInfo.setLoserY(player2.getY());
            WinInfo.setWinFile(new File("src\\main\\resources\\ap\\ap_project\\p1win.png"));
        }
        else if (player2.getPosition() == 100){
            System.out.println("\n--------Player 2 wins!--------\n");
            WinInfo.setWinner(2);
            WinInfo.setWinnerX(player2.getX());
            WinInfo.setWinnerY(player2.getY());
            WinInfo.setLoserX(player1.getX());
            WinInfo.setLoserY(player1.getY());
            WinInfo.setWinFile(new File("src\\main\\resources\\ap\\ap_project\\p2win.png"));
        }

        if (player1.getPosition() == 100 || player2.getPosition() == 100){

            try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("hello-view.fxml")));
            Stage stage = (Stage)player1.getPiece().getScene().getWindow();
            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }

        }
    }

}