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
	
	
	public Result manageUsers()
	{
		List<User> userList = User.find.all();
		return ok(manageUsers.render(User.getLoggedIn(session().get("loginname")),userList));
	}
	
	public Result delUser(Long userid)
	{
		List<User> user = User.find.all();
		
		for(int i = 0 ; i < user.size();i++)
		{
			if(user.get(i).getid() == userid)
			{
			 user.get(i).delete();
				
			}
		}
		 return redirect("/admin");
	}
	
	public Result editUser(Long userid)
	  {
		  
        
		List<User> user = User.find.all();
		
		for(int i = 0 ; i < user.size();i++)
		{
			if(user.get(i).getid() == userid)
			{
				
				Form<User> manageUserForm = Form.form(User.class).fill(user.get(i));
				 return ok(manageFormUser.render(User.getLoggedIn(session().get("loginName")),manageUserForm,user.get(i)));
			}
			
			
		}
		
       return redirect("/");
    }
	
		public Result editPassword(Long userid)
	  {
		  
        
		List<User> user = User.find.all();
		
		for(int i = 0 ; i < user.size();i++)
		{
			if(user.get(i).getid() == userid)
			{
				
				Form<User> managePasswordForm = Form.form(User.class).fill(user.get(i));
				 return ok(editPassword.render(User.getLoggedIn(session().get("loginName")),managePasswordForm,user.get(i)));
			}
			
			
		}
		
       return redirect("/");
    }
	
	public Result submitEditPassword(Long id){
		 Form manageUserForm = Form.form().bindFromRequest();
		 List<User> user= User.find.all();
		User editUser = user.get(0);
		editUser.password = manageUserForm.password;
		
		 if( manageUserForm.hasErrors()){
            return redirect("/admin");

        }
		editUser = manageUserForm.get();
		
		for(int i = 0;i < user.size();i++)
		{
			if(user.get(i).getid() == id)
			{
				CalcSHA cs = new CalcSHA();
				String md = cs.calcPassword(editUser.password);
				editUser.password = md;
				user.get(i).password = editUser.password ;
				user.get(i).update();
				
				
         

        
     
		
        
			}
			
         
         
		}
         		
		
		flash("Success", "user"+manageUserForm.get().name+" has been updated");
		return redirect("/admin");
}
	
	public Result submitEditUser(Long id){
		 Form<User> manageUserForm = Form.form(User.class).bindFromRequest();
		 List<User> user= User.find.all();
		User editUser;
		 if( manageUserForm.hasErrors()){
            return redirect("/");

        }
		editUser = manageUserForm.get();
		for(int i = 0;i < user.size();i++)
		{
			if(user.get(i).getid() == id)
			{
				
				
				user.get(i).setName(editUser.getName());
				//user.get(i).setPassword(editUser.getPassword());
				user.get(i).update();
			}
			
         
         
		}
         		
		
		flash("Success", "user"+manageUserForm.get().name+" has been updated");
		return redirect("/admin");
}
}