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




    public void main()
    {
        HashMap<Integer, Integer> snakes = new HashMap<Integer,Integer>();
        snakes.put(24,5); snakes.put(56,25); snakes.put(43,22); snakes.put(60,42); snakes.put(69,48);
        snakes.put(86,53);snakes.put(98,58); snakes.put(96,84); snakes.put(94,73);snakes.put(90,72);
        HashMap<Integer,Integer> ladders  = new HashMap<Integer,Integer>();
        ladders.put(3,21); ladders.put(29,33); ladders.put(16,26); ladders.put(8,46); ladders.put(37,65); ladders.put(64,77);
        ladders.put(50,70); ladders.put(61,82); ladders.put(76,95); ladders.put(89,91);

    }
    HashMap snakes;
    HashMap ladders;
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
    public void setCoords(double lX, double lY){
        this.lX=lX;
        this.lY=lY;
        piece.setLayoutX(lX);
        piece.setLayoutY(lY);
    }

    public void move(int dc)
    {//System.out.print("in move");
        if(position==0)
        {   System.out.print("in move");
            if(dc==1)
            {   System.out.println("got1");
                this.position=1;
                System.out.println("new position" +this.position);
                this.setCoords(lX,lY-42);
            }
        }

        else if(position!=100)
        {

            if(position+dc<=(int) (Math.round((position+5)/10.0)*10))
            {
                this.setCoords(lX+55*dc,lY);

            }
            else{
                this.setCoords((int) (Math.round((position+5)/10.0)*10)*55+lX,lY);
                this.setCoords(lX, lY-40);
                this.setCoords(((int) (Math.round((position+5)/10.0)*10)-position)*55, lY);
            }
            position+=dc;
            if(snakes.containsKey(position))
            {
                int d= position-(int) snakes.get(position);
                move(d);


            }
            if(ladders.containsKey(position))
            {
                int d= position-(int) ladders.get(position);
                move(d);
            }
            if(position==100)
            {
                System.out.println(" Winner ");
            }

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
