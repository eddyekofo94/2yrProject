package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;
import java.sql.*;

import play.db.*;





import views.html.*;
import models.*;
 public class Application extends Controller {

      public Result index() {

        return ok(index.render(User.getLoggedIn(session().get("loginName"))));
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();
        return ok(fixtures.render(fixture,teams, User.getLoggedIn(session().get("loginName"))));
    }

    public Result leagueTable() {
    List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();

        return ok(views.html.leagueTable.render(fixture,teams,models.leagueTable.getLeague(), User.getLoggedIn(session().get("loginName"))));
    }
    
    
    public Result upload(){

generateFixtures();

List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();

startLeague(teams);

return ok(fixtures.render(fixture,teams, User.getLoggedIn(session().get("loginName"))));
}

public Result LeagueUpdate(){
updateLeague();
List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();
return ok(views.html.leagueTable.render(fixture,teams,models.leagueTable.getLeague(), User.getLoggedIn(session().get("loginName"))));

}
   
 public static void generateFixtures(){
 

//the following statment is destructive and needs validation and admin only .

for(Fixtures f : Fixtures.<Fixtures>findAll()) {
    f.delete();
}





 //to here
      
 
      
		ArrayList<models.Team> teams = new ArrayList() ;
		int count = 1;
		long id = 2;
		int week = 1;
		int hScore=0;
		int aScore=0;
		models.Fixtures f1 ;
		
		
		
		
		     for(Team t : Team.<Team>findAll()) {
		     teams.add(t);
    		    
		}
		
		
		models.Fixtures[] weekFixtures = new models.Fixtures[teams.size()];
		
		
		ArrayList<models.Fixtures> fixtures = new ArrayList();
		
		for(int i = 0;i < teams.size();i++)
		{
			for(int j = count; j < teams.size();j++)
			{
			
			//long MatchID , String leagueName, int week, long homeTeamID , int homeScore,long awayTeamID,int awayScore
				f1=new models.Fixtures(id,"bing",week,teams.get(i).getTeamID(),hScore,teams.get(j).getTeamID(),aScore);
				 	
				 	teams.get(i).flist.add(f1);
				 	teams.get(j).flist.add(f1);
				 	f1.tList.add(teams.get(i));
				 	f1.tList.add(teams.get(j));
				 	f1.save();
				
				
				//System.out.println("week"+week+"Home Team: "+teams[j]+"Score"+hScore +"vs"+"Away Team "+"Score"+aScore+teams[i]);
				
				
				
				f1=new models.Fixtures(id,"bing",(week+teams.size()),teams.get(j).getTeamID(),hScore,teams.get(i).getTeamID(),aScore);
				 	
				 	teams.get(i).flist.add(f1);
				 	teams.get(j).flist.add(f1);
				 	f1.tList.add(teams.get(i));
				 	f1.tList.add(teams.get(j));
				 	
				 	f1.save();
				
				
				
				
				
				
				if(week == ((teams.size())))
				{
				week=1;
				}
				else
				{
				week++;
				}
			
			}
		
			count++;
			id++;
		}
		
		}
		
		
	public void startLeague(List<models.Team> teams)
	{
	
	for(int i = 0;i < teams.size();i++)
	{
	models.leagueTable league = new models.leagueTable(teams.get(i).getTeamID());
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
    
   	 for(Fixtures f : Fixtures.<Fixtures>findAll()) {
    	fixtureCurrent.add(f);
   	 }
   	 
   	 for(int i = 0 ; i < fixtureCurrent.size();i++)
   	 {
   	 if((fixtureCurrent.get(i).played ) && (fixtureCurrent.get(i).added == false) )
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
   	 
   	 models.leagueTable.updateLeague(homeTeamID,1,0,0,homeGoalDifference,3);
   	 models.leagueTable.updateLeague(awayTeamID,0,1,0,awayGoalDifference,0);
   	 fixtureCurrent.get(i).added = true;
   	 fixtureCurrent.get(i).save();
   	 }
   	 else if (homeScore < awayScore)
   	 {
   	 awayPts = 2;
   	 homePts = 0;
   	 models.leagueTable.updateLeague(homeTeamID,0,1,0,homeGoalDifference,0);
   	 models.leagueTable.updateLeague(awayTeamID,1,0,0,awayGoalDifference,3);
   	 fixtureCurrent.get(i).added = true;
   	 fixtureCurrent.get(i).save();
   	 }
   	 else{
   	 awayPts = 0;
   	 homePts = 0;
   	 models.leagueTable.updateLeague(homeTeamID,0,0,1,homeGoalDifference,1);
   	 models.leagueTable.updateLeague(awayTeamID,0,0,1,awayGoalDifference,1);
   	 fixtureCurrent.get(i).added = true;
   	 fixtureCurrent.get(i).save();
   	 }
   	 }
   	 else
   	 {
   	 break;
   	 }
   	 
   	 
   	 
   	 
   	 
   	 
   	 
   	 }
    
    }
		

    public Result squad(Long position) {
        
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();
        List<Team> teams = Team.findAll();
        List<User> users = User.findAll();
        Team team = new Team();

        
        if(position == 0){
            players = Player.findAll();
        }
        else{
            for(int i = 0; i< positions.size();i++){
                if(positions.get(i).id == position){
                    players = positions.get(i).players;
                    break;
                }
            }
        }
        
        return ok(squad.render(positions, players,team, User.getLoggedIn(session().get("loginName"))));
    }
    
    public Result login() {

        return ok(login.render(Form.form(Login.class),User.getLoggedIn(session().get("loginName"))));
    }
    
    public Result authenticate(){
        Form<Login> loginForm = Form.form(Login.class).bindFromRequest();
        
        if(loginForm.hasErrors()){
            return badRequest(login.render(loginForm,User.getLoggedIn(session().get("loginName"))));
        }
        else{
            session().clear();
            session("userID", loginForm.get().userID);
            return redirect(routes.Application.index());
        }
    }
    
    public Result register() {
        
            Form<User> registerForm = Form.form(User.class);

        return ok(register.render(User.getLoggedIn(session().get("loginName")),registerForm));
    }
       public Result registerFormSubmit() {

         return ok("user registered");
     }
     
     public Result playerDB(Long position) {
        
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();
        if(position == 0){
            players = Player.findAll();
        }
        else{
            for(int i = 0; i< positions.size();i++){
                if(positions.get(i).id == position){
                    players = positions.get(i).players;
                    break;
                }
            }
        }
        return ok(playerDB.render(User.getLoggedIn(session().get("loginName")),positions, players));
    }
     public Result addPlayer(){
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(User.getLoggedIn(session().get("loginName")),addPlayerForm));
    }
    public Result addPlayerSubmit(){
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
        
        if(newPlayerForm.hasErrors()){
            return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginName")),newPlayerForm));
            
        }
        newPlayerForm.get().save();
        flash("Success", "Player "+ newPlayerForm.get().playerName+" has been created");
        
        return redirect("/");
    }

}
