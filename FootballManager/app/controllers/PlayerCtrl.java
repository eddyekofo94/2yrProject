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
 //random number generator
     Random ranNum = new Random();
     
     public Result setPosition(String position, Long pID){        
         List<Position> positions = Position.findAll();
         List<Player> players = Player.findAll();
         for(Player p : players ){
             if(p.playerID == pID){
                for(int i=0; i<positions.size();i++){
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
                    deductHealth(ranNum.nextInt(4)+1,p);;
                    p.injury = getInjured(p.health,p);
                    p.save();
                flash(p.playerName+" trained");
                }
                else{
                    addTrainVal(position,randomTrainVal,p);
                    deductHealth(ranNum.nextInt(5)+2,p);
                    p.injury = getInjured(p.health,p);
                    p.save();
                    flash(p.playerName+" trained");
                }
             }
         }
         return redirect("/squad/0");  
     }
     
     public boolean getInjured(int health, Player player){
         final int INJURY_LEVEL = 4;
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
         final int INJURY_LEVEL =4;
         final int MAX_HEALTH = 10;
         if(player.health > 4 && player.health < MAX_HEALTH){
            player.injury = false;
         }
         else{
             player.injury = true;
         }
     }
     public boolean playerMaxed(String position, Player player){
         final int MAX_VALUE = 10;
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
 
 }
 