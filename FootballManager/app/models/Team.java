package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;


@Entity
 public class Team extends Model{
     
     /*private final int MAX_PLAYERS = 15;
     private final int MAX_ON_FIELD = 11;
     private final int MAX_SUBS = 4;*/
     
     @Id
     private Long teamID;
     @Constraints.Required
     private int userID;
     @Constraints.Required
     private String teamName;
     @OneToMany
     public List<Player> players;
     private int teamScore; 
     public String leagueName;  
     //Default constructor
     public Team(){
         
     }  
     //Overloaded constructor 
     public Team(Long teamID, int userID, String TeamName, int teamScore,List<Player> players){
         this.teamID = teamID;
         this.userID = userID;
         this.teamName = teamName;
         this.teamScore = teamScore;
         this.players = players;
     }
     // Generic wuery helper for entity Computer with id Long
     //public static Model.Finder<Long, Team> find = new Model.Finder<Long, Team>(Long.class, Team.class);
     public static Finder<Long, Player> find = new Finder<Long, Player>(Long.class, Player.class);

     /*public static List<Team> findAll(){
         return Team.find.all();
     }*/
      
      //public void playMatch(){
      //    calculateMatch(); //simulates the match
          //print results from Calculate match
      //    calculateHealthLose(); //decreases the health of the players involved in the game
       //   injuredPlayerIncreaseHealth(); //increase the health of injured players
          
     // }
      
     // public boolean manageTeam(String newTeamName,String newPlayerPosition, String oldPlayerPosition, int newPlayerID,
     // int oldPlayerID){
          
      
 }