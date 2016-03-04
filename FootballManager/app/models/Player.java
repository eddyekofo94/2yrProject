package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;


@Entity
public class Player extends Model{
     //private final int INJURY_HEALTH = 4;
    // private final int MAX_PALYER_STAT = 10;
     
     @Id
     public Long playerID;
     @Constraints.Required
     public int jerseyNum;
     @Constraints.Required
     public String playerName;
     @ManyToOne //many players to one position
     public Position position;
     @Constraints.Required
     public int attVal; //attacking strength 
     @Constraints.Required
     public int defVal; //defensive strength
     @Constraints.Required
     public int midFVal; // midfield strength
     @Constraints.Required
     public int gkVal; // goal keeping strength 
     @Constraints.Required
     public int health;
     @ManyToOne
     public Team teamID;
     public boolean injury;
     public double salary;
     public double transferValue;
     
     //constants for getTrained and getInjured methods
     
     //random number generator
     Random ranNum = new Random();
     
     //Default constructor
     public Player(){
         
     }  
     //Overloaded constructor 
     public Player(Long playerID, int jerseyNum, String playerName, Position position, int attVal, int defVal,
       int midFVal, int gkVal){
         this.playerID = playerID;
         this.jerseyNum = jerseyNum;
         this.playerName = playerName;
         this.position = position;
         this.attVal = attVal;
         this.defVal = defVal;
         this.midFVal = midFVal;
         this.gkVal = gkVal;
     }
     // Generic wuery helper for entity Computer with id Long
     public static Model.Finder<Long, Player> find = new Model.Finder<Long, Player>(Long.class, Player.class);
     
     public static List<Player> findAll(){
         return Player.find.all();
     }
     /*
     public boolean getTrained(String position, int playerID){
         //train value earned to be added to position value
         private int trainVal = ranNum.nextInt(5)+1;
         //health lost from training
         private int healthLose = ranNum.nextInt(4)+1;
         
         if(playerMaxed(playerID) == true){
             
         }
     }*/
}   