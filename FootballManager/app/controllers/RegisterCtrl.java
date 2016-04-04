package controllers;

import play.*;
import play.mvc.*;
import play.data.*;
import play.data.Form.*;
import play.data.validation.*;

import play.mvc.Http.Context;

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
        Form<User> registerForm = Form.form(User.class);

        // Render the Add User View, passing the form object
        return ok(register.render(User.getLoggedIn(session().get("userID")),registerForm));   //, User.getLoggedIn(session().get("userName")))
    }

    // Handle the form data when a new user is submitted
    public Result registerFormSubmit() {

        //String saveImageMsg;

        // Create a user form object (to hold submitted data)
        // 'Bind' the object to the submitted form (this copies the filled form)
        Form<User> newRegisterForm = Form.form(User.class).bindFromRequest();

        // Check for errors (based on Product class annotations)
        if (newRegisterForm.hasErrors()) {
            // Display the form again
            return ok(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        }
        /*
        Map<String,String> anyData = new HashMap();
        anyData.put("email", "bob@gmail.com");
        anyData.put("password", "secret");

        User user = userForm.bind(anyData).get();
        If you have a request available in the scope, you can bind directly from the request content:

        User user = userForm.bindFromRequest().get();
         */



        User user = newRegisterForm.get();
        // Team team = new Team(k,"2",3);
        // team =  user.RegisterUser(team);
        user.save();
        // team.save();

        flash("success", "User " + newRegisterForm.get().name + " has been registered");

        return redirect("/squad/0");
    }

}
