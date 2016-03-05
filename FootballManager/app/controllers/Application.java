package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;

<<<<<<< HEAD
=======
//fixture upload imports
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
>>>>>>> 347b00272a2985f6a36c3fa5bbb02e4441ab0d09

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
    
    public Result login() {

        return ok(login.render());
    }
    
         //REGISTER!!!!!!!!!!!
    
    public Result register() {
<<<<<<< HEAD
=======
        
>>>>>>> 347b00272a2985f6a36c3fa5bbb02e4441ab0d09
            Form<User> registerForm = Form.form(User.class);

        return ok(register.render(registerForm));
    }
<<<<<<< HEAD


     public Result registerFormSubmit() {

         return ok("user registered");
     }

}
=======
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






>>>>>>> 347b00272a2985f6a36c3fa5bbb02e4441ab0d09
