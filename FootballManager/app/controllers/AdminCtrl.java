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


public class AdminCtrl extends Controller
{
	
	
	public Result admin()
	{
		
		return ok(admin.render(User.getLoggedIn(session().get("loginname"))));
	}
	
	
	public Result addUserAdmin()
	{
		Form<User> registerForm = Form.form(User.class);
		
		return ok(addUserAdmin.render(User.getLoggedIn(session().get("loginname")),registerForm));
	}
	
	
	
	
	    public Result registerFormSubmit() {

        //String saveImageMsg;

        // Create a user form object (to hold submitted data)
        // 'Bind' the object to the submitted form (this copies the filled form)
        
        Form<User> newRegisterForm = Form.form(User.class).bindFromRequest();

        // Check for errors (based on Product class annotations)
        //if (newRegisterForm.hasErrors()) {
            // Display the form again
            //return ok(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        //}



         CalcSHA cs = new CalcSHA();

         User admin = newRegisterForm.get();
         String md = cs.calcPassword(admin.password);

        admin.password = md;
         // Team team = new Team(k,"2",3);
         // team =  user.RegisterUser(team);
         // user.save();
         // user.en(user.password);
		 Administrator a1 = new Administrator(admin);
         a1.save();

        flash("success", "Admin " + newRegisterForm.get().name + " has been registered");

        return redirect("/admin");
    }
	
}