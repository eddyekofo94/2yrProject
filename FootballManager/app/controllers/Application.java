package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
import play.data.*;
import play.data.Form.*;


import play.*;


import views.html.*;
import models.*;

 public class Application extends Controller {

      public Result index() {

        return ok(index.render());
    }

    public Result fixtures() {
List<Fixtures> fixture = Fixtures.findAll();
        return ok(fixtures.render());
    }

    public Result leagueTable() {

        return ok(leagueTable.render());
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
            Form<User> registerForm = Form.form(User.class);

        return ok(register.render(registerForm));
    }


     public Result registerFormSubmit() {

         return ok("user registered");
     }

}