package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;
import models.users.*;
import play.*;


import views.html.*;
import models.*;
 public class PlayerCtrl extends Controller {
     // The amount of times a user can train per match
     static int numberOfTraining = 3;
     //variable to check if numberOfTraining = 0
     final int minTrainingTest = 0;
     //injury health value once reached player is injured
     final int INJURY_LEVEL =4;
     //Max value for health
     final int MAX_HEALTH = 10;
     //Max attribute value
     final int MAX_VALUE = 10;
     //Max amount of players per team
     private final int MAX_PLAYERS = 15;
     //Max amount of players on field
     private final int MAX_ON_FIELD = 11;
     //Max amount of goalkeepers/goalkeeper position ID
     private final int MAX_GOALKEEPER = 1;
     
    //random number generator
     Random ranNum = new Random();
 
    //returns the number of times a user has trained per match (max three times) 
     public int getNumOfTrain(){
         return this.numberOfTraining;
     }
     //resets number of times a user has trained when a match is played to three
     public static void reSetNumberOfTraining(){
         List<Player> players = Player.findAll();
         numberOfTraining = 3;
     }
     //sets the position of a player specified by the user
     public Result setPosition(String position, Long pID){        
         int countOnField = 0; //a count of the players on the field
         List<Position> positions = Position.findAll(); //creates a list of positions
         List<Player> players = Player.findAll(); //creates a list of players
         
         for(Player p : players){//loops through all the players in the list
             if(p.position.id != getPositionID("Sub")){ //position of player not equal to a sub add 1 to countOnField
                 countOnField ++;
             }
         }
         for(Player p : players ){ //loops through all the players in the list
             if(position.equals("Goalkeeper") && p.getPosition() == MAX_GOALKEEPER){//insures each team only has one goalkeeper on the field
                 flash("error3","already have a goalkeeper");
                 return redirect("/squad/1");//returns to squad page and flashes an apropriate error messages
             }
             else if(countOnField == MAX_ON_FIELD && getPositionID(position)!=5 ){//checks if there are too may players on the field max 11
                 flash("error4","too many on field");
                 return redirect("/squad/0");//returns to squad page and flashes an apropriate error messages
             }
             else{
                 if(p.playerID == pID){
             
                for(int i=0; i < positions.size();i++){
                    if(positions.get(i).position.equals(position)){
                        p.setPosition(positions.get(i));
                        p.update();
                    }
                }
               
                  for(int i=0; i < positions.size();i++){
                    if(positions.get(i).position.equals(position)){
                        p.setPosition(positions.get(i));
                        p.update();
                    }
                } 
                flash("Success2", "Player has changed position to ");
                return redirect("/squad/5");
                }
             
             }
         }
         return redirect("/squad/4");
     }
  public Result getTrained(String position, Long pID){
      //if(numberOfTraining >= minTrainingTest){
         //train value earned to be added to position value
         int randomTrainVal = ranNum.nextInt(5)+1;
         List<Player> players = Player.findAll();
         for(Player p : players ){
             if(p.playerID == pID){
                if(playerMaxed(position,p) == true){
                    flash("error1","error1");
                    return redirect("/squad/20");
                   }
                else if(randomTrainVal <= 2){
                    flash("error2","error2");
                    return redirect("/squad/0");
                    }
                else if(randomTrainVal <= 4){
                    addTrainVal(position,randomTrainVal,p);
                    deductHealth(ranNum.nextInt(2),p);;
                    p.injury = getInjured(p.health,p);
                    numberOfTraining = numberOfTraining -1;
                    p.update();
                    flash("success","success");
                    return redirect("/squad/0");
               }
                else{
                    addTrainVal(position,randomTrainVal,p);
                    deductHealth(ranNum.nextInt(3),p);
                    p.injury = getInjured(p.health,p);
                    numberOfTraining = numberOfTraining -1;
                    p.update();
                    flash("success","success");
                    return redirect("/squad/0");
                    }
                    
             }
			 p.calcTransValue();
             p.update();
         }
         
     /*}else{
         flash("error","error");   
     return redirect("/squad/0");
     } */
     return redirect("/squad/0");  
     }
     
     public boolean getInjured(int health, Player player){

         if(player.health <= INJURY_LEVEL ){
             return true;
         }
         else{
             return false;
         }
     }
     public void addTrainVal(String position,int trainVal, Player player){
         final int MAX_VAL = 10;
         int overMaxVal;
         int positionVal = getPositionVal(position, player);
         if((positionVal + trainVal) <= MAX_VAL){
             setPositionVal(trainVal,position,player);
         }
         else{
             if((positionVal+trainVal) > MAX_VAL){
                 overMaxVal = (positionVal+trainVal) - MAX_VAL;
                 setPositionVal((trainVal-overMaxVal),position,player);
             }
         }
		 
     }
     public int getPositionVal(String position, Player player){
         
             if(position.equals("Goalkeeper")){
                  return player.gkVal;
             }
             else if(position.equals("Defense")){
                 return player.defVal;
             }
              else if(position.equals("Midfield")){
                 return player.midFVal;
             }
             else{
                 return player.attVal;
             }                                            
     }
    public void setPositionVal(int trainVal, String position, Player player){
        
          if(position.equals("Goalkeeper")){
                  player.setGkVal(player.gkVal + trainVal);
                 
             }
             else if(position.equals("Defense")){
                  player.setDefVal(player.defVal + trainVal);
             }
              else if(position.equals("Midfield")){
                  player.setMidVal(player.midFVal + trainVal);
             }
             else{
                  player.setAtkVal(player.attVal + trainVal);
             }
        
     }
     
   public void deductHealth(int healthLose, Player player){
         int negativeHealth;
         if((player.health - healthLose) < 0){
             negativeHealth = player.health - healthLose;
             player.health = healthLose + negativeHealth;
         }
         else{
             player.health -= healthLose;
         }
     }
     public void playerHealthyTest(Player player){
        
         if(player.health > 4 && player.health < MAX_HEALTH){
            player.injury = false;
         }
         else{
             player.injury = true;
         }
     }
     public boolean playerMaxed(String position, Player player){
        
         if(player.position.equals("Goalkeeper")){
                  if(player.getGkVal() == MAX_VALUE){
                      return true;
             }
             else if(player.position.equals("Defense")){
                   if(player.getDefVal() == MAX_VALUE){
                      return true;
                }
             }
              else if(player.position.equals("Midfield")){
                   if(player.getMidVal() == MAX_VALUE){
                      return true;
                }
             }
             else if(player.position.equals("Striker")){
                  if(player.getAtkVal() == MAX_VALUE){
                      return true;
                }
             }
         }
         return false;    
     }
     public boolean checkHealthMax(int health){
         if(health == MAX_HEALTH){
             return true;
         }
         else{
             return false;
         }
     }
     
     public void calculateInjuredHealthIncrease(Player player){
         int healthIncrease = ranNum.nextInt(3);
         int positiveHealth;
         
         if((player.health + healthIncrease) > MAX_HEALTH){
             positiveHealth = player.health + healthIncrease;
             healthIncrease = MAX_HEALTH - player.health;
             player.health += healthIncrease;
         }
         else{
             player.health += healthIncrease;
         }
     }
	 
	  public static void randomStats()
     {
     Random rand = new Random();
     int count = 0;
     //get all the teams
     List<Team> teams = models.Team.findAll();
     
     //get all the players 
      List<Player> players = Player.findAll();
      
      //for each of the teams assign all the players random stats
      
      for(int i = 0 ; i < teams.size();i++)
      	{
      
      		for(int j = 0 ; j < players.size();j++)
      		{
      			if(teams.get(i).getTeamID() == players.get(j).getTeamID().getTeamID())
      			{
      			//11 players in team 1 gk 4 def 4 mid 2 attk guarenteed rest is random
      			//set goalkeeper
      			
      				if(count == 0)
      				{
      			         players.get(j).setGkVal(rand.nextInt(3)+1+6);
      			         players.get(j).setDefVal(rand.nextInt(10)+1);
      			         players.get(j).setMidVal(rand.nextInt(10)+1);
      			         players.get(j).setAtkVal(rand.nextInt(10)+1);
      			         
      				}
      				else if((count >=1)&&(count<= 4))
      				{
      				players.get(j).setGkVal(rand.nextInt(10)+1);
      			         players.get(j).setDefVal(rand.nextInt(3)+1+6);
      			         players.get(j).setMidVal(rand.nextInt(10)+1);
      			         players.get(j).setAtkVal(rand.nextInt(10)+1);
      			         
      				}
      				else if((count >=5)&&(count<=9))
      				{
      				players.get(j).setGkVal(rand.nextInt(10)+1);
      			         players.get(j).setDefVal(rand.nextInt(10)+1);
      			         players.get(j).setMidVal(rand.nextInt(3)+1+6);
      			         players.get(j).setAtkVal(rand.nextInt(10)+1);
						 
      				}
      				else if((count >=10)&&(count <= 11))
      				{
      				players.get(j).setGkVal(rand.nextInt(10)+1);
      			         players.get(j).setDefVal(rand.nextInt(10)+1);
      			         players.get(j).setMidVal(rand.nextInt(10)+1);
      			         players.get(j).setAtkVal(rand.nextInt(3)+1+6);
      				}
      				else
      				{
      				 players.get(j).setGkVal(rand.nextInt(10)+1);
      			         players.get(j).setDefVal(rand.nextInt(10)+1);
      			         players.get(j).setMidVal(rand.nextInt(10)+1);
      			         players.get(j).setAtkVal(rand.nextInt(10)+1);
						 
      				}
      				count++;
					players.get(j).calcTransValue();
      				players.get(j).save();
      				
      			}
      
      		}
      	}
     
     }
	 
	 public static void genPlayerStat(Player player)
	 {
		  Random rand = new Random();
		player.setGkVal(rand.nextInt(10)+1);
      	player.setDefVal(rand.nextInt(10)+1);
      	player.setMidVal(rand.nextInt(10)+1);
      	player.setAtkVal(rand.nextInt(10)+1);
		player.calcTransValue();
		
	 }
     
       public Result genStats() {

       randomStats();

        return redirect("/squad/0");
    }

	
public Result buyPlayer(Long id, Long userid) {
		
		Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
		List<Player> players = Player.findAll();
		List<Team> teamList = Team.findAll();
		List<User> userList = Manager.findAll();
		Manager owner = null;
		Team transfer = Team.getTeamDefault();
		Team userTeam ;
		 
		for(int k = 0 ; k < userList.size();k++)
		{
			if(userid == userList.get(k).getid())
			{
				owner = (Manager)userList.get(k);
			}
		}
		
		for(int i = 0 ; i < players.size();i++)
		{
			if(id == players.get(i).getPlayerID())
			{
				
				
				for(int j = 0 ; j < teamList.size();j++)
				{
					if(teamList.get(j).getTeamID() == owner.getid())
					{
						userTeam = teamList.get(j);
						
						if(owner.getBankaccount() >= players.get(i).getTransferValue())
						{
						players.get(i).setTeam(userTeam);
						owner.updateBankaccount(players.get(i).getTransferValue());
						}
				
						
					}
					owner.update();
				}
				players.get(i).update();
			}
			
		}
		
		return ok(transferPlayer.render(User.getLoggedIn(session().get("loginName")),players,transferPlayerForm, transfer.getTeamID()));
		
	}

    public Long getPositionID(String position){
        long defaultValue = 0;
        List<Position> positions = Position.findAll();
        for(Position p : positions){
            if(position.equals(p.position)){
                return p.id;
            }
       }
       return defaultValue;
    }
 }

 
 }
    