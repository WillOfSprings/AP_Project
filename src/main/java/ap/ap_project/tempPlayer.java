package ap.ap_project;

import javafx.scene.image.ImageView;



public class tempPlayer {
    double Xdistance = 45.8f;
    double Ydistance = 65.3f;


    ImageView piece;
    int position;
    double lX;
    double lY;

    public tempPlayer(ImageView piece, int position){
        this.piece = piece;
        this.position = position;
        lX = piece.getLayoutX();
        lY = piece.getLayoutY();
    }

    public void getCoords(){
        System.out.println("X: " + this.lX + " Y: " + this.lY);
    }
}
