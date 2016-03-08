package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;


@Entity
public class Player extends Model{
     
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
     
     public Team getTeamID(){
         return teamID;
     }
     
     public static Map<String,String> options(){
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Player p: Player.find.orderBy("playerID").findList()){
            if(p.teamID == null){
                options.put(p.playerID.toString(), p.playerName.toString());
            }    
        }
        return options;
    }
     /*
     public String getTrained(String position, int playerID){
         //train value earned to be added to position value
         private int randomTrainVal = ranNum.nextInt(5)+1;
         if(playerMaxed(playerID) == true){
             return "Player already fully trained in this position "+position;
         }
         else if(randomTrainVal <= 2){
             return "Unable to train this player this time!";
         }
         else if(randomTrainVal <= 4){
             addTrainVal(randomTrainVal);
             deductHealth(ranNum.nextInt(4)+1);;
             injury = getInjured(health);
             return true;
         }
         else{
             addTrainVal(randomTrainVal);
             deductHealth(ranNum.nextInt(4 - 5)+1);
             injury = getInjured(health);
             return true;
         }
     }*/
     
     /*public boolean getInjured(int health){
         final int INJURY_LEVEL = 4;
         if(health <= INJURY_LEVEL ){
             return true;
         }
         else{
             return false;
         }
     }*/
     /*public void addTrainVal(String position,int trainVal){
         final int MAX_VAL = 10;
         int overMaxVal;
         int positionVal = getPositionVal(position);
         if((positionVal + trainVal) <= MAX_VAL){
             setPositionVal(trainVal,position);
         }
         else{
             if((positionVal+trainVal) > MAX_VAL){
                 overMaxVal = (positionVal+trainVal) - MAX_VAL;
                 setPositionVal((trainVal-overMaxVal),position);
             }
         }
     }*/
     /*public int getPositionVal(String position){
         Switch(position){
             case "Goalkeeper": return gkVal;
                                break;
             case "Defense": return defVal;
                            break;
              case "Midfield": return midFVal;
                                break;
              case "Striker": return attVal;
                                break;                                             
         }  
     }*/
    /* public void setPositionVal(int trainVal, String position){
         Switch(position){
             case "Goalkeeper": gkVal += trainVal;
                                break;
             case "Defense":  defVal+= trainVal;
                            break;
              case "Midfield":  midFVal+= trainVal;
                                break;
              case "Striker":  attVal+= trainVal;
                                break;                                             
         }  
     }*/
     
   /* public void deductHealth(int healthLose){
         int negativeHealth;
         if((health - healthLose) < 0){
             negativeHealth = health - healthLose;
             health = healthLose + negativeHealth;
         }
         else{
             health -= healthLose;
         }
     }*/
     /*public void playerHealthyTest(){
         final int INJURY_LEVEL =4;
         final int MAX_HEALTH = 10;
         if(health > 4 && health < MAX_HEALTH){
            injury = false;
         }
         else{
             injury = true;
         }
     }*/
}   