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
}