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
     public int health; // players health
     @ManyToOne
     public Team team;
     public boolean injury;
     public double salary;

     public int transferValue;
	 
	 @Transient
    private  final int TRANSFER_VALUE_WEIGHT = 1000	;
    
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
     //find all the players in the database and return them in a list
     public static List<Player> findAll(){

         return Player.find.all();
        
     }
     public Long getPlayerID(){//return player id
		 return playerID;
	 }
     public Team getTeam(){//return team object
         return team;
     }
	 public String getPlayerName()//returns player name
	 {
		 return playerName;
	 }
	 public void setPlayerName(String playerName)//Sets player name to a new name
	 {
		 this.playerName = playerName;
	 }
	 
	 public int getJerseyNum()//returns players jersey number
	 {
		 return jerseyNum;
	 }
	 public void setJerseyNum(int num )//sets player jersey number to a new number
	 {
		 this.jerseyNum = num ;
	 }
	 public void setTeam(Team team)//sets player to a team
	{
		this.team = team;
	}
	
	 public Long getTID(){//returns players team id
		 return team.getTeamID();
	 }
public boolean getInjury()
{
	return this.injury;
}
     
     public static Map<String,String> options(){ //returns a map list of players ordered by id
         final int TEAM_ID_NOT_ASSIGNED = 0;
        LinkedHashMap<String,String> options = new LinkedHashMap<String,String>();
        for(Player p: Player.find.orderBy("playerID").findList()){
            if(p.team.getTeamID() == TEAM_ID_NOT_ASSIGNED){
                options.put(p.playerID.toString(), p.playerName.toString());
            }    
        }
        return options;
    }

	
	public int getTransferValue()//returns players selling value
	{
		return this.transferValue;
	}
	
	public void setTransferValue(int transferValue) //sets a new transfer value
	{
		this.transferValue = transferValue;
	}

    public void setGkVal(int gkVal)//sets goalkeeping value
     {
     this.gkVal = gkVal;
     }
     
    public void setDefVal(int defVal)//sets defensive value
     {
     this.defVal = defVal;
     }
     
     
    public void setMidVal(int midVal)//sets midfield value
     {
     this.midFVal = midVal;
     }
     
    
	public void setAtkVal(int atkVal)//sets striker value
     {
     this.attVal = atkVal;
     }
	 
    
     public int getGkVal()//returns goalkeeping value
     {
     return this.gkVal;
     }
     
    public int getDefVal()//returns defensive value
     {
      return this.defVal;
     }
     
     
    public int getMidVal()//returns midfield value
     {
     return this.midFVal;
     }
     
    
	public int getAtkVal()//returns striker value
     {
      return this.attVal;
     }
    
   public Long getPosition()//returns player position ID
   {
	 return position.getPositionID();  
   }
   public void setPosition(Position position)//sets player position
   {
	   this.position = position;
   }

   	public void calcTransValue() //calculates a transferValue based on there position values then multiplies by 1000
	{
		int totalValue = 0;	 	  	  	          
      	totalValue += this.gkVal;
		totalValue += this.defVal;
		totalValue += this.midFVal;
		totalValue += this.attVal;
		this.transferValue = totalValue*TRANSFER_VALUE_WEIGHT;						
    }
}