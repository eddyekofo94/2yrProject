package controllers;

import java.util.*;
import play.mvc.Controller;
import play.mvc.*;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;
import java.sql.*;
import models.users.Login;
import models.users.*;
import controllers.security.*;
import play.db.*;
import views.html.*;
import models.*;

/**
 * Created by Phillip on 12/03/2016.
 */
public class MatchCtrl extends Controller {
    private static int curWeek = 1;
    private int homeTeamIndex;
    private int awayTeamIndex;
    private int[] scores = new int[2];
    User user = this.user;


private User getCurrentUser() {
        User u = User.getLoggedIn(session().get("userid"));
        return u;
    }

    public Result Match() {

       playMatch();

        return redirect("/fixtures");
    }
	
	public void calcTeamScore(Team team)
	{
		int teamScore = 0;
		 List<Player> players = Player.findAll();
		 
		 for(int i = 0;i < players.size();i++)
		 {
			 if(team.getTeamID() == players.get(i).getTID())
			 {
				 //GoalKeeper position
				if(players.get(i).getPosition() == 1) 
				{
					teamScore += players.get(i).getGkVal();
				}
				//defender position
				if(players.get(i).getPosition() == 2) 
				{
					teamScore += players.get(i).getDefVal();
				}
				//midfield position
				if(players.get(i).getPosition() == 3) 
				{
					teamScore += players.get(i).getMidVal();
				}
				
				//attacker position
				if(players.get(i).getPosition() == 4) 
				{
					teamScore += players.get(i).getAtkVal();
				}
			 }
			 
		 }
		 
		 team.setTeamScore(teamScore);
         
         team.save();		 
	}

    public  void playMatch(){
        List<User> users = User.findAll();
        List<Player> players = Player.findAll();
        List<models.Fixtures> weekFixtures = new ArrayList();
        List<models.Team> teamsPlaying = new ArrayList<>();
        PlayerCtrl pCtrl = new PlayerCtrl();
        Random ranNum = new Random();
//populate this weeks Fixtures
        for (Fixtures f : Fixtures.<Fixtures>findAll()) {
            if (f.getWeek() == curWeek) {
                weekFixtures.add(f);
            }
        }

        for (Team t : Team.<Team>findAll()) {
            teamsPlaying.add(t);
        }

        for (int i = 0; i < weekFixtures.size(); i++) {
            for (int j = 0; j < teamsPlaying.size(); j++) {
                if ((weekFixtures.get(i).getHomeTeamID() == teamsPlaying.get(j).getTeamID())) {
                    this.homeTeamIndex = j;
                } else if (weekFixtures.get(i).getAwayTeamID() == teamsPlaying.get(j).getTeamID()) {
                    this.awayTeamIndex = j;
                }

            }
			calcTeamScore(teamsPlaying.get(homeTeamIndex));
			calcTeamScore(teamsPlaying.get(awayTeamIndex));
			
            Match m1 = new Match(teamsPlaying.get(homeTeamIndex).getTeamScore(),teamsPlaying.get(awayTeamIndex).getTeamScore());
            m1.calcMatch();
            scores = m1.getScores();
            weekFixtures.get(i).setHomeScore(scores[0]);
            weekFixtures.get(i).setAwayScore(scores[1]);
            weekFixtures.get(i).setPlayed(true);
            weekFixtures.get(i).save();

        }
        curWeek++;

        for(User user : users){
            user.reSetNumberOfTraining();
            user.update();
        }
        for(Player p : players){
            if(p.injury == true){
                pCtrl.calculateInjuredHealthIncrease(p);               
            }
            else{
                pCtrl.deductHealth(ranNum.nextInt(2),p);
            }
            p.update();
        }
    }
     

    public static void setCurWeek(int curWeek) {
        MatchCtrl.curWeek = curWeek;
    }
	
	public Result submitForMatch(Long userid)
	{
		Boolean allReady = true;
	Manager manager;
		List<User> managerList = Manager.findAll();
		
		
		for(int k = 0;k < managerList.size();k++)
		{
			
			if(managerList.get(k).getid()== userid && managerList.get(k).getUserType().equals("manager"))
			{
				
				manager = (Manager)managerList.get(k);
				manager.setReady(true);
				manager.update();
				
			}
		}
		
		for(int i = 0;i < managerList.size();i++)
		{
			if(managerList.get(i).getUserType().equals("manager"))
			{
				manager = (Manager)managerList.get(i);
				if(!manager.getReady())
				{
				allReady = false;
				}
			}
				
		}
		if(allReady)
		{
			for(int k = 0;k < managerList.size();k++)
		{
			
				if(managerList.get(k).getUserType().equals("manager"))
			{
				manager = (Manager)managerList.get(k);
				manager.setReady(false);
				manager.update();
			}
			
		}
			playMatch();
			return redirect("/fixtures");
		}
		else {
			flash("success", "Awaiting managers");
			return redirect("/squad");
		}
		
	}
}
