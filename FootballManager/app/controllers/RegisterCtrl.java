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

public class RegisterCtrl extends Controller {


    // Display an empty form in the view
    public Result register() {

        // Instantiate a form object based on the User class
        Form<Manager> registerForm = Form.form(Manager.class);

        // Render the Add User View, passing the form object
        return ok(register.render(User.getLoggedIn(session().get("userID")),registerForm));   //, User.getLoggedIn(session().get("userName")))
    }

    // Handle the form data when a new user is submitted
    public Result registerFormSubmit() {

        //String saveImageMsg;

        // Create a user form object (to hold submitted data)
        // 'Bind' the object to the submitted form (this copies the filled form)
        
        Form<Manager> newRegisterForm = Form.form(Manager.class).bindFromRequest();

        // Check for errors (based on Product class annotations)
        if (newRegisterForm.hasErrors()) {
            // Display the form again
            return ok(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        }



         CalcSHA cs = new CalcSHA();

         Manager manager = newRegisterForm.get();
         String md = cs.calcPassword(manager.password);

         manager.password = md;
         // Team team = new Team(k,"2",3);
         // team =  user.RegisterUser(team);
         // user.save();
         // user.en(user.password);
         manager.save();

        flash("success", "Manager " + newRegisterForm.get().name + " has been registered");

        return redirect("/squad/0");
    }

}
