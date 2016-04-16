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

import models.users.*;
import play.*;

import views.html.*;
import models.*;

public class PlayerCtrl extends Controller {

    //variable to check if numberOfTraining = 0
    final int minTrainingTest = 0;
    //injury health value once reached player is injured
    final int INJURY_LEVEL = 4;
    //Max value for health
    final int MAX_HEALTH = 10;
    //Max attribute value
    final int MAX_VALUE = 10;
    //Max amount of players per team
    private final int MAX_PLAYERS = 15;
    //Max amount of players on field
    private final int MAX_ON_FIELD = 11;
    //Max amount of goalkeepers/goalkeeper position ID
    private final int MAX_GOALKEEPER = 1;
    //Max amount of Defenders
    private final int MAX_DEFENSE = 4;
    //Max amount of Midfielders
    private final int MAX_MIDFIELD = 4;
    //Max amount of Strikers
    private final int MAX_STRIKER = 2;
    User user = this.user;
    //Count for amount of players in positions
    int countPlayers;
    //random number generator
    Random ranNum = new Random();

    //A method to find the current user of the system
    private User setUser(Long userID) {
        User user = this.user;
        List<User> users = User.findAll();
        for(User u : users){
            if(u.getid() == userID){
                user = u;
            }
        }
        return user;
    }

    //Insures user is manager before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfManager.class)
    public Result squad(Long position) {
        List<Player> players = Player.findAll();
        List<Position> positions = Position.find.where().orderBy("position asc").findList();

        if (position == 0) {
            players = Player.findAll();
        } else {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).id == position) {
                    players = positions.get(i).players;
                }
            }
        }
        return ok(squad.render(User.getLoggedIn(session().get("loginname")), positions, players));
    }

    //sets the position of a player specified by the user
    public Result setPosition(String position, Long pID, Long teamID) {
        List<Position> positions = Position.findAll(); 
        List<Player> players = Player.findAll();
        if (positionCount(getTeam(teamID), position) == false) {            
            return redirect("/squad/0");
        } else {
            for (Player p : players) { 
                if (p.playerID == pID) {//Insures the player changed matches the player selected by the user

                    for (int i = 0; i < positions.size(); i++) {
                        if (positions.get(i).position.equals(position)) {//Mathces the position to a position in the list to get the correct position
                            p.setPosition(positions.get(i)); // Sets the players position to the new position
                            p.update(); //update the player in the database
                            flash("success", "Player has changed position to " + position + "!"); 
                            return redirect("/squad/" + positions.get(i).getPositionID());
                        }
                    }
                }

            }

        }
        flash("error", "Player has not been changed position to " + position + "!"); 
        return redirect("/squad/0");

    }
    //Counts number of players in each position and on the field
    public boolean positionCount(Team team, String position){
        boolean output = true;
        List<Player> playerList = Player.find.all();//creates a list of players
        int[] posCount = {0, 0, 0, 0, 0, 0}; //intilizing an array of integers for the counts
        final int MAX_ON_FIELD = 11; //Max amount allowed on the field
        final int MAX_TEAM = 15; //Max amount allowed on a team
        int totalOnfield = 0; //Count total on field variable
        int totalOnteam = 0; //Count total on team variable
        for (int j = 0; j < playerList.size(); j++) { 
            if (team.getTeamID() == playerList.get(j).getTID()) {//Makes sure player is on the users team
                if (playerList.get(j).getPosition() == 0) {//Insure player is not assigned a position
                    posCount[0]++; 
                }
                if (playerList.get(j).getPosition() == 1) {//Insure player is a Goalkeeper
                    posCount[1]++;
                }
                if (playerList.get(j).getPosition() == 2) {//Insure player is a Defense
                    posCount[2]++;
                }
                if (playerList.get(j).getPosition() == 3) {//Insure player is a Midfield
                    posCount[3]++;
                }
                if (playerList.get(j).getPosition() == 4) {//Insure player is a Striker
                    posCount[4]++;
                }
                if (playerList.get(j).getPosition() == 5) {//Insure player is a Sub
                    posCount[5]++;
                }

            }
        }

        if (posCount[1] >= 1 && position.equals("Goalkeeper")) {//If Goalkeeper posCount is > 1 and position = Goalkeerer
            flash("error", "Sorry you alread have a Goalkeeper!");
            return false;
        }

        else if (posCount[2] >= 4 && position.equals("Defense")) {//If Defense posCount is > 1 and position = Defense
            flash("error", "Sorry you already have enough players in position Defense!");
            return false;
        }

        else if (posCount[3] >= 4 && position.equals("Midfield")) {//If Midfield posCount is > 1 and position = Midfield
            flash("error", "Sorry you already have enough players in position Midfield!");
            return false;
        }

        else if (posCount[4] >= 2 && position.equals("Striker")) {//If Striker posCount is > 1 and position = Striker
            flash("error", "Sorry you already have enough players in position Striker!");
            return false;
        }
        else{ 

            totalOnfield = posCount[1] + posCount[2] + posCount[3] + posCount[4]; //Add up all the players on the pitch 
            if (totalOnfield > MAX_ON_FIELD) { 
                flash("error", "Sorry too many players on the field!");
                return false;
            }
            totalOnteam = totalOnfield + posCount[5]; //Adds up the amount of players on a team 
            if (totalOnteam > MAX_TEAM) { 
                flash("error", "Sorry you already have enough players! If you want an new one please sell one of your players");
                return false;
            }
        }    
        return output;
    }

    public Team getTeam(Long teamID) {//Gets the team that matches the ID passed in
        Team defualtTeam = new Team();
        List<Team> teams = Team.findAll();
        for (Team t : teams) { 
            if (t.teamID == teamID) { // matches a team to the team ID passed in
                return t; 
            }
        }
        return defualtTeam; 
    }

    public Result getTrained(String position, Long pID, Long userID) { //Increases a players statistic for a certain position
        User user = setUser(userID); //sets a variable of user to the one logged in
        //Creates a list of players
        List<Player> players = Player.findAll();
        if (user.getNumOfTrain() > minTrainingTest) { //Checks the user still has trains left to use MAX 3
            //train value earned to be added to position value
            int randomTrainVal = ranNum.nextInt(5) + 1;
            for (Player p : players) {
                if (p.playerID == pID) {
                    if (playerMaxed(position, p) == true) { //If position chosen is maxed to 10
                        flash("error", "Player is already max in position " + position);
                        return redirect("/squad/20");
                    } else if (randomTrainVal <= 2) { // dont train
                        user.numberOfTraining--;
                        user.update();
                        flash("error", "Sorry could not train player this time please try again");
                        return redirect("/squad/0");
                    } else if (randomTrainVal <= 4) { //train by small amount between 1 and 2
                        addTrainVal(position, randomTrainVal, p);
                        deductHealth(ranNum.nextInt(2), p);;
                        p.injury = getInjured(p.health, p);
                        user.numberOfTraining--;
                        user.update();
                        p.update();
                        flash("success", "Player trained!");
                        return redirect("/squad/0");
                    } else { //Train by the max amount no more than 3
                        addTrainVal(position, randomTrainVal, p);
                        deductHealth(ranNum.nextInt(3), p);
                        p.injury = getInjured(p.health, p);
                        user.numberOfTraining--;
                        user.update();
                        p.update();
                        flash("success", "Player trained!");
                        return redirect("/squad/0");
                    }

                }               
                p.calcTransValue();
                p.update();
            }

        } else { //If no more trains left
            flash("error", "Sorry you have used all of your training for this match!");
            return redirect("/squad/0");
        }
        return redirect("/squad/0");
    }

    public boolean getInjured(int health, Player player) {//Checks if player is injured

        if (player.health <= INJURY_LEVEL) {
            return true;
        } else {
            return false;
        }
    }

    public void addTrainVal(String position, int trainVal, Player player) {//Adds the amount gained from training
        final int MAX_VAL = 10;
        int overMaxVal;
        int positionVal = getPositionVal(position, player);
        if ((positionVal + trainVal) <= MAX_VAL) {
            setPositionVal(trainVal, position, player);//Sets the new position
        } else {
            if ((positionVal + trainVal) > MAX_VAL) { //Insures value isnt greater then MAX_VAL
                overMaxVal = (positionVal + trainVal) - MAX_VAL;
                setPositionVal((trainVal - overMaxVal), position, player);//Sets the new position
            }
        }

    }

    public int getPositionVal(String position, Player player) {//Gets the value for a players specified position

        if (position.equals("Goalkeeper")) {
            return player.gkVal;
        } else if (position.equals("Defense")) {
            return player.defVal;
        } else if (position.equals("Midfield")) {
            return player.midFVal;
        } else {
            return player.attVal;
        }
    }

    public void setPositionVal(int trainVal, String position, Player player) { //Sets a players position value

        if (position.equals("Goalkeeper")) {
            player.setGkVal(player.gkVal + trainVal);

        } else if (position.equals("Defense")) {
            player.setDefVal(player.defVal + trainVal);
        } else if (position.equals("Midfield")) {
            player.setMidVal(player.midFVal + trainVal);
        } else {
            player.setAtkVal(player.attVal + trainVal);
        }

    }

    public void deductHealth(int healthLose, Player player) { //Deducts a players health based on the amount passed in
        int negativeHealth;
        if ((player.health - healthLose) < 0) {
            negativeHealth = player.health - healthLose;
            player.health = healthLose + negativeHealth;
        } else {
            player.health -= healthLose;
        }
    }

    public void playerHealthyTest(Player player) {//Checks if a player is healthy to play again

        if (player.health > 4) {
            player.injury = false;
        } else {
            player.injury = true;
        }
    }

    public boolean playerMaxed(String position, Player player) { //Checks if a players postion value is maxed

        if (player.position.equals("Goalkeeper")) {
            if (player.getGkVal() == MAX_VALUE) {
                return true;
            } else if (player.position.equals("Defense")) {
                if (player.getDefVal() == MAX_VALUE) {
                    return true;
                }
            } else if (player.position.equals("Midfield")) {
                if (player.getMidVal() == MAX_VALUE) {
                    return true;
                }
            } else if (player.position.equals("Striker")) {
                if (player.getAtkVal() == MAX_VALUE) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean checkHealthMax(int health) {//Checks if a players health is at the max amount
        if (health == MAX_HEALTH) {
            return true;
        } else {
            return false;
        }
    }

    public void calculateInjuredHealthIncrease(Player player) { //Calculates an amount to add to an injured players health 
        int healthIncrease = ranNum.nextInt(3);
        int positiveHealth;

        if ((player.health + healthIncrease) > MAX_HEALTH) {//Insures player Health doesnt go over the max
            positiveHealth = player.health + healthIncrease;
            healthIncrease = MAX_HEALTH - player.health;
            player.health += healthIncrease;
        } else {
            player.health += healthIncrease;
        }
    }

    public static void randomStats() {//Generates random position values
        Random rand = new Random();
        int count = 0;
        //get all the teams
        List<Team> teams = models.Team.findAll();
        //Creates a list of players
        List<Player> players = Player.findAll();
      //for each of the teams assign all the players random stats
        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (teams.get(i).getTeamID() == players.get(j).getTeam().getTeamID()) {//Matchs player to the correct team
      			//11 players in team 1 gk 4 def 4 mid 2 attk guarenteed rest is random
                    //set goalkeeper
                    //Insures a team has players to suit all positions
                    if (count == 0) {
                        players.get(j).setGkVal(rand.nextInt(3) + 1 + 6);
                        players.get(j).setDefVal(rand.nextInt(10) + 1);
                        players.get(j).setMidVal(rand.nextInt(10) + 1);
                        players.get(j).setAtkVal(rand.nextInt(10) + 1);
                    } else if ((count >= 1) && (count <= 4)) {
                        players.get(j).setGkVal(rand.nextInt(10) + 1);
                        players.get(j).setDefVal(rand.nextInt(3) + 1 + 6);
                        players.get(j).setMidVal(rand.nextInt(10) + 1);
                        players.get(j).setAtkVal(rand.nextInt(10) + 1);
                    } else if ((count >= 5) && (count <= 9)) {
                        players.get(j).setGkVal(rand.nextInt(10) + 1);
                        players.get(j).setDefVal(rand.nextInt(10) + 1);
                        players.get(j).setMidVal(rand.nextInt(3) + 1 + 6);
                        players.get(j).setAtkVal(rand.nextInt(10) + 1);
                    } else if ((count >= 10) && (count <= 11)) {
                        players.get(j).setGkVal(rand.nextInt(10) + 1);
                        players.get(j).setDefVal(rand.nextInt(10) + 1);
                        players.get(j).setMidVal(rand.nextInt(10) + 1);
                        players.get(j).setAtkVal(rand.nextInt(3) + 1 + 6);
                    } else {
                        players.get(j).setGkVal(rand.nextInt(10) + 1);
                        players.get(j).setDefVal(rand.nextInt(10) + 1);
                        players.get(j).setMidVal(rand.nextInt(10) + 1);
                        players.get(j).setAtkVal(rand.nextInt(10) + 1);
                    }
                    count++;
                    players.get(j).calcTransValue();
                    players.get(j).save();

                }
            }
        }
    }

    public static void genPlayerStat(Player player) {//Generate position values for only one player
        Random rand = new Random();
        player.setGkVal(rand.nextInt(10) + 1);
        player.setDefVal(rand.nextInt(10) + 1);
        player.setMidVal(rand.nextInt(10) + 1);
        player.setAtkVal(rand.nextInt(10) + 1);
        player.calcTransValue();

    }

    public Result genStats() {//Generate position values for all players
        randomStats();

        return redirect("/playerDB");
    }

    public Result buyPlayer(Long id, Long userid) {//Sets a players team ID to the one he was bought for and deducts users bank balance

        Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
        List<Player> players = Player.findAll();
        List<Team> teamList = Team.findAll();
        List<User> userList = Manager.findAll();
        Manager owner;
        Team transfer = Team.getTeamDefault();
        Team userTeam;

        for (int k = 0; k < userList.size(); k++) {
            if (userid == userList.get(k).getid()) {
                owner = (Manager) userList.get(k);
                for (int i = 0; i < players.size(); i++) {
                    if (id == players.get(i).getPlayerID()) {

                        for (int j = 0; j < teamList.size(); j++) {
                            if (teamList.get(j).getTeamID() == owner.getid()) {
                                userTeam = teamList.get(j);

                                if (owner.getBankaccount() >= players.get(i).getTransferValue()) { //Insures user can afford player

                                    players.get(i).setTeam(userTeam);
                                    owner.updateBankaccount(players.get(i).getTransferValue());
                                }
                            }
                            owner.update();
                        }
                        players.get(i).update();
                    }
                }
            }
        }
        flash("success", "You have bought a player!");
        return redirect("/squad/0");
    }

    public Long getPositionID(String position) { //Returns an positionID to match the position that was passed in
        long value = 0; // Default position of not assigned
        List<Position> positions = Position.findAll();
        for (Position p : positions) {
            if (p.position.equals(position)) {
                return p.id;
            }
        }
        return value;
    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result playerDB(Long position) {//Renders  the playerDB page
        //Creates a list of players
        List<Player> players = Player.findAll();
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        if (position == 0) {
            players = Player.findAll();
        } else {
            for (int i = 0; i < positions.size(); i++) {
                if (positions.get(i).id == position) {
                    players = positions.get(i).players;
                    break;
                }
            }
        }
        return ok(playerDB.render(User.getLoggedIn(session().get("loginname")), positions, players));
    }

    //Insures user is logged in before allowing access
    @Security.Authenticated(Secured.class)
    public Result transferPlayer(Long id) { //Renders the transfer player page
        //Creates a list of players
        List<Player> players = Player.findAll();
        Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
        Team transfer = Team.getTeamDefault();

        Long ids = transfer.getTeamID();

        for (int i = 0; i < players.size(); i++) {
            if (id == players.get(i).getPlayerID()) {

                players.get(i).setTeam(transfer);

            }

        }

        return ok(transferPlayer.render(User.getLoggedIn(session().get("loginname")), players, transferPlayerForm, ids));

    }

    //Insures user is logged in before allowing access

    @Security.Authenticated(Secured.class)
    public Result transferPlayerSubmit(Long id) { //Processes the form from the transferPlayer page and saves the result in the database
        Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        //Creates a list of players
        List<Player> players = Player.findAll();
        if (transferPlayerForm.hasErrors()) {
            return redirect("/squad/0");

        }
        int pID = 0;
        for (int i = 0; i < players.size(); i++) {
            if (id == (i + 1)) {
                pID = i;
            }
        }
        Player p = transferPlayerForm.get();
        p.update();
        flash("Success", "Player " + transferPlayerForm.get().playerName + " has added to your team");

        return redirect("/squad/0");
    }
      //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result deletePlayer() { //renders the deletePlayer page
        //Creates a list of players
        List<Player> players = Player.findAll();
        return ok(delPlayer.render(User.getLoggedIn(session().get("loginname")), players));
    }
    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result delPlayer(Long playerID) { //Removes a player from the database that the user has selected
        List<Player> player = Player.find.all();
        Player playerToDelete;
        for (int i = 0; i < player.size(); i++) {
            if (player.get(i).getPlayerID() == playerID) {
                player.get(i).delete();
            }
        }
        return redirect("/playerDB/0");
    }


    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result editPlayer(Long playerID) { //Renders the edit player page if player passed not there redirects back to the playerDB
        //Creates a list of players
        List<Player> players = Player.findAll();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerID() == playerID) {
                Form<Player> editPlayerForm = Form.form(Player.class).fill(players.get(i));
                return ok(editPlayer.render(User.getLoggedIn(session().get("loginname")), editPlayerForm, players.get(i)));

            }

        }
        return redirect("/playerDB");

    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result submitEditPlayer(Long id) { //Processes the edit player form and saves the changes to the database
        Form<Player> editPlayerForm = Form.form(Player.class).bindFromRequest();
        //Creates a list of players
        List<Player> players = Player.findAll();
        Player player;
        if (editPlayerForm.hasErrors()) {
            return redirect("/");

        }

        player = editPlayerForm.get();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerID() == id) {
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
    public Result addPlayer() { //Renders the add player page
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(User.getLoggedIn(session().get("loginname")), addPlayerForm));
    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result addPlayerSubmit() { //Processes the addPlayer form and saves the new player in the database
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
        Player newPlayer;

        if (newPlayerForm.hasErrors()) {
            return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginname")), newPlayerForm));

        }
        else if(playerNameTaken(newPlayerForm.get().playerName)==true){
            flash("error","Player name already used!");
            return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginname")), newPlayerForm));
        }
        else if(newPlayerForm.get().health > MAX_HEALTH){
            flash("error","Player health can not be more than 10!");
            return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginname")), newPlayerForm));
        }
        else{
            newPlayer = newPlayerForm.get();

            newPlayer.setPosition(Position.getPositionNone());
            newPlayer.setTeam(Team.getTeamDefault());
            PlayerCtrl.genPlayerStat(newPlayer);
            newPlayer.save();
            flash("success", "Player " + newPlayerForm.get().playerName + " has been created");
            return redirect("/playerDB");
        }
    }
    public boolean playerNameTaken(String newPlayerName){//Checks if a player name is already taken
        List<Player> players = Player.findAll();
        for(Player p : players){
            if(p.getPlayerName().equals(newPlayerName)){
                return true;
            }
        }
        return false;
    }
    
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
	public Result editTeam(Long teamID)//Renders a form based on the team selected by the admin
	{	 
		List<Team> team = Team.find.all();
		for(int i = 0 ; i < team.size();i++)
		{
			if(team.get(i).getTeamID() == teamID)
			{	
				Form<Team> manageTeamForm = Form.form(Team.class).fill(team.get(i));
				 return ok(manageFormTeam.render(User.getLoggedIn(session().get("loginname")),manageTeamForm,team.get(i)));
			}		
		}
		
       return redirect("/");
    }
     //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
	public Result editTeamNameSubmit(Long id){//Processes the
		 Form<Team> manageTeamForm = Form.form(Team.class).bindFromRequest();
		 List<Team> team = Team.find.all();
		 Team editTeam;
		 if(teamNameUsed(manageTeamForm.get().teamName) == true){//Insures two team names arent the same
            flash("error","Team name is already used please try again!");
            return redirect("/teamDB");
        }
        else{
            editTeam = manageTeamForm.get();
            for(int i = 0;i < team.size();i++)
            {
                if(team.get(i).getTeamID() == id)
                {
                    team.get(i).setTeamName(editTeam.getTeamName());
                    team.get(i).update();
                }			               
            }       		
        }
		flash("Success", "Team"+manageTeamForm.get().teamName+" has been updated");
		return redirect("/squad/0");
	}
    public boolean teamNameUsed(String name){//Insures two team names arent the same
        boolean taken = false;
        List<Team> teams = Team.findAll();
        for(int i = 0;i < teams.size(); i++){
            if(teams.get(i).teamName.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
}
