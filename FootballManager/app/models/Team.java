package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;


@Entity
 public class Team extends Model{
     
     private final int MAX_PLAYERS = 15;
     private final int MAX_ON_FIELD = 11;
     private final int MAX_SUBS = 4;
     
     @Id
     @OneToOne(mappedBy = "teamID")
     public Long teamID;
     
    public Long userID;
    
     public ArrayList <User> user = new ArrayList<>();
     public ArrayList <Team> teamList = new ArrayList<>();
     
     @Constraints.Required
     public String teamName;
     private int teamScore;   
     @ManyToMany(mappedBy = "tList")
    public List<Fixtures> flist = new ArrayList<Fixtures>();
     //Default constructor
     public Team(){
         
     }  
     //Overloaded constructor 
     public Team(Long teamID, String TeamName, int teamScore){
         this.teamID = teamID;
         this.teamName = teamName;
         this.teamScore = teamScore;
     }
     // Generic wuery helper for entity Computer with id Long
     public static Model.Finder<Long, Team> find = new Model.Finder<Long, Team>(Long.class, Team.class);
     
     public static List<Team> findAll(){
         return Team.find.all();
     }
      
      //public void playMatch(){
      //    calculateMatch(); //simulates the match
          //print results from Calculate match
      //    calculateHealthLose(); //decreases the health of the players involved in the game
       //   injuredPlayerIncreaseHealth(); //increase the health of injured players
          
     // }
      
     // public boolean manageTeam(String newTeamName,String newPlayerPosition, String oldPlayerPosition, int newPlayerID,
     // int oldPlayerID){
     public Long getTeamID(){
         return teamID;
     }

    public Long getUserID(){
         return userID;
     }

    public int getTeamScore() {
        return teamScore;
    }
}
