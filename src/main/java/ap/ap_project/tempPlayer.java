package ap.ap_project;
import java.util.*;
import javafx.scene.image.ImageView;

import java.util.HashMap;
import java.util.Map;

// 100 at 33x66
// x distance = 45.8
// y distance = 65.3
// 1 at 33x653.7

class coords{
    double x;
    double y;
    int multiplier;
    int position;
    coords(double x, double y, int multiplier, int position){
        this.x = x;
        this.y = y;
        this.multiplier = multiplier;
        this.position = position;
    }

    public double getX(){
        return x;
    }

    public double getY(){
        return y;
    }

    public int getMultiplier(){
        return multiplier;
    }

    public int getPosition(){
        return position;
    }
}


public class tempPlayer {
    double Xdistance = 45.8f;
    double Ydistance = 65.3f;

    static HashMap<Integer, coords> snakes;
    static HashMap<Integer, coords> ladders;
    ImageView piece;
    int position;
    double lX;
    double lY;

    static {
        snakes = new HashMap<Integer,coords>();
        snakes.put(24,new coords(226.1, 665.35, 1, 5));snakes.put(56,new coords(226.1, 534.75, -1, 25));
        snakes.put(43,new coords(88.7, 534.75, 1, 22));snakes.put(60,new coords(88.7, 404.15, -1, 42));
        snakes.put(69,new coords(363.5, 404.15, 1, 48));snakes.put(86,new coords(363.5, 338.85, -1, 53));
        snakes.put(98,new coords(134.5, 338.85, 1, 58));snakes.put(96,new coords(180.3, 142.95, -1, 84));
        snakes.put(94,new coords(363.5, 208.25, 1, 73));snakes.put(90,new coords(409.3, 208.25, -1, 72));

        ladders  = new HashMap<Integer,coords>();
        ladders.put(3,new coords(42.9, 534.75, 1, 21)); ladders.put(29,new coords(363.5, 469.45, -1, 33));
        ladders.put(16,new coords(271.9, 534.75, -1, 26)); ladders.put(8,new coords(271.9, 404.15, 1, 46));
        ladders.put(37,new coords(226.1, 273.55, -1, 65)); ladders.put(64,new coords(180.3, 208.25, -1, 77));
        ladders.put(50,new coords(455.1, 273.55, 1, 70)); ladders.put(61,new coords(88.7, 142.95, 1, 82));
        ladders.put(76,new coords(271.9, 77.65, 1, 95)); ladders.put(89,new coords(455.1, 77.65, -1, 91));

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
        if(this.position%10==0)
        {
            nearestTen=this.position;
        }

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

            if ((position)%10 == 0) {
                this.setCoords(lX, lY - 65.3);
                Xdistance *= -1;
                this.setCoords((lX + ((dc-1) * Xdistance)), lY);

            }

            else if (position + dc <= nearestTen) {
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
            this.setCoords(snakes.get(position).getX(), snakes.get(position).getY());
            Xdistance *= snakes.get(position).getMultiplier();
            position = snakes.get(position).getPosition();
        }

        if (ladders.containsKey(position)) {
            this.setCoords(ladders.get(position).getX(), ladders.get(position).getY());
            Xdistance *= ladders.get(position).getMultiplier();
            position = ladders.get(position).getPosition();
        }
        if (position == 100) {
            System.out.println(" Winner ");
        }
        System.out.println(position);

    }


    //TODO:
    // if pos == 0; wait for dice == 1;
    // pos >0, new pos = pos + dice
    // if new pos > 100, don't move
    // if new pos <= nearest 10, translate x
    // else, translate to nearest 10, translate 1 y, translate to new pos - nearest 10
    // After move, check if pos == 100, if yes, pop up win screen



}
