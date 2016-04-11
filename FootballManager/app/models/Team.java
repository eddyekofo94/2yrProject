package models;

import java.util.*;
import javax.persistence.*;

import play.data.format.*;
import play.data.validation.*;

import com.avaje.ebean.*;
import com.avaje.ebean.Model;

import models.users.*;



@Entity
 public class Team extends Model{
	@Transient
     private final int MAX_PLAYERS = 15;
	 @Transient
     private final int MAX_ON_FIELD = 11;
	 @Transient
     private final int MAX_SUBS = 4;

     @Id
     @OneToOne(mappedBy = "teamID")
     public Long teamID;

     @OneToOne
     public Long userid;

     @Constraints.Required
     public String teamName;

     private int teamScore;
     @ManyToMany(mappedBy = "tList")
     public List<Fixtures> flist = new ArrayList<Fixtures>();
     @Transient
    Long userID;

     public ArrayList <Team> teamList = new ArrayList<>();

     //Default constructor
     public Team(){
		
     }
     //Overloaded constructor
     public Team(Long teamID, Long userid, String TeamName, int teamScore){
         this.teamID = teamID;
         this.userID = userid ;
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
	 
	 public String getTeamName()
	 {
		 return teamName;
	 }
	 public void setTeamName(String teamName)
	 {
		 this.teamName = teamName;
	 }

     public Long getuserid(){
          return userid;
      }
	  
	  public void setuserid(Long userid){
		  this.userid = userid;
	  }

    public int getTeamScore() {
        return teamScore;
    }
	
	
	
	public void setTeamScore(int teamScore) {
        this.teamScore = teamScore;
    }
	
	public static Team getTeamDefault(){
		List<Team> transfer;
		transfer = Team.find.all();
		Team team = null;
		for(int i = 0 ; i < transfer.size();i++)
		{
			if(transfer.get(i).getTeamID() == 0)
			{
				team = transfer.get(i);
			}
		}
		
		return team;
	}
}
