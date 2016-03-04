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

        return ok(index.render(User.getLoggedIn(session().get("userID"))));
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
        return ok(fixtures.render(fixture, User.getLoggedIn(session().get("userID"))));
    }

    public Result leagueTable() {

        return ok(leagueTable.render(User.getLoggedIn(session().get("userID"))));
    }
    public Result upload(){

    uploadFixtures();
    List<Fixtures> fixture = Fixtures.findAll();
 return ok(fixtures.render(fixture, User.getLoggedIn(session().get("userID"))));

}

    public Result squad(Long position) {
        
        List<Position> positions = Position.find.where().orderBy("position asc").findList();
        // get the list of team attributes
        List<Player> players = new ArrayList<Player>();
        Team team;

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
        return ok(squad.render(positions, players,team, User.getLoggedIn(session().get("userID"))));
    }
    
    public Result login() {

        return ok(login.render(User.getLoggedIn(session().get("userID"))));
    }
    
    public Result register() {
        
            Form<User> registerForm = Form.form(User.class);

        return ok(register.render(registerForm);
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
        return ok(playerDB.render(positions, players));
    }
     public Result addPlayer(){
        Form<Player> addPlayerForm = Form.form(Player.class);
        return ok(addPlayer.render(addPlayerForm));
    }
    public Result addPlayerSubmit(){
        Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();
        
        if(newPlayerForm.hasErrors()){
            return badRequest(addPlayer.render(newPlayerForm));
            
        }
        newPlayerForm.get().save();
        flash("Success", "Player "+ newPlayerForm.get().playerName+" has been created");
        
        return redirect("/");
    }

}
