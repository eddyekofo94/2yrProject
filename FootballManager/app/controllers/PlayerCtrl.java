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
    private User getCurrentUser() {
        User u = User.getLoggedIn(session().get("userid"));
        return u;
    }

    //Insures user is manager before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfManager.class)
    public Result squad(Long position) {
        List<Player> players = Player.findAll();//Creates a list of players
        List<Position> positions = Position.find.where().orderBy("position asc").findList();//Creates a list of positions

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
        List<Position> positions = Position.findAll(); //creates a list of positions
        //Creates a list of players
        List<Player> players = Player.findAll();
        if (positionCount(getTeam(teamID), position) == false) {//checks if there are too may players on the field max 11                 
            return redirect("/squad/0");//returns to squad page and flashes an apropriate error messages
        } else {
            for (Player p : players) { //loops through all the players in the list

                if (p.playerID == pID) {//Insures the player changed matches the player selected by the user

                    for (int i = 0; i < positions.size(); i++) {//loops through all the positions in the list
                        if (positions.get(i).position.equals(position)) {//Mathces the position to a position in the list to get the correct position
                            p.setPosition(positions.get(i)); // Sets the players position to the new position
                            p.update(); //update the player in the database
                            flash("success", "Player has changed position to " + position + "!"); //return this flash message
                            return redirect("/squad/" + positions.get(i).getPositionID());//return to the squad page showing players in the new position
                        }
                    }
                }

            }

        }
        flash("error", "Player has not been changed position to " + position + "!"); //return this flash message
        return redirect("/squad/0");//returns to squad page and flashes an apropriate error messages

    }
    //Counts number of players in each position and on the field
    public boolean positionCount(Team team, String position) {
        boolean output = true;//Default output
        List<Player> playerList = Player.find.all();//creates a list of players
        int[] posCount = {0, 0, 0, 0, 0, 0}; //intilizing an array of integers for the counts
        final int MAX_ON_FIELD = 11; //Max amount allowed on the field
        final int MAX_TEAM = 15; //Max amount allowed on a team
        int totalOnfield = 0; //Count total on field variable
        int totalOnteam = 0; //Count total on team variable
        for (int j = 0; j < playerList.size(); j++) { //Loops through playerList
            if (team.getTeamID() == playerList.get(j).getTID()) {//Makes sure player is on the users team
                if (playerList.get(j).getPosition() == 0) {//Insure player is not assigned a position
                    posCount[0]++; //Increases posCount for not assigned by one
                }
                if (playerList.get(j).getPosition() == 1) {//Insure player is a Goalkeeper
                    posCount[1]++;//Increases posCount for Goalkeeper by one
                }
                if (playerList.get(j).getPosition() == 2) {//Insure player is a Defense
                    posCount[2]++;//Increases posCount for Defense by one
                }
                if (playerList.get(j).getPosition() == 3) {//Insure player is a Midfield
                    posCount[3]++;//Increases posCount for Midfield by one
                }
                if (playerList.get(j).getPosition() == 4) {//Insure player is a Striker
                    posCount[4]++;//Increases posCount for Striker by one
                }
                if (playerList.get(j).getPosition() == 5) {//Insure player is a Sub
                    posCount[5]++;//Increases posCount for Sub by one
                }

            }
        }

        if (posCount[1] >= 1 && position.equals("Goalkeeper")) {
            flash("error", "Sorry you alread have a Goalkeeper!");
            return false;
        }

        if (posCount[2] >= 4 && position.equals("Defense")) {
            flash("error", "Sorry you already have enough players in position Defense!");
            return false;
        }

        if (posCount[3] >= 4 && position.equals("Midfield")) {
            flash("error", "Sorry you already have enough players in position Midfield!");
            return false;
        }

        if (posCount[4] >= 2 && position.equals("Striker")) {
            flash("error", "Sorry you already have enough players in position Striker!");
            return false;
        }

        totalOnfield = posCount[1] + posCount[2] + posCount[3] + posCount[4];
        if (totalOnfield > MAX_ON_FIELD) {
            flash("error", "Sorry too many players on the field!");
            return false;
        }
        totalOnteam = totalOnfield + posCount[5];
        if (totalOnteam > MAX_TEAM) {
            flash("error", "Sorry you already have enough players! If you want an new one please sell one of your players");
            return false;
        }

        return output;
    }

    public Team getTeam(Long teamID) {
        Team defualtTeam = new Team();
        List<Team> teams = Team.findAll();
        for (Team t : teams) {
            if (t.teamID == teamID) {
                return t;
            }
        }
        return defualtTeam;
    }

    public Result getTrained(String position, Long pID) {

        //Creates a list of players
        List<Player> players = Player.findAll();
        if (user.getNumOfTrain() > minTrainingTest) {
            //train value earned to be added to position value
            int randomTrainVal = ranNum.nextInt(5) + 1;
            for (Player p : players) {
                if (p.playerID == pID) {
                    if (playerMaxed(position, p) == true) {
                        flash("error", "Player is already max in position " + position);
                        return redirect("/squad/20");
                    } else if (randomTrainVal <= 2) {
                        user.numberOfTraining--;
                        flash("error", "Sorry could not train player this time please try again");
                        return redirect("/squad/0");
                    } else if (randomTrainVal <= 4) {
                        addTrainVal(position, randomTrainVal, p);
                        deductHealth(ranNum.nextInt(2), p);;
                        p.injury = getInjured(p.health, p);
                        user.numberOfTraining--;
                        p.update();
                        flash("success", "Player trained!");
                        return redirect("/squad/0");
                    } else {
                        addTrainVal(position, randomTrainVal, p);
                        deductHealth(ranNum.nextInt(3), p);
                        p.injury = getInjured(p.health, p);
                        user.numberOfTraining--;
                        p.update();
                        flash("success", "Player trained!");
                        return redirect("/squad/0");
                    }

                }
                p.calcTransValue();
                p.update();
            }

        } else {
            flash("error", "Sorry you have used all of your training for this match!");
            return redirect("/squad/0");
        }
        return redirect("/squad/0");
    }

    public boolean getInjured(int health, Player player) {

        if (player.health <= INJURY_LEVEL) {
            return true;
        } else {
            return false;
        }
    }

    public void addTrainVal(String position, int trainVal, Player player) {
        final int MAX_VAL = 10;
        int overMaxVal;
        int positionVal = getPositionVal(position, player);
        if ((positionVal + trainVal) <= MAX_VAL) {
            setPositionVal(trainVal, position, player);
        } else {
            if ((positionVal + trainVal) > MAX_VAL) {
                overMaxVal = (positionVal + trainVal) - MAX_VAL;
                setPositionVal((trainVal - overMaxVal), position, player);
            }
        }

    }

    public int getPositionVal(String position, Player player) {

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

    public void setPositionVal(int trainVal, String position, Player player) {

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

    public void deductHealth(int healthLose, Player player) {
        int negativeHealth;
        if ((player.health - healthLose) < 0) {
            negativeHealth = player.health - healthLose;
            player.health = healthLose + negativeHealth;
        } else {
            player.health -= healthLose;
        }
    }

    public void playerHealthyTest(Player player) {

        if (player.health > 4 && player.health < MAX_HEALTH) {
            player.injury = false;
        } else {
            player.injury = true;
        }
    }

    public boolean playerMaxed(String position, Player player) {

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

    public boolean checkHealthMax(int health) {
        if (health == MAX_HEALTH) {
            return true;
        } else {
            return false;
        }
    }

    public void calculateInjuredHealthIncrease(Player player) {
        int healthIncrease = ranNum.nextInt(3);
        int positiveHealth;

        if ((player.health + healthIncrease) > MAX_HEALTH) {
            positiveHealth = player.health + healthIncrease;
            healthIncrease = MAX_HEALTH - player.health;
            player.health += healthIncrease;
        } else {
            player.health += healthIncrease;
        }
    }

    public static void randomStats() {
        Random rand = new Random();
        int count = 0;
        //get all the teams
        List<Team> teams = models.Team.findAll();
        //Creates a list of players
        List<Player> players = Player.findAll();
      //for each of the teams assign all the players random stats

        for (int i = 0; i < teams.size(); i++) {
            for (int j = 0; j < players.size(); j++) {
                if (teams.get(i).getTeamID() == players.get(j).getTeamID().getTeamID()) {
      			//11 players in team 1 gk 4 def 4 mid 2 attk guarenteed rest is random
                    //set goalkeeper

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

    public static void genPlayerStat(Player player) {
        Random rand = new Random();
        player.setGkVal(rand.nextInt(10) + 1);
        player.setDefVal(rand.nextInt(10) + 1);
        player.setMidVal(rand.nextInt(10) + 1);
        player.setAtkVal(rand.nextInt(10) + 1);
        player.calcTransValue();

    }

    public Result genStats() {

        randomStats();

        return redirect("/playerDB");
    }

    public Result buyPlayer(Long id, Long userid) {

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

                                if (owner.getBankaccount() >= players.get(i).getTransferValue()) {

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

    public Long getPositionID(String position) {
        long value = 0;
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
    public Result playerDB(Long position) {
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
    public Result transferPlayer(Long id) {
        //Creates a list of players
        List<Player> players = Player.findAll();
        Form<Player> transferPlayerForm = Form.form(Player.class).bindFromRequest();
        Team transfer = Team.getTeamDefault();

        Long ids = transfer.getTeamID();

        for (int i = 0; i < players.size(); i++) {
            if (id == players.get(i).getPlayerID()) {

                players.get(i).setTeamID(transfer);

            }

        }

        return ok(transferPlayer.render(User.getLoggedIn(session().get("loginname")), players, transferPlayerForm, ids));

    }

    //Insures user is logged in before allowing access

    @Security.Authenticated(Secured.class)
    public Result transferPlayerSubmit(Long id) {
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
    public Result delPlayer(Long playerID) {
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
    public Result editPlayer(Long playerID) {
        //Creates a list of players
        List<Player> players = Player.findAll();

        for (int i = 0; i < players.size(); i++) {
            if (players.get(i).getPlayerID() == playerID) {
                Form<Player> editPlayerForm = Form.form(Player.class).fill(players.get(i));
                return ok(editPlayer.render(User.getLoggedIn(session().get("loginName")), editPlayerForm, players.get(i)));

            }

        }
        return redirect("/delPlayer");

    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result submitEditPlayer(Long id) {
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
    public Result deletePlayer() {
        //Creates a list of players
        List<Player> players = Player.findAll();
        return ok(delPlayer.render(User.getLoggedIn(session().get("loginName")), players));
    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result addPlayer() {
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(User.getLoggedIn(session().get("loginName")), addPlayerForm));
    }

    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result addPlayerSubmit() {
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
        Player newPlayer;

        if (newPlayerForm.hasErrors()) {
            return badRequest(addPlayer.render(User.getLoggedIn(session().get("loginName")), newPlayerForm));

        }
        newPlayer = newPlayerForm.get();

        newPlayer.setPosition(Position.getPositionNone());
        newPlayer.setTeam(Team.getTeamDefault());
        PlayerCtrl.genPlayerStat(newPlayer);
        newPlayer.save();
        flash("success", "Player " + newPlayerForm.get().playerName + " has been created");
        return redirect("/playerDB");
    }
}
