package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;

import play.*;


import views.html.*;
import models.*;
 public class PlayerCtrl extends Controller {
      //injury health value once reached player is injured
      final int INJURY_LEVEL =4;
      //Max value for health
      final int MAX_HEALTH = 10;
      //Max attribute value
      final int MAX_VALUE = 10;
 //random number generator
     Random ranNum = new Random();
     
     public Result setPosition(String position, Long pID){        
         List<Position> positions = Position.findAll();
         List<Player> players = Player.findAll();
         for(Player p : players ){
             if(p.playerID == pID){
                for(int i=0; i < positions.size();i++){
                    if(positions.get(i).position.equals(position)){
                        p.position = positions.get(i);
                    }
                }
             }
             flash("Success", "Player "+ p.playerName+" has changed position to "+ position);
             p.save();
         }
         return redirect("/squad/0");
     }
  public Result getTrained(String position, Long pID){
         //train value earned to be added to position value
         int randomTrainVal = ranNum.nextInt(5)+1;
         List<Player> players = Player.findAll();
         for(Player p : players ){
             if(p.playerID == pID){
                if(playerMaxed(position,p) == true || p.injury == true){
                    
                    flash(p.playerName+" already fully trained in this position "+position);
                }
                else if(randomTrainVal <= 2){
                    flash( "Unable to train this "+p.playerName+" this time!");
                    
                }
                else if(randomTrainVal <= 4){
                    addTrainVal(position,randomTrainVal,p);
                    deductHealth(ranNum.nextInt(2),p);;
                    p.injury = getInjured(p.health,p);
                    p.save();
                flash(p.playerName+" trained");
                }
                else{
                    addTrainVal(position,randomTrainVal,p);
                    deductHealth(ranNum.nextInt(3),p);
                    p.injury = getInjured(p.health,p);
                    p.save();
                    flash(p.playerName+" trained");
                }
             }
         }
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
                  player.gkVal+=trainVal;
             }
             else if(position.equals("Defense")){
                  player.defVal+=trainVal;
             }
              else if(position.equals("Midfield")){
                  player.midFVal+=trainVal;
             }
             else{
                  player.attVal+=trainVal;
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
                  if(player.gkVal == MAX_VALUE){
                      return true;
             }
             else if(player.position.equals("Defense")){
                   if(player.defVal == MAX_VALUE){
                      return true;
                }
             }
              else if(player.position.equals("Midfield")){
                   if(player.midFVal == MAX_VALUE){
                      return true;
                }
             }
             else if(player.position.equals("Striker")){
                  if(player.attVal == MAX_VALUE){
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
      				players.get(j).save();
      				
      			}
      
      		}
      	}
     
     }
     
       public Result genStats() {

       randomStats();

        return redirect("/squad/0");
    }
     }
 
 
 