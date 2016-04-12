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

    public Result fixtures() {

List<Fixtures> fixture = Fixtures.findAll();
List<Team> teams = Team.findAll();
        Collections.sort(fixture);
        return ok(fixtures.render(User.getLoggedIn(session().get("loginname")),fixture,teams));
    }
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
    public Result upload(){
        generateFixtures();
        List<Fixtures> fixture = Fixtures.findAll();
        List<Team> teams = Team.findAll();
        Collections.sort(fixture);
        return ok(fixtures.render(User.getLoggedIn(session().get("loginname")),fixture,teams));
    }
    // Authenticate user needs to be added start of each method to be secured
    @Security.Authenticated(Secured.class)
    public Result LeagueUpdate(){
        updateLeague();
        List<Fixtures> fixture = Fixtures.findAll();
        List < Team > teams = Team.findAll();
        return ok(views.html.leagueTable.render(User.getLoggedIn(session().get("loginname")),fixture,teams,models.LeagueTable.getLeague()));
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
		     if(t.getTeamID() != 0)
			{
		      teams.add(t);
		    }
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
        else
        {
        break;
        }

        }

    }
    //Insures user is manager before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfManager.class)
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
        return ok(squad.render(User.getLoggedIn(session().get("loginname")),positions, players,team));
    }

    public Result login() {

        return ok(login.render(Form.form(Login.class),User.getLoggedIn(session().get("loginname"))));
    }


    public Result register() {

        Form<User> registerForm = Form.form(User.class);

        return ok(register.render(User.getLoggedIn(session().get("loginName")),registerForm));
    }
    public Result registerFormSubmit() {

        return ok("user registered");
    }
    
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
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

    //Insures user is logged in before allowing access
	@Security.Authenticated(Secured.class)
	public Result transferPlayer(Long id) {
		
		Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
		List<Player> players = Player.findAll();
		List<User> managerList = Manager.find.all();
		Manager owner;
		Team transfer = Team.getTeamDefault();
		Team userTeam;
		 
		Long ids = transfer.getTeamID();
		
		for(int i = 0 ; i < players.size();i++)
		{
			if(id == players.get(i).getPlayerID())
			{
				userTeam=players.get(i).getTeamID();
				
				for(int j = 0 ; j < managerList.size();j++)
				{
					if(managerList.get(j).getid() == userTeam.getUserID())
					{
						owner = (Manager)managerList.get(j);
				owner.setBankaccount(players.get(i).getTransferValue());
				players.get(i).setTeam(transfer);
				
				
						
					}
					managerList.get(i).update();
				}
				players.get(i).update();
			}
			
		}
		
		return ok(transferPlayer.render(User.getLoggedIn(session().get("loginname")),players,transferPlayerForm,ids));
		
	}
    //Insures user is logged in before allowing access
	@Security.Authenticated(Secured.class)
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
        //p.teamID = user.teamID;

        p.update();
        flash("Success", "Player "+ transferPlayerForm.get().playerName+" has added to your team");

        return redirect("/squad/0");
    }
    
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result delPlayer(Long playerID)
	{
		List<Player> player = Player.find.all();
		Player playerToDelete;
		for(int i = 0 ; i < player.size();i++)
		{
			if(player.get(i).getPlayerID()== playerID)
			{
			 player.get(i).delete();
				
			}
		}
		 return redirect("/playerDB/0");
	}
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editPlayer(Long playerID)
	{
		List<Player> player = Player.find.all();
		
		
		for(int i = 0 ; i < player.size();i++)
		{
			if(player.get(i).getPlayerID()== playerID)
			{
			Form<Player> editPlayerForm = Form.form(Player.class).fill(player.get(i));
			 return ok(editPlayer.render(User.getLoggedIn(session().get("loginName")),editPlayerForm, player.get(i)));
				
			}
			
		}
		return redirect("/delPlayer");
		 
	}
		
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)	
	public  Result submitEditPlayer(Long id)
	{
		Form<Player> editPlayerForm = Form.form(Player.class).bindFromRequest();
		List<Player> players = Player.find.all();
		Player player;
		 if(editPlayerForm.hasErrors()){
            return redirect("/");

        }
		
		player = editPlayerForm.get();
		
		for(int i = 0;i < players.size();i++)
		{
			if(players.get(i).getPlayerID()== id)
			{
				players.get(i).setPlayerName(player.playerName);
				players.get(i).setJerseyNum(player.jerseyNum);
				players.get(i).setPlayerName(player.getPlayerName());
				players.get(i).setJerseyNum(player.getJerseyNum());
				players.get(i).setAtkVal(player.getAtkVal());
				players.get(i).setDefVal(player.getDefVal());
				players.get(i).setMidVal(player.getMidVal());
				players.get(i).setGkVal(player.getGkVal());
				players.get(i).update();
			}
			
		}
		return redirect("/admin");
	}
	
	
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result deletePlayer()
	{
		 List<Player> players = new ArrayList<Player>();
       
            players = Player.findAll();
		
		return ok(delPlayer.render(User.getLoggedIn(session().get("loginName")), players));
	}
	
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
     public Result addPlayer(){
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(User.getLoggedIn(session().get("loginName")),addPlayerForm));
    }
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result addPlayerSubmit(){
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
		Player newPlayer;
		
        if(newPlayerForm.hasErrors()){
	return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginName")),newPlayerForm));

        }
		newPlayer = newPlayerForm.get();
        
		newPlayer.setPosition(Position.getPositionNone());
		newPlayer.setTeam(Team.getTeamDefault());
		PlayerCtrl.genPlayerStat(newPlayer);
		newPlayer.save();
        flash("Success", "Player "+ newPlayerForm.get().playerName+" has been created");

        return redirect("/");
    }
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	  public Result addTeam(){
        Form<Team> addTeamForm = Form.form(Team.class);
        return ok(addTeam.render(User.getLoggedIn(session().get("loginName")),addTeamForm));
    }
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result addTeamSubmit(){
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
		Team newTeam;
		newTeam = newTeamForm.get();
		newTeam.setTeamScore(0);
		newTeam.save();
		flash("Success", "Team"+newTeamForm.get().teamName+" has been created");
		return redirect("/admin");
	}
	
	public Result manageTeam()
	{
		List<Team> team = Team.find.all();
		Form<Team> manageTeamForm = Form.form(Team.class);
        return ok(manageTeam.render(User.getLoggedIn(session().get("loginName")),team));
	}
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result delTeam(Long teamID)
	{
		List<Team> team = Team.find.all();
		Team TeamToDelete;
		for(int i = 0 ; i < team.size();i++)
		{
			if(team.get(i).getTeamID() == teamID)
			{
			 team.get(i).delete();
				
			}
		}
		 return redirect("/manageTeam");
	}
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editTeam(Long teamID)
	{	 
		List<Team> team = Team.find.all();
		for(int i = 0 ; i < team.size();i++)
		{
			if(team.get(i).getTeamID() == teamID)
			{	
				Form<Team> manageTeamForm = Form.form(Team.class).fill(team.get(i));
				 return ok(manageFormTeam.render(User.getLoggedIn(session().get("loginName")),manageTeamForm,team.get(i)));
			}		
		}
		
       return redirect("/");
    }
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editTeamSubmit(Long id){
		 Form<Team> manageTeamForm = Form.form(Team.class).bindFromRequest();
		 List<Team> team = Team.find.all();
		Team editTeam;
		 if( manageTeamForm.hasErrors()){
            return redirect("/");
        }
		editTeam = manageTeamForm.get();
		for(int i = 0;i < team.size();i++)
		{
			if(team.get(i).getTeamID() == id)
			{
				team.get(i).setTeamName(editTeam.getTeamName());
				team.get(i).setUserID(editTeam.getUserID());
				team.get(i).setTeamScore(0);
				team.get(i).update();
			}
			
         
         
		}
         		
		
		flash("Success", "Team"+manageTeamForm.get().teamName+" has been updated");
		return redirect("/admin");
	}
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result manageTeamSubmit(){
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
		Team newTeam;
		newTeam = newTeamForm.get();
		newTeam.setTeamScore(0);
		newTeam.save();
		flash("SuccessTeam", "Team"+newTeamForm.get().teamName+" has been created");
		return redirect("/admin");
	}
}
