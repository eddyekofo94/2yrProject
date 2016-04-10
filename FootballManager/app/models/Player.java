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
		 
		 
		 this.attVal = 0;
		 this.defVal =0;
		 this.midFVal = 0;
		 this.gkVal =0;
		 this.injury = false;
		 this.salary = 0;
		 this.transferValue = 0;
		
         
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
     public Long getPlayerID(){
		 return playerID;
	 }
     public Team getTeamID(){
         return teamID;
     }
	 public String getPlayerName()
	 {
		 return playerName;
	 }
	 public void setPlayerName(String playerName)
	 {
		 this.playerName = playerName;
	 }
	 
	 public int getJerseyNum()
	 {
		 return jerseyNum;
	 }
	 public void setJerseyNum(int num )
	 {
		 this.jerseyNum = num ;
	 }
	 public void setTeam(Team team)
	{
		this.teamID = team;
	}
	
	 public Long getTID(){
		 return teamID.getTeamID();
	 }
     public void setPlayerName(String name){
         this.playerName = name;
     }
     public void setJerseyNum(int number){
         this.jerseyNum = number;
     }
     
<<<<<<< HEAD
    
=======
     
     public static Map<String,String> options(){
         final int TEAM_ID_NOT_ASSIGNED = 0;
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Player p: Player.find.orderBy("playerID").findList()){
            if(p.teamID.getTeamID() == TEAM_ID_NOT_ASSIGNED){
                options.put(p.playerID.toString(), p.playerName.toString());
            }    
        }
        return options;
    }
>>>>>>> 7f3f076ef74bc281cc2d88ef0581015038302b97
	
	
    public void setGkVal(int gkVal)
     {
     this.gkVal = gkVal;
     }
     
    public void setDefVal(int defVal)
     {
     this.defVal = defVal;
     }
     
     
    public void setMidVal(int midVal)
     {
     this.midFVal = midVal;
     }
     
    
	public void setAtkVal(int atkVal)
     {
     this.attVal = atkVal;
     }
	 
    
     public int getGkVal()
     {
     return this.gkVal;
     }
     
    public int getDefVal()
     {
      return this.defVal;
     }
     
     
    public int getMidVal()
     {
     return this.midFVal;
     }
     
    
	public int getAtkVal()
     {
      return this.attVal;
     }
    
   public Long getPosition()
   {
	 return position.getPositionID();  
   }
   public void setPosition(Position position)
   {
	   this.position = position;
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
}