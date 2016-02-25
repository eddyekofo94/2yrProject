package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import play.*;


import views.html.*;


 public class Application extends Controller {

      public static  Result index() {

        return ok(index.render());
    }

    public static Result fixtures() {

        return ok(fixtures.render());
    }

    public static Result leagueTable() {

        return ok(leagueTable.render());
    }

    public static Result squad() {

        return ok(squad.render());
    }
    
    public static Result login() {

        return ok(login.render());
    }
    
    public static Result register() {

        return ok(register.render());
    }
}