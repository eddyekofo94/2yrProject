package models;

import java.util.*;
import javax.persistence.*;
import play.data.format.*;
import play.data.validation.*;
import com.avaje.ebean.*;


@Entity
public class User extends Model {

    @Id
    public int userID;

    @Constraints.Required
    public String password;

    @Constraints.Required
    public String name;

    @Constraints.Required
    public String loginName;
    @OneToOne
    public int teamID;

    public User() {

    }

    public User(int userID, String password, String name, String loginName) {
        this.userID = userID;
        this.password = password;
        this.name = name;
        this.loginName = loginName;
    }

    public static Finder<String, User> find = new Finder <String, User> (String.class, User.class); //Not Ure

    public static List<User> findAll(){

        return User.find.all();
    }

    public static User getLoggedIn(String userID){
        if(userID == null)
        return null;
        else
        return find.byId(userID);
    }
//    public static User authenticate(String email, String password){
//        return find.where().eq("email", email).eq("password", password).findUnique();
//    }

//    public static User getLoggedIn(String id){
//        if(id == null)
//            return null;
//
//        else
//            return find.byId(id);
//    }
}