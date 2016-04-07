package controllers.security;

import models.users.Login;
import models.users.SuperUser;
import play.data.Form;
import play.mvc.Controller;
            import play.mvc.Result;
            import views.html.login;

// Import required classes
// Import models

            public class LoginCtrl extends Controller {

                public Result login() {
                    // Pass a login form to the login view and render
                    return ok(login.render(Form.form(Login.class), SuperUser.getLoggedIn(session().get("suserid"))));
                }

                // Process the user login form
                public Result authenticate() {
                    // Bind form instance to the values submitted from the form
                    Form<Login> loginForm = Form.form(Login.class).bindFromRequest();

                    // Check for errors
                    // Uses the validate method defined in the Login class
                    if (loginForm.hasErrors()) {
                        // If errors, show the form again
                        return badRequest(login.render(loginForm, SuperUser.getLoggedIn(session().get("suserid"))));
                    }
                    else {
            // SuperUser Logged in sucessfully
            // Clear the existing session
            session().clear();
            // Store the logged in email in the session
            session("suserid", loginForm.get().suserid);
            
            // Check user type
            SuperUser u = SuperUser.getLoggedIn(loginForm.get().suserid);
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
