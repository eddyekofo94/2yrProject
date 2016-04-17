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
    private User getCurrentUser() {
        User u = User.getLoggedIn(session().get("userid"));
        return u;
    }
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result admin()//renders admin page
	{	
		return ok(admin.render(User.getLoggedIn(session().get("loginname"))));
	}
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result addUserAdmin()//renders addUserPage 
	{
		Form<User> registerForm = Form.form(User.class);
		
		return ok(addUserAdmin.render(User.getLoggedIn(session().get("loginname")),registerForm));
	}
    
	    public Result registerFormSubmit() { //Process the registration of a user and saves it to the database

        Form<User> newRegisterForm = Form.form(User.class).bindFromRequest();

        //Check for errors (based on User class annotations)
        if (newRegisterForm.hasErrors()) {
            return ok(register.render(User.getLoggedIn(session().get("userID")),newRegisterForm));
        }

         CalcSHA cs = new CalcSHA(); //creates an object of the class that encrypts the password for the new user
         User admin = newRegisterForm.get();
         String md = cs.calcPassword(admin.password); //encrypts the password

         admin.password = md; //sets the users password to the encrypted version
		 Administrator a1 = new Administrator(admin);
         a1.save();

        flash("success", "Admin " + newRegisterForm.get().name + " has been registered");

        return redirect("/admin");
    }
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result manageUsers()//renders manageUser page
	{
		List<User> userList = User.find.all();
		return ok(manageUsers.render(User.getLoggedIn(session().get("loginname")),userList));
	}
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result delUser(Long userid) //deletes a user specified by the admin
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
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editUser(Long userid)//renders the edit user page for based in the user seleceted by the admin
	  {		       
		List<User> user = User.find.all();	
		for(int i = 0 ; i < user.size();i++)
		{
			if(user.get(i).getid() == userid)
			{				
				Form<User> manageUserForm = Form.form(User.class).fill(user.get(i));
				 return ok(manageFormUser.render(User.getLoggedIn(session().get("loginname")),manageUserForm,user.get(i)));
			}					
		}		
       return redirect("/");
    }
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result submitEditUser(Long id){ //Processes the form submitted from the editUser Page
	    Form<User> manageUserForm = Form.form(User.class).bindFromRequest();
	    List<User> user= User.find.all();
		User editUser;
		
		editUser = manageUserForm.get();
		for(int i = 0;i < user.size();i++)
		{
			if(user.get(i).getid() == id)
			{								
				user.get(i).setName(editUser.getName());
				user.get(i).setLoginName(editUser.getLoginName());
				//user.get(i).setPassword(editUser.getPassword());
				if(user.get(i).password !=  editUser.password)//if there is a new password encrypt it and save it
				{					
                    CalcSHA cs = new CalcSHA();
                    String md = cs.calcPassword(editUser.password);               
                    user.get(i).setPassword(md);
				}
				user.get(i).update();
			}			                
		}		
		flash("success", "user"+manageUserForm.get().name+" has been updated");
		return redirect("/admin");
    }
    
    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result teamDB() {//Render the teamDB page and pass in a list of teams
        //Creates a list of players
        List<Team> teams = Team.findAll();
        
        return ok(teamDB.render(User.getLoggedIn(session().get("loginname")), teams));
    }
    
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	  public Result addTeam(){ //Render the addTeam page
        Form<Team> addTeamForm = Form.form(Team.class);         
        return ok(addTeam.render(User.getLoggedIn(session().get("loginname")),addTeamForm));
    }
    
    public boolean teamNameUsed(String name){//Insures two team names arent the same
        boolean taken = false;
        List<Team> teams = Team.findAll();
        for(int i = 0;i < teams.size(); i++){
            if(teams.get(i).teamName.equalsIgnoreCase(name)){
                return true;
            }
        }
        return false;
    }
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result addTeamSubmit(){//Processes the form submitted by the addTeam page
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
         // Check for errors (based on Product class annotations)
        if (newTeamForm.hasErrors()) {
            //Display the form again
            return badRequest(addTeam.render(User.getLoggedIn(session().get("userID")),newTeamForm));
        }
        else if(teamNameUsed(newTeamForm.get().teamName) == true){//Insures two team names arent the same
            flash("error","Team name is already used please try again!");
            return badRequest(addTeam.render(User.getLoggedIn(session().get("userID")),newTeamForm));
        }
        else{
		Team newTeam;
		newTeam = newTeamForm.get();
		newTeam.setTeamScore(0);
		newTeam.save();
		flash("success", "Team "+newTeamForm.get().teamName+" has been created");
        }
		return redirect("/teamDB");
	}
    
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result manageTeam()//renders the manage team page and passes a list of teams
	{
		List<Team> team = Team.find.all();
		Form<Team> manageTeamForm = Form.form(Team.class);
        return ok(manageTeam.render(User.getLoggedIn(session().get("loginname")),team));
	}
    
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result delTeam(Long teamID)//Deletes a team specified by the admin
	{
		List<Team> team = Team.find.all();
		Team TeamToDelete;
		for(int i = 0 ; i < team.size();i++)
		{
			if(team.get(i).getTeamID() == teamID)
			{
			 team.get(i).delete();
				
			}
		}
		 return redirect("/manageTeam");
	}
    
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
	public Result editTeam(Long teamID)//Renders a form based on the team selected by the admin
	{	 
		List<Team> team = Team.find.all();
		for(int i = 0 ; i < team.size();i++)
		{
			if(team.get(i).getTeamID() == teamID)
			{	
				Form<Team> manageTeamForm = Form.form(Team.class).fill(team.get(i));
				 return ok(manageFormTeam.render(User.getLoggedIn(session().get("loginname")),manageTeamForm,team.get(i)));
			}		
		}
		
       return redirect("/");
    }
    
   //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editTeamSubmit(Long id){
		 Form<Team> manageTeamForm = Form.form(Team.class).bindFromRequest();
		 List<Team> team = Team.find.all();
		Team editTeam;
		 if( manageTeamForm.hasErrors()){
            return badRequest(manageFormTeam.render(User.getLoggedIn(session().get("userID")),manageTeamForm,manageTeamForm.get()));
        }
        if(manageTeamForm.get().userID != null){
            if(userIDFind(manageTeamForm.get().userID) == false){
                flash("error","User ID no found!");
                return badRequest(manageFormTeam.render(User.getLoggedIn(session().get("userID")),manageTeamForm,manageTeamForm.get()));
            }
        }
		editTeam = manageTeamForm.get();
		for(int i = 0;i < team.size();i++)
		{
			if(team.get(i).getTeamID() == id)
			{
				team.get(i).setTeamName(editTeam.getTeamName());
				team.get(i).setUserID(editTeam.getUserID());
				team.get(i).setTeamScore(0);
				team.get(i).update();
			}			                 
		}        			
		flash("success", "Team "+manageTeamForm.get().teamName+" has been updated");
		return redirect("/teamDB");
	}
    public boolean userIDFind(Long id){
        List<User> users = User.findAll();
        for(User u : users){
            if(u.getid() == id && u.getUserType().equals("manager")){
                return true;
            }
        }
        return false;
    }
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    public Result manageTeamSubmit(){
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
		Team newTeam;
		newTeam = newTeamForm.get();
		newTeam.setTeamScore(0);
		newTeam.save();
		flash("successTeam", "Team"+newTeamForm.get().teamName+" has been created");
		return redirect("/admin");
	}
}