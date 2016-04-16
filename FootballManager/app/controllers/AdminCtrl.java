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
	public Result admin()
	{
		
		return ok(admin.render(User.getLoggedIn(session().get("loginname"))));
	}
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
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
	
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result manageUsers()
	{
		List<User> userList = User.find.all();
		return ok(manageUsers.render(User.getLoggedIn(session().get("loginname")),userList));
	}
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
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
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result editUser(Long userid)
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
	public Result submitEditUser(Long id){
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
				if(user.get(i).password !=  editUser.password)
				{
					
				CalcSHA cs = new CalcSHA();
				String md = cs.calcPassword(editUser.password);
				
				user.get(i).setPassword(md);
				}
				user.get(i).update();
			}
			
         
         
		}
         		
		
		flash("Success", "user"+manageUserForm.get().name+" has been updated");
		return redirect("/admin");
    }
    //Insures user is admin before allowing access
    @Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result teamDB() {
        //Creates a list of players
        List<Team> teams = Team.findAll();
        
        return ok(teamDB.render(User.getLoggedIn(session().get("loginname")), teams));
    }
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	  public Result addTeam(){
        Form<Team> addTeamForm = Form.form(Team.class);
         
        return ok(addTeam.render(User.getLoggedIn(session().get("loginname")),addTeamForm));
    }
    public boolean teamNameUsed(String name){
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
	public Result addTeamSubmit(){
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
         // Check for errors (based on Product class annotations)
        if (newTeamForm.hasErrors()) {
            //Display the form again
            return badRequest(addTeam.render(User.getLoggedIn(session().get("userID")),newTeamForm));
        }
        else if(teamNameUsed(newTeamForm.get().teamName) == true){
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
	public Result manageTeam()
	{
		List<Team> team = Team.find.all();
		Form<Team> manageTeamForm = Form.form(Team.class);
        return ok(manageTeam.render(User.getLoggedIn(session().get("loginname")),team));
	}
	//Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
	public Result delTeam(Long teamID)
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
    @With(CheckIfAdmin.class)
	public Result editTeam(Long teamID)
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
            return redirect("/");
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
         		
		
		flash("Success", "Team"+manageTeamForm.get().teamName+" has been updated");
		return redirect("/admin");
	}
    //Insures user is admin before allowing access
	@Security.Authenticated(Secured.class)
    @With(CheckIfAdmin.class)
    public Result manageTeamSubmit(){
		 Form<Team> newTeamForm = Form.form(Team.class).bindFromRequest();
		Team newTeam;
		newTeam = newTeamForm.get();
		newTeam.setTeamScore(0);
		newTeam.save();
		flash("SuccessTeam", "Team"+newTeamForm.get().teamName+" has been created");
		return redirect("/admin");
	}
}