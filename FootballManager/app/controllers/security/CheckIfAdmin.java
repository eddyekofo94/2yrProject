package controllers.security;

import models.users.User;
import play.libs.F;
import play.mvc.Action;
import play.mvc.Http;
import play.mvc.Result;

/* - Docs -
https://alexgaribay.com/2014/06/16/authentication-in-play-framework-using-java/
https://www.playframework.com/documentation/2.2.x/JavaActionsComposition
*/

// Check if this is an admin user (before permitting an action)
public class CheckIfAdmin extends Action.Simple {
    
    // Functional Java which is executed concurrently
    // Promise represents a handle for the future result
    // Http.Context contains the current request - which will be allowed
    // only if the conditions here are met
    public F.Promise<Result> call(Http.Context ctx) throws Throwable {
        
        // Check if current user (in session) is an admin
        String loginname = ctx.session().get("loginname");
        if (loginname!= null) {
            User u = User.getLoggedIn(loginname);
            if ("admin".equals(u.getUserType())) {
                
                // SuperUser admin sp continue with the http request
                return delegate.call(ctx);
            }    
        }
        //Result unauthorized = Results.unauthorized("unauthorized");
        // Unauthorised - redirect to login page
        ctx.flash().put("error", "Admin Login Required.");
        return F.Promise.pure(redirect(routes.LoginCtrl.login()));
    }
}