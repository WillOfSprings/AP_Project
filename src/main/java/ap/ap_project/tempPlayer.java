package ap.ap_project;
import java.util.*;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

// 100 at 33x66
// x distance = 45.8
// y distance = 65.3
// 1 at 33x653.7


public class tempPlayer {
    double Xdistance = 45.8f;
    double Ydistance = 65.3f;

    static HashMap<Integer, Integer> snakes;
    static HashMap<Integer, Integer> ladders;
    ImageView piece;
    int position;
    double lX;
    double lY;

    static {
        snakes = new HashMap<Integer,Integer>();
        snakes.put(24,5); snakes.put(56,25); snakes.put(43,22); snakes.put(60,42); snakes.put(69,48);
        snakes.put(86,53);snakes.put(98,58); snakes.put(96,84); snakes.put(94,73);snakes.put(90,72);

        ladders  = new HashMap<Integer,Integer>();
        ladders.put(3,21); ladders.put(29,33); ladders.put(16,26); ladders.put(8,46); ladders.put(37,65); ladders.put(64,77);
        ladders.put(50,70); ladders.put(61,82); ladders.put(76,95); ladders.put(89,91);

    }


    public tempPlayer(ImageView piece, int position){
        this.piece = piece;
        this.position = position;
        lX = piece.getLayoutX();
        lY = piece.getLayoutY();
    }

    public void getCoords(){
        System.out.println("X: " + this.lX + " Y: " + this.lY);
    }

    public void setCoords(double lX, double lY){
        this.lX=lX;
        this.lY=lY;
        piece.setLayoutX(lX);
        piece.setLayoutY(lY);
    }

    public void move(int dc) {

        //System.out.print("in move");

        int nearestTen = this.position + (10 - (this.position % 10));

        if (position == 0) {
            System.out.print("in move");
            if (dc == 1) {
                System.out.println("got1");
                this.position = 1;
                System.out.println("new position" + this.position);
                this.setCoords(42.9, 665.35);
            }
            return;
        }

        else if (this.position + dc > 100) {
            System.out.println("can't move");
            return;
        }

        else {

            if (position + dc <= nearestTen) {
                this.setCoords(lX + (Xdistance * dc), lY);

            } else {
                this.setCoords((lX + (nearestTen - this.position) * Xdistance), lY);
                this.setCoords(lX, lY - 65.3);
                Xdistance *= -1;
                this.setCoords(lX + (dc - 1 - nearestTen + this.position) * Xdistance, lY);
            }
        }


        position += dc;
        if (snakes.containsKey(position)) {
            int d = position - (int) snakes.get(position);
            move(d);
        }

        if (ladders.containsKey(position)) {
            int d = (int) ladders.get(position) - position;
            move(d);
        }
        if (position == 100) {
            System.out.println(" Winner ");
        }

    }


    //TODO:
    // if pos == 0; wait for dice == 1;
    // pos >0, new pos = pos + dice
    // if new pos > 100, don't move
    // if new pos <= nearest 10, translate x
    // else, translate to nearest 10, translate 1 y, translate to new pos - nearest 10
    // After move, check if pos == 100, if yes, pop up win screen



}
