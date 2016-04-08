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
import java.util.*;



import views.html.*;


import models.*;

public class Application extends Controller {

      private User getCurrentUser() {
        User u = User.getLoggedIn(session().get("userid"));
        return u;
    }

    public Result index() {

        return ok(index.render(User.getLoggedIn(session().get("loginname"))));
    }
// Authenticate user needs to be added start of each method to be secured
@Security.Authenticated(Secured.class)
// Authorise user (check if user)
//needs to be configed to admin and manager.
@With(CheckIfCustomer.class)
    public Result fixtures() {

List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();
        Collections.sort(fixture);
        return ok(fixtures.render(fixture,teams, User.getLoggedIn(session().get("loginName"))));
    }

    public Result leagueTable() {
    List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();
        startLeague(teams);
        updateLeague();




        return ok(views.html.leagueTable.render(fixture,teams,models.LeagueTable.getLeague(), User.getLoggedIn(session().get("loginName"))));
    }


    public Result upload(){

generateFixtures();

List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();

Collections.sort(fixture);

return ok(fixtures.render(fixture,teams, User.getLoggedIn(session().get("loginName"))));
}

public Result LeagueUpdate(){
updateLeague();
List<Fixtures> fixture = Fixtures.findAll();



            List < Team > teams = Team.findAll();
return ok(views.html.leagueTable.render(fixture,teams,models.LeagueTable.getLeague(), User.getLoggedIn(session().get("loginName"))));

}

 public static void generateFixtures(){


//the following statment is destructive and needs validation and admin only .

for(Fixtures f : Fixtures.<Fixtures>findAll()) {
    f.delete();
}
     MatchCtrl.setCurWeek(1);






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



				f1=new models.Fixtures(id,"bing",(week+teams.size()+1),teams.get(j).getTeamID(),hScore,teams.get(i).getTeamID(),aScore);

				 	teams.get(i).flist.add(f1);
				 	teams.get(j).flist.add(f1);
				 	f1.tList.add(teams.get(i));
				 	f1.tList.add(teams.get(j));

				 	f1.save();






				if(week == ((teams.size()+1)))
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
                }
            }
        }

        return ok(squad.render(positions, players,team, User.getLoggedIn(session().get("loginName"))));
    }

    public Result login() {

        return ok(login.render(Form.form(Login.class),User.getLoggedIn(session().get("loginName"))));
    }


    // public Result authenticate(){
    //     Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

    //     if(loginForm.hasErrors()){
    //         return badRequest(login.render(loginForm,User.getLoggedIn(session().get("loginName"))));
    //     }
    //     else{
    //         session().clear();
    //         session("userid", loginForm.get().userid);
    //         return redirect(routes.Application.index());
    //     }
    // }

    public Result register() {

        Form<Manager> registerForm = Form.form(Manager.class);

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

    public Result transferPlayer(Long position,Long id) {
        Form<Player> transferPlayerForm = Form.form(Player.class);
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
        return ok(transferPlayer.render(User.getLoggedIn(session().get("loginName")),positions, players,transferPlayerForm,id));
    }

    public Result transferPlayerSubmit(Long id){
        Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();

        if(transferPlayerForm.hasErrors()){
            return redirect("/squad/0");

        }
        int pID = 0;
        for(int i =0 ; i < players.size();i++){
            if(id == (i+1)){
                pID = i;
            }
        }
        Player p = transferPlayerForm.get();
        p.teamID = players.get(pID).getTeamID();

        p.update();
        flash("Success", "Player "+ transferPlayerForm.get().playerName+" has added to your team");

        return redirect("/squad/0");
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
