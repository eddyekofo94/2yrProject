package models.users;

import java.util.*;
import javax.persistence.*;
import models.*;

import play.data.validation.*;
import com.avaje.ebean.*;


@Entity
// Map inherited classes to a single table
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
// This is a Customer of type admin
@DiscriminatorValue("manager")

// Customer inherits from the SuperUser class

public class Manager extends User {


//    @OneToOne
//    public Long teamID;

    // Customer has one basket.
    // Customer is the owner (forieng key will be added to Basket table)
    // All changes to Customer are cascaded.
    @OneToOne(mappedBy="manager", cascade = CascadeType.ALL)
    public Team team;



    public Manager() {

    }

    public Manager(String password, String name, String loginName) {
        super("sdasdqfsdf", password, name, loginName);
//        this.suserid = suserid;
//        this.password = password;
//        this.name = name;
//        this.loginname = loginname;
    }

//    public static Finder<String, User> find = new Finder<String, User>(String.class, User.class); //Not Ure

    //Generic query helper for entity SuperUser with unique id String
    public static Finder<String,User> find = new Finder<String,User>(User.class);

    public static List<User> findAll(){

        return User.find.all();
    }


    // Check if a user is logged in (by id - email address)
    public static User getLoggedIn(String id) {
        if (id == null)
            return null;
        else
            // Find user by id and return object
            return find.byId(id);
    }
public String getUserid()
{
   return  super.getid();
}

    // Check if a user is logged in (by id - email address)
//    public static User getLoggedIn(String id) {
//        if (id == null)
//            return null;
//        else
//            // Find user by id and return object
//            return find.byId(id);
//    }


//  public static User authenticate(String suserid, String password){
//       return find.where().eq("suserid", suserid).eq("password", password).findUnique();
//   }
//
//  public static User getLoggedIn(String id){
//     if(id == null)
//          return null;
//
//      else
//          return find.byId(id);
//   }
}
