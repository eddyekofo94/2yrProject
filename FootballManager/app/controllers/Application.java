package controllers;
import java.util.*;
import play.mvc.Controller;
import play.mvc.Result;
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

    public Result squad() {
        // get the list of team attributes
        List<Player> players = Player.findAll();
        return ok(squad.render(players));
    }
    
    public Result login() {

        return ok(login.render());
    }
    
    public Result register() {

        return ok(register.render());
    }
}