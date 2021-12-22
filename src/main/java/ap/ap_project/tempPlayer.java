package ap.ap_project;

import javafx.scene.image.ImageView;
import java.util.HashMap;

// 100 at 33x66
// x distance = 45.8
// y distance = 65.3
// 1 at 33x653.7

// Coords for snakes and ladders, present in hashmap
class coords{
    private final double x;
    private final double y;
    private final int multiplier;
    private final int position;

    public coords(double x, double y, int multiplier, int position){
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



// Player class, X and Ydistance = distance between 2 tiles, piece image, position, overlap flag and coords
public class tempPlayer {

    private double Xdistance = 45.8f;
    private final double Ydistance = 65.3f;

    private final static HashMap<Integer, coords> snakes;
    private final static HashMap<Integer, coords> ladders;

    private final String name;
    private final ImageView piece;
    private int position;
    private int overlap;
    private double lX;
    private double lY;

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


    public tempPlayer(ImageView piece, int position, String name){
        this.name = name;
        this.piece = piece;
        this.position = position;
        this.lX = piece.getLayoutX();
        this.lY = piece.getLayoutY();
        this.overlap = 0;
    }

    public void setOverlap(int overlap){
        this.overlap = overlap;
    }

    public int getOverlap(){
        return this.overlap;
    }

    public ImageView getPiece(){
        return piece;
    }

    public int getPosition(){
        return position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public double getXdistance(){
        return Xdistance;
    }

    public double getYdistance() {
        return Ydistance;
    }

    public void setXdistance(double Xdistance){
        this.Xdistance = Xdistance;
    }

    public String getName(){
        return name;
    }

    //To print coords
//    public void getCoords(){
//        System.out.println("X: " + this.lX + " Y: " + this.lY);
//    }

    public double getX(){
        return lX;
    }
    public double getY(){
        return lY;
    }

    public void setCoords(double lX, double lY){
        this.lX=lX;
        this.lY=lY;
        piece.setLayoutX(lX);
        piece.setLayoutY(lY);
    }


    // Move pieces when called.
    public void move(double dc) {

        //Logic:
        // if pos == 0; wait for dice == 1;
        // pos >0, new pos = pos + dice
        // if new pos > 100, don't move
        // if new pos <= nearest 10, translate x
        // else, translate to nearest 10, translate 1 y, translate to new pos - nearest 10
        // After move, check if pos == 100, if yes, pop up win screen


        // Find nearest 10 to account for jump, move from 0 only if dice == 1, else move tiles horizontally till nearest 10.
        int nearestTen = this.getPosition() + (10 - (this.getPosition() % 10));

        if(this.getPosition()%10==0)
        {
            nearestTen=this.getPosition();
        }

        if (this.getPosition() == 0) {
            if (dc == 1) {
                System.out.println("\n" + this.getName() + " started moving.\n");
                this.setPosition(1);
                this.setCoords(42.9, 665.35);
            }
            return;
        }

        else if (this.getPosition() + dc > 100) {
            System.out.println("\nNot enough places to move for " + this.getName() + "\n");
            return;
        }

        else {

            if ((this.getPosition())%10 == 0) {
                this.setCoords(lX, lY - 65.3);
                this.setXdistance(this.getXdistance()*-1);
                this.setCoords((lX + ((dc-1) * Xdistance)), lY);

            }

            else if (position + dc <= nearestTen) {
                this.setCoords(lX + (Xdistance * dc), lY);

            } else {
                this.setCoords((lX + (nearestTen - this.position) * Xdistance), lY);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                this.setCoords(lX, lY - 65.3);
                Xdistance *= -1;
                this.setCoords(lX + (dc - 1 - nearestTen + this.position) * Xdistance, lY);
            }
        }

        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // Check if snake or ladder
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

        // Deprecated
//        if (position == 100) {
//            System.out.println(" Winner ");
//        }

    }

}
