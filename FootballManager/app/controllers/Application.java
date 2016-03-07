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

        return ok(index.render());
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
        return ok(fixtures.render(fixture));
    }

    public Result leagueTable() {

        return ok(leagueTable.render());
    }
    public Result upload(){

generateFixtures();
List<Fixtures> fixture = Fixtures.findAll();
 return ok(fixtures.render(fixture));

}

    public Result squad(Long position) {
        
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
        return ok(squad.render(positions, players));
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
         return ok(squad.render(positions, players));
     }

     public Result addPlayer(){
         Form<Player> addPlayerForm = Form.form(Player.class);
         return ok(addPlayer.render(User.getLoggedIn(session().get("userID")),addPlayerForm));
     }
     public Result addPlayerSubmit(){
         Form<Player> newPlayerForm = Form.form(Player.class).bindFromRequest();

         if(newPlayerForm.hasErrors()){
             return badRequest(addPlayer.render(User.getLoggedIn(session().get("userID")),newPlayerForm));

         }
         newPlayerForm.get().save();
         flash("Success", "Player "+ newPlayerForm.get().playerName+" has been created");

         return redirect("/");
     }
    
    public Result login() {

        return ok(login.render());
    }
    
         //REGISTER!!!!!!!!!!!
    
    public Result register() {

            Form<User> registerForm = Form.form(User.class);

        return ok(register.render(registerForm));
    }


     public Result registerFormSubmit() {

         return ok("user registered");
     }



     
       //fixtures upload
 public static void generateFixtures(){
//get file data 
		long[] teams = {1,2,3,4,5,6};
		int count = 1;
		long id = 2;
		int week = 1;
		
		int hScore=0;
		int aScore=0;
		models.Fixtures f1 ;
		models.Fixtures[] weekFixtures = new models.Fixtures[teams.length-1];
		ArrayList<models.Fixtures> fixtures = new ArrayList();
		
		for(int i = 0;i < teams.length;i++)
		{
			for(int j = count; j < teams.length;j++)
			{
			
			//long MatchID , String leagueName, int week, long homeTeamID , int homeScore,long awayTeamID,int awayScore
				f1=new models.Fixtures(id,"bing",week,teams[j],hScore,teams[i],aScore);
				f1.save();
				
				
				//System.out.println("week"+week+"Home Team: "+teams[j]+"Score"+hScore +"vs"+"Away Team "+"Score"+aScore+teams[i]);
				
				if(week == 7)
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

}

