package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;


@Entity
public class User extends Model {

    @Id
    public Long userID;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String loginName;
    
    @OneToOne
    @JoinColumn(name = "team_id")
    public Long team_id;
    
    public ArrayList <Team> team = new ArrayList<>();

    public User() {

    }

    public User(Long userID, String password, String name, String loginName) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
    }

    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class); //Not Ure

    public static List<User> findAll(){

        return User.find.all();
    }


    public static User authenticate(String userID, String password){
        return find.where().eq("userID", userID).eq("password", password).findUnique();
    }

    public static User getLoggedIn(String id){
        if(id == null)
            return null;

        else
            return find.byId(id);
    }
    public Long getTeamID(){
         return team_id;
     }

    public Long getUserID(){
         return userID;
     }
    
     public void RegisterUser(){
        for(Team t : Team.<Team>findAll()){
        this.team.add(t);
    }

    for(int i = 0;i < team.size();i++)
    {
        if(team.get(i).getUserID() == null)
        {
            
            this.team_id = team.get(i).getTeamID();
            break;
        }
    }
    }
}