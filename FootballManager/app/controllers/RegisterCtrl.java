package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;
import play.data.validation.*;
import play.mvc.Http.Context;
import models.users.*;
import play.mvc.Http.*;
import play.mvc.Http.MultipartFormData.FilePart;
import java.io.*;
import java.io.File;
import javax.activation.MimetypesFileTypeMap;
import org.im4java.process.ProcessStarter;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IMOperation;
import views.html.*;

// Import required classes
import java.util.ArrayList;
import java.util.List;

// http://superuser.com/questions/163818/how-to-install-rmagick-on-ubuntu-10-04
// http://im4java.sourceforge.net/

// Import models
import models.*;
import models.users.User;
import controllers.security.*;

public class RegisterCtrl extends Controller {

    // Display an empty form in the view
    public Result register() {
        // Instantiate a form object based on the User class
        Form<User> registerForm = Form.form(User.class);
        // Render the Add User View, passing the form object
        return ok(register.render(User.getLoggedIn(session().get("userID")),registerForm));   //, User.getLoggedIn(session().get("userName")))
    }

    // Handle the form data when a new user is submitted
    public Result registerFormSubmit() {
        int numberOfTraining = 3;    
        Form<User> newRegisterForm = Form.form(User.class).bindFromRequest();

        // Check for errors (based on Product class annotations)
        if (newRegisterForm.hasErrors()) {
            //Display the form again
            return badRequest(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        }
        else if(loginnameUsed(newRegisterForm.get().loginname) == true){
            flash("error","Login name is already used please try again!");
             return badRequest(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        }else{
            CalcSHA cs = new CalcSHA();  
            User manager = newRegisterForm.get();	 
            String md = cs.calcPassword(manager.password);
            manager.password = md;
            Manager m1 = new Manager(manager);
            m1.numberOfTraining = numberOfTraining;
            m1.save();
            if(assignTeam(m1) != null){
                 flash("success", "Manager " + newRegisterForm.get().name + " has been registered");            
            }else{
                m1.delete();
                flash("error","No teams to Manage at the moment please try later");
            }
        }
        return redirect("/squad/6");
    }

    //Check if login name is taken
    public boolean loginnameUsed(String name){
        boolean taken = false;
        List<User> users = User.findAll();
        for(int i = 0;i < users.size(); i++){
            if(users.get(i).loginname.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
	
	//assign a null team to a user 	
	public Long assignTeam(User manager)
	{
		Long output = null;
		ArrayList<Team> teams = new ArrayList();
          for(Team t : Team.<Team>findAll()) {
		     if(t.getUserID() == null)
			{
		      teams.add(t);
			  teams.get(0).setUserID(manager.getid());
			teams.get(0).update();
		return	teams.get(0).getTeamID();
			  
		    }
		}	
		
		
	return output;
		 
	}
}
