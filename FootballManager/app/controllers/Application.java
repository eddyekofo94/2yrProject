package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;

//fixture upload imports
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;

import play.*;


import views.html.*;
import models.*;
 public class Application extends Controller {

      public Result index() {

        return ok(index.render(User.getLoggedIn(session().get("loginName"))));
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
        return ok(fixtures.render(fixture, User.getLoggedIn(session().get("loginName"))));
    }

    public Result leagueTable() {

        return ok(leagueTable.render(User.getLoggedIn(session().get("loginName"))));
    }
    public Result upload(){

    
    List<Fixtures> fixture = Fixtures.findAll();
 return ok(fixtures.render(fixture, User.getLoggedIn(session().get("loginName"))));

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
