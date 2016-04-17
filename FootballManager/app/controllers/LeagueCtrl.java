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

public class LeagueCtrl extends Controller {
    // Authenticate user needs to be added start of each method to be secured
    @Security.Authenticated(Secured.class)
    public Result leagueTable() {
    List<Fixtures> fixture = Fixtures.findAll();
    List<Team> teams = Team.findAll();
        startLeague(teams);
        updateLeague();
        return ok(views.html.leagueTable.render(User.getLoggedIn(session().get("loginname")),fixture,teams,models.LeagueTable.getLeague()));
    }
     // Authenticate user needs to be added start of each method to be secured
    @Security.Authenticated(Secured.class)
    public Result LeagueUpdate(){
        updateLeague();
        List<Fixtures> fixture = Fixtures.findAll();
        List < Team > teams = Team.findAll();
        return ok(views.html.leagueTable.render(User.getLoggedIn(session().get("loginname")),fixture,teams,models.LeagueTable.getLeague()));
    }
    public void startLeague(List<models.Team> teams){

        for(int i = 1;i < teams.size();i++)
        {
        models.LeagueTable league = new models.LeagueTable(teams.get(i).getTeamID());
        league.addTeam();
        }
     }
     
     public void updateLeague()
     {
        Long homeTeamID;
        Long awayTeamID;
        int homeScore;
        int awayScore;
        int homePts;
        int homeGoalDifference;
        int awayPts;
        int awayGoalDifference;
            List<Fixtures> fixtureCurrent = new ArrayList();
            for(int l =0; l < models.LeagueTable.getLeague().size();l++)
            {
                models.LeagueTable.getLeague().get(l).resetLeague();
            }
        for(Fixtures f : Fixtures.<Fixtures>findAll()) {
            fixtureCurrent.add(f);
        }

        for(int i = 0 ; i < fixtureCurrent.size();i++)
        {
            if((fixtureCurrent.get(i).played )== true )
            {
                homeTeamID = fixtureCurrent.get(i).getHomeTeamID();
                awayTeamID = fixtureCurrent.get(i).getAwayTeamID();
                homeScore = fixtureCurrent.get(i).gethomeScore();
                awayScore = fixtureCurrent.get(i).getawayScore();
                homeGoalDifference = homeScore- awayScore;
                awayGoalDifference = awayScore - homeScore;
                if(homeScore > awayScore)
                {
                    homePts = 2;
                    awayPts = 0;
                    models.LeagueTable.updateLeague(homeTeamID,1,0,0,homeGoalDifference,3);
                    models.LeagueTable.updateLeague(awayTeamID,0,1,0,awayGoalDifference,0);
                    fixtureCurrent.get(i).save();
                }
                else if (homeScore < awayScore)
                {
                    awayPts = 2;
                    homePts = 0;
                    models.LeagueTable.updateLeague(homeTeamID,0,1,0,homeGoalDifference,0);
                    models.LeagueTable.updateLeague(awayTeamID,1,0,0,awayGoalDifference,3);
                    fixtureCurrent.get(i).save();
                }
                else{
                    awayPts = 0;
                    homePts = 0;
                    models.LeagueTable.updateLeague(homeTeamID,0,0,1,homeGoalDifference,1);
                    models.LeagueTable.updateLeague(awayTeamID,0,0,1,awayGoalDifference,1);
                    fixtureCurrent.get(i).save();
                }
            }
        
        }
    }

// Authenticate user needs to be added start of each method to be secured
    @Security.Authenticated(Secured.class)
    public Result fixtures() {
        List<Fixtures> fixture = Fixtures.findAll();
        List<Team> teams = Team.findAll();
        Collections.sort(fixture);
        return ok(fixtures.render(User.getLoggedIn(session().get("loginname")),fixture,teams));
    }

     // Authenticate user needs to be added start of each method to be secured
    @Security.Authenticated(Secured.class)
    public Result upload(){
		 List<Team> teams = Team.findAll();
		if(teams.size()%2!=0)
        {
        generateFixtures();
		flash("success", "Fixtures have been generated  " ); 
		
		}else{
			flash("success", "Odd number of teams please add a team  " ); 
		}
		return redirect("/fixtures");
     
    }
    
    public static void generateFixtures(){
        //needs locked to admin only .
        for(Fixtures f : Fixtures.<Fixtures>findAll()) {
            f.delete();
        }
        MatchCtrl.setCurWeek(1);

        
		
		ArrayList<models.Team> teams = new ArrayList() ;
		
		long id = 2;
		int week = 1;
		int hScore=0;
		int aScore=0;
		models.Fixtures f1 ;

		 for(Team t : Team.<Team>findAll()) {
		     if(t.getTeamID() != 0)
			{
		      teams.add(t);
		    }
		}
		
		
		models.Fixtures[] weekFixtures = new models.Fixtures[teams.size()];

		ArrayList<models.Fixtures> fixtures = new ArrayList();
		for(int i = 0;i < teams.size();i++)
		{
			for(int j = i+1; j < teams.size();j++)
			{

			//long MatchID , String leagueName, int week, long homeTeamID , int homeScore,long awayTeamID,int awayScore
				f1=new models.Fixtures(id,week,teams.get(i).getTeamID(),hScore,teams.get(j).getTeamID(),aScore);

				 	teams.get(i).flist.add(f1);
				 	teams.get(j).flist.add(f1);
				 	f1.tList.add(teams.get(i));
				 	f1.tList.add(teams.get(j));
				 	f1.save();
				    f1=new models.Fixtures(id,(week+teams.size()*2),teams.get(j).getTeamID(),hScore,teams.get(i).getTeamID(),aScore);
				 	teams.get(i).flist.add(f1);
				 	teams.get(j).flist.add(f1);
				 	f1.tList.add(teams.get(i));
				 	f1.tList.add(teams.get(j));
				 	f1.save();

                    if(week == ((teams.size()*2)))
                    {
                    week=1;
                    }
                    else
                    {
                    week++;
                    }
			 }    

			
			id++;
		}
		
	}
}    