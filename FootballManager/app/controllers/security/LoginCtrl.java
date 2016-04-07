package controllers.security;

import models.users.Login;
import models.users.*;
import play.data.Form;
import play.mvc.Controller;
            import play.mvc.Result;
            import views.html.login;

// Import required classes
// Import models

            public class LoginCtrl extends Controller {

                public Result login() {
                    // Pass a login form to the login view and render
                    return ok(login.render(Form.form(Login.class), User.getLoggedIn(session().get("userid"))));
                }

                // Process the user login form
                public Result authenticate() {
                    // Bind form instance to the values submitted from the form
                    Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

                    // Check for errors
                    // Uses the validate method defined in the Login class
                    if (loginForm.hasErrors()) {
                        // If errors, show the form again
                        return badRequest(login.render(loginForm, User.getLoggedIn(session().get("userid"))));
                    }
                    else {
            // SuperUser Logged in sucessfully
            // Clear the existing session
            session().clear();
            // Store the logged in email in the session
            session("userid", loginForm.get().userid);
            
            // Check user type
            User u = User.getLoggedIn(loginForm.get().userid);
            // If admin - go to admin section
            if (u != null && "admin".equals(u.getUserType())) {
                return redirect(controllers.routes.Application.index());
            }
            
            // Return to home page
            return redirect(controllers.routes.Application.index());
        }
    }

    public Result logout() {
        session().clear();
        flash("success", "You've been logged out");
        return redirect(
                routes.LoginCtrl.login()
        );
    }


}
