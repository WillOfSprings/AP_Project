package ap.ap_project;

import javafx.scene.image.ImageView;

// 100 at 33x66
// x distance = 45.8
// y distance = 65.3
// 1 at 33x653.7


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
        int a = (int) (Math.round((position+5)/10.0)*10);
    }

    public void getCoords(){
        System.out.println("X: " + this.lX + " Y: " + this.lY);
    }

    // if pos == 0; wait for dice == 1;
    // pos >0, new pos = pos + dice
    // if new pos <= nearest 10, translate x
    // else, translate to nearest 10, translate 1 y, translate to new pos - nearest 10
}
